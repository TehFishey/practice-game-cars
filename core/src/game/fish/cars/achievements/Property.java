package game.fish.cars.achievements;

public abstract class Property {
	private int counter;
	private boolean active;
	
	public Property (int counterMax) {
		this.counter = 0;
		this.active = false;
	}

	public int getCounter() {
		return this.counter;
	}
	
	public boolean isActive() {
		return this.active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public void hardUpdate(int newValue) {		
		this.counter = newValue;
	}
	
	public void softUpdate(int updateValue) {
		this.counter += updateValue;
	}
}
