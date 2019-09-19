package game.fish.cars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.physics.box2d.Fixture;

import game.fish.cars.achievements.Achievement;
import game.fish.cars.achievements.BooleanAchievement;
import game.fish.cars.achievements.IncrementalAchievement;
import game.fish.cars.achievements.MultipleAchievement;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import static game.fish.cars.Constants.PATH_ACHIEVEMENTS;
import static game.fish.cars.Constants.CAR_FWDCAR;
import static game.fish.cars.Constants.CAR_AWDCAR;
import static game.fish.cars.Constants.CAR_MOTORCYCLE;
import static game.fish.cars.Constants.CAR_HOVERCAR;

import static game.fish.cars.Constants.PLAY_SCREEN;
import static game.fish.cars.Constants.MENU_SCREEN;
import static game.fish.cars.Constants.SETTINGS_SCREEN;
import static game.fish.cars.Constants.KEYBINDINGS_SCREEN;
import static game.fish.cars.Constants.ACHIEVEMENTS_SCREEN;

import static game.fish.cars.Constants.MAP_MAP1;
import static game.fish.cars.Constants.MAP_MAP2;
import static game.fish.cars.Constants.MAP_MAP3;

import static game.fish.cars.KeyBindings.KEY_DRIVE;
import static game.fish.cars.KeyBindings.KEY_REVERSE;
import static game.fish.cars.KeyBindings.KEY_LEFT;
import static game.fish.cars.KeyBindings.KEY_RIGHT;
import static game.fish.cars.KeyBindings.KEY_BRAKE;
import static game.fish.cars.KeyBindings.KEY_ZOOMIN;
import static game.fish.cars.KeyBindings.KEY_ZOOMOUT;
import static game.fish.cars.KeyBindings.KEY_MENU;

