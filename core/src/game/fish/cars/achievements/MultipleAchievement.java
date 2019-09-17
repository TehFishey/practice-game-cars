package game.fish.cars.achievements;

import java.util.Arrays;

public class MultipleAchievement extends Achievement {
	protected boolean[] values;
	protected int variableCount;
	
	public MultipleAchievement(String name, String description, String triggerPropertyName, int variableCount) {
		super(name, description, triggerPropertyName);
		this.variableCount = variableCount;
		this.values = new boolean[variableCount];
		Arrays.fill(values, false);
		this.progress = 0;
	}
	
	public void loadProgress(int progress) {
		this.progress = progress;
		this.values = readProgress(progress, variableCount);
	}
	
	public void update(Object newValue) {
		if (!completed) addProgress(multipleCondition(newValue));
	}
	
	protected void addProgress(boolean[] updateArray) {
		values = updateArray;
		progress = writeProgress(values);
		if (checkCompleted()) setCompleted();
	}
	
	protected boolean[] multipleCondition(Object newValue) {
		return new boolean[0];
	}
	
	protected boolean checkCompleted() {
		for (boolean value : values) if (!value) return false;
		return true;
	}
	
	private boolean[] readProgress(int bitProgress, int arrayLen) {
		boolean[] boolArray = new boolean[arrayLen];
		for (int i = 0; i < boolArray.length; i++)
		    if ((bitProgress & 1 << i) != 0)
		    	boolArray[i] = true;
		
		return boolArray;
	}
	
	private int writeProgress(boolean[] values) {
		int bitProgress = 0;
		for (int i = 0; i < values.length; i++)
		    if (values[i])
		        bitProgress |= 1 << i;
		
		return bitProgress;
	}
}
