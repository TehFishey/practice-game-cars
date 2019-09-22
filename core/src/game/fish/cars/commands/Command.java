package game.fish.cars.commands;

// My attempt at implementing the Command pattern, for handling entity actions and screen/interface controls.
// Theoretically, such an implementation would make it easy to hook in AI controllers for vehicles/entities, or add more players.

public abstract class Command {
	public Command(){}
	
	public void execute() {}
}
