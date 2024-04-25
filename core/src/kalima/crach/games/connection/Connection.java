package kalima.crach.games.connection;

import kalima.crach.games.entites.CrachPlayer;
import kalima.crach.games.utils.Creator.B2WorldCreator;

public  class Connection {
	    float update_times;
	    float times;
	    CrachPlayer player;
	    String id;
	    String ServerAdressIp = "localhost";


	   public Connection(B2WorldCreator creator2,float update_time) {
		   this.player=creator2.getPlayer();
		   this.update_times=update_time;
		   id = String.valueOf((float)Math.random());
	    }
	   public void updateServer(float dt) {
		   return;
	   }
	   public void Connecte() {
		   return;
	   }
	   public void DeConnecte() {
		   return;
	   }
}
