package kalima.crach.games.entites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import kalima.crach.games.Assest;
import kalima.crach.games.kalimaCrach;
import net.dermetfan.gdx.physics.box2d.PositionController;
import net.dermetfan.gdx.physics.box2d.RotationController;

public class Crach extends Entity{
	 
    
    protected int CodeCrach =1;
    

	int mass = 60;
    int inertia = 50;
    int AngularDamp = 3;
    int LinearDamp = 2;
   
    public float Force = 1;
    public float rotation = 1;
    public int Vitass = 450;
    public float fire ;
     
    int derivative = 30;
     
    public PositionController.PD toPosition ;
    public RotationController.PD toRoration ; 
    private ParticleEffect Effictation;
  
    protected boolean isMyplayer;
    
    
    
	
    
    public Crach() {
    	super();
    }
	public Crach(int code) {
		this(code, 100, 400, 0);
	}
	public Crach(int code,float x,float y,float z) {
		CodeCrach= code;
		defineEntity(x,y,z);
        setTexture();
	}
  
	public void setTexture() {
        setRegion(Assest.Crach.findRegion("crach"+CodeCrach));
        setOriginCenter();
        setZIndex(3);
	}
	
	public void update(float dt) {
		//boolean move = false;
        if(!MathUtils.isEqual(getBody().getPosition().x,toPosition.getDestination().x,4 ) ||
           !MathUtils.isEqual(getBody().getPosition().y,toPosition.getDestination().y,4 )) 
        {
			toPosition.applyForceToCenter(this.getBody(),true);
			Gdx.app.log("test", " moving");
			//move=true;
       }
      if( !MathUtils.isEqual((b2body.getAngle()) %2*MathUtils.E ,toRoration.getAngle().floatValue()%2*MathUtils.E, 0.005f)) {
			toRoration.applyTorque(getBody(), true);
			Gdx.app.log("test", " rotation ");
      }	
      }
    
