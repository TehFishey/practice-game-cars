package game.fish.cars.achievements;

import java.util.Arrays;

public class MultipleAchievement extends Achievement {
	protected boolean[] values;
	protected int variableCount;
	
	public MultipleAchievement(String name, String description, String triggerPropertyName, int variables) {
		super(name, description, triggerPropertyName);
		this.variableCount = variables - 1;
		this.values = new boolean[variableCount];
		Arrays.fill(values, false);
		this.progressCap = (int) mersenneCalculate(variableCount);
		this.progress = 0;
	}
	
	public boolean isCompleted() {
		for (boolean value : values) if (!value) return false;
		return true;
	}
	
	public void setProgress(int progress) {
		this.progress = progress;
		this.values = readProgress(progress, variableCount);
	}
	
	public void addProgress(boolean[] updateArray) {
		values = updateArray;
		progress = writeProgress(values);
	}
	
	public void conditionalUpdate(Object newValue) {
		addProgress(multiCondition(newValue));
	}
	
	protected boolean[] multiCondition(Object newValue) {
		return new boolean[0];
	}
	
	private boolean[] readProgress(int progress, int arrayLength) {
		boolean[] values = new boolean[arrayLength];
		
		for (int i = 0; i < arrayLength; i++) {
			int identity = mersenneCalculate(i);
			if (!isMersenneNumber((progress - identity), arrayLength)) values[i] = true;
		}
		
		return values;
	}
	
	private int writeProgress(boolean[] valuesArray) {
		int progress = 0;
		
		for (int i = 0; i < valuesArray.length; i++) {
			if (valuesArray[i]) progress += mersenneCalculate(i);
		}
		
		return progress;
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
