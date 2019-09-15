package game.fish.cars.achievements;

public class BooleanAchievement extends Achievement {
	
	public BooleanAchievement(String name, String description, String triggerPropertyName) {
		super(name, description, triggerPropertyName);
		this.progressCap = 1;
		this.progress = 0;
	}
	
	public boolean isCompleted() {
		return (progress == 1);
	}
	
	public void addProgress(boolean complete) {
		if (isCompleted()) return;
		
		if (complete) progress = 1;
		else progress = 0;
	}
	
	public void conditionalUpdate(Object newValue) {
		if (condition(newValue)) addProgress(true);
	}
}
