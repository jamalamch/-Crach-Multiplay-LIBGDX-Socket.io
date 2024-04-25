package kalima.crach.games.utils.Creator;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;

import kalima.crach.games.entites.Entity;
import kalima.crach.games.entites.box;
import kalima.crach.games.entites.Enimy.Enimy;

public class B2WorldCreatorOnlineAdmin extends B2WorldCreatorOnline{
	private  int MaxNumberBox ;
	private int numberBox;
	private  int MaxNumberCry ;
	private int numberCry;
	private  int MaxNumberEnimy ;
	private int numberEnimy;
	private int BoondsX, BoondsY;

	public B2WorldCreatorOnlineAdmin(World word, TiledMap tiled, int Code, int Bonndsx, int Bonndsy,int numberBox,int numberCry,int numberEnimy) {
		super(word, tiled, Code);
		this.MaxNumberBox = numberBox;
		this.MaxNumberCry = numberCry;
		this.MaxNumberEnimy=numberEnimy;
		this.BoondsX = Bonndsx;
		this.BoondsY = Bonndsy;
	}
	@Override
	public void update(float dt) {
		super.update(dt);
		updateRoom();
	}
	@Override
	public void destoryEntity(StateEntity State, String id) {
		super.destoryEntity(State, id);
		switch (State) {
		case Destor_Box:
		case Destor_Box_Char:
			numberBox--;
			break;
		case Destor_Crystal:
			numberCry--;
		case Destor_Enimy:
			numberEnimy--;
		case Destor_Players:
			nomberplayer --;
		default:
			break;
		}
	}
	public void creatEntity(StateEntity State, String id) {
		int x = random.nextInt(BoondsX - box.getHieght()) + box.getHieght() / 2;
		int y = random.nextInt(BoondsY - box.getHieght()) + box.getHieght() / 2;
		Entity E = creatEntity(State, id, x, y);
		EntityMap.put(id, E);
		EntityActive.put(id, State);
		E.SetForceDeplace(x, y, E.getBody().getAngle());
	}
	public void updateRoom() {
		if (numberBox < MaxNumberBox) {
			creatEntity(StateEntity.New_Box, String.valueOf(EntityMap.size() + 1));
		}
		if (numberCry < MaxNumberCry) {
			creatEntity(StateEntity.New_Crystal, String.valueOf(EntityMap.size() + 1));
		}
		if (numberEnimy < MaxNumberEnimy) {
			creatEntity(StateEntity.New_Enimy, String.valueOf(EntityMap.size() + 1));
		}
	}
	public void addAlltoActive() {
		for (Entity b : EntityMap.values())
			if (b instanceof box)
				EntityActive.put(b.Id, StateEntity.New_Box);
			else if (b instanceof Enimy)
				EntityActive.put(b.Id, StateEntity.New_Enimy);
    }
}
