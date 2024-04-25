package kalima.crach.games;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public class Assest {
 
	public static AssetManager manager;
	
	public static Skin Style ;
    public static JsonValue ValueData ;
    public static JsonValue ValueLangue;
    public static BitmapFont font ;

    public static TextureAtlas Crach ;
	public static Texture background ;
	public static Texture background2 ;
	
	public  static Texture IconCrach;
	
    public static Texture Crystal ;
    public static Texture boxActive ;
    public static Texture boxNoActive ;
    public static Texture PlaceNoActive ;
    public static Texture placeActive;
    
    public static ParticleEffect CrachEffect ;
    
    
    public static final Drawable ButtonUp = new TextureRegionDrawable(new Texture(Gdx.files.internal("skins/test/ButtonUp.png")));
    public static final Drawable ButtonDown = new TextureRegionDrawable(new Texture(Gdx.files.internal("skins/test/ButtonDown.png")));
    public static final Drawable ButtonLeft = new TextureRegionDrawable(new Texture(Gdx.files.internal("skins/test/ButtonLeft.png")));
    public static final Drawable ButtonRight = new TextureRegionDrawable(new Texture(Gdx.files.internal("skins/test/ButtonRight.png")));
    public static final Drawable Buttonfire = new TextureRegionDrawable(new Texture(Gdx.files.internal("skins/test/Buttonfire.png")));
    public static final Drawable ButtonInteraction = new TextureRegionDrawable(new Texture(Gdx.files.internal("skins/test/ButtonInteraction.png")));

    
    
    public static void load() {
    	manager = new AssetManager();
    	manager.load("skins/star-soldier-ui.json",Skin.class);
    	manager.load("fonts/char3.fnt",BitmapFont.class);
    	   	
    	manager.load("Sprite/Crach.atlas",TextureAtlas.class);
    	
    	manager.load("background/planet-4.png",Texture.class);
    	manager.load("background/bg_1_1.png",Texture.class);
    	manager.load("LogoFx/LogoCrach.png",Texture.class);
    	
    	manager.load("SpriteAnimation/crystal.png",Texture.class);
    	manager.load("Sprite/box.png",Texture.class);
    	manager.load("Sprite/boxActive.png",Texture.class);
    	manager.load("Sprite/placeBox.png",Texture.class);
    	manager.load("Sprite/placeBoxActive.png",Texture.class);
    	
    	//manager.load("effects/CrachEffects.particle",ParticleEffect.class);

		manager.finishLoading();
		
	    Style = manager.get("skins/star-soldier-ui.json",Skin.class);
		font = manager.get("fonts/char3.fnt",BitmapFont.class);
        font.setColor(Color.GOLD);

    	JsonReader jsonReader = new JsonReader();
    	ValueData = jsonReader.parse(Gdx.files.internal("CrachType.json"));
    	ValueLangue = jsonReader.parse(Gdx.files.internal("traduction.json")).get(kalimaCrach.Langue);;
    	
		Crach = manager.get("Sprite/Crach.atlas", TextureAtlas.class);
		
		background   =manager.get("background/planet-4.png",Texture.class);
		background2 = manager.get("background/bg_1_1.png",Texture.class);
        IconCrach = manager.get("LogoFx/LogoCrach.png",Texture.class);
		Crystal      =manager.get("SpriteAnimation/crystal.png",Texture.class);
		boxActive   = manager.get("Sprite/boxActive.png",Texture.class);
		boxNoActive = manager.get("Sprite/box.png",Texture.class);
		PlaceNoActive=manager.get("Sprite/placeBox.png",Texture.class);
		placeActive =manager.get("Sprite/placeBoxActive.png",Texture.class);
		
		//CrachEffect = manager.get("effects/CrachEffects.particle",ParticleEffect.class);
    }
	
	
}
