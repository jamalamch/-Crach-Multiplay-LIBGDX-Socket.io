package kalima.crach.games.utils.Creator;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;

public class B2WorldCreatorOfline extends B2WorldCreator{

	public B2WorldCreatorOfline(World word, TiledMap tiled,int Code) {
		super(word, tiled,Code);
		super.addBoxTitle();
		super.addCrystalTitle();
		super.addEnimyTitle();
	}

}
