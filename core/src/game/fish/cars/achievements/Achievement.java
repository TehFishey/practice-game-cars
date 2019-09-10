package game.fish.cars.achievements;

public abstract class Achievement {
	private String name;
	private String description;
	private String triggerPropertyName;
	protected int progress;
	protected int progressCap;
	
	public Achievement(String name, String description, String triggerPropertyName) {
		this.name = name;
		this.description = description;
		this.triggerPropertyName = triggerPropertyName;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getDesc() {
		return this.description;
	}
	
	public String getTriggerProperty() {
		return this.triggerPropertyName;
	}
	
	public boolean isCompleted() {
		return false;
	}
	
	public int getProgress() {
		return progress;
	}
	
	public void setProgress(int progress) {
		this.progress = progress;
	}
	
	public void addProgress(Object newValue) {	}
	
	public void conditionalUpdate(Object newValue) {
		if (condition(newValue)) addProgress(newValue);
	}
	
	protected boolean condition(Object newValue) {
		return false;
	}
	
}
