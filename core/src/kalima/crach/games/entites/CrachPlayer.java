package kalima.crach.games.entites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef;
import com.badlogic.gdx.utils.JsonValue;

import kalima.crach.games.Assest;
import kalima.crach.games.kalimaCrach;
import kalima.crach.games.control.controulMobile;
import kalima.crach.games.control.controulPc;
import kalima.crach.games.control.controuleClass;

public class CrachPlayer extends Crach {
	
    private controuleClass controule;

	public Entity interactEntity;
    private Joint currentJoint;
        
    public int Point;
    
    private int power =0;        

	public enum State {
	        CONTROL, MOVE_BOX
	    }
    public enum Interaction {
        NONE, BOX
    }   
    public State state = State.CONTROL;
    public Interaction interaction = Interaction.NONE;
    
	public box boxBumber;
	Vector2 Vectorforce;
	
	
	public CrachPlayer(int code) {
		this(code, 100, 400, 0);
	}
	public CrachPlayer(int code,float x,float y,float r) {
		this.CodeCrach= code;
		this.isMyplayer=true;
		defineEntity(x,y,r);
        setBounds(0, 0, 170 /kalimaCrach.PPM ,130 / kalimaCrach.PPM);      
        updateParameter(code);
        setTexture();
	}


	private void updateParameter(int code) {
        switch (Gdx.app.getType()) {
		case Android:
		case iOS:
			controule = new controulMobile(this);
			break;
		case Desktop :
		case WebGL:
        	controule = new controulPc(this);
		default:
			break;
		}
		JsonValue Value = Assest.ValueData.get("crach").get(String.valueOf(code));
		this.Vitass = Value.getInt("vitass");
		this.Force= Value.getFloat("force");
		this.rotation = Value.getFloat("rotation");
		this.fire = Value.getFloat("fire");
	}
	
	public void update(float dt) {
		controule.update(dt);
   }
	
	
	
    public void onContactStart(Entity otherEntity) {
    	
    	if(otherEntity instanceof box){
            if (interaction == Interaction.NONE) {
            	setInteraction(Interaction.BOX, otherEntity);
            }
    	}
    	else if(otherEntity instanceof Crystal) {
    		creator.SetDestoryEntity(otherEntity);
    		addScore(10);
    		
    	}else if(otherEntity instanceof Crach) {
    		AntyReaction(otherEntity);
    	}
    }
    
    public void onContactEnd(Entity otherEntity) {
        if ( otherEntity instanceof box) {
            if (interaction == Interaction.BOX && state == State.CONTROL) {
            	setInteraction(Interaction.NONE, null);
            }
        }
    }
    
    public void setInteraction( Interaction interaction,Entity interactEntity) {
        controule.setInteractionBox((interactEntity == null) ? false : true);
        this.interactEntity = interactEntity;
        this.interaction = interaction;

    }
    
    

    
    
    public void endInteractionWithBox(box box) {
    	if(power != 0) {
    		boxBumber =box;
    		Vectorforce = new Vector2(power*100f,power*100f);
    		Vectorforce.setAngleRad(b2body.getAngle());
    	}
        if (currentJoint == null) return;
        setState(State.CONTROL);
        setInteraction(Interaction.NONE ,null);

        world.destroyJoint(currentJoint);
        currentJoint = null;
    }

    public void startInteractionWithBox(box box) {
		if (currentJoint != null) return;
		addPower(0);
		boxBumber = null;
        setState(State.MOVE_BOX);
        Vector2 V =new  Vector2(40/kalimaCrach.PPM,40/kalimaCrach.PPM);
        V.setAngleRad(b2body.getAngle());
        V.add(getBody().getWorldCenter());
        		
		 
			PrismaticJointDef jointDef = new PrismaticJointDef();
			
			  jointDef.initialize(getBody(),box.getBody(),V, new Vector2(1,1));	
			  jointDef.motorSpeed = 1.0f; jointDef.enableMotor = true;
			  jointDef.lowerTranslation =0f;
			  jointDef.upperTranslation = 0f;
			  jointDef.enableLimit = true;


        currentJoint = world.createJoint(jointDef);
    }
    
    public void AntyReaction(Entity E) {
    	Vector2 V= b2body.getWorldCenter();
    	Vector2 force = new Vector2(Force*500f,Force*500f);
    	Vector2 chemain = new Vector2(V);
        chemain.sub(E.b2body.getWorldCenter());
        force.setAngleRad(chemain.angleRad());
        b2body.applyLinearImpulse(force,V, true);
    }
    public void TireFire() {
        creator.addEntity(new fire(getBody().getPosition(), b2body.getAngle(), Force));
    }
    
    public void setState(State state) {
        this.state = state;
    }
    

    public void addPower(int P) {
    	power += P; 
    	if(power > 200 || P == 0)  power = 0 ;
    	controule.getHub().upPowerCrach(power);
    }
    public void boxBumber(box box) {
    	if(power > 0) {    	
    	box.getBody().applyForceToCenter(Vectorforce, true);
    	addPower(-1);
    	}
    	else {
    		boxBumber = null;
    	}
    }
    
    public void addScore(int Ps) {
    	controule.getHub().updatePointScore(Point += Ps);
    }
    
	public boolean isActive() {
        if(b2body.getLinearVelocity().isZero(100) && (int)(b2body.getAngularVelocity()) == 0)
		return false;
        return true;
	}
	public controuleClass getControule() {
		return controule;
	}
}
