package game.fish.cars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import static game.fish.cars.Constants.PATH_SETTINGS;

public class Settings {
		
	private static final String MUSIC_VOLUME = "musicVolume";
	private static final String MUSIC_ENABLED = "musicEnabled";
	
	private Preferences settingsFile;
	
	public Settings() {
		settingsFile = Gdx.app.getPreferences(PATH_SETTINGS);
		getMusicVolume();
		getMusicEnabled();
	}
	
	public float getMusicVolume() {
		return settingsFile.getFloat(MUSIC_VOLUME, 0.5f);
	}
	public void setMusicVolume(float volume) {
		settingsFile.putFloat(MUSIC_VOLUME, volume);
		settingsFile.flush();		
	}
	
	public boolean getMusicEnabled() {
		return settingsFile.getBoolean(MUSIC_ENABLED, true);
	}
	public void setMusicEnabled(boolean muted) {
		settingsFile.putBoolean(MUSIC_ENABLED, muted);
		settingsFile.flush();
	}
}
