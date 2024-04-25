package kalima.crach.games.connection;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.badlogic.gdx.Gdx;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import kalima.crach.games.entites.CrachPlayer;
import kalima.crach.games.utils.Creator.B2WorldCreator;


public class connectionServer extends Connection{
	
	private Socket socket;
    
	public connectionServer(B2WorldCreator creator, CrachPlayer player, float update_time) {
		super(creator, player, update_time); 
		}
	@Override
	public void Connecte() {
		connectSocket();
		configSocketEvents();
	}
	public void updateServer(float dt) {
		times +=dt;
		if(times >= update_times) {
		if(player.isActive()) {
			JSONObject data = new JSONObject();
			times =0;
			try {
				data.put("x",player.b2body.getPosition().x);
				data.put("y",player.b2body.getPosition().y);
				data.put("r",player.b2body.getAngle());
                socket.emit("playerMoved", data);
                Gdx.app.log("Sokit.io", " sending data Moving player  "+id );
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}
		if(!creator.BoxActive.isEmpty()) {
			String b = creator.BoxActive.keySet().iterator().next();
			JSONObject data = new JSONObject();
			times =0;
			switch(creator.BoxActive.get(b)) {
			case New_Box :
				try {
					data.put("Boxid",b);
					data.put("x",creator.getBox(b).b2body.getPosition().x);
					data.put("y",creator.getBox(b).b2body.getPosition().y);
					data.put("r",creator.getBox(b).b2body.getAngle());
					data.put("char", creator.getBox(b).getLettercode());
	                Gdx.app.log("Sokit.io", " sending data new Box  "+b);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			break;
			case Move_Box:
				try {
					data.put("Boxid",b);
					data.put("x",creator.getBox(b).b2body.getPosition().x);
					data.put("y",creator.getBox(b).b2body.getPosition().y);
					data.put("r",creator.getBox(b).b2body.getAngle());
	                socket.emit("moveBox", data);
	                Gdx.app.log("Sokit.io", " sending data Moveing Box  "+b);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			break;
			
			case Destor_Box:
				try {
					data.put("Boxid",b);
	                socket.emit("destorBox", data);
	                Gdx.app.log("Sokit.io", " sending data Remove Box  "+b);
				} catch (JSONException e) {
					e.printStackTrace();				
				}
			break;

			default : 
                Gdx.app.log(" null ", " defulse  ");
		}
			creator.BoxActive.remove(b);
		}
	}
	}
	public void connectSocket(){
		try {
			socket = IO.socket("http://localhost:8080").connect();
			sendingcreatConnection();
		} catch(Exception e){
            e.printStackTrace();
		}
	}
	public void configSocketEvents(){
		socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				creator.setOnLine(true);
				Gdx.app.log("SocketIO", "Connected");
			}
		}).on(Socket.EVENT_RECONNECT_ERROR, new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				Gdx.app.log("SocketIO", "NO Connection");
			}
		}).on("socketID", new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				JSONObject data = (JSONObject) args[0];
				try {
					id = data.getString("id");
					Gdx.app.log("SocketIO", "My ID: " + id);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}).on("newPlayer", new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				JSONObject data = (JSONObject) args[0];
				try {
					String playerid = data.getString("id");
					float x = ((Double) data.getDouble("x")).floatValue();
					float y = ((Double) data.getDouble("y")).floatValue();
					float r = ((Double) data.getDouble("r")).floatValue();
					int C=data.getInt("code");
					Gdx.app.log("SocketIO", "New Player Connect: " + playerid+" code "+C);
					if(playerid != id)
					       creator.putfriendlyPlayers(playerid,x,y,r,C);
				}catch(JSONException e){
					e.printStackTrace();
				}
			}
		}).on("playerDisconnected", new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				JSONObject data = (JSONObject) args[0];
				try {
					String playerid = data.getString("id");
					creator.removefriendlyPlayers(playerid);
					Gdx.app.log("SocketIO", " Player Deconnecte : " + playerid);
				}catch(JSONException e){
					e.printStackTrace();
				}
			}
		}).on("playerMoved", new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				JSONObject data = (JSONObject) args[0];
				try {
					String playerId = data.getString("id");
					float x = ((Double) data.getDouble("x")).floatValue();
					float y = ((Double) data.getDouble("y")).floatValue();
					float r = ((Double) data.getDouble("r")).floatValue();
					creator.updatePositionPlayers(playerId, x,y,r);
				}catch(JSONException e){
					e.printStackTrace();
				}
			}
		}).on("getPlayers", new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				JSONArray objects = (JSONArray) args[0];
				try {
					int size = objects.length();
					if(size == 0)
						creator.BeAdminisrationRoom(true);
					for(int i = 0; i < size; i++){
						float x = ((Double) objects.getJSONObject(i).getDouble("x")).floatValue();
						float y = ((Double) objects.getJSONObject(i).getDouble("y")).floatValue();
						float r = ((Double) objects.getJSONObject(i).getDouble("r")).floatValue();
						int Code = objects.getJSONObject(i).getInt("code");
						creator.putfriendlyPlayers(objects.getJSONObject(i).getString("id"), x, y, r,Code);
					}
				} catch(JSONException e){
					e.printStackTrace();
				}
			}
		}).on("newBox", new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				JSONObject data = (JSONObject) args[0];
				try {
					String Boxid = data.getString("Boxid");
					float x = ((Double) data.getDouble("x")).floatValue();
					float y = ((Double) data.getDouble("y")).floatValue();
					float r = ((Double) data.getDouble("r")).floatValue();
					char C = data.getString("char").charAt(0);
				
					creator.putNewBox(Boxid, x, y,r,C);
					
					Gdx.app.log("SocketIO", "New Box Connect: " + Boxid);
				}catch(JSONException e){
					e.printStackTrace();
				}
			}
		}).on("destorBox", new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				JSONObject data = (JSONObject) args[0];
				try {
					String Boxid = data.getString("Boxid");
					creator.removeBox(id);
					Gdx.app.log("SocketIO", "Destory  Box Connect: " + Boxid);

				}catch(JSONException e){
					e.printStackTrace();
				}
			}
		}).on("moveBox", new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				JSONObject data = (JSONObject) args[0];
				try {
					String Boxid = data.getString("Boxid");
					float x = ((Double) data.getDouble("x")).floatValue();
					float y = ((Double) data.getDouble("y")).floatValue();
					float r = ((Double) data.getDouble("r")).floatValue();
					creator.updateBoxPosition(Boxid, x, y, r);
					Gdx.app.log("SocketIO", "MOVED   Box Connect: " + Boxid);

				}catch(JSONException e){
					e.printStackTrace();
				}
			}
		}).on("getBoxes", new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				JSONArray objects = (JSONArray) args[0];
				try {
					int size = objects.length();
					if(size != 0)
						creator.BeAdminisrationRoom(false);
					
					for(int i = 0; i < size; i++){
						String boxId = objects.getJSONObject(i).getString("Boxid");
						float x = ((Double) objects.getJSONObject(i).getDouble("x")).floatValue();
						float y = ((Double) objects.getJSONObject(i).getDouble("y")).floatValue();
						float r = ((Double) objects.getJSONObject(i).getDouble("r")).floatValue();
						Character C =  Character.toChars( (objects.getJSONObject(i).getInt("char")))[0];
						creator.putNewBox(boxId, x, y, r,C);
		                Gdx.app.log("Sokit.io", " getting DATA for  Boxe   "+ boxId + " code " + C);
					}
	                Gdx.app.log("Sokit.io", " getting DATA for  Boxes leng  "+size);

				}catch(JSONException e){
					e.printStackTrace();
				}
			}
		});
	}
    public void sendingcreatConnection() {
		JSONObject data = new JSONObject();
		try {
			data.put("id",id);
			data.put("x",player.b2body.getPosition().x);
			data.put("y",player.b2body.getPosition().y);
			data.put("r",player.b2body.getAngle());
			data.put("code", player.getCodeCrach());
            socket.emit("newPlayer", data);
            Gdx.app.log("Sokit.io", " sending data creatConnection  "+player.getCodeCrach());
		} catch (JSONException e) {
              Gdx.app.log("Sokit.io", " erour sending DATA ");
		}
    }
    @Override
    public void DeConnecte() {
        socket.disconnect();
    	super.DeConnecte();
    }
}
