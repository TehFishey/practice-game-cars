package game.fish.cars.achievements;

public abstract class Achievement {
	private String name;
	private String description;
	private String triggerPropertyName;
	protected boolean completed;
	protected int progress;
	
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
	
	public int getProgress() {
		return progress;
	}
	
	public boolean getCompleted() {
		return completed;
	}
	
	public void loadProgress(int progress) {
		this.progress = progress;
		if (checkCompleted()) completed = true;
	}
	
	public void update(Object newValue) {
		if (condition(newValue)) addProgress(newValue);
	}
	
	protected void addProgress(Object newValue) {	}
	
	protected boolean condition(Object newValue) {
		return true;
	}
	
	protected boolean checkCompleted() {
		return true;
	}
	
	protected void setCompleted() {
		completed = true;
		
	}
	
}
