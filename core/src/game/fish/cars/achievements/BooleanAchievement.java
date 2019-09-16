package game.fish.cars.achievements;

public class BooleanAchievement extends Achievement {
	
	public BooleanAchievement(String name, String description, String triggerPropertyName) {
		super(name, description, triggerPropertyName);
		this.progress = 0;
	}
	
	public void update(Object newValue) {
		if (!completed) {
			if (condition(newValue)) addProgress(true);
		}
	}
	
	protected void addProgress(boolean update) {
		if (update) {
			this.progress = 1;
			this.setCompleted();
		}
		
		else this.progress = 0;
	}
	
	protected boolean checkCompleted() {
		return (progress == 1);
	}
}
