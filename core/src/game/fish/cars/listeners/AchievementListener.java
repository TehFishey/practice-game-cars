package game.fish.cars.listeners;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.badlogic.gdx.Screen;

import game.fish.cars.Achievements;

// My first attempt at implementing an Observer pattern.
// I really don't like how propertyChangeListener works with raw Objects instead of typed variables,
// But I'm not sure how to change it in a way that isn't bloated. The achievement-related propertyChangeEvents
// send/expect a wide variety of types, which are all just cast-to in the Achievements class. It's very messy,
// but the various classes are necessary for the achievement conditions...

public class AchievementListener implements PropertyChangeListener {
	Achievements achievements;
	Screen activeScreen;
	
	public AchievementListener(Achievements achievements) {
		this.achievements = achievements;
	}
	
    public void propertyChange(PropertyChangeEvent event) {
        String propertyName = event.getPropertyName();
        Object propertyValue = event.getNewValue();
      
        achievements.takePropertyChange(propertyName,propertyValue);
    }
}
