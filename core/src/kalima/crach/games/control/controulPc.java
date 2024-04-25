package kalima.crach.games.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import kalima.crach.games.entites.CrachPlayer;
import kalima.crach.games.entites.box;
import kalima.crach.games.entites.CrachPlayer.State;

public class controulPc extends controuleClass{

    public controulPc(CrachPlayer player) {
               super(player);
    }
    
	@Override
	public void update(float delta) {
    	Center= b2body.getPosition();
    	vitass.setAngleRad(b2body.getAngle());

        	//boolean move = false;
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            	b2body.applyLinearImpulse(vitass,Center, true);
           //     move=true;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            	b2body.applyLinearImpulse(new Vector2(-vitass.x, -vitass.y).scl(0.3f),Center, true);
          //      move=true;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) ) {
                if(player.state == State.CONTROL) b2body.setAngularVelocity(-player.rotation);
                else b2body.setAngularVelocity(-player.rotation*player.Force);
             //   move=true;          
            }
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            	if(player.state == State.CONTROL) b2body.setAngularVelocity(player.rotation);
            	else b2body.setAngularVelocity(player.rotation*player.Force);
           //     move=true;
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.F)){
    			if ( player.state == State.CONTROL && Dt_time > player.fire){
                        player.TireFire();
                        Dt_time = 0;
    			}
    		} 
            // addEffictation(V ,move) ;     
            if(InteractionBox) {
                final box box = (box) player.interactEntity;
                if(Gdx.input.isKeyPressed(Input.Keys.F)){
        			if ( player.state == State.MOVE_BOX && player.boxBumber == null){
        				 player.addPower(2);
        			}
        		} 
                if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {	
                    if (box.isEnabled() && player.state == State.CONTROL) {
                    	player.startInteractionWithBox(box);
                        box.setState(kalima.crach.games.entites.box.State.INTER);
                   }else  if ( player.state == State.MOVE_BOX) {
                       box.setState(kalima.crach.games.entites.box.State.NONE);
                       player.endInteractionWithBox(box);           
                    }
               }
                if(box.isINTER_PLAYERS()) {
                	player.AntyReaction(box);
                }
            }
            else if(player.boxBumber != null)
             	   player.boxBumber(player.boxBumber);
            
            super.update(delta);
    }
}
