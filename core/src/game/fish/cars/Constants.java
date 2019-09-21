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
	public static final String PATH_ACHIEVEMENT_FALSE = "ico-x.png";
	public static final String PATH_ACHIEVEMENT_TRUE = "ico-check.png";
	public static final String PATH_SETTINGS = "carsSettings";
	public static final String PATH_KEYBINDINGS = "carsKeyBindings";
	public static final String PATH_ACHIEVEMENTS = "carsAchievements";
	
	public static enum SCREEN {
		PLAY_SCREEN, 
		MENU_SCREEN,
		SETTINGS_SCREEN,
		KEYBINDINGS_SCREEN,
		ACHIEVEMENTS_SCREEN
	}
	
	public static enum CAR {
		FWDCAR, 
		AWDCAR,
		MOTORCYCLE,
		HOVERCAR
	}
	
	public static enum MAP {
		MAP1, 
		MAP2,
		MAP3,
	}
}
