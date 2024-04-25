package kalima.crach.games.windows;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Align;

import kalima.crach.games.Assest;
import kalima.crach.games.kalimaCrach;
import kalima.crach.games.utils.ArFont;

public abstract class WindowsGames extends Window{
 
	private static Random R = new Random();
	
	protected Table tableWindows;
   
	public WindowsGames(String title) {
        super(title, Assest.Style, kalimaCrach.Font);
        super.getTitleLabel().setAlignment(Align.bottom);
        this.setVisible(false);
        setKeepWithinStage(false);   
        tableWindows = new Table();
	}   
	
	public abstract void addContent() ;
	
    public void Open(){
        int i;
        i = R.nextInt((int)kalimaCrach.Width);
        if(!this.isVisible()){
    		this.addAction(Actions.sequence(Actions.moveTo(Gdx.graphics.getWidth(),i),Actions.visible(true),Actions.parallel(Actions.alpha(1, 0.3f),Actions.moveTo(this.getX(), this.getY(),0.3f))));
    	}
    }
    public void Close() {
        int i;
        i = R.nextInt((int)kalimaCrach.Width);
    	if(this.isVisible()) {
    		this.addAction(Actions.sequence(Actions.parallel(Actions.alpha(0, 1),Actions.moveTo(i,Gdx.graphics.getHeight() ,0.9f)),Actions.visible(false),Actions.moveTo(this.getX(), this.getY())));
    	}
    }
}
