package game.fish.cars;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.Audio;

import game.fish.cars.achievements.Achievement;
import game.fish.cars.views.AchievementsInterface;
import game.fish.cars.views.KeyBindingsInterface;
import game.fish.cars.views.MenuInterface;
import game.fish.cars.views.PlayScreen;
import game.fish.cars.views.SettingsInterface;
import game.fish.observers.AchievementListener;

import static game.fish.cars.Constants.PATH_MUSIC;
import static game.fish.cars.Constants.PLAY_SCREEN;
import static game.fish.cars.Constants.MENU_SCREEN;
import static game.fish.cars.Constants.SETTINGS_SCREEN;

import java.beans.PropertyChangeListener;

import static game.fish.cars.Constants.KEYBINDINGS_SCREEN;
import static game.fish.cars.Constants.ACHIEVEMENTS_SCREEN;

public class CarsGame extends Game {
	private Settings settings;
	private KeyBindings keyBindings;
	private Achievements achievements;
	private AchievementListener achievementListener;
	private AchievementOverlay achievementOverlay;
	private Music music;
	
	private PlayScreen playScreen;
	private MenuInterface menuScreen;
	private SettingsInterface settingsScreen;
	private KeyBindingsInterface keyBindingsScreen;
	private AchievementsInterface achievementsScreen;
	
	public void create() {
		settings = new Settings();
		keyBindings = new KeyBindings();
		achievements = new Achievements(this);
		achievementListener = new AchievementListener(achievements);
		achievementOverlay = new AchievementOverlay();
		
		music = Gdx.audio.newMusic(Gdx.files.internal(PATH_MUSIC));
		music.setLooping(true);
		music.setVolume(settings.getMusicVolume());
        if (settings.getMusicEnabled()) music.play();
		
		menuScreen = new MenuInterface(this);
		setScreen(this.menuScreen);
		
	}
	
	public void changeScreen(int screen) {
		switch(screen) {
		case PLAY_SCREEN:
			if (playScreen == null) playScreen = new PlayScreen(this, 0, 0);
			this.setScreen(playScreen);
			achievementListener.trackScreen(playScreen);
			break;
		case MENU_SCREEN:
			if (menuScreen == null) menuScreen = new MenuInterface(this);
			this.setScreen(menuScreen);
			achievementListener.trackScreen(menuScreen);
			break;
		case SETTINGS_SCREEN:
			if (settingsScreen == null) settingsScreen = new SettingsInterface(this);
			this.setScreen(settingsScreen);
			achievementListener.trackScreen(settingsScreen);
			break;
		case KEYBINDINGS_SCREEN:
			if (keyBindingsScreen == null) keyBindingsScreen = new KeyBindingsInterface(this);
			this.setScreen(keyBindingsScreen);
			achievementListener.trackScreen(keyBindingsScreen);
			break;
		case ACHIEVEMENTS_SCREEN:
			if (achievementsScreen == null) achievementsScreen = new AchievementsInterface(this);
			this.setScreen(achievementsScreen);
			achievementListener.trackScreen(achievementsScreen);
			break;
		}
	}
	
	public void changeScreen(int screen, int carChoice, int mapChoice) {
		switch(screen) {
		case PLAY_SCREEN:
			if (playScreen == null) playScreen = new PlayScreen(this, carChoice, mapChoice);
			this.setScreen(playScreen);
			achievementListener.trackScreen(playScreen);
			break;
		case MENU_SCREEN:
			if (menuScreen == null) menuScreen = new MenuInterface(this);
			this.setScreen(menuScreen);
			achievementListener.trackScreen(menuScreen);
			break;
		case SETTINGS_SCREEN:
			if (settingsScreen == null) settingsScreen = new SettingsInterface(this);
			this.setScreen(settingsScreen);
			achievementListener.trackScreen(settingsScreen);
			break;
		case KEYBINDINGS_SCREEN:
			if (keyBindingsScreen == null) keyBindingsScreen = new KeyBindingsInterface(this);
			this.setScreen(keyBindingsScreen);
			achievementListener.trackScreen(keyBindingsScreen);
			break;
		case ACHIEVEMENTS_SCREEN:
			if (achievementsScreen == null) achievementsScreen = new AchievementsInterface(this);
			this.setScreen(achievementsScreen);
			achievementListener.trackScreen(achievementsScreen);
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

	public void displayAchievement(Achievement achievement) {
		achievementOverlay.displayAchievement(achievement);
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
	
	public PropertyChangeListener getAchievementListener() {
		return achievementListener;
	}

	public AchievementOverlay getAchievementOverlay() {
		return achievementOverlay;
	}
	
	public Music getMusic() {
		return music;
	}
	
	@Override
	public void render() {
		super.render();
	}
	
	@Override
	public void dispose() {
		super.dispose();
		music.dispose();
		achievementOverlay.dispose();
	}
}
