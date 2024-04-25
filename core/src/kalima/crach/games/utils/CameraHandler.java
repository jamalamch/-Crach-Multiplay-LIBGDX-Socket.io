package kalima.crach.games.utils;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import kalima.crach.games.kalimaCrach;

public class CameraHandler 
{
	private OrthographicCamera camera;
	private Body target;
	private float lerp = 0.08f;
	private boolean followTarget = false;
	private boolean followX = true;
	private boolean followY = true;
	private boolean useBounds = false;
	private float xBound;


	private float yBound;
	
	public CameraHandler(OrthographicCamera camera) {
		this.camera = camera;
	}
	
	public void setTarget(Body target, boolean followTarget) {
		this.target = target;
		this.followTarget = followTarget;
	}
	
	public void update() {
		float x = 0;
		float y = 0;
		if(followTarget) {
			x = target.getPosition().x;
			y = target.getPosition().y;
		}
		if(followX) camera.position.x = camera.position.x + (x - camera.position.x) * lerp;
		if(followY) camera.position.y = camera.position.y + (y - camera.position.y) * lerp;
		if(useBounds) {
			camera.position.x = MathUtils.clamp(camera.position.x, kalimaCrach.Width/2/kalimaCrach.PPM, xBound-(kalimaCrach.Width/2/kalimaCrach.PPM));
			camera.position.y = MathUtils.clamp(camera.position.y, kalimaCrach.Height/2/kalimaCrach.PPM, yBound-(kalimaCrach.Height/2/kalimaCrach.PPM));
		}
		camera.update();
	}
	
	public void setLerp(float lerp) {
		this.lerp = lerp;
	}
	
	public void setFollowX(boolean follow) {
		this.followX = follow;
	}

	public void setFollowY(boolean follow) {
		this.followY = follow;
	}

	public void setBounds(TiledMap map) {
		MapProperties prop = map.getProperties();
		int width = prop.get("width", Integer.class);
		int height = prop.get("height", Integer.class);
		int tilePixelWidth = prop.get("tilewidth", Integer.class);
		int tilePixelHeight = prop.get("tileheight", Integer.class);
		setBoundX((width * tilePixelWidth) / kalimaCrach.PPM);
		setBoundY((height * tilePixelHeight) / kalimaCrach.PPM);
	}
	
	public void setBoundY(float mapHeight) {
		useBounds = true;
		yBound = mapHeight;
	}

	public void setBoundX(float mapWidth) {
		useBounds = true;
		xBound = mapWidth;
	}
	
	public boolean isOffCamera(Vector2 pos) {
		return (pos.x >= (camera.position.x + camera.viewportWidth/2)
			 || pos.x <= (camera.position.x - camera.viewportWidth/2));
	}	
	
	
	public float getxBound() {
		return  xBound;
	}

	public float getyBound() {
		return  yBound;
	}
}
