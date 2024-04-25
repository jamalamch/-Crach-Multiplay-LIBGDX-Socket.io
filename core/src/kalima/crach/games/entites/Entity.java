package kalima.crach.games.entites;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import kalima.crach.games.utils.Creator.B2WorldCreator;
import net.dermetfan.gdx.graphics.g2d.Box2DSprite;
import net.dermetfan.gdx.physics.box2d.PositionController;
import net.dermetfan.gdx.physics.box2d.RotationController;


public abstract class Entity extends Box2DSprite{
    public static World world;
	public static B2WorldCreator creator;
    public Body b2body;
    public String Id;
    
    
    public Entity() {
	}
	public void setTexture() {
	}
    public  void update(float dt) {
    }
    public void onContactStart(Entity otherEntity) {
    }
    public void onContactEnd(Entity otherEntity) {
    }
    public void destroy() {
    	world.destroyBody(b2body);
    }
    public  Body getBody() {
		return b2body;  	
    }
	public void defineEntity(MapObject object) {		
	}
	public void defineEntity(float X, float Y) {		
	}
	public void defineEntity(float X, float Y , float R ) {		
	}  
	
    public void SetForceDeplace(float x,float y,float r){
    }
    public void UpdateToDeplace(Vector2 toPosion,float toAngle) {
    	
    }
	public static void setCreator(B2WorldCreator creator) {
		Entity.creator = creator;
	}
	public static void setWorld(World world) {
		Entity.world = world;
	}
}
