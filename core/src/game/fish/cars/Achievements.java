package game.fish.cars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import game.fish.cars.achievements.AchievementProperty;
import game.fish.cars.achievements.Achievement;
import game.fish.cars.achievements.AchievementCondition;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.Array;

import static game.fish.cars.Constants.DEFAULT_ZOOM;

import static game.fish.cars.Constants.PATH_ACHIEVEMENTS;
import static game.fish.cars.Constants.PATH_PROPERTIES;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;

public class Achievements {

	private HashSet<Achievement> achievements;
	private HashMap<String, AchievementProperty> properties;
	private Preferences achievementsFile;
	private Preferences propertiesFile;
	private CarsGame parent;
	
	private Map<String, ?> achievementsFileIndex;
	private Map<String, ?> propertiesFileIndex;
	
	public Achievements(CarsGame parent) {
		parent = parent;
		
		achievementsFile = Gdx.app.getPreferences(PATH_ACHIEVEMENTS);
		propertiesFile = Gdx.app.getPreferences(PATH_PROPERTIES);
		achievementsFileIndex = achievementsFile.get();
		propertiesFileIndex = achievementsFile.get();
		
		properties = generateDefaultProperties();
		achievements = generateDefaultAchievements();
	}
	
	private HashMap<String, AchievementProperty> generateDefaultProperties() {
		HashMap<String, AchievementProperty> newProperties = new HashMap<String, AchievementProperty>();
		
		newProperties.put("carType", new AchievementProperty(0, "carType"));
		newProperties.put("mapChoice", new AchievementProperty(0, "mapChoice"));
		newProperties.put("musicPlaying", new AchievementProperty((parent.getSettings().getMusicEnabled()) ? 1 : 0, "musicPlaying"));
		newProperties.put("musicVolume", new AchievementProperty((int) parent.getSettings().getMusicVolume()*10, "musicVolume"));
		newProperties.put("playerSpeed", new AchievementProperty(0, "playerSpeed"));
		newProperties.put("cameraZoom", new AchievementProperty((int) DEFAULT_ZOOM, "cameraZoom"));
		newProperties.put("keyMapped-" + KEY_DRIVE, new AchievementProperty(parent.getKeyBindings().getKeyBinding(KEY_DRIVE),  "keyMapped-" + KEY_DRIVE));
		newProperties.put("keyMapped-" + KEY_REVERSE, new AchievementProperty(parent.getKeyBindings().getKeyBinding(KEY_REVERSE), "keyMapped-" + KEY_REVERSE));
		newProperties.put("keyMapped-" + KEY_LEFT, new AchievementProperty(parent.getKeyBindings().getKeyBinding(KEY_LEFT), "keyMapped-" + KEY_LEFT));
		newProperties.put("keyMapped-" + KEY_RIGHT, new AchievementProperty(parent.getKeyBindings().getKeyBinding(KEY_RIGHT), "keyMapped-" + KEY_RIGHT));
		newProperties.put("keyMapped-" + KEY_BRAKE, new AchievementProperty(parent.getKeyBindings().getKeyBinding(KEY_BRAKE), "keyMapped-" + KEY_BRAKE));
		newProperties.put("keyMapped-" + KEY_ZOOMIN, new AchievementProperty(parent.getKeyBindings().getKeyBinding(KEY_ZOOMIN), "keyMapped-" + KEY_ZOOMIN));
		newProperties.put("keyMapped-" + KEY_ZOOMOUT, new AchievementProperty(parent.getKeyBindings().getKeyBinding(KEY_ZOOMOUT), "keyMapped-" + KEY_ZOOMOUT));
		newProperties.put("keyMapped-" + KEY_MENU, new AchievementProperty(parent.getKeyBindings().getKeyBinding(KEY_MENU), "keyMapped-" + KEY_MENU));
		
		return newProperties;
	}
	
	private HashSet<Achievement> generateDefaultAchievements() {
		HashSet<Achievement> newAchievements = new HashSet<Achievement>();
		
		newAchievements.add(new Achievement("Not a Classic", "Muted the game's music.", 
				new AchievementCondition(properties.get("musicPlaying"), "==", 0)
				));
		newAchievements.add(new Achievement("A Classical Piece", "Set the game music to maximum volume.", 
				new AchievementCondition(properties.get("musicVolume"), "==", 10)
				));
		newAchievements.add(new Achievement("A Decent Clip", "Drove any vehicle at over 50kpH.", 
				new AchievementCondition(properties.get("playerSpeed"), ">=", 50)
				));
		newAchievements.add(new Achievement("Feeling Wind in my Hair", "Drove any vehicle at over 100kpH.", 
				new AchievementCondition(properties.get("playerSpeed"), ">=", 100)
				));
		newAchievements.add(new Achievement("Unsafe Velocities", "Drove any vehicle at over 150kpH.", 
				new AchievementCondition(properties.get("playerSpeed"), ">=", 150)
				));
		newAchievements.add(new Achievement("Road Runner", "Drove any vehicle at over 150kpH.", 
				new AchievementCondition(properties.get("playerSpeed"), ">=", 200)
				));
		newAchievements.add(new Achievement("Extreme CloseUp", "Zoomed the camera in as close as possible.", 
				new AchievementCondition(properties.get("cameraZoom"), "<=", 3)
				));
		newAchievements.add(new Achievement("Sattelite Imagery", "Zoomed the camera out as far as possible.", 
				new AchievementCondition(properties.get("cameraZoom"), ">=", 199)
				));
		newAchievements.add(new Achievement("Commuter Vehicle", "Drove a front-wheel drive car", 
				new AchievementCondition(properties.get("carType"), "==", CAR_FWDCAR)
				));
		newAchievements.add(new Achievement("They See Me Rollin", "Drove an all-wheel drive car.", 
				new AchievementCondition(properties.get("carType"), "==", CAR_AWDCAR)
				));
		newAchievements.add(new Achievement("Corley Motors", "Drove a motorcycle.", 
				new AchievementCondition(properties.get("carType"), "==", CAR_MOTORCYCLE)
				));
		newAchievements.add(new Achievement("Not a Testla", "Drove a hover car.", 
				new AchievementCondition(properties.get("carType"), "==", CAR_HOVERCAR)
				));
		
		
		return newAchievements;
	}
	
