package kalima.crach.games.entites;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import kalima.crach.games.Assest;
import kalima.crach.games.Text;
import kalima.crach.games.kalimaCrach;
import kalima.crach.games.entites.box.State;
import net.dermetfan.gdx.physics.box2d.PositionController;
import net.dermetfan.gdx.physics.box2d.RotationController;

public class box extends Entity{
	
	private char lettercode ;
	private Text text;

	
	private static final int hieght  = 50;
    private static final int mass = 1;
    private static final int inertia = 90;
    private static final int AngularDamp = 1;
    private static final int  LinearDamp = 1;
    
    private static int force = 90;
    private static int derivative = 20;
    
    private static MassData data ;
    private PlaceBox Place;
    
    
    public PositionController.PD toPosition ;
    public RotationController.PD toRoration ; 

    
    public enum State {
        NONE, PLACE,  INTER ,INTER_PLAYERS
    }
    private  State state = State.NONE;
	public box(MapObject object) {
        Rectangle rect = ((RectangleMapObject) object).getRectangle();
		defineEntity((rect.getX() + rect.getWidth() / 2) /kalimaCrach.PPM, (rect.getY() + rect.getHeight() / 2) /kalimaCrach.PPM);
 		setTexture();
	}
	public box(float x,float y) {
		this(x, y, (float)(Math.random()*1.7));
	}
	public box(String id,float x,float y) {
		this(x, y);
		this.Id=id;
	}
	public box(float x,float y,float r) {
		setTexture();
		defineEntity(x,y,r);
	}
	public box(String id,float x,float y,float r) {
		 this(x, y, r);
         this.Id=id;
	}
	public void setTexture() {
 	    setRegion(Assest.boxActive);
        setOriginCenter();
        setZIndex(2);
	}
	@Override
	public void defineEntity(float X,float Y) {
        this.defineEntity(X, Y, (float)(Math.random()*1.7));
	}
	@Override
	public void defineEntity(MapObject object) {
        Rectangle rect = ((RectangleMapObject) object).getRectangle();
        this.defineEntity((rect.getX() + rect.getWidth() / 2) /kalimaCrach.PPM, (rect.getY() + rect.getHeight() / 2) /kalimaCrach.PPM);
	}
	@Override
	public void defineEntity(float X, float Y, float R) {
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();

        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(X,Y);
        bdef.angle = R;
        b2body = world.createBody(bdef);
        shape.setAsBox( hieght/kalimaCrach.PPM, hieght/kalimaCrach.PPM);
        fdef.shape = shape;
        fdef.filter.categoryBits = kalimaCrach.ALPHA_BOX;
        b2body.createFixture(fdef);
        b2body.setLinearDamping (LinearDamp);
        b2body.setAngularDamping(AngularDamp);
        
		b2body.setMassData(data); 
        b2body.setUserData(this);
        shape.dispose();
	}
 
      @Override
     public void update(float dt) {
//    	  if(creator.isOnLine()) {
//       if(isINTER_PLAYERS()){
//    	   boolean NONE =true;
//           if(!MathUtils.isEqual(getBody().getPosition().x,toPosition.getDestination().x,5 ) ||
//              !MathUtils.isEqual(getBody().getPosition().y,toPosition.getDestination().y,5 )) 
//                {
//        			toPosition.applyForceToCenter(this.getBody(),true);
//        			Gdx.app.log("test", " box moving "+id);
//        			NONE = false;
//                }
//           if( !MathUtils.isEqual(b2body.getAngle()%2*MathUtils.E , toRoration.getAngle().floatValue()%2*MathUtils.E, 0.1f)) {
//        			toRoration.applyTorque(getBody(), true);
//        			Gdx.app.log("test", " box rotation "+id);
//        			NONE = false;
//              }
//           if(NONE) {
//        	   state = State.NONE;
//        	   getBody().setAwake(false);
//           }
//       }
//       else  if(!b2body.getLinearVelocity().isZero(100) || (int)(b2body.getAngularVelocity()) != 0) {
//      		creator.BoxActive.put(id,StateBox.Move_Box);
//        }
//      } 
      if(getBody().isAwake())
    	  setRegion(Assest.boxActive);
      else 
    	  setRegion(Assest.boxNoActive);
      }
      @Override
      public void SetForceDeplace(float x,float y,float r){
			toPosition = new PositionController.PD(force,derivative,new Vector2(x, y));
			toRoration = new RotationController.PD(force, derivative, r);
      }
      @Override
    public void UpdateToDeplace(Vector2 toPosion, float toAngle) {
			toPosition.setDestination(toPosion);
			toRoration.setAngle(toAngle);
			setState(State.INTER_PLAYERS);
    }
	public boolean isEnabled() {
         switch( state){
        	 case  NONE :   		
        	 case PLACE :
        		 return true;
	   	default:
			return false;
         }            
	}
	public boolean isEnplace() {
        switch( state){
   	 case PLACE :
   		 return true;
  	default:
		return false;
    }
	}
	public boolean isINTER_PLAYERS() {
		if(state == State.INTER_PLAYERS) {
			return true;
		}
		return false;
	}
	
	public void setState(State S) {
		state = S;
	}
	
	public static void upMassBox() {
		data = new MassData();
        data.I=inertia;
        data.mass=mass;
	}

    public char getLettercode() {
		return lettercode;
	}

	public void setLettercode(char lettercode) {
		this.lettercode = lettercode;
		text.setText(String.valueOf(lettercode));
	}
	
	public PlaceBox getplace() {
           return Place;		
	}
    
	
    public void postDraw(Batch batch, OrthographicCamera camera) {
        text.draw(batch, getBody().getPosition().x,
              getBody().getPosition().y );
        
    }

	public static int getHieght() {
		return hieght;
	}
}
