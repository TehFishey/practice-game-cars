package game.fish.observers;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import game.fish.cars.Achievements;

import static game.fish.cars.Constants.CAR_FWDCAR;
import static game.fish.cars.Constants.CAR_AWDCAR;
import static game.fish.cars.Constants.CAR_MOTORCYCLE;
import static game.fish.cars.Constants.CAR_HOVERCAR;
import static game.fish.cars.Constants.MAP_MAP1;
import static game.fish.cars.Constants.MAP_MAP2;
import static game.fish.cars.Constants.MAP_MAP3;

public class AchievementListener implements PropertyChangeListener {
	Achievements achievements;
	
	public AchievementListener(Achievements achievements) {
		this.achievements = achievements;
	}
	
    public void propertyChange(PropertyChangeEvent event) {
        String propertyName = event.getPropertyName();
        Object propertyValue = event.getNewValue();
        
        achievements.takePropertyChange(propertyName,propertyValue);
    }
}
