package kalima.crach.games.utils;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;

import kalima.crach.games.kalimaCrach;
import kalima.crach.games.MotAndChar.Arabicchar;
import kalima.crach.games.entites.Crach;
import kalima.crach.games.entites.Crystal;
import kalima.crach.games.entites.Entity;
import kalima.crach.games.entites.PlaceBox;
import kalima.crach.games.entites.box;
import kalima.crach.games.entites.box.State;
import kalima.crach.games.entites.fire;
import net.dermetfan.gdx.math.MathUtils;

public class B2WorldCreator implements Disposable{
	private World box2dWorld;
	private TiledMap tileMap; 
	
	private  Random random = new Random();
	
	private boolean CreatorRoom ;
	private boolean onLine;
	
    private HashMap<String,PlaceBox> ZONNEBOX;
    
    private ArrayList<Crystal> CRYSTALS;
    private ArrayList<fire> FIRE;
    
	public enum StateBox {
        New_Box, Destor_Box ,Move_Box
    }
	
    private HashMap<String,box> BOXES;
    public  HashMap<String,StateBox> BoxActive;
    private HashMap<String,Crach> friendlyPlayers;
    
    private ArrayList<Entity> ToDestroy;
    private int numberBox = 0;
    private int numberCry = 0;

    private int nomberplayer = 1;
    private int BoondsX,BoondsY;

        public B2WorldCreator(World word,TiledMap tiled,float X,float Y) {
                   this.box2dWorld = word;
                   this.tileMap = tiled;
                   this.BoondsX=(int)X;
                   this.BoondsY=(int)Y;
                   BodyDef bdef = new BodyDef();
                   PolygonShape shape = new PolygonShape();
                   FixtureDef fdef = new FixtureDef();
                   Body body;
                   
                   BOXES = new HashMap<String, box>();
                   ZONNEBOX = new HashMap<String, PlaceBox>();
                   CRYSTALS = new ArrayList<Crystal>();
                   FIRE = new ArrayList<fire>();
                   friendlyPlayers = new HashMap<String, Crach>();
                   BoxActive = new HashMap<String, B2WorldCreator.StateBox>();
                   ToDestroy = new ArrayList<Entity>();
             //      Entity.setCreator(this);
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
                   
                   box.upMassBox();
                   int J=0;
                   for(MapObject object : tileMap.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
                         box B = new box(String.valueOf(J++));
                         B.setLettercode(Arabicchar.GetCharArabic());
                         B.defineEntity(object);
                         BOXES.put(String.valueOf(J++),B);
                   }
                   
                   
                   
                   for(MapObject object : tileMap.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
                	   PlaceBox B = new PlaceBox();
                	   int i=Integer.valueOf(object.getName());
                	   B.setPose(i);
                       B.defineEntity(object);
                       ZONNEBOX.put(String.valueOf(i),B);
                    }
                   PlaceBox.MotAndTentativeNull(nomberplayer,ZONNEBOX.size()/nomberplayer);
                                    
           
         }
         public void update(float dt) {
        	 for(PlaceBox PB : ZONNEBOX.values()) {
        		 PB.update(dt);
        	 }
        	 for(Crach C : friendlyPlayers.values()) {
        		 C.update(dt);
        	 }
         	for(box BX : BOXES.values()) {
   			     BX.update(dt); 
         	}
         	for(Crystal C : CRYSTALS)
         		C.update(dt);
         	for(fire F : FIRE)
         		F.update(dt);
        	if (CRYSTALS.size() < numberCry) {
        			 creatCry();
            }
        	else if(CreatorRoom && BOXES.size() < numberBox ){
                   creatBox(String.valueOf(BOXES.size()+1));
            } 
        	 if(!ToDestroy.isEmpty()) {
        		 System.out.println("Destor");
        		 Entity destor = ToDestroy.iterator().next();
        		 if(destor instanceof Crystal) {
        			 destoryCry((Crystal) destor); 
        		 }
        		 if(destor instanceof fire) {
        			 destorfire((fire)destor);
        		 }
        	  }

         }

		public Collection<box> getBOXES() {
			return BOXES.values();
		}
	    public void removeAllBoxPlace(int Place){
       	 for(PlaceBox B : ZONNEBOX.values()) {
    		 if(B.getPlace() == Place)
    			 destroyBox(B.disposeInteract(B.interactEntity));
       	 }
	    }
         
