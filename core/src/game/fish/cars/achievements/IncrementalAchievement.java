package game.fish.cars.achievements;

public class IncrementalAchievement extends Achievement {
	private int progressCap;
	
	public IncrementalAchievement(String name, String description, String triggerPropertyName, int progressionCap) {
		super(name, description, triggerPropertyName);
		this.progressCap = progressionCap;
		this.progress = 0;
	}
	
	public void update(Object newValue) {
		if (!completed) {
			addProgress(incrementalCondition(newValue));
		}
	}
	
	protected void addProgress(int progress) {
		if ((this.progress + progress) >= this.progressCap) { 
			this.progress = this.progressCap;
			this.setCompleted();
		}
		else this.progress += progress;
	}
	
	protected int incrementalCondition(Object newValue) {
		return 1;
	}

	protected boolean checkCompleted() {
		return (progress >= progressCap);
	}
	
}
