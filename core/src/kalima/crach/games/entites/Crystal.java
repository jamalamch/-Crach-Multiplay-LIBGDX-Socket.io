package kalima.crach.games.entites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;

import kalima.crach.games.Assest;
import kalima.crach.games.kalimaCrach;

public class Crystal extends Entity{

	private static  int hieght  = 20;
	private Animation<TextureRegion> animation;
	private float stateTimer = 1;

	public Crystal(String id,float x, float y) {
        this(x, y);
        this.Id=id;
	}
	public Crystal(float x, float y) {
		Array<TextureRegion> array = new Array<TextureRegion>(TextureRegion.split(Assest.Crystal, 16, 16)[0]);
		animation = new Animation<TextureRegion>(0.1f,array);
		defineEntity(x,y);
        setTexture();
	}

	public Crystal(MapObject object) {
        Rectangle rect = ((RectangleMapObject) object).getRectangle();
		Array<TextureRegion> array = new Array<TextureRegion>(TextureRegion.split(Assest.Crystal, 16, 16)[0]);
		animation = new Animation<TextureRegion>(0.1f,array);
		defineEntity((rect.getX() + rect.getWidth() / 2) /kalimaCrach.PPM, (rect.getY() + rect.getHeight() / 2) /kalimaCrach.PPM);
        setTexture();
	}

	public void setTexture() {
    	setRegion(animation.getKeyFrame(stateTimer, true));
        setOriginCenter();
        setZIndex(3);
	}
	
	@Override
	public void defineEntity(float X,float Y) {
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set(X,Y);
        float angel = (float)(Math.random()*1.7); 
        bdef.angle = angel;
        b2body = world.createBody(bdef);
        shape.setAsBox( hieght/kalimaCrach.PPM, hieght/kalimaCrach.PPM);
        fdef.isSensor = true;
        fdef.shape = shape;
        fdef.filter.categoryBits = kalimaCrach.CRYSTAL_BIT;
        b2body.createFixture(fdef);
        b2body.setUserData(this);
        shape.dispose();
        setRotation(getRotation()-45);
        stateTimer =angel;
	}
    @Override
    public void update(float dt) {
    	stateTimer +=dt;
    	setRegion(animation.getKeyFrame(stateTimer, true));
    }
}
