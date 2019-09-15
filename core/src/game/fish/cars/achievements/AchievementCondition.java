package game.fish.cars.achievements;

public class AchievementCondition {
	AchievementProperty property;
	String operator;
	int value;
	
	public AchievementCondition(AchievementProperty property, String operator, int value) {
		this.property = property;
		this.operator = operator;
		this.value = value;
	}
		
	public boolean run() {
		if (operator.equals("==")) return property.getValue() == value;
		else if (operator.equals(">")) return property.getValue() > value;
		else if (operator.equals("<")) return property.getValue() < value;
		else if (operator.equals(">=")) return property.getValue() >= value;
		else if (operator.equals("<=")) return property.getValue() <= value;
		else return false;
	}
	
	public String propertyTag() {
		return property.getTag();
	}
}

