package game.fish.cars.listeners;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.badlogic.gdx.Screen;

import game.fish.cars.Achievements;

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
