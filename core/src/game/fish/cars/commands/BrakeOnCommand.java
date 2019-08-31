package game.fish.cars.commands;

import game.fish.cars.entities.CarEntity;

public class BrakeOnCommand extends Command {
	
	public void execute(CarEntity entity){
		entity.inputBrakes(true);
	}
}
