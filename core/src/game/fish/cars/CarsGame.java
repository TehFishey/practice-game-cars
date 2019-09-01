package game.fish.cars;

import com.badlogic.gdx.Game;

import game.fish.cars.views.AchievementsScreen;
import game.fish.cars.views.LoadingScreen;
import game.fish.cars.views.MenuScreen;
import game.fish.cars.views.PlayScreen;
import game.fish.cars.views.SettingsScreen;

import static game.fish.cars.Constants.PLAY_SCREEN;
import static game.fish.cars.Constants.MENU_SCREEN;
import static game.fish.cars.Constants.SETTINGS_SCREEN;
import static game.fish.cars.Constants.ACHIEVEMENTS_SCREEN;
import static game.fish.cars.Constants.LOADING_SCREEN;

//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CarsGame extends Game {
	public Settings settings;
	
	private LoadingScreen loadingScreen;
	private PlayScreen playScreen;
	private MenuScreen menuScreen;
	private SettingsScreen settingsScreen;
	private AchievementsScreen achievementsScreen;
	
	public void create() {
		settings = new Settings();
		menuScreen = new MenuScreen(this);
		setScreen(this.menuScreen);
	}

	public void changeScreen(int screen) {
		switch(screen) {
		case LOADING_SCREEN:
			if (loadingScreen == null) loadingScreen = new LoadingScreen(this);
			this.setScreen(loadingScreen);
			break;
		case PLAY_SCREEN:
			if (playScreen == null) playScreen = new PlayScreen(this);
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
		case ACHIEVEMENTS_SCREEN:
			if (achievementsScreen == null) achievementsScreen = new AchievementsScreen(this);
			this.setScreen(achievementsScreen);
			break;
		}
	}
	
	public void clearScreen(int screen) {
		switch(screen) {
		case LOADING_SCREEN:
			if (loadingScreen != null) {
				loadingScreen.dispose();
				loadingScreen = null;
			}
			break;
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
		case ACHIEVEMENTS_SCREEN:
			if (achievementsScreen != null) {
				achievementsScreen.dispose();
				achievementsScreen = null;
			}
			break;
		}
	}
	
	public boolean doesScreenExist(int screen) {
		switch(screen) {
		case LOADING_SCREEN:
			return (loadingScreen != null);
		case PLAY_SCREEN:
			return (playScreen != null);
		case MENU_SCREEN:
			return (menuScreen != null);
		case SETTINGS_SCREEN:
			return (settingsScreen != null);
		case ACHIEVEMENTS_SCREEN:
			return (achievementsScreen != null);
		default:
			return false;
		}
	}
	
	public Settings getSettings() {
		return settings;
	}
	
	
	@Override
	public void render() {
		super.render();
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}
}
