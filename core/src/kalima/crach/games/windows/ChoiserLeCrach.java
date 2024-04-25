package kalima.crach.games.windows;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.JsonValue;

import kalima.crach.games.Assest;
import kalima.crach.games.kalimaCrach;
import kalima.crach.games.Screen.menu;
import kalima.crach.games.utils.ArFont;


public class ChoiserLeCrach extends WindowsGames{
	
	private String  vitass;
	private String  force;
	private String rotation;
	
	private ScrollPane  LalisteCrach;
	private Image rightSuit;
	private Image leftSuit;
	private menu Menu;

    
    public enum TypeBar {
	      vitass,force,rotaion
    }
	
	public ChoiserLeCrach(menu M) {
		super(M.typeCrach);
		this.Menu=M;
		this.setVisible(false);
        this.setOrigin(Align.center);        
        this.setSize(700/kalimaCrach.RATA ,700/kalimaCrach.RATA);
        this.setPosition((this.Menu.ViewMenu.getWorldWidth()-getWidth())/2, (this.Menu.ViewMenu.getWorldHeight()-getHeight())/2);
        AddString();
        addContent();
	}
	private void AddString() {
		vitass =Assest.ValueLangue.getString("vitass");
		force = Assest.ValueLangue.getString("force");
		rotation =Assest.ValueLangue.getString("rotation");
		 if(kalimaCrach.Langue.equals("arabic")) {
			 vitass =ArFont.getText(vitass);
			 force = ArFont.getText(force);
			 rotation=ArFont.getText(rotation);
		 }
	}
	@Override
	public void addContent() {
        rightSuit = new Image(Assest.Style.getRegion("right-arrow"));
        leftSuit = new Image(Assest.Style.getRegion( "left-arrow"));
        LalisteCrach = new ScrollPane(new LaListeCrach(),Assest.Style);
        row().center();
		add(leftSuit).minWidth(50/kalimaCrach.RATA).padRight(-10/kalimaCrach.RATA);
		add(LalisteCrach).expandX().prefWidth(600/kalimaCrach.RATA).prefHeight(600/kalimaCrach.RATA);
		add(rightSuit).minWidth(50/kalimaCrach.RATA).padLeft(-10/kalimaCrach.RATA);
		row();
		pack();
	}

	public void update() {
		this.setPosition((Menu.ViewMenu.getWorldWidth()-getWidth())/2, (Menu.ViewMenu.getWorldHeight()-getHeight())/2);
	}
	class LaListeCrach extends Table{
		JsonValue ValueData ;
		public LaListeCrach() {
			this.padLeft(30);
			this.padRight(30);
	        defaults().center();
	        row().prefWidth(600/kalimaCrach.RATA).prefHeight(550/kalimaCrach.RATA).padTop(10/kalimaCrach.RATA);
			for(int i= 1 ;i<16 ; i++) {
				add(new selection(i));		
			}
			pack();
		}
	}
	class selection extends Table{
		private final int MaxVitass = 1450;
		private final int MaxForce = 7;
		private final int MaxAngule =3;
		
		public selection(final int code) {
            super(Assest.Style);
	        //defaults();
	        row().width(500/kalimaCrach.RATA).prefHeight(600/kalimaCrach.RATA).padTop(20/kalimaCrach.RATA);
			add(new Image(Assest.Crach.findRegion("crach"+code))).colspan(2);
			JsonValue Value = Assest.ValueData.get("crach").get(String.valueOf(code));
			Float Vvitas = Value.getFloat("vitass");
			Float Vforc= Value.getFloat("force");
			Float Vrotatio = Value.getFloat("rotation");
			row();
			add(force,kalimaCrach.Font);
			add(addtoTable(Vforc,TypeBar.force)).prefWidth(300/kalimaCrach.RATA);
			row();
			add(vitass,kalimaCrach.Font);
			add(addtoTable(Vvitas,TypeBar.vitass)).prefWidth(300/kalimaCrach.RATA);
			row();
			add(rotation,kalimaCrach.Font);
			add(addtoTable(Vrotatio,TypeBar.rotaion)).prefWidth(300/kalimaCrach.RATA);
			row();
			pack();
			this.addCaptureListener(new ClickListener() {
				@Override
            	public void clicked(InputEvent event, float x, float y) {
                        Close();
                        Menu.CodeCrach = code;}});
		
		}
		public ProgressBar addtoTable(float V,TypeBar type) {
			ProgressBar progressBar = null;
			switch(type) {
			case vitass :
				progressBar = new ProgressBar(0, MaxVitass, 10, false, Assest.Style);
				progressBar.setValue(V);
				break;
			case force:
				progressBar = new ProgressBar(0, MaxForce, 0.1f, false, Assest.Style);
				progressBar.setValue(V);
				break;
			case rotaion:
				progressBar = new ProgressBar(0, MaxAngule, 0.1f, false, Assest.Style);
				progressBar.setValue(V);
				break;
			}
			return progressBar;
		}
	}
	
}
