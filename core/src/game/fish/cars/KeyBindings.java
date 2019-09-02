package game.fish.cars;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Preferences;

public class KeyBindings {

	public static final String KEY_DRIVE = "driveForward";
	public static final String KEY_REVERSE = "driveBackward";
	public static final String KEY_LEFT = "turnLeft";
	public static final String KEY_RIGHT = "turnRight";
	public static final String KEY_BRAKE = "brake";
	public static final String KEY_ZOOMIN = "zoomIn";
	public static final String KEY_ZOOMOUT = "zoomOut";
	public static final String KEY_MENU = "menu";
	
	private static final String KEYBINDINGS_FILE = "carsKeyBindings";
	
	public HashMap<String, Integer> keyBindings;
	private Preferences bindingsFile;
	private Map<String, ?> savedBindings;
	
	public KeyBindings() {
		keyBindings = new HashMap<String, Integer>();
		bindingsFile = Gdx.app.getPreferences(KEYBINDINGS_FILE);
		savedBindings = bindingsFile.get();
		
		loadKeyBindings();
		saveKeyBindings();
	}
	
	public int getKeyBinding(String keyString) {
		return keyBindings.get(keyString);
	}
	
	public void setKey(String keyString, int keyBinding) {
		keyBindings.put(keyString,keyBinding);
		saveKeyBindings();
	}
	
	public boolean checkKeyBinding(String keyString) {
		return Gdx.input.isKeyPressed(getKeyBinding(keyString));
	}
	
	private void loadKeyBindings() {
		handleBinding("driveForward", Keys.W);
		handleBinding("driveBackward", Keys.S);
		handleBinding("turnLeft", Keys.A);
		handleBinding("turnRight", Keys.D);
		handleBinding("brake", Keys.SPACE);
		handleBinding("zoomIn", Keys.EQUALS);
		handleBinding("zoomOut", Keys.MINUS);
		handleBinding("menu", Keys.ESCAPE);
	}
	
	private void saveKeyBindings() {
		bindingsFile.put(keyBindings);
		bindingsFile.flush();
	}
	
	private void handleBinding(String keyString, int defaultKey) {
		if (savedBindings.containsKey(keyString)) {
			keyBindings.put(keyString, bindingsFile.getInteger(keyString));
		}
		else
			keyBindings.put(keyString, defaultKey);
	}
	
	
	
	
	
}
