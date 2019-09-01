package game.fish.cars.commands;

import game.fish.cars.entities.VehicleEntity;

public class BrakeOnCommand extends Command {
	
	public void execute(VehicleEntity entity){
		entity.inputBrakes(true);
	}
}
