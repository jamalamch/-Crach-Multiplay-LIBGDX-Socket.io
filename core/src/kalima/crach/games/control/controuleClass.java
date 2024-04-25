package kalima.crach.games.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import kalima.crach.games.kalimaCrach;
import kalima.crach.games.Screen.Tools.Hub;
import kalima.crach.games.entites.CrachPlayer;

public class controuleClass implements controule ,Disposable{

    private Hub hub;

    protected Stage stage;
	public ExtendViewport Viewhub;
	
	protected CrachPlayer player;
	protected Body b2body;
	protected Vector2 vitass ;
	protected Vector2 Center;
	protected boolean InteractionBox ;
	protected float Dt_time;
	
    public controuleClass(CrachPlayer player){
        Viewhub  = new ExtendViewport(kalimaCrach.Width/kalimaCrach.RATA,kalimaCrach.Height/kalimaCrach.RATA);
        stage = new Stage(Viewhub);
        Gdx.input.setInputProcessor(stage);
        this.player = player;
        this.b2body = player.getBody();
        this.vitass = new Vector2(player.Vitass,player.Vitass);
        hub = new Hub();
        stage.addActor(hub);
    }
	@Override
	public void update(float delta) {
		Dt_time += (Dt_time < 6) ?delta:0;
        hub.update(delta);
	}

    @Override
   public void setInteractionBox(boolean Inter) {
  	  InteractionBox = Inter;
   }

	@Override
	public void resize(int width, int height) {
		Viewhub.update(width, height, true);
	}
	
	@Override
	public Hub getHub() {
		return hub;
	}
	@Override
	public void dispose() {
         stage.dispose();		
	}
	@Override
	public void drawHub() {
        stage.draw();
        stage.act();	
	}

}
