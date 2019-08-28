package game.fish.cars;

import com.badlogic.gdx.math.Vector2;

public class Constants {
	
	private Constants() {}
	
	public static final Vector2 GRAVITY = new Vector2(0, 0);
	public static final float DEFAULT_ZOOM = 6f;
	public static final float PPM = 50f;
	public static final String MAP1 = "map1.tmx";
	public static final String MAP_WALL = "walls";
	public static final String MAP_OBSTACLES = "debris";
	public static final String MAP_PLAYER = "player";
}
