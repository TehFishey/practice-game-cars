package game.fish.cars;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;

import game.fish.cars.achievements.Achievement;
import game.fish.cars.listeners.AchievementListener;
import game.fish.cars.views.AchievementsInterface;
import game.fish.cars.views.KeyBindingsInterface;
import game.fish.cars.views.MenuInterface;
import game.fish.cars.views.PlayScreen;
import game.fish.cars.views.SettingsInterface;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;

import static game.fish.cars.Constants.PATH_MUSIC;
import static game.fish.cars.Constants.SCREEN;
import static game.fish.cars.Constants.CAR;
import static game.fish.cars.Constants.MAP;

public class CarsGame extends Game {
	private Settings settings;
	private KeyBindings keyBindings;
	private Achievements achievements;
	private AchievementListener achievementListener;
	private AchievementOverlay achievementOverlay;
	private PropertyChangeSupport achievementPCS;
	private Music music;
	
	private PlayScreen playScreen;
	private MenuInterface menuScreen;
	private SettingsInterface settingsScreen;
	private KeyBindingsInterface keyBindingsScreen;
	private AchievementsInterface achievementsScreen;
	private HashMap<SCREEN, Screen> screensMap;
	
	public void create() {
		settings = new Settings();
		keyBindings = new KeyBindings();
		achievements = new Achievements(this);
		achievementListener = new AchievementListener(achievements);
		achievementOverlay = new AchievementOverlay();
		achievementPCS = new PropertyChangeSupport(this);
		achievementPCS.addPropertyChangeListener(getAchievementListener());
		
		screensMap = new HashMap<SCREEN, Screen>() {
        	{
        		put(SCREEN.PLAY_SCREEN, playScreen);
        		put(SCREEN.MENU_SCREEN, menuScreen);
        		put(SCREEN.SETTINGS_SCREEN, settingsScreen);
        		put(SCREEN.KEYBINDINGS_SCREEN, keyBindingsScreen);
        		put(SCREEN.ACHIEVEMENTS_SCREEN, achievementsScreen);
        		
        	}
        };
		
		music = Gdx.audio.newMusic(Gdx.files.internal(PATH_MUSIC));
		music.setLooping(true);
		music.setVolume(settings.getMusicVolume());
        if (settings.getMusicEnabled()) music.play();
        
		menuScreen = new MenuInterface(this);
		setScreen(this.menuScreen);
		achievementPCS.firePropertyChange("gameStart",null,true);
	}
	
	public void changeScreen(SCREEN screen) {
		Screen s = screensMap.get(screen);
		if (s == null) s = createScreen(screen, CAR.FWDCAR, MAP.MAP1);
		this.setScreen(s);
		achievementPCS.firePropertyChange("screenChange",null,screen);
	}
	
	public void changeScreen(SCREEN screen, CAR carChoice, MAP mapChoice) {
		Screen s = screensMap.get(screen);
		if (s == null) s = createScreen(screen, carChoice, mapChoice);
		this.setScreen(s);
		achievementPCS.firePropertyChange("screenChange",null,screen);
	}
	
	public void clearScreen(SCREEN screen) {
		Screen s = screensMap.get(screen);
		if (s != null) {
			s.dispose();
			screensMap.remove(s);
		}
	}

	public boolean getScreenExists(SCREEN screen) {
		Screen s = screensMap.get(screen);
		return (s != null);
	}
	
	public void displayAchievement(Achievement achievement) {
		achievementOverlay.displayAchievement(achievement);
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
		if (playScreen != null) playScreen.dispose();
		if (menuScreen != null) menuScreen.dispose();
		if (settingsScreen != null) settingsScreen.dispose();
		if (keyBindingsScreen != null) keyBindingsScreen.dispose();
		if (achievementsScreen != null) achievementsScreen.dispose();
	}
	
	private Screen createScreen(SCREEN type, CAR carChoice, MAP mapChoice) {
		switch(type) {
		case PLAY_SCREEN:
			return new PlayScreen(this, carChoice, mapChoice);
		case MENU_SCREEN:
			return new MenuInterface(this);
		case SETTINGS_SCREEN:
			return new SettingsInterface(this);
		case KEYBINDINGS_SCREEN:
			return new KeyBindingsInterface(this);
		case ACHIEVEMENTS_SCREEN:
			return new AchievementsInterface(this);
		default:
			return new MenuInterface(this);
		}
	}
}