import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Achievements {

	private final CarsGame parent;
	private final PropertyChangeSupport achievementPCS;
	private Preferences achievementsFile;
	private LinkedHashMap<String, Achievement> currentAchievements;
	private Map<String, ?> achievementsFileIndex;
	private HashMap<String, Integer> currentProgress;
	
	public Achievements(CarsGame parent) {
		this.parent = parent;
		this.achievementPCS = new PropertyChangeSupport(this);
		achievementsFile = Gdx.app.getPreferences(PATH_ACHIEVEMENTS);
		achievementsFileIndex = achievementsFile.get();
		currentAchievements = generateAchievementDefaults();
		currentProgress = new HashMap<String, Integer>();
		
		loadAchievementProgress();
		
	}
	
	public LinkedHashMap<String, Achievement> getAchievements() {
		return currentAchievements;
	}
	
	public void takePropertyChange(String triggerPropertyName, Object newValue) {
		boolean update = false;
		for (Achievement achievementValue : currentAchievements.values()) {
			if (achievementValue.getTriggerProperty() == triggerPropertyName && (!achievementValue.getCompleted())) {
				achievementValue.update(newValue);
				if (achievementValue.getCompleted()) { 
					if (achievementPCS.getPropertyChangeListeners().length == 0) 
						achievementPCS.addPropertyChangeListener(parent.getAchievementListener());
					achievementPCS.firePropertyChange("achieveEvent", null, achievementValue);
					parent.displayAchievement(achievementValue);
				}
				update = true;
			}
		}
		
		if (update) saveAchievementProgress();
	}
	
	private LinkedHashMap<String, Achievement> generateAchievementDefaults() {
		
		LinkedHashMap<String, Achievement> newMap = new LinkedHashMap<String, Achievement>();
		newMap.put("gameStart", new BooleanAchievement("Player", "Started the game", "gameStart") 
		{
			@Override
			protected boolean condition(Object newValue) {
				return (boolean) newValue;
			}
    	});
		newMap.put("gameExit", new BooleanAchievement("Quitter", "Exited the game.", "gameEnd") 
		{
			@Override
			protected boolean condition(Object newValue) {
				return (boolean) newValue;
			}
    	});
		newMap.put("viewMenu", new BooleanAchievement("Menu Viewer", "Navigated to the menu screen.", "screenChange") 
		{
			@Override
			protected boolean condition(Object newValue) {
				return ((int) newValue == MENU_SCREEN);
			}
    	});
		newMap.put("viewSettings", new BooleanAchievement("Settings Viewer", "Navigated to the settings screen.", "screenChange") 
		{
			@Override
			protected boolean condition(Object newValue) {
				return ((int) newValue == SETTINGS_SCREEN);
			}
    	});
		newMap.put("viewAcheivements", new BooleanAchievement("Progress Viewer", "Navigated to the achievements screen.", "screenChange") 
		{
			@Override
			protected boolean condition(Object newValue) {
				return ((int) newValue == ACHIEVEMENTS_SCREEN);
			}
    	});
		newMap.put("viewKeyBindings", new BooleanAchievement("Key Viewer", "Navigated to the key bindings screen.", "screenChange") 
		{
			@Override
			protected boolean condition(Object newValue) {
				return ((int) newValue == KEYBINDINGS_SCREEN);
			}
    	});
		newMap.put("viewPlay", new BooleanAchievement("Game Player", "Started a game.", "screenChange") 
		{
			@Override
			protected boolean condition(Object newValue) {
				return ((int) newValue == PLAY_SCREEN);
			}
    	});
		newMap.put("played30s", new IncrementalAchievement("Played 30 Seconds", "Spent 30 seconds playing any map", "timeElapsed", 30)
		{
			@Override
			protected int incrementalCondition(Object newValue) {
				return 1;
		}
		});
		newMap.put("played60s", new IncrementalAchievement("Played 1 Minute", "Spent 1 minute playing any map", "timeElapsed", 60)
		{
			@Override
			protected int incrementalCondition(Object newValue) {
				return 1;
		}
		});
		newMap.put("played180s", new IncrementalAchievement("Played 3 Minutes", "Spent 3 minutes playing any map", "timeElapsed", 180)
		{
			@Override
			protected int incrementalCondition(Object newValue) {
				return 1;
		}
		});
		newMap.put("played300s", new IncrementalAchievement("Played 5 Minutes", "Spent 5 minutes playing any map", "timeElapsed", 300)
		{
			@Override
			protected int incrementalCondition(Object newValue) {
				return 1;
		}
		});
		newMap.put("oneAchieve", new IncrementalAchievement("Beginner", "Earned an Achievement.", "achieveEvent", 1)
		{
			@Override
			protected int incrementalCondition(Object newValue) {
				return 1;
		}
		});
		newMap.put("HalfAchieve", new IncrementalAchievement("Halfway", "Earned half of the game's achievements.", "achieveEvent", 18)
		{
			@Override
			protected int incrementalCondition(Object newValue) {
				return 1;
		}
		});
		newMap.put("AllAchieve", new IncrementalAchievement("Completionist", "Earned all other achievements.", "achieveEvent", 35)
		{
			@Override
			protected int incrementalCondition(Object newValue) {
				return 1;
		}
		});
		newMap.put("musicVolumeMaxed", new BooleanAchievement("Music Listener", "Set the game music to maximum volume.", "musicVolume") 
		{
			@Override
			protected boolean condition(Object newValue) {
				float musicVolume = (float) newValue;
				return (musicVolume == 1);
			}
		});
		newMap.put("musicMuted", new BooleanAchievement("Music Hater", "Muted the game's music.", "musicPlaying") 
		{
			@Override
			protected boolean condition(Object newValue) {
				boolean musicPlaying = (boolean) newValue;
				return !musicPlaying;
			}
    	});
		newMap.put("playerDrivingFast", new BooleanAchievement("Speeder", "Drove your vehicle at high speed.", "playerSpeed") 
		{
			@Override
			protected boolean condition(Object newValue) {
				float playerSpeed = (float) newValue;
				return (playerSpeed >= 100f);
			}
		});
		newMap.put("playerMinZoom", new BooleanAchievement("Extreme Closeup", "Zoomed the camera in as close as possible.", "cameraZoom") 
		{
			@Override
			protected boolean condition(Object newValue) {
				float cameraZoom = (float) newValue;
				return (cameraZoom == 2f);
			}
		});
		newMap.put("playerMaxZoom", new BooleanAchievement("Zoomed Out", "Zoomed the camera out as far as possible.", "cameraZoom") 
		{
			@Override
			protected boolean condition(Object newValue) {
				float cameraZoom = (float) newValue;
				return (cameraZoom >= 50f);
			}
		});
		newMap.put("fwdCarDriven", new BooleanAchievement("Commuter", "Started a game driving a front-wheel drive car.", "carType") 
		{
			@Override
			protected boolean condition(Object newValue) {
				float carType = (int) newValue;
				return (carType == CAR_FWDCAR);
			}
		});
		newMap.put("awdCarDriven", new BooleanAchievement("Offroader", "Started a game driving an all-wheel drive car.", "carType") 
		{
			@Override
			protected boolean condition(Object newValue) {
				float carType = (int) newValue;
				return (carType == CAR_AWDCAR);
			}
		});
		newMap.put("motorcycleDriven", new BooleanAchievement("Biker", "Started a game driving a motorcycle.", "carType") 
		{
			@Override
			protected boolean condition(Object newValue) {
				float carType = (int) newValue;
				return (carType == CAR_MOTORCYCLE);
			}
		});
		newMap.put("hoverCarDriven", new BooleanAchievement("Not a Testla", "Started a game driving a hover car.", "carType") 
		{
			@Override
			protected boolean condition(Object newValue) {
				float carType = (int) newValue;
				return (carType == CAR_HOVERCAR);
			}
		});
		newMap.put("allCarsDriven", new MultipleAchievement("Car Collector", "Started a game driving each car.", "carType", 4)
		{
			@Override
			protected boolean[] multipleCondition(Object newValue) {
				boolean[] update = values;
				int carType = (int) newValue;
				
				switch (carType){
				case CAR_FWDCAR: 
					update[0] = true;
					break;
				case CAR_AWDCAR: 
					update[1] = true;
					break;
				case CAR_MOTORCYCLE: 
					update[2] = true;
					break;
				case CAR_HOVERCAR: 
					update[3] = true;
					break;
				}
				return update;
		}
		});
		newMap.put("cityMapPlayed", new BooleanAchievement("City Rat", "Started a game on the city map.", "mapChoice") 
		{
			@Override
			protected boolean condition(Object newValue) {
				float mapType = (int) newValue;
				return (mapType == MAP_MAP1);
			}
		});
		newMap.put("trackMapPlayed", new BooleanAchievement("Jockey", "Started a game on the racetrack map.", "mapChoice") 
		{
			@Override
			protected boolean condition(Object newValue) {
				float mapType = (int) newValue;
				return (mapType == MAP_MAP2);
		}
		});
		newMap.put("parkingMapPlayed", new BooleanAchievement("Shopper", "Started a game on the parking lot map.", "mapChoice") 
		{
			@Override
			protected boolean condition(Object newValue) {
				float mapType = (int) newValue;
				return (mapType == MAP_MAP3);
		}
		});
		newMap.put("allMapPlayed", new MultipleAchievement("Traveller", "Started a game on each map.", "mapChoice", 3)
		{
			@Override
			protected boolean[] multipleCondition(Object newValue) {
				boolean[] update = values;
				int mapType = (int) newValue;
				
				switch (mapType){
				case MAP_MAP1: 
					update[0] = true;
					break;
				case MAP_MAP2: 
					update[1] = true;
					break;
				case MAP_MAP3: 
					update[2] = true;
					break;
				}
				return update;
		}
		});
		newMap.put("anyKeyMapped", new BooleanAchievement("Key Binder", "Re-Bound a key.", "keyMapped") 
		{
			@Override
			protected boolean condition(Object newValue) {
				return true;
		}
		});
		newMap.put("arrowKeysMapped", new MultipleAchievement("Arrow Key User", "Re-bound movement keys to arrow keys.", "keyMapped", 4)
		{
			@Override
			protected boolean[] multipleCondition(Object obj) {
				boolean[] update = values;
				
				Array bindingInfo = (Array) obj;
				String bindCommand = (String) bindingInfo.get(0);
				int bindKey = (int) bindingInfo.get(1);
				
				switch (bindCommand){
				case KEY_DRIVE: 
					if (bindKey == Keys.UP) update[0] = true;
					else update[0] = false;
					break;
				case KEY_REVERSE: 
					if (bindKey == Keys.DOWN) update[1] = true;
					else update[1] = false;
					break;
				case KEY_LEFT: 
					if (bindKey == Keys.LEFT) update[2] = true;
					else update[2] = false;
					break;
				case KEY_RIGHT: 
					if (bindKey == Keys.RIGHT) update[3] = true;
					else update[3] = false;
					break;
				}
				return update;
			}
		});
		newMap.put("allKeysMapped", new MultipleAchievement("Custom Control Scheme", "Re-bound all keys to non-defaults.", "keyMapped", 8)
		{
			@Override
			protected boolean[] multipleCondition(Object obj) {
				boolean[] update = values;
				
				Array bindingInfo = (Array) obj;
				String bindCommand = (String) bindingInfo.get(0);
				int bindKey = (int) bindingInfo.get(1);
				
				switch (bindCommand){
				case KEY_DRIVE: 
					if (bindKey != Keys.W) update[0] = true;
					else update[0] = false;
					break;
				case KEY_REVERSE: 
					if (bindKey != Keys.S) update[1] = true;
					else update[1] = false;
					break;
				case KEY_LEFT: 
					if (bindKey != Keys.A) update[2] = true;
					else update[2] = false;
					break;
				case KEY_RIGHT: 
					if (bindKey != Keys.D) update[3] = true;
					else update[3] = false;
					break;
				case KEY_BRAKE: 
					if (bindKey != Keys.SPACE) update[4] = true;
					else update[4] = false;
					break;
				case KEY_ZOOMIN: 
					if (bindKey != Keys.EQUALS) update[5] = true;
					else update[5] = false;
					break;
				case KEY_ZOOMOUT: 
					if (bindKey != Keys.MINUS) update[6] = true;
					else update[6] = false;
					break;
				case KEY_MENU: 
					if (bindKey != Keys.ESCAPE) update[7] = true;
					else update[7] = false;
					break;
				}
				return update;
			}
		});
		newMap.put("crashedOnce", new IncrementalAchievement("Fender Bender", "Crashed a vehicle.", "contactEvent", 1)
		{
			@Override
			protected int incrementalCondition(Object newValue) {
				return 1;
		}
		});
		newMap.put("crashedTen", new IncrementalAchievement("Wrecked", "Crashed a vehicle ten times.", "contactEvent", 10)
		{
			@Override
			protected int incrementalCondition(Object newValue) {
				return 1;
		}
		});
		newMap.put("crashedFifty", new IncrementalAchievement("Uninsurable", "Crashed a vehicle fifty times.", "contactEvent", 50)
		{
			@Override
			protected int incrementalCondition(Object newValue) {
				return 1;
		}
		});
		newMap.put("crashedHundred", new IncrementalAchievement("Crash Test Dummy", "Crashed a vehicle one hundred times.", "contactEvent", 100)
		{
			@Override
			protected int incrementalCondition(Object newValue) {
				return 1;
		}
		});
		newMap.put("speedCrash", new BooleanAchievement("High-Speed Impact", "Crashed a vehicle at high speed.", "contactEvent")
		{
			@Override
			protected boolean condition(Object newValue) {
				Array contactInfo = (Array) newValue;
				Fixture player = (Fixture) contactInfo.get(0);
				Vector2 velocity = player.getBody().getLinearVelocity();
				float speed = Math.abs(velocity.x) + Math.abs(velocity.y);
				if (speed >= 100) return true;
				return false;
		}
		});

		return newMap;
	}

	private void loadAchievementProgress() {
		for (String key : achievementsFileIndex.keySet()) {
	
			int progress = achievementsFile.getInteger(key);

			for (Entry<String, Achievement> achievementEntry : currentAchievements.entrySet()) {
				if (achievementEntry.getKey().equals(key)) {
					
					Achievement achievementValue = achievementEntry.getValue();
					achievementValue.loadProgress(progress);
				}
			}
		}	
	}
		
	private void saveAchievementProgress() {
		for (Entry<String, Achievement> achievementEntry : currentAchievements.entrySet()) {
			
			String achievementKey = achievementEntry.getKey();
			Achievement achievementValue = achievementEntry.getValue();
			int progress = achievementValue.getProgress();

			currentProgress.put(achievementKey, progress);
		}
		
		achievementsFile.put(currentProgress);
		achievementsFile.flush();
	}
}
