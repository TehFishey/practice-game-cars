package game.fish.cars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Settings {
	
	public Settings() {}
	
	private static final String MUSIC_VOLUME = "musicVolume";
	private static final String MUSIC_ENABLED = "musicEnabled";
	private static final String SETTINGS_FILE = "carsSettings";
	
	private Preferences getSettings() {
		return Gdx.app.getPreferences(SETTINGS_FILE);
	}
	
	public float getMusicVolume() {
		return getSettings().getFloat(MUSIC_VOLUME, 0.5f);
	}
	public void setMusicVolume(float volume) {
		getSettings().putFloat(MUSIC_VOLUME, volume);
		getSettings().flush();		
	}
	
	public boolean getMusicEnabled() {
		return getSettings().getBoolean(MUSIC_ENABLED, true);
	}
	public void setMusicEnabled(boolean muted) {
		getSettings().putBoolean(MUSIC_ENABLED, muted);
		getSettings().flush();
	}
}