	@Override
	public void defineEntity(float X,float Y,float R) {
        BodyDef bdef = new BodyDef();
        bdef.position.set(X /kalimaCrach.PPM, Y/kalimaCrach.PPM);
        bdef.angle=R;
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        
        CircleShape shape = new CircleShape();
        shape.setRadius(60/kalimaCrach.PPM);
        fdef.shape = shape; 
        if(!isMyplayer) {
        	    fdef.isSensor=true;
        		fdef.filter.categoryBits=kalimaCrach.FRENDS_PLAY;
    	        fdef.filter.maskBits=kalimaCrach.CRACH_BIT|kalimaCrach.HEAD_CRACH|kalimaCrach.LAMAS_CRACH ;
        }
        else {
         fdef.filter.categoryBits=kalimaCrach.CRACH_BIT;
         fdef.filter.maskBits=kalimaCrach.ALPHA_BOX|kalimaCrach.CRACH_BIT|kalimaCrach.HEAD_CRACH|kalimaCrach.CRYSTAL_BIT |kalimaCrach.FRENDS_PLAY;
        }
        b2body.createFixture(fdef);  
		shape.dispose();
        
        fdef = new FixtureDef();
		PolygonShape shape1 = new PolygonShape();
		shape1.setAsBox(20/kalimaCrach.PPM, 60/kalimaCrach.PPM,new Vector2(60/kalimaCrach.PPM,0),0);
		fdef.shape = shape1;
		if(!isMyplayer) {
	        fdef.isSensor=true;
	        fdef.filter.categoryBits=kalimaCrach.FRENDS_PLAY;
	        fdef.filter.maskBits=kalimaCrach.CRACH_BIT|kalimaCrach.HEAD_CRACH|kalimaCrach.LAMAS_CRACH ;
		}
		else
		fdef.filter.categoryBits=kalimaCrach.HEAD_CRACH;
		b2body.createFixture(fdef);		
		shape1.dispose();
		
        fdef = new FixtureDef(); 

		shape1 = new PolygonShape();
		shape1.setAsBox(2/kalimaCrach.PPM, 50/kalimaCrach.PPM,new Vector2(80/kalimaCrach.PPM,0),0);
		fdef.shape = shape1;
		if(!isMyplayer) {
	        fdef.isSensor=true;
	        fdef.filter.categoryBits=kalimaCrach.FRENDS_PLAY;
	        fdef.filter.maskBits=kalimaCrach.CRACH_BIT|kalimaCrach.HEAD_CRACH|kalimaCrach.LAMAS_CRACH ;
		}
		else {
        fdef.filter.categoryBits = kalimaCrach.LAMAS_CRACH;
        fdef.filter.maskBits=kalimaCrach.ALPHA_BOX|kalimaCrach.CRACH_BIT|kalimaCrach.HEAD_CRACH|kalimaCrach.CRYSTAL_BIT | kalimaCrach.FRENDS_PLAY;
		}
		b2body.createFixture(fdef);		
		
		shape1.dispose();
		
        b2body.setAngularDamping(AngularDamp);
        b2body.setLinearDamping (LinearDamp);   
		MassData data = new MassData();
        data.I=inertia;
        data.mass=mass;
		b2body.setMassData(data); 		
		b2body.setUserData(this);		
		
	}
		public void defineEntity(float X,float Y,float R) {
        BodyDef bdef = new BodyDef();
        bdef.position.set(X , Y);
        bdef.linearDamping=2;
        bdef.angularDamping=3;
        bdef.angle=R;
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(widthBody/2, DstBetTr+HieghtTr/2);
        fdef.shape = shape;
        if(!isMyplayer) {
        	    fdef.isSensor=true;
        		fdef.filter.categoryBits=CrachGame.FRENDS_PLAY;
    	        fdef.filter.maskBits=CrachGame.CRACH_BIT|CrachGame.LAMAS_CRACH ;
        }
        else {
         fdef.filter.categoryBits=CrachGame.CRACH_BIT;
        }
        b2body.createFixture(fdef);  
		shape.dispose();

        fdef = new FixtureDef(); 

		shape = new PolygonShape();
		shape.setAsBox(WidthPos/2, HieghtPos/2,new Vector2(WidthPos/2+WidthGun/2,0),0);
		fdef.shape = shape;
		if(!isMyplayer) {
	        fdef.isSensor=true;
	        fdef.filter.categoryBits=CrachGame.FRENDS_PLAY;
	        fdef.filter.maskBits=CrachGame.CRACH_BIT|CrachGame.LAMAS_CRACH ;
		}
		else {
        fdef.filter.categoryBits = CrachGame.LAMAS_CRACH;
		}
		b2body.createFixture(fdef);		
		
		shape.dispose();
		MassData data = new MassData();
        data.I=5;
        data.mass=5;
	    b2body.setMassData(data); 		
		b2body.setUserData(this);		
		
	}
	@Override
	public void SetForceDeplace(float x,float y, float r) {
		toPosition = new PositionController.PD(Vitass,derivative,new Vector2(x, y));
		toRoration = new RotationController.PD(Vitass, derivative, r);
	}
	@Override
	public void UpdateToDeplace(Vector2 toPosion, float toAngle) {
		toPosition.setDestination(toPosion);
		toRoration.setAngle(toAngle);   
	}
	
	public void addEffictation(Vector2 position , boolean move) {
		Vector2 P = new Vector2(40,40);
		P.setAngleRad(b2body.getAngle());
		position.sub(P);
		Effictation.setPosition(position.x,position.y);
		if(Effictation.isComplete() && move)
			Effictation.reset(move);
	}
	@Override
	public void draw(Batch batch, Body body) {
		//drawEffictation(batch,Gdx.graphics.getDeltaTime());
		super.draw(batch, b2body);
	}
	public void drawEffictation(Batch spriteBatch,float delta) {
		Effictation.draw(spriteBatch, delta);
	}
	
    public int getCodeCrach() {
		return CodeCrach;
	}
}
