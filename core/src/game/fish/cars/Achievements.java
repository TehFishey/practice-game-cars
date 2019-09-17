package game.fish.cars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import game.fish.cars.achievements.Achievement;
import game.fish.cars.achievements.BooleanAchievement;
import game.fish.cars.achievements.IncrementalAchievement;
import game.fish.cars.achievements.MultipleAchievement;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.utils.Array;

import static game.fish.cars.Constants.PATH_ACHIEVEMENTS;
import static game.fish.cars.Constants.CAR_FWDCAR;
import static game.fish.cars.Constants.CAR_AWDCAR;
import static game.fish.cars.Constants.CAR_MOTORCYCLE;
import static game.fish.cars.Constants.CAR_HOVERCAR;

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

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Achievements {

	private CarsGame parent;
	private LinkedHashMap<String, Achievement>currentAchievements;
	private Preferences achievementsFile;
	private Map<String, ?> achievementsFileIndex;
	private HashMap<String, Integer> currentProgress;
	
	public Achievements(CarsGame parent) {
		this.parent = parent;
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
				if (achievementValue.getCompleted()) parent.displayAchievement(achievementValue);
				update = true;
			}
		}
		
		if (update) saveAchievementProgress();
	}
	
	private LinkedHashMap<String, Achievement> generateAchievementDefaults() {
		
		LinkedHashMap<String, Achievement> newMap = new LinkedHashMap<String, Achievement>();
		newMap.put("musicVolumeMaxed", new BooleanAchievement("It's a Classic", "Set the game music to maximum volume.", "musicVolume") 
		{
			@Override
			protected boolean condition(Object newValue) {
				float musicVolume = (float) newValue;
				return (musicVolume == 1);
			}
		});
		newMap.put("musicMuted", new BooleanAchievement("Not a Classic", "Muted the game's music.", "musicPlaying") 
		{
			@Override
			protected boolean condition(Object newValue) {
				boolean musicPlaying = (boolean) newValue;
				return !musicPlaying;
			}
    	});
		newMap.put("playerDrivingFast", new BooleanAchievement("Speeder", "Drive your vehicle at high speed.", "playerSpeed") 
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
		newMap.put("playerMaxZoom", new BooleanAchievement("Sattelite Images", "Zoomed the camera out as far as possible.", "cameraZoom") 
		{
			@Override
			protected boolean condition(Object newValue) {
				float cameraZoom = (float) newValue;
				return (cameraZoom >= 50f);
			}
		});
		newMap.put("fwdCarDriven", new BooleanAchievement("Commuter", "Start a game driving a front-wheel drive car.", "carType") 
		{
			@Override
			protected boolean condition(Object newValue) {
				float carType = (int) newValue;
				return (carType == CAR_FWDCAR);
			}
		});
		newMap.put("awdCarDriven", new BooleanAchievement("Offroader", "Start a game driving an all-wheel drive car.", "carType") 
		{
			@Override
			protected boolean condition(Object newValue) {
				float carType = (int) newValue;
				return (carType == CAR_AWDCAR);
			}
		});
		newMap.put("motorcycleDriven", new BooleanAchievement("Biker", "Start a game driving a motorcycle.", "carType") 
		{
			@Override
			protected boolean condition(Object newValue) {
				float carType = (int) newValue;
				return (carType == CAR_MOTORCYCLE);
			}
		});
		newMap.put("hoverCarDriven", new BooleanAchievement("Not a Testla", "Start a game driving a hover car.", "carType") 
		{
			@Override
			protected boolean condition(Object newValue) {
				float carType = (int) newValue;
				return (carType == CAR_HOVERCAR);
			}
		});
		newMap.put("cityMapPlayed", new BooleanAchievement("City Rat", "Start a game on the city map.", "mapChoice") 
		{
			@Override
			protected boolean condition(Object newValue) {
				float carType = (int) newValue;
				return (carType == MAP_MAP1);
			}
		});
		newMap.put("trackMapPlayed", new BooleanAchievement("Jockey", "Start a game on the racetrack map", "mapChoice") 
		{
			@Override
			protected boolean condition(Object newValue) {
				float carType = (int) newValue;
				return (carType == MAP_MAP2);
		}
		});
		newMap.put("arrowKeysMapped", new MultipleAchievement("Arrows are Superior", "Re-bind movement keys to arrow keys", "keyMapped", 4)
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
		newMap.put("allKeysMapped", new MultipleAchievement("Custom Control Scheme", "Re-bind all keys to non-defaults.", "keyMapped", 8)
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
