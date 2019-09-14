package game.fish.cars.achievements;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MersenneCalculationsTest {
	Achievement testAchievement;
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		
		
		int numberOfTestVariables = 5;
		int numberOfTestIndicies = numberOfTestVariables;
		boolean[] testValues = new boolean[numberOfTestVariables];
		testValues[1] = true;
		testValues[4] = true;
		
		int testProgress = (mersenneCalculate(4) + mersenneCalculate(1));
		System.out.println("Test Values Array is : " + testValues);
		
		boolean[] readValues = readProgress(testProgress, numberOfTestIndicies);
		System.out.println("Read Values Array is : " + readValues);
		
		Assert.assertArrayEquals(testValues, readValues);
		
	}
	
	private boolean[] readProgress(int progress, int arrayLength) {
		boolean[] values = new boolean[arrayLength];
		
		for (int i = 0; i < arrayLength; i++) {
			System.out.println("ReadProgress for index " + i + " out of arrayLength " + arrayLength);
			int identity = mersenneCalculate(i + 1);
			System.out.println("identity of " + i + " is " + identity);
			if (!isMersenneNumber((progress - identity), arrayLength)) values[i] = true;
			System.out.println("calculated that this index (" + i + ") is "+ values[i]);
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
