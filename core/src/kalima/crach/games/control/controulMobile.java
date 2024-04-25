package kalima.crach.games.control;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import kalima.crach.games.Assest;
import kalima.crach.games.kalimaCrach;
import kalima.crach.games.entites.CrachPlayer;
import kalima.crach.games.entites.box;
import kalima.crach.games.entites.CrachPlayer.State;

public class controulMobile extends controuleClass {


	private Button buttonUp;
	private Button buttonDown;
	private Button buttonRIGHT;
	private Button buttonLEFT;
	private Button buttonForce;
	private Button buttonIteraction;

	public controulMobile(final CrachPlayer player) {
        super(player);
		buttonUp = new ImageButton(Assest.ButtonUp);
		buttonDown = new ImageButton(Assest.ButtonDown);
		buttonRIGHT = new ImageButton(Assest.ButtonRight);
		buttonLEFT = new ImageButton(Assest.ButtonLeft);
		buttonForce = new ImageButton(Assest.Buttonfire);
		buttonIteraction = new ImageButton(Assest.ButtonInteraction);
		buttonIteraction.setVisible(false);
		buttonIteraction.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(InteractionBox) {
				  box box = (box) player.interactEntity; 
		            if (box.isEnabled() && player.state == State.CONTROL) {
		            	player.startInteractionWithBox(box);
		                box.setState(kalima.crach.games.entites.box.State.INTER);
		           }else  if ( player.state == State.MOVE_BOX) {
		               box.setState(kalima.crach.games.entites.box.State.NONE);
		               player.endInteractionWithBox(box);           
		            }
			      }
			  }
		});
		buttonForce.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
    			if ( player.state == State.CONTROL){
                    player.TireFire();
			}
		}});
        Table table = new Table();
        table.bottom();
        table.defaults().pad(10/kalimaCrach.RATA).center();
        table.debug();
        table.setFillParent(true);
        table.add(buttonIteraction).colspan(2).center();
        table.add().expandX();
        table.add(buttonForce).colspan(2).center();
        table.row();
        table.add(buttonLEFT);
        table.add(buttonRIGHT);
        table.add().expandX();
        table.add(buttonUp);
        table.add(buttonDown);
        table.pack();
        stage.addActor(table);
        
		
	}
	@Override
	public void update(float delta) {
    	Center= b2body.getPosition();
    	vitass.setAngleRad(b2body.getAngle());

        	//boolean move = false;
            if (buttonUp.isPressed()) {
            	b2body.applyLinearImpulse(vitass,Center, true);
           //     move=true;
            }
            if (buttonDown.isPressed()) {
            	b2body.applyLinearImpulse(new Vector2(-vitass.x, -vitass.y).scl(0.3f),Center, true);
          //      move=true;
            }
            if (buttonRIGHT.isPressed()) {
                if(player.state == State.CONTROL) b2body.setAngularVelocity(-player.rotation);
                else b2body.setAngularVelocity(-player.rotation*player.Force);
             //   move=true;          
            }
            if (buttonLEFT.isPressed()) {
            	if(player.state == State.CONTROL) b2body.setAngularVelocity(player.rotation);
            	else b2body.setAngularVelocity(player.rotation*player.Force);
           //     move=true;
            }
            // addEffictation(V ,move) ;     
            if(InteractionBox) {
                final box box = (box) player.interactEntity;
                if(buttonForce.isPressed()){
        			if ( player.state == State.MOVE_BOX && player.boxBumber == null){
        				 player.addPower(2);
        			}
        		} 
                if(box.isINTER_PLAYERS()) {
                	player.AntyReaction(box);
                }
            }else if(player.boxBumber != null)
          	   player.boxBumber(player.boxBumber);
            
    super.update(delta);
	}
	@Override
	public void setInteractionBox(boolean Inter) {
        super.setInteractionBox(Inter);
		buttonIteraction.setVisible(Inter);
	}
}
