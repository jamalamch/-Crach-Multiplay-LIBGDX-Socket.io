package kalima.crach.games.utils.Creator;

import java.util.HashMap;
import java.util.Random;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import kalima.crach.games.entites.Crach;
import kalima.crach.games.entites.Crystal;
import kalima.crach.games.entites.Entity;
import kalima.crach.games.entites.box;
import kalima.crach.games.entites.box_Char;
import kalima.crach.games.entites.fire;
import kalima.crach.games.entites.Enimy.Enimy;

public class B2WorldCreatorOnline extends B2WorldCreator {

	protected Random random = new Random();
	protected HashMap<String, Entity> EntityMap;
	protected HashMap<String, StateEntity> EntityActive;

	private boolean onLine;

	public enum StateEntity {
		New_Box, Destor_Box, Move_Box,
		New_Player, Destor_Player, Move_Player,
		New_Box_Char,
		New_Crystal, Destor_Crystal,
		New_fire,
		New_Enimy, Destor_Enimy, Move_Enimy,
	}

	public B2WorldCreatorOnline(World word, TiledMap tiled, int Code) {
		super(word, tiled, Code);
	}

	@Override
	public void update(float dt) {
		super.update(dt);
		for(Entity E : EntityMap.values())
			E.update(dt);
	}

	public void putEntity(StateEntity State, String id, float x, float y, float r, int code) {
		if (!EntityMap.containsKey(id)) {
			EntityMap.put(id, creatEntity(State, id, x, y, r, code));
		}
	}

	public Entity getEntiy(String id) {
		return EntityMap.get(id);
	}

	public void updateEntityPosition(String id, float x, float y, float r) {
		if (EntityMap.containsKey(id)) {
			Entity E = getEntiy(id);
			E.UpdateToDeplace(new Vector2(x, y), r);
		}
	}

	public void destoryEntity(StateEntity State, String id) {
		if (EntityMap.containsKey(id)) {
			EntityMap.get(id).destroy();
			EntityMap.remove(id);
		}
	}



	public Entity creatEntity(StateEntity State, String id, float x, float y, float r, int C) {
		Entity E;
		switch (State) {
		case New_Box:
			E = new box(id, x, y, r);
			break;
		case New_Player:
			E = new Crach(C, x, y, r);
			nomberplayer ++;
			break;
		case New_Box_Char:
			char c = (char) C;
			E = new box_Char(id, x, y, r, c);
			break;
		case New_Crystal:
			E = new Crystal(id, x, y);
			break;
		case New_Enimy:
			E = new Enimy(id, x, y, r);
		case New_fire:
			E = new fire(new Vector2(x, y), r);
			break;
		default:
			return null;
		}
		E.SetForceDeplace(x, y, r);
		
		return E;
	}

	public Entity creatEntity(StateEntity State, String id, float x, float y) {
		Entity E;
		switch (State) {
		case New_Box:
			E = new box(id, x, y);
			break;
		case New_Crystal:
			E = new Crystal(id, x, y);
			break;
		case New_Enimy:
			E = new Enimy(id, x, y);
		default:
			return null;
		}
		return E;
	}

	public HashMap<String, StateEntity> getEntityActive() {
		return EntityActive;
	}
    
	public HashMap<String, Entity> getEntityMap() {
		return EntityMap;
	}

	public boolean isOnLine() {
		return onLine;
	}

	public void setOnLine(boolean onLine) {
		this.onLine = onLine;
	}
    public boolean isEntityExist(String id) {
    	return EntityMap.containsKey(id);
    }


}
