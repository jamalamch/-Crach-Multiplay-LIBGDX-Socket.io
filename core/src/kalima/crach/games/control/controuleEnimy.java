package kalima.crach.games.control;

import kalima.crach.games.Screen.Tools.Hub;
import kalima.crach.games.entites.Enimy.Enimy;
import kalima.crach.games.entites.CrachPlayer;

public class controuleEnimy implements controule {
	private Enimy Enimy;
	private CrachPlayer Player;
	private float Dt_time;


	public controuleEnimy(Enimy Enimy,CrachPlayer Player) {
		this.Enimy = Enimy;
		this.Player = Player;
	}
	@Override
	public void update(float delta) {
		Dt_time += delta;
	    if(Dt_time > 6){
		Enimy.toPosition.setDestination(Player.getBody().getPosition());
		Dt_time = 0;
	    }
	}

	@Override
	public void setInteractionBox(boolean Inter) {
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public Hub getHub() {
		return null;
	}
	@Override
	public void drawHub() {
		return ;
	}

}
