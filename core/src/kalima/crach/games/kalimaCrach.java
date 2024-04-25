package kalima.crach.games;

import com.badlogic.gdx.Application.ApplicationType;

import java.lang.Thread.State;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import de.tomgrill.gdxdialogs.core.GDXDialogs;
import de.tomgrill.gdxdialogs.core.GDXDialogsSystem;
import kalima.crach.games.Screen.menu;
import kalima.crach.games.Screen.playScreen;
import kalima.crach.games.Screen.playScreen.StateConnection;

public class kalimaCrach extends Game {
     
	
	public static float Width = 1200;
	public static float Height= 700;
	
	public static final float PPM = 1;
	public static float RATA ;
	
	public static String Langue ;
	public static String Font ;
    
	public static final short NOTHING_BIT = 0;
	public static final short CRACH_BIT = 1;
	public static final short HEAD_CRACH =2;
	public static final short LAMAS_CRACH = 4;
	public static final short FRENDS_PLAY = 8;
	public static final short ALPHA_BOX =16;
	public static final short PLACE_BOX =32;
	public static final short OBJECT_BIT = 64;
	public static final short CRYSTAL_BIT = 128;
	public static final short FIRE_BIT = 256;
	public static final short ENIMY_BIT = 512;
    
	public static GDXDialogs dialogs;
	
	public static Screen menu;
	
	@Override
	public void create() {
		Langue = "arabic";
		Font = (Langue.equals("arabic"))? "arabic" :  "default";
		installDialogs();
	  	RATA=RATAPPM();
        Assest.load();
		//menu = new menu();

		setScreen(new playScreen(StateConnection.ofline, 15));
       // setScreen(menu);
	}
	public float RATAPPM(){
		float width = Gdx.graphics.getWidth();
		float height = Gdx.graphics.getHeight();
		if(width < height)
			return  1200/width;
		else 
			return  600/height;
	}
	public void installDialogs() {

		dialogs = GDXDialogsSystem.install();
		if(Gdx.app.getType() == ApplicationType.Desktop) {
			dialogs.registerDialog("de.tomgrill.gdxdialogs.core.dialogs.GDXProgressDialog", "kalima.crach.games.desktop.ProgressDialog");
		}
	}
	public static void SetScreen(Screen S){
		 ((Game) Gdx.app.getApplicationListener()).setScreen(S);
	}
	

}
