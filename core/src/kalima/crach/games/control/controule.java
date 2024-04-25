package kalima.crach.games.control;

import kalima.crach.games.Screen.Tools.Hub;

public interface controule {
     public void update(float delta);
     public void setInteractionBox(boolean Inter);
	 public void resize(int width, int height); 
	 public Hub getHub();
	 public void drawHub();
}
