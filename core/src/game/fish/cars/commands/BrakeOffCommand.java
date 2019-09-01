package game.fish.cars.commands;

import game.fish.cars.entities.VehicleEntity;

public class BrakeOffCommand extends Command {
	
	public void execute(VehicleEntity entity){
		entity.inputBrakes(false);
	}
}
