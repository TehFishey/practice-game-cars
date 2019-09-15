package game.fish.cars.achievements;

public class AchievementProperty {
	int value;
	String tag;
	
	public AchievementProperty(int initialValue, String tag) {
		this.value = initialValue;
		this.tag = tag;
	}
	
	public int getValue() {
		return value;
	}
	
	public String getTag() {
		return tag;
	}
	
	public void setValue(int newValue) {
		value = newValue;
	}
	
}

