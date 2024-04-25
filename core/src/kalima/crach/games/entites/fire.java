package kalima.crach.games.entites;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import kalima.crach.games.kalimaCrach;

public class fire extends Entity{
	private static final  int radius  = 5;
	private float timeLief = 10;
	private float danger;
	private Vector2 Direction;
	private Vector2 LongOfCente = new Vector2(60,60);
	
    public fire(Vector2 position,float r) {
           this(position, r, 4);
   }
	
    public fire(Vector2 position,float r,float vitass) {
    	 this.Direction = new Vector2(vitass*150, vitass*150);
    	 this.Direction.setAngleRad(r);
    	 this.LongOfCente.setAngleRad(r);
    	 this.LongOfCente.add(position);
         defineEntity(LongOfCente.x, LongOfCente.y);
         setTexture();
     	 b2body.applyLinearImpulse(Direction, getBody().getPosition(), true);
    }
    @Override
    public void setTexture() {
    	Pixmap P = new Pixmap(radius,radius,Pixmap.Format.RGB888);
    	P.setColor(Color.RED);
    	setTexture(new Texture(P));
        setOriginCenter();
        setZIndex(4);
    }
    @Override
	public void defineEntity(float X, float Y) {
        BodyDef bdef = new BodyDef();
        CircleShape shape = new CircleShape();
        FixtureDef fdef = new FixtureDef();

        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(X,Y);
        bdef.bullet=true;
        b2body = world.createBody(bdef);
        shape.setRadius(radius);
        fdef.shape = shape;
        fdef.restitution =0.95f;
        fdef.filter.categoryBits = kalimaCrach.FIRE_BIT;	
        b2body.createFixture(fdef);
        b2body.setUserData(this);
        shape.dispose();
        }
    @Override
    public void update(float dt){
      timeLief -= dt;
      if(timeLief<0){
    	  creator.SetDestoryEntity(this);
      }
    }
}
