package game.fish.cars.achievements;

// Rough abstract class for the various achievement types.
// I feel like there must be ways that I can simplify the number of methods in each class more,
// but I can't think of them.

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
	
	protected void setCompleted() {
		completed = true;
	}
	
	// Kinda an abstract class, but it takes different types depending on subclass.
	// Is there a better way to do this?
	protected void addProgress(Object newValue) {}
	
	abstract protected boolean checkCompleted();
	
	abstract public void update(Object newValue);
}
