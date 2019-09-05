package game.fish.cars;

import com.badlogic.gdx.math.Vector2;

public class Constants {
	
	private Constants() {}
	
	public static final Vector2 GRAVITY = new Vector2(0, 0);
	public static final float DEFAULT_ZOOM = 6f;
	public static final float ZOOM_STEP = 0.4f;
	
	public static final float PPM = 50f;
	
	public static final String PATH_MUSIC = "music.ogg";
	public static final String PATH_MAP1 = "map1.tmx";
	public static final String PATH_MAP2 = "map2.tmx";
	public static final String PATH_MAP3 = "map3.tmx";
	
	public static final int PLAY_SCREEN = 0;
	public static final int MENU_SCREEN = 1;
	public static final int SETTINGS_SCREEN = 2;
	public static final int KEYBINDINGS_SCREEN = 3;
	public static final int ACHIEVEMENTS_SCREEN = 4;
	
	public static final int CAR_FWDCAR = 0;
	public static final int CAR_AWDCAR = 1;
	public static final int CAR_MOTORCYCLE = 2;
	public static final int CAR_HOVERCAR = 3;
	
	public static final int MAP_MAP1 = 0;
	public static final int MAP_MAP2 = 1;
	public static final int MAP_MAP3 = 2;
	

}
