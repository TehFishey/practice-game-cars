package game.fish.cars;

import com.badlogic.gdx.math.Vector2;

public class Constants {
	
	private Constants() {}
	
	public static final Vector2 GRAVITY = new Vector2(0, 0);
	public static final float DEFAULT_ZOOM = 6f;
	public static final float ZOOM_STEP = 0.4f;
	public static final float PPM = 50f;
	public static final String MAP1 = "map1.tmx";
	public static final String MAP_WALL = "walls";
	public static final String MAP_OBSTACLES = "debris";
	public static final String MAP_PLAYER = "player";
	
	public static final int PLAY_SCREEN = 0;
	public static final int MENU_SCREEN = 1;
	public static final int SETTINGS_SCREEN = 2;
	public static final int ACHIEVEMENTS_SCREEN = 3;
	public static final int LOADING_SCREEN = 4;
}
