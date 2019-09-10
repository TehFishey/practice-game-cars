package game.fish.cars.achievements;

import java.util.ArrayList;

public class MultipleAchievement extends Achievement {
	private ArrayList<String> variables;
	
	public MultipleAchievement(String name, String description, String triggerPropertyName, ArrayList<String> variables) {
		super(name, description, triggerPropertyName);
		this.variables = variables;
		this.progressCap = (int) mersenneCalculate(variables.size() - 1);
		this.progress = 0;
	}
	
	public boolean isCompleted() {
		return (progress >= progressCap);
	}
	
	public void addProgress(String newBoolean) {
		int identity;
		
		if (variables.contains(newBoolean)) identity = mersenneCalculate(variables.indexOf(newBoolean));
		else return;
		
		if (progress > this.progressCap) this.progress = this.progressCap;
		else if (!isMersenneNumber((progress - identity), progressCap)) this.progress += identity;
	}
	
	private boolean isMersenneNumber(int number, int max) {
		for (int i = 0; ; i++) {
			if (number == mersenneCalculate(i)) return true;
			if (i >= max) return false;
		}
	}
	
	private int mersenneCalculate(int power) {
		if (power == 0) return 1;
		else return (int) Math.pow(2, (power)) - 1;
	}
}
