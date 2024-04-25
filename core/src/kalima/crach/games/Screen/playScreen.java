package kalima.crach.games.Screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import kalima.crach.games.Assest;
import kalima.crach.games.kalimaCrach;
import kalima.crach.games.Screen.Tools.Hub;
import kalima.crach.games.connection.Connection;
import kalima.crach.games.connection.connectionLocal;
import kalima.crach.games.connection.connectionServer;
import kalima.crach.games.entites.CrachPlayer;
import kalima.crach.games.entites.box;
import kalima.crach.games.utils.CameraHandler;
import kalima.crach.games.utils.WorldContactListener;
import kalima.crach.games.utils.Creator.B2WorldCreator;
import kalima.crach.games.utils.Creator.B2WorldCreatorOfline;
import net.dermetfan.gdx.graphics.g2d.Box2DSprite;





public class playScreen implements Screen {


    private World box2dWorld;
    private TiledMap tileMap;

    private Box2DDebugRenderer box2DDebugRenderer;
    private WorldContactListener worldContactListener;
    private OrthogonalTiledMapRenderer tiledMapRenderer;

	private OrthographicCamera box2DCamera;
    private CameraHandler cameraHandler;
    private Viewport gamePort;
    
    private SpriteBatch batch;
	private Texture background;

    private B2WorldCreator creator;
    
    public enum StateConnection {
        ofline, online ,localline
    }
    public StateConnection connecte;
    private Connection connection;
    
   // private GDXButtonDialog dialogToExite;
   // private Screen ToScreen;
    
    public playScreen(StateConnection state,int CrachCode) {
        connecte = state;
		box2DCamera = new OrthographicCamera();
        gamePort = new ExtendViewport(kalimaCrach.Width/kalimaCrach.PPM,kalimaCrach.Height/kalimaCrach.PPM,box2DCamera);
        cameraHandler = new CameraHandler(box2DCamera);
        
        //tileMap = new TmxMapLoader().load("maps/demo.tmx");

        tileMap = new TmxMapLoader().load("maps/demo_Enimy.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tileMap,1/kalimaCrach.PPM);
        
        cameraHandler.setBounds(tileMap); 
        
        box2dWorld = new World(new Vector2(0, 0), true);
        box2DDebugRenderer = new Box2DDebugRenderer(true,true,false,true,true,true); 
          
        worldContactListener = new WorldContactListener();
        box2dWorld.setContactListener(worldContactListener);
        creator = new B2WorldCreatorOfline(box2dWorld, tileMap,CrachCode);
       // crach = new CrachPlayer(CrachCode);
//        switch(connecte) {
//        case online:
//        	connection = new connectionServer(creator, crach,6/60f);
//        	break;
//        case localline:
//        	connection = new connectionLocal(creator, crach,6/60f);
//        	creator.setOnLine(true);
//        	break;
//        case ofline:
//        	creator.BeAdminisrationRoom(true);
//        	creator.setOnLine(false);
//        	connection = new Connection(creator, crach,1);
//        	break;
//        }
         connection = new Connection(creator,1);
         cameraHandler.setTarget(creator.getPlayer().b2body, true);

         
         batch = new SpriteBatch();
         background = Assest.background;
    }
	
	@Override
	public void show() {
		connection.Connecte();
//    	dialogToExite = kalimaCrach.dialogs.newDialog(GDXButtonDialog.class);
//    	dialogToExite.setTitle("Exite Games");
//    	dialogToExite.setMessage("Do you whant to exit ?");
//    	dialogToExite.addButton("Yes");
//    	dialogToExite.addButton("No");
//        dialogToExite.setClickListener(new ButtonClickListener() {
//			@Override
//			public void click(int button) {
//               switch(button) {
//               case 0:
//                   ToScreen = new menu();
//               };
//			}
//		});
	}

	@Override
	public void render(float delta) {
        Gdx.gl.glClearColor(0.001f, 0, 0.01f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        connection.updateServer(Gdx.graphics.getDeltaTime());
        box2dWorld.step(1 / 60f, 6, 2);
		batch.begin();
	    	batch.draw(background, 0, 0, cameraHandler.getxBound(), cameraHandler.getyBound());
		batch.end();
        updatecamera();
        creator.update(delta);

        tiledMapRenderer.render();
        
        batch.setProjectionMatrix(box2DCamera.combined);
        creator.drawEntity(batch);
        box2DDebugRenderer.render(box2dWorld, box2DCamera.combined);
        
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
      	  kalimaCrach.SetScreen(kalimaCrach.menu);
      	  dispose();
        }
	}
	
	
    public void postDraw(SpriteBatch batch, OrthographicCamera camera) {

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.setColor(1, 1, 1, 1);
//        for (box e : creator.getBOXES() ) {
//            e.postDraw(batch, camera);
//        }
        batch.end();

    }

	@Override
	public void resize(int width, int height) {
		box2DCamera.viewportWidth=width;
		box2DCamera.viewportHeight=height;
		box2DCamera.zoom=1.5f;
		box2DCamera.update();
        tiledMapRenderer.setView(box2DCamera);
		gamePort.update(width, height);
		creator.getPlayer().getControule().resize(width, height);
		
	}
	public void updatecamera() {
//		if(Gdx.input.isTouched()) {
//			float loopX =  (box2DCamera.position.x + (Gdx.input.getX()-Gdx.graphics.getWidth()/2)/40);
//			float loopY = (box2DCamera.position.y-( Gdx.input.getY()-Gdx.graphics.getHeight()/2)/40);
//			if(loopX < cameraHandler.getxBound() && loopX > 0) 
//		                    box2DCamera.position.x=loopX;
//			if(loopY < cameraHandler.getyBound() && loopY > 0) 
//		                   box2DCamera.position.y=loopY;			
//		box2DCamera.update();
//        tiledMapRenderer.setView(box2DCamera);			
//		}
//		else {		
		cameraHandler.update();
        tiledMapRenderer.setView(box2DCamera);	
		//}


	}
    
    public Connection getConnection() {
		return connection;
	}
	
	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		 connection.DeConnecte();
	}

	@Override
	public void dispose() {
	   	 creator.dispose();
		 connection.DeConnecte();
	     box2dWorld.dispose();
	     box2DDebugRenderer.dispose();    
	     tileMap.dispose();
	     tiledMapRenderer.dispose();
	     batch.dispose();
	}

}
