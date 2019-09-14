package game.fish.cars.achievements;

public class Achievement {
	String name;
	String description;
	Property[] properties;
	boolean complete;
	
	public Achievement(String name, String desc, Property ...properties) {
		this.name = name;
		this.description = desc;
		this.properties = properties;
		this.complete = false;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDesc() {
		return description;
	}
	
	public boolean isComplete() {
		return complete;
	}
	
	public void update() {
		if (complete) return;
		
		for (Property property : properties) {
			if (!property.isActive()) return;
		}
		complete = true;
	}
}
