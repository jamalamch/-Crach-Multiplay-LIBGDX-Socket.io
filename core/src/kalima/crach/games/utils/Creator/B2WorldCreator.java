package kalima.crach.games.utils.Creator;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;

import kalima.crach.games.kalimaCrach;
import kalima.crach.games.entites.Crach;
import kalima.crach.games.entites.CrachPlayer;
import kalima.crach.games.entites.Crystal;
import kalima.crach.games.entites.Entity;
import kalima.crach.games.entites.PlaceBox;
import kalima.crach.games.entites.box;
import kalima.crach.games.entites.Enimy.Enimy;
import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

public class B2WorldCreator implements Disposable{


	
	private World box2dWorld;
	private TiledMap tileMap; 
    private CrachPlayer player;
    private ArrayList<Entity> ENTITYS;    
    private ArrayList<Entity> ToDestroy;
    
	public enum TypeEntity {
        Box, Box_Char ,Crach,Crystal,PlaceBox,Enimy;
    }
	protected int nomberplayer = 1;

	public B2WorldCreator(World word,TiledMap tiled,int Code) {
        this.box2dWorld = word;
        this.tileMap = tiled;
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;
        
        Entity.setCreator(this);
        Entity.setWorld(box2dWorld);
        
        for(MapObject object : tileMap.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) /kalimaCrach.PPM, (rect.getY() + rect.getHeight() / 2) /kalimaCrach.PPM);
            body = box2dWorld.createBody(bdef);
            shape.setAsBox(rect.getWidth() / 2/kalimaCrach.PPM , rect.getHeight() / 2 /kalimaCrach.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
        ToDestroy = new ArrayList<Entity>();
        ENTITYS = new ArrayList<Entity>();
        this.player = new CrachPlayer(Code);
        ENTITYS.add(player);
	}
	
	public void addBoxTitle() {
        box.upMassBox();
        for(MapObject object : tileMap.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
        	  box B = new box(object);
              ENTITYS.add(B);
        }
	}
	public void addCrystalTitle() {
        for(MapObject object : tileMap.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
            Crystal C = new Crystal(object);
            ENTITYS.add(C);
        }
	}
	public void addEnimyTitle() {
        for(MapObject object : tileMap.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
              Enimy E = new Enimy(object, (float)(Math.random()*1.7));
              ENTITYS.add(E);
        }
	}
	public void addPlaceBoxTitle() {
		int size=0;
        for(MapObject object : tileMap.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
     	   PlaceBox B = new PlaceBox();
     	    int i=Integer.valueOf(object.getName());
     	    B.setPose(i);
            B.defineEntity(object);
            ENTITYS.add(B);
            size++;
         }
        PlaceBox.MotAndTentativeNull(nomberplayer,size/nomberplayer);
	}
	
    public void update(float dt) {
    	    destoryEntity();
            for(Entity E : ENTITYS)
            	E.update(dt);
    }
    public void drawEntity(Batch batch) {
        batch.begin();
        Box2DSprite.draw(batch, box2dWorld,true);
        batch.end();
        getPlayer().getControule().drawHub();
    }
    public void destoryEntity() {
   	 if(!ToDestroy.isEmpty()){
		 System.out.println("Destor");
		 Entity destor = ToDestroy.iterator().next();
		 destoryEntity(destor);
	  }
    }
    public void addEntity(Entity E) {
    	ENTITYS.add(E);
    }
    public void destoryEntity(Entity E) {
    	E.destroy();
    	ENTITYS.remove(E);
		ToDestroy.remove(E);
    }
    public void SetDestoryEntity(Entity E) {
    	ToDestroy.add(E);
    }
    
	public CrachPlayer getPlayer() {
		return player;
	}

	@Override
	public void dispose() {
		player.getControule().dispose();
        ToDestroy.clear();
        ENTITYS.clear();
	}        
}
