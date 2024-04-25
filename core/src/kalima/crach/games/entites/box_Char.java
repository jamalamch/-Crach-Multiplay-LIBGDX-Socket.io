package kalima.crach.games.entites;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;

import kalima.crach.games.Text;

public class box_Char extends box{
	private char lettercode ;
	private Text text;
    public box_Char(float x, float y, char C) {
         this(null, x, y, C);
    }
    public box_Char(String id, float x, float y, char C) {
    	this(id, x, y,(float)(Math.random()*1.7), C);
    }
    public box_Char(String id, float x, float y, float r, char C) {
		super( x, y, r);
		this.Id=id;
		text = new Text();
		text.setVisible(true);
		setLettercode(C);
	}
    public char getLettercode() {
		return lettercode;
	}
	public void setLettercode(char lettercode) {
		this.lettercode = lettercode;
		text.setText(String.valueOf(lettercode));
	}
	public void postDraw(Batch batch, OrthographicCamera camera) {
        text.draw(batch, getBody().getPosition().x,
              getBody().getPosition().y );
        
    }
}
