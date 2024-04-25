package kalima.crach.games.Screen.Tools;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import kalima.crach.games.Assest;

public class Hub extends Table {
	


	private float timer ;
	private int secs ;
	private int mins ;
	
	
	
	private Label LabelScore;
	private static Label LabelBits;
	private Label CrachPower;
	private Label timerLabel;
	private boolean disableTimer;
	
	
	public Hub() {
		      super();
              LabelScore = new Label(String.format(" %06d ", 0), Assest.Style);
              LabelBits = new Label(String.format (" %03d ", 0), Assest.Style);
              CrachPower = new Label(String.format(" %04d ", 0), Assest.Style );          
              timerLabel = new Label("00:00", Assest.Style);
              top();
              defaults().expandX();
              setFillParent(true);
              add(LabelBits).padTop(10);
              add(LabelScore).padTop(10);
              add(timerLabel).padTop(10);
              row();
              add(CrachPower).expandY().left().bottom();
              pack();
	}
	
	
	public void update( float delta) {
		timer += delta;
		if(!disableTimer ) {
			if(mins >= 99 && secs >= 99 && timer >= 99) {
				disableTimer  = true;
			} else {
				if(timer >= 1) {
					secs++;
					timer = 0;
				}
				if(secs >= 60) {
					mins++;
					secs = 0;
				}
			}
		}
			updateTimerLabel();
		}
	
	private void updateTimerLabel() {
			timerLabel.setText(String.format(" %02d : %02d", mins , secs));
	}
	public void updatePointScore(int point) {
		LabelScore.setText(String.format(" %06d ", point));
	}
	public static  void updateBits(int Bits) {
		LabelBits.setText(String.format(" %03d ",Bits));
	}
	public  void upPowerCrach(int power) {
		CrachPower.setText(String.format(" %04d ",power));
	}

}
