package game.fish.cars.achievements;

import java.util.HashSet;

public class Achievement {
	String name;
	String description;
	AchievementCondition[] conditions;
	HashSet<String> propertyTags;
	boolean complete;
	
	public Achievement(String name, String desc, AchievementCondition... conditions) {
		this.name = name;
		this.description = desc;
		this.conditions = conditions;
		this.complete = false;
		
		for (int i = 0; i < conditions.length; i++) {
			String tag = conditions[i].propertyTag();
			if (!this.propertyTags.contains(tag))
				propertyTags.add(tag);
		}
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
	
	public Set<String> getTags() {
		return propertyTags;
	}
	
	public void update() {	
		complete = condition();
	}
	
	protected boolean condition() {
		return false;
	}
	
}
