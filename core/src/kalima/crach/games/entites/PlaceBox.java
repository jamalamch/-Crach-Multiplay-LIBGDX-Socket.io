package kalima.crach.games.entites;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef;
import com.badlogic.gdx.sql.SQLiteGdxException;

import kalima.crach.games.Assest;
import kalima.crach.games.kalimaCrach;
import kalima.crach.games.Screen.Tools.Hub;
import kalima.crach.games.basedonne.BaseDonnes;
import kalima.crach.games.utils.B2WorldCreator;

public class PlaceBox extends Entity{
	
	private int hieght = 70;
	
    private Joint currentJoint;
    private static char[][] MOT ; 
    
    private static B2WorldCreator creator;
    
    private static int LoungerMot;
    private static int   NomberPlace ;
	private static int[] tentativetrue ;
	private static int[]  tentativefalse ;
	
    private int pose ;
    private int Place = 1 ;

    public enum Interaction {
        NONE, BOX
    }

	private Interaction interaction = Interaction.NONE;
	public box interactEntity;
	
	public PlaceBox() {
		super();
		setTexture();
	}
	public PlaceBox(int x,int y,int pose,int place) {
		super();
		defineEntity(x, y);
		this.pose=pose;
		this.Place=place;
		setTexture();
	}
	public void setTexture() {
		setRegion(Assest.PlaceNoActive);
        setOriginCenter();
        setZIndex(1);
	}
	
	@Override
	public void defineEntity(MapObject object) {
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Rectangle rect = ((RectangleMapObject) object).getRectangle();
        
            
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((rect.getX() + rect.getWidth() / 2) /kalimaCrach.PPM, (rect.getY() + rect.getHeight() / 2) /kalimaCrach.PPM);
        b2body = world.createBody(bdef);
        
        shape.setAsBox( hieght/kalimaCrach.PPM, hieght/kalimaCrach.PPM);
        fdef.shape = shape;
        fdef.isSensor = true;
        b2body.createFixture(fdef);        
        shape.dispose();
        
        fdef = new FixtureDef();
        shape = new PolygonShape();
        shape.setAsBox( 5/kalimaCrach.PPM, 5/kalimaCrach.PPM);
        fdef.shape = shape;
        fdef.isSensor = true;
        fdef.filter.categoryBits = kalimaCrach.PLACE_BOX;
        fdef.filter.maskBits=kalimaCrach.ALPHA_BOX ;
        b2body.createFixture(fdef);        
        shape.dispose();
        
        
        
        
        b2body.setUserData(this);

	}
	
	public void update(float dt) {
		switch (interaction){
		case NONE : 
			    
			break;
		case BOX:
			    updateBoxInteraction() ;
		break;
		}
	}
	
    public void onContactStart(Entity otherEntity) {
    	
    	if(otherEntity instanceof box ){ 		
    		if (interaction != Interaction.BOX)
    			setInteraction(Interaction.BOX,otherEntity);
		        setRegion(Assest.placeActive);
    	}
    	
    }
    
    public void onContactEnd(Entity otherEntity) {
		
		  if ( otherEntity instanceof box) { if (interaction == Interaction.BOX && currentJoint == null) {
		  setInteraction(Interaction.NONE,null); 
	        setRegion(Assest.PlaceNoActive);
		  } }
		 
    }

    public void setInteraction( Interaction interaction,Entity interactEntity) {
        this.interactEntity = (box)interactEntity;
        this.interaction = interaction;

    }
    
    private void updateBoxInteraction() {
        final box box = this.interactEntity;
         if (box.isEnabled()){
                startInteractionWithBox(box);
                box.setState(kalima.crach.games.entites.box.State.PLACE);
        } else {
                endInteractionWithBox();
            }        
    }

	public void endInteractionWithBox() {
        if (currentJoint == null) return;
        setInteraction(Interaction.NONE ,null);

        world.destroyJoint(currentJoint);
        currentJoint = null;
        MotOnPlace(' ',Place,pose);
	}

	private void startInteractionWithBox(box box) {
		if (currentJoint != null) return;
		if (!box.isEnplace()){

		PrismaticJointDef jointDef = new PrismaticJointDef();
		
		  jointDef.initialize(getBody(),box.getBody(),getBody().getWorldCenter(), new Vector2(1,1));	
		  jointDef.motorSpeed = 100.0f; jointDef.enableMotor = true;
		  jointDef.lowerTranslation =10f;
		  jointDef.upperTranslation = 10f;
		  jointDef.enableLimit = true;

		  
        currentJoint = world.createJoint(jointDef);
        Gdx.app.log(" connect  ", " box " +box.getLettercode() +"and placebox " +getPose()+" Place"+Place);
        MotOnPlace(box.getLettercode(), Place, pose);
        if(MotCompletExister( Place)) {
                    setTentativetrue(1);
                    creator.removeAllBoxPlace(Place);
        }
		}
	}
	
	public static void MotAndTentativeNull(int nomberPlace,int longerMot) {
		NomberPlace = nomberPlace;
		LoungerMot = longerMot;
		tentativefalse = new int[NomberPlace];
		tentativetrue = new int[NomberPlace];
		MOT = new char[NomberPlace][LoungerMot];
		for(int i =0 ; i < NomberPlace ; i++) {
			tentativefalse[i] = 0;
			tentativetrue[i]=0;
			for(int j=0;j< LoungerMot; j++)
			    MOT[i][j]  = ' ';	
		}
	}
	
	public static void MotOnPlace(char C,int place,int pose) {
		MOT[--place][--pose] = C;
	}
	public static boolean MotCompletExister(int place) {
		for(int i = 0 ; i< LoungerMot ; i++) {
			if(MOT[place-1][i] == ' ') {
								return false;
			}
		}
        try {
			BaseDonnes.connectToDB();  
			try {
				if(BaseDonnes.chercherlesmotshamza(MOT[place-1]))
					return true;
			} catch (SQLiteGdxException e) {
				e.printStackTrace();
			}
            BaseDonnes.diconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return false;
	}
	public box disposeInteract(box box) {
		endInteractionWithBox();	
		return box;
	}
	
	
	public String getMots() {
		return String.valueOf(MOT[Place-1]);
	}

	public int getPose() {
		return pose;
	}
	public int getPlace() {
		return Place;
	}
	
	public  void setTentativetrue(int Tent) {
		 Hub.updateBits(tentativetrue[Place-1] +=Tent);
		 
	}

	public void setPose(int pose) {
		this.pose = pose;
	}
	public static int getTentativetrue(int Place) {
		return tentativetrue[--Place];
	}
    
}
