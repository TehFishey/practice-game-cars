package game.fish.cars.achievements;

public class IncrementalAchievement extends Achievement {

	public IncrementalAchievement(String name, String description, String triggerPropertyName, int progressionCap) {
		super(name, description, triggerPropertyName);
		this.progressCap = progressionCap;
		this.progress = 0;
	}
	
	public boolean isCompleted() {
		return (progress >= progressCap);
	}
	
	public void addProgress(int progress) {
		if (progress >= this.progressCap) this.progress = this.progressCap;
		else this.progress += progress;
	}
	
	public void conditionalUpdate(Object newValue) {
		if (!isCompleted()) {
			if (condition(newValue)) addProgress(newValue);
		}
		
	}
}
