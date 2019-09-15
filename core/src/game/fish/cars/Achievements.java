package game.fish.cars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import game.fish.cars.achievements.Achievement;
import game.fish.cars.achievements.BooleanAchievement;
import game.fish.cars.achievements.MultipleAchievement;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Achievements {

	public HashMap<String, Achievement>currentAchievements;
	private Preferences achievementsFile;
	private Map<String, ?> achievementsFileIndex;
	private HashMap<String, Integer> currentProgress;
	
	public Achievements() {
		achievementsFile = Gdx.app.getPreferences(PATH_ACHIEVEMENTS);
		achievementsFileIndex = achievementsFile.get();
		currentAchievements = generateAchievementDefaults();
		currentProgress = new HashMap<String, Integer>();
		loadAchievementProgress();
		
	}
	
	public void takePropertyChange(String triggerPropertyName, Object newValue) {
		
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
	}
}
