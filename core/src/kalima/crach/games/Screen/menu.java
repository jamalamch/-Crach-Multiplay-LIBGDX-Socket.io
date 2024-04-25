package kalima.crach.games.Screen;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import de.tomgrill.gdxdialogs.core.dialogs.GDXProgressDialog;
import kalima.crach.games.Assest;
import kalima.crach.games.kalimaCrach;
import kalima.crach.games.Screen.playScreen.StateConnection;
import kalima.crach.games.connection.connectionLocal;
import kalima.crach.games.utils.ArFont;
import kalima.crach.games.windows.ChoiserLeCrach;

public class menu implements Screen{

    public Viewport ViewMenu;
    private SpriteBatch batch;

	
	private String titlegames = " Kalima Crach";
	private String star_ofline  ;
	private String star_online ;
	private String star_local ;
	private String Loding_ser ;
	private String Lod_ser_mess ;
	
	public String typeCrach;

	
	private Label TitleGames;
	
	private TextField ServerAdress;
	private TextField ServerPort;
	private TextField MyservPort;
	
	private Image ScoreImage;
	
	private Button buttonSetting;
	private Button StarOfline;
	private Button StarOnline;
	private Button StarLocalh;
	private Stage stage;
	
	private ChoiserLeCrach Choise;
	public int CodeCrach = 1;
	
	GDXProgressDialog LodingServer;
	
	
	 public menu() {
		 ViewMenu = new ExtendViewport(kalimaCrach.Width/kalimaCrach.PPM,kalimaCrach.Height/kalimaCrach.PPM);
		 AddString();
		 LodingServer = kalimaCrach.dialogs.newDialog(GDXProgressDialog.class);
	     batch = new SpriteBatch();
			stage =new Stage(ViewMenu);
			TitleGames = new Label(titlegames, Assest.Style, "title");
			//TitleGames.setPosition(0, ViewMenu.getWorldHeight()-(TitleGames.getHeight()+20 ));
			Table table = new Table(Assest.Style);
			table.setDebug(true);
			ServerAdress = new TextField("localhost", Assest.Style);
			ServerAdress.setMessageText(" Server Adress !! ");
			ServerAdress.setVisible(false);
//			ServerPort = new TextField("9021", Assest.Style);
//			ServerPort.setMessageText(" Server port !! ");
//			ServerPort.setVisible(false);
//			MyservPort = new TextField("9020", Assest.Style);
//			MyservPort.setMessageText(" Server Port !! ");
//			MyservPort.setVisible(false);
			
			buttonSetting = new ImageButton(Assest.Style.getDrawable("buttonSetting"));
			buttonSetting.setSkin(Assest.Style);
			buttonSetting.add(" setting ");
			ScoreImage = new Image(Assest.Style.getDrawable("prize-money"));
			StarOfline = new ImageTextButton(star_ofline,Assest.Style,kalimaCrach.Font);
			StarOnline = new ImageTextButton(star_online,Assest.Style,kalimaCrach.Font);
			StarLocalh = new ImageTextButton(star_local,Assest.Style,kalimaCrach.Font);
	        
			ServerAdress.getStyle().font.getData().setScale(kalimaCrach.RATA);
			TitleGames.getStyle().font.getData().setScale(kalimaCrach.RATA);
	        Assest.font.getData().setScale(1/kalimaCrach.PPM);         
             
	        LodingServer.setTitle(Loding_ser);
	        LodingServer.setMessage(Lod_ser_mess);
	        
			StarOfline.addListener(new ClickListener() {
				@Override
		    	public void clicked(InputEvent event, float x, float y) {
	                
					if(Choise.isVisible()) {
						 ((Game) Gdx.app.getApplicationListener()).setScreen(new playScreen(StateConnection.ofline,CodeCrach));
					      Choise.setVisible(false);
					}
					else {
						Choise.Open();
						}
		     	}});
			StarOnline.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					//((Game) Gdx.app.getApplicationListener()).setScreen(new playScreen(StateConnection.online,CodeCrach));
	                 //dispose();
					LodingServer.build().show();
				}});
			StarLocalh.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					int PortServer = Integer.valueOf(ServerPort.getText());
					int MyPort = Integer.valueOf(MyservPort.getText());
					if(PortServer > 1 && MyPort > 1 && PortServer< 10000 &&  MyPort < 10000) {
					playScreen Screen = new playScreen(StateConnection.localline,CodeCrach);
					((connectionLocal) Screen.getConnection()).SetServerAdress(ServerAdress.getText(),PortServer,MyPort);
					((Game) Gdx.app.getApplicationListener()).setScreen(Screen);
	                dispose();
					}}});
	        table.defaults().pad(10/kalimaCrach.RATA).right().prefHeight(65/kalimaCrach.RATA).prefWidth(500/kalimaCrach.RATA);
	        table.setFillParent(true);
	        table.row();
	        table.add(ScoreImage).width(80/kalimaCrach.RATA);
	        table.add(" $ 120754 ").expandX().left();
	        table.add(buttonSetting).top().prefWidth(80/kalimaCrach.RATA);
	        table.row().expandY().top();
	        table.add(TitleGames).left().expandY().colspan(2);
	        table.add("ID:  4RFDS45");
	        table.row();
	        table.add(StarOfline).colspan(3);
	        table.row();
	        table.add(StarOnline).colspan(3);
	        table.row();
	        table.add(StarLocalh).colspan(3);
	        table.row();
	        table.pack();
			stage.addActor(table);	
			Choise = new ChoiserLeCrach(getMenu());
		    stage.addActor(Choise);
	 }
	private void AddString() {
		star_ofline = Assest.ValueLangue.getString("star_ofline");
		 star_online = Assest.ValueLangue.getString("star_online");
		 star_local = Assest.ValueLangue.getString("star_local");
		 typeCrach =  Assest.ValueLangue.getString("typeCrach");

		 Loding_ser = Assest.ValueLangue.getString("Loding_ser");
		 Lod_ser_mess = Assest.ValueLangue.getString("Lod_ser_mess");
        if(kalimaCrach.Langue.equals("arabic")) {
        	star_ofline=ArFont.getText(star_ofline);
        	star_online =ArFont.getText(star_online);
        	star_local =ArFont.getText(star_local);
        	typeCrach = ArFont.getText(typeCrach);
        }
	}
	@Override
	public void show() {
	     Gdx.input.setInputProcessor(stage);	
	}

	@Override
	public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
		batch.begin();
				batch.draw(Assest.background2, 0, 0, ViewMenu.getWorldWidth(), ViewMenu.getWorldHeight());
	    batch.end();
		batch.begin();
				batch.draw(Assest.IconCrach, 10, 20);
      batch.end(); 
        stage.draw();
        stage.act();
	}

	@Override
	public void resize(int width, int height) {
		ViewMenu.update(width, height,true);
		if(Choise != null) Choise.update();
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
         stage.dispose();		
         batch.dispose();
	}
	
	public menu getMenu() {
		return this;
	}

}
