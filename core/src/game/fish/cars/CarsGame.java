package game.fish.cars;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.Audio;

import game.fish.cars.views.AchievementsScreen;
import game.fish.cars.views.KeyBindingsScreen;
import game.fish.cars.views.MenuScreen;
import game.fish.cars.views.PlayScreen;
import game.fish.cars.views.SettingsScreen;
import game.fish.observers.AchievementListener;

import static game.fish.cars.Constants.PATH_MUSIC;
import static game.fish.cars.Constants.PLAY_SCREEN;
import static game.fish.cars.Constants.MENU_SCREEN;
import static game.fish.cars.Constants.SETTINGS_SCREEN;

import java.beans.PropertyChangeListener;

import static game.fish.cars.Constants.KEYBINDINGS_SCREEN;
import static game.fish.cars.Constants.ACHIEVEMENTS_SCREEN;

public class CarsGame extends Game {
	public Settings settings;
	public KeyBindings keyBindings;
	public Achievements achievements;
	private PropertyChangeListener achievementListener;
	private Music music;
	
	private PlayScreen playScreen;
	private MenuScreen menuScreen;
	private SettingsScreen settingsScreen;
	private KeyBindingsScreen keyBindingsScreen;
	private AchievementsScreen achievementsScreen;
	
	public void create() {
		settings = new Settings();
		keyBindings = new KeyBindings();
		achievements = new Achievements();
		achievementListener = new AchievementListener(achievements);
		
		music = Gdx.audio.newMusic(Gdx.files.internal(PATH_MUSIC));
		music.setLooping(true);
		music.setVolume(settings.getMusicVolume());
        if (settings.getMusicEnabled()) music.play();
		
		menuScreen = new MenuScreen(this);
		setScreen(this.menuScreen);
		
	}
	
	public void changeScreen(int screen, int carChoice, int mapChoice) {
		switch(screen) {
		case PLAY_SCREEN:
			if (playScreen == null) playScreen = new PlayScreen(this, carChoice, mapChoice);
			this.setScreen(playScreen);
			break;
		case MENU_SCREEN:
			if (menuScreen == null) menuScreen = new MenuScreen(this);
			this.setScreen(menuScreen);
			break;
		case SETTINGS_SCREEN:
			if (settingsScreen == null) settingsScreen = new SettingsScreen(this);
			this.setScreen(settingsScreen);
			break;
		case KEYBINDINGS_SCREEN:
			if (keyBindingsScreen == null) keyBindingsScreen = new KeyBindingsScreen(this);
			this.setScreen(keyBindingsScreen);
			break;
		case ACHIEVEMENTS_SCREEN:
			if (achievementsScreen == null) achievementsScreen = new AchievementsScreen(this);
			this.setScreen(achievementsScreen);
			break;
		}
	}
	
	public void changeScreen(int screen) {
		switch(screen) {
		case PLAY_SCREEN:
			if (playScreen == null) playScreen = new PlayScreen(this, 0, 0);
			this.setScreen(playScreen);
			break;
		case MENU_SCREEN:
			if (menuScreen == null) menuScreen = new MenuScreen(this);
			this.setScreen(menuScreen);
			break;
		case SETTINGS_SCREEN:
			if (settingsScreen == null) settingsScreen = new SettingsScreen(this);
			this.setScreen(settingsScreen);
			break;
		case KEYBINDINGS_SCREEN:
			if (keyBindingsScreen == null) keyBindingsScreen = new KeyBindingsScreen(this);
			this.setScreen(keyBindingsScreen);
			break;
		case ACHIEVEMENTS_SCREEN:
			if (achievementsScreen == null) achievementsScreen = new AchievementsScreen(this);
			this.setScreen(achievementsScreen);
			break;
		}
	}
	
	public void clearScreen(int screen) {
		switch(screen) {
		case PLAY_SCREEN:
			if (playScreen != null) {
				playScreen.dispose();
				playScreen = null;
			}
			break;
		case MENU_SCREEN:
			if (menuScreen != null) {
				menuScreen.dispose();
				menuScreen = null;
			}
			break;
		case SETTINGS_SCREEN:
			if (settingsScreen != null) {
				settingsScreen.dispose();
				settingsScreen = null;
			}
			break;
		case KEYBINDINGS_SCREEN:
			if (keyBindingsScreen != null) {
				keyBindingsScreen.dispose();
				keyBindingsScreen = null;
			}
			break;
		case ACHIEVEMENTS_SCREEN:
			if (achievementsScreen != null) {
				achievementsScreen.dispose();
				achievementsScreen = null;
			}
			break;
		}
	}
	
	public boolean getScreenExists(int screen) {
		switch(screen) {
		case PLAY_SCREEN:
			return (playScreen != null);
		case MENU_SCREEN:
			return (menuScreen != null);
		case SETTINGS_SCREEN:
			return (settingsScreen != null);
		case KEYBINDINGS_SCREEN:
			return (keyBindingsScreen != null);
		case ACHIEVEMENTS_SCREEN:
			return (achievementsScreen != null);
		default:
			return false;
		}
	}
	
	public Settings getSettings() {
		return settings;
	}
	
	public KeyBindings getKeyBindings() {
		return keyBindings;
	}
	
	public Achievements getAchievements() {
		return achievements;
	}
	
	public Music getMusic() {
		return music;
	}
	
	public PropertyChangeListener getAchievementListener() {
		return achievementListener;
	}
	
	@Override
	public void render() {
		super.render();
	}
	
	@Override
	public void dispose() {
		super.dispose();
		music.dispose();
	}
}
