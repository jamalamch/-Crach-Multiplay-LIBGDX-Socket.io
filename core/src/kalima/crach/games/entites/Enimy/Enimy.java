package kalima.crach.games.entites.Enimy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;

import kalima.crach.games.Assest;
import kalima.crach.games.kalimaCrach;
import kalima.crach.games.control.controuleEnimy;
import kalima.crach.games.entites.Entity;
import kalima.crach.games.entites.fire;
import net.dermetfan.gdx.physics.box2d.PositionController;
import net.dermetfan.gdx.physics.box2d.RotationController;

public class Enimy extends Entity{
	private static final  int radius  = 50;
    private final float force = 2;
    
    private int life = 100;
    private controuleEnimy controule;
    private int derivative = 30;
    private int Vitass = 100;

    public float fire ;
    
    public PositionController.PD toPosition ;
    public RotationController.PD toRoration ;
    
    
    public Enimy(String id,float X, float Y, float R) {
        this(X, Y, R);
        this.Id=id;
    } 
    public Enimy(String id,float X, float Y) {
           this(id, X, Y, (float)(Math.random()*1.7));
    }
    public Enimy(float X, float Y, float R) {
        defineEntity(X, Y, R);
		toPosition = new PositionController.PD(Vitass,derivative,new Vector2(X, Y));
		toRoration = new RotationController.PD(Vitass, derivative, R);
		controule = new controuleEnimy(this, creator.getPlayer());
		setTexture();
    }
    public Enimy(MapObject object, float R) {
        Rectangle rect = ((RectangleMapObject) object).getRectangle();
        float X = (rect.getX() + rect.getWidth() / 2) /kalimaCrach.PPM;
        float Y = (rect.getY() + rect.getHeight() / 2) /kalimaCrach.PPM;
		defineEntity(X, Y,R);
		toPosition = new PositionController.PD(Vitass,derivative,new Vector2(X, Y));
		toRoration = new RotationController.PD(Vitass, derivative, R);
		controule = new controuleEnimy(this, creator.getPlayer());
        setTexture();
    }
	@Override
	public void defineEntity(float X, float Y, float R) {
        BodyDef bdef = new BodyDef();
        CircleShape shape = new CircleShape();
        FixtureDef fdef = new FixtureDef();

        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(X,Y);
        bdef.angle = R;
        b2body = world.createBody(bdef);
        shape.setRadius(radius);
        fdef.shape = shape;
        fdef.filter.categoryBits = kalimaCrach.ENIMY_BIT;	
        b2body.setAngularDamping(2);
        b2body.setLinearDamping (2);  
        b2body.createFixture(fdef);
        MassData M = new MassData();
        M.I=1;
        b2body.setMassData(M);
        b2body.setUserData(this);
        shape.dispose();
    }
    @Override
    public void setTexture() {
        setRegion(Assest.Crach.findRegion("crach"+5));
        setOriginCenter();
        setZIndex(3);
    }
    @Override
    public void update(float dt) {
        if(!MathUtils.isEqual(getBody().getPosition().x,toPosition.getDestination().x,5 ) ||
           !MathUtils.isEqual(getBody().getPosition().y,toPosition.getDestination().y,5 )) 
             {
     			toPosition.applyForceToCenter(this.getBody(),true);
     			Gdx.app.log("Enimy", " moving");
              }
           if( !MathUtils.isEqual((b2body.getAngle()) %2*MathUtils.E ,toRoration.getAngle().floatValue()%2*MathUtils.E, 0.1f)) {
     			toRoration.applyTorque(getBody(), true);
     			Gdx.app.log("Enimy", " rotation ");
              }
           controule.update(dt);
    }
    
    public void attack() {
        creator.addEntity(new fire(getBody().getPosition(), b2body.getAngle(), force));
    }
    
}
