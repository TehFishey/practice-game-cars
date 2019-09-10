package game.fish.cars;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Preferences;

import static game.fish.cars.Constants.PATH_KEYBINDINGS;

public class KeyBindings {

	public static final String KEY_DRIVE = "driveForward";
	public static final String KEY_REVERSE = "driveBackward";
	public static final String KEY_LEFT = "turnLeft";
	public static final String KEY_RIGHT = "turnRight";
	public static final String KEY_BRAKE = "brake";
	public static final String KEY_ZOOMIN = "zoomIn";
	public static final String KEY_ZOOMOUT = "zoomOut";
	public static final String KEY_MENU = "menu";
	
	public HashMap<String, Integer> keyBindings;
	private Preferences bindingsFile;
	private Map<String, ?> savedBindings;
	
	public KeyBindings() {
		bindingsFile = Gdx.app.getPreferences(PATH_KEYBINDINGS);
		savedBindings = bindingsFile.get();
		keyBindings = new HashMap<String, Integer>();
		
		loadKeyBindings();
		saveKeyBindings();
	}
	
	public int getKeyBinding(String keyString) {
		return keyBindings.get(keyString);
	}
	
	public void setKeyBinding(String keyString, int keyBinding) {
		keyBindings.put(keyString,keyBinding);
		saveKeyBindings();
	}
	
	public boolean isKeyBindingPressed(String keyString) {
		return Gdx.input.isKeyPressed(getKeyBinding(keyString));
	}
	
	private void loadKeyBindings() {
		handleKeyBinding("driveForward", Keys.W);
		handleKeyBinding("driveBackward", Keys.S);
		handleKeyBinding("turnLeft", Keys.A);
		handleKeyBinding("turnRight", Keys.D);
		handleKeyBinding("brake", Keys.SPACE);
		handleKeyBinding("zoomIn", Keys.EQUALS);
		handleKeyBinding("zoomOut", Keys.MINUS);
		handleKeyBinding("menu", Keys.ESCAPE);
	}
	
	private void saveKeyBindings() {
		bindingsFile.put(keyBindings);
		bindingsFile.flush();
	}
	
	private void handleKeyBinding(String keyString, int defaultKey) {
		if (savedBindings.containsKey(keyString)) 
			keyBindings.put(keyString, bindingsFile.getInteger(keyString));
		else 
			keyBindings.put(keyString, defaultKey);
	}
	
	
	
	
	
}