	    public void destroyBox(box b) {
	    	String id=b.id;
	    	b.destroy();
	    	BOXES.remove(id);
	    	creatBox(id);
	    }
	    public void destoryCry(Crystal C) {
	    	C.destroy();
	    	CRYSTALS.remove(C);
            ToDestroy.remove(C);
	    
	    }
	    public void destorfire(fire F) {
	    	F.destroy();
	    	FIRE.remove(F);
	    	ToDestroy.remove(F);
	    }
	    public void SetDestoryEntity(Entity E) {
	    	ToDestroy.add(E);
	    }
	    
		public void creatBox(String i) {
			int x=random.nextInt(BoondsX-box.getHieght())+box.getHieght()/2;
			int y=random.nextInt(BoondsY-box.getHieght())+box.getHieght()/2;
            box B = new box(i,x,y,Arabicchar.GetCharArabic());
            BOXES.put(i,B);
            if(onLine) {
            BoxActive.put(i,StateBox.New_Box);
            B.BoxOnline(x, y, B.getBody().getAngle());
            }
		}
		public box creatBox(String id,float x,float y,float r,char C) {
            box B = new box(id,x,y,r,C);
            if(onLine) {
            B.BoxOnline(x, y,r);
            }
            return B;
		}
		
		private void creatCry() {
			int x=random.nextInt(BoondsX-box.getHieght())+box.getHieght()/2;
			int y=random.nextInt(BoondsY-box.getHieght())+box.getHieght()/2;
			Crystal C = new Crystal(x,y);
            CRYSTALS.add(C);
		}
		
		public void putfriendlyPlayers(String id,int C) {
			if(!isPlayerExist(id))
			friendlyPlayers.put(id, new Crach(C));
		}
		public void putfriendlyPlayers(String id,float x,float y,float r,int C) {
			if(!isPlayerExist(id))
			friendlyPlayers.put(id, new Crach(C,x,y,r));
		}
		public void removefriendlyPlayers(String id) {
			if(isPlayerExist(id)){	
			getfriendlyPlayers(id).destroy();
			friendlyPlayers.remove(id);
			}
		}
		public Crach getfriendlyPlayers(String id) {
			return friendlyPlayers.get(id);
		}
		public void updatePositionPlayers(String id,float x,float y,float r) {
			if(isPlayerExist(id)) {
				getfriendlyPlayers(id).toPosition.setDestination(new Vector2(x, y));
		      	getfriendlyPlayers(id).toRoration.setAngle(r);
			}
		}
		public boolean isPlayerExist(String id) {
			if(friendlyPlayers.containsKey(id))
				return true;
			return false;
		}
		
		
		public void putNewBox(String id,float x,float y,float r,char C) {
		    if(!BOXES.containsKey(id)) {
		        	BOXES.put(id, creatBox(id,x, y, r,C));
		    }
		}

		public box getBox(String id) {
				return BOXES.get(id);
		}
		public void updateBoxPosition(String id,float x,float y,float r) {
			if(getBox(id) != null) {
				     getBox(id).toPosition.setDestination(new Vector2(x, y));
				     getBox(id).toRoration.setAngle(r);
				     getBox(id).setState(State.INTER_PLAYERS);
			}
		}
		public void removeBox(String id) {
			if(BOXES.get(id) != null) {
                  destroyBox(BOXES.get(id));
			}
		}
		public void addFire(Vector2 position,float r,float vitass) {
			FIRE.add(new fire(position, r, vitass));
		}
		public boolean hasBoxActive() {
			if(BoxActive.size() != 0 ) {
				return true;
			}
 			return false;
		}
		private void addAlltoBoxesActive() {
			if(CreatorRoom) {
				for(box b : BOXES.values())
					BoxActive.put(b.id, StateBox.New_Box);
			}
		}
		public void BeAdminisrationRoom(boolean b) {
			CreatorRoom = b;
		}
		public boolean isOnLine() {
			return onLine;
		}
		public void setOnLine(boolean onLine) {
			this.onLine = onLine;
		}
		@Override
		public void dispose() {
		    ZONNEBOX.clear();
		    BOXES.clear(); 
		    CRYSTALS.clear();
            BoxActive.clear();
            friendlyPlayers.clear();		
            ToDestroy.clear();
		} 
}