	/*public void takePropertyChange(String triggerPropertyName, Object newValue) {
		
		boolean update = false;
		for (Achievement achievementValue : currentAchievements.values()) {
			
			if (achievementValue.getTriggerProperty() == triggerPropertyName) {
				achievementValue.conditionalUpdate(newValue);
				update = true;
			}
		}
		
		if (update) saveAchievementProgress();
	}
	
	public HashMap<String, Achievement> getAchievements() {
		return currentAchievements;
	}
	
	private HashMap<String, Achievement> generateAchievementDefaults() {
		
		HashMap<String, Achievement> newMap = new HashMap<String, Achievement>();
		newMap.put("musicMuted", new BooleanAchievement("Not a Classic", "Muted the game's music.", "musicPlaying") 
		{
			@Override
			protected boolean condition(Object newValue) {
				boolean musicPlaying = (boolean) newValue;
				return !musicPlaying;
			}
    	});
		newMap.put("musicVolumeMaxed", new BooleanAchievement("That's my Jam!", "Set the game music to maximum volume.", "musicVolume") 
		{
			@Override
			protected boolean condition(Object newValue) {
				float musicVolume = (float) newValue;
				return (musicVolume == 1);
			}
		});
		newMap.put("playerDrivingFast", new BooleanAchievement("Speeding", "Drive your vehicle at high speed.", "playerSpeed") 
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
		newMap.put("fwdCarDriven", new BooleanAchievement("Boring and Reliable", "Start a game driving a front-wheel drive car.", "carType") 
		{
			@Override
			protected boolean condition(Object newValue) {
				float carType = (int) newValue;
				return (carType == CAR_FWDCAR);
			}
		});
		newMap.put("awdCarDriven", new BooleanAchievement("Like a Rock", "Start a game driving an all-wheel drive car.", "carType") 
		{
			@Override
			protected boolean condition(Object newValue) {
				float carType = (int) newValue;
				return (carType == CAR_AWDCAR);
			}
		});
		newMap.put("motorcycleDriven", new BooleanAchievement("Look at My Bike", "Start a game driving a motorcycle.", "carType") 
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
		newMap.put("trackMapPlayed", new BooleanAchievement("Floor it!", "Start a game on the racetrack map", "mapChoice") 
		{
			@Override
			protected boolean condition(Object newValue) {
				float carType = (int) newValue;
				return (carType == MAP_MAP2);
		}
		});
		newMap.put("arrowKeysMapped", new MultipleAchievement("I Prefer Arrows", "Re-bind movement keys to arrow keys", "keyMapped", 4)
		{
			@Override
			protected boolean[] multiCondition(Object obj) {
				boolean[] update = values;
				Array bindingInfo = (Array) obj;
				
				if (bindingInfo.get(0).equals(KEY_DRIVE) && bindingInfo.get(1).equals(Keys.UP))
					update[0] = true;
				else if (bindingInfo.get(0).equals(KEY_REVERSE) && bindingInfo.get(1).equals(Keys.DOWN))
					update[1] = true;
				else if (bindingInfo.get(0).equals(KEY_LEFT) && bindingInfo.get(1).equals(Keys.LEFT))
					update[2] = true;
				else if (bindingInfo.get(0).equals(KEY_RIGHT) && bindingInfo.get(1).equals(Keys.RIGHT))
					update[3] = true;
				
				return update;
			}
		});
		
		return newMap;
	}

	private void loadAchievementProgress() {
		for (String key : achievementsFileIndex.keySet()) {
	
			int progress = achievementsFile.getInteger(key);

			for (Entry achievementEntry : currentAchievements.entrySet()) {
				if (achievementEntry.getKey().equals(key)) {
					
					Achievement achievementValue = (Achievement) achievementEntry.getValue();
					achievementValue.setProgress(progress);
				}
			}
		}	
	}
		
	private void saveAchievementProgress() {
		for (Entry achievementEntry : currentAchievements.entrySet()) {
			
			String achievementKey = (String) achievementEntry.getKey();
			Achievement achievementValue = (Achievement) achievementEntry.getValue();
			int progress = achievementValue.getProgress();

			currentProgress.put(achievementKey, progress);
		}
		
		achievementsFile.put(currentProgress);
		achievementsFile.flush();
	}*/
}
