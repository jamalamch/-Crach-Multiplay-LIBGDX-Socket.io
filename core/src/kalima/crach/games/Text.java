package kalima.crach.games;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Align;

import kalima.crach.games.utils.ArFont;

public class Text {
    public String text;
    public float textWidth;
    public float textHeight;
    public boolean visible;

    public Text() {
    }

    public Text(String text) {
        if (this.text == null || !this.text.equals(text)) {
            setText(text);
        }
    }

    public Text setVisible(boolean visible) {
        this.visible = visible;
        return this;
    }

    public Text setText(String text) {
        this.text = ArFont.getText(text);

        if (text == null) {
            textWidth = 0;
            textHeight = 0;
        } else {
            final GlyphLayout layout = new GlyphLayout(Assest.font, text);
            textWidth = layout.width;
            textHeight = layout.height;

        }

        return this;
    }

    public void draw(Batch batch, float x, float y) {
        if (visible && text != null) {
            Assest.font.draw(batch, text, x - (textWidth/2), y+textHeight/2,textWidth/kalimaCrach.PPM, Align.center, false);
        }
    }
}
