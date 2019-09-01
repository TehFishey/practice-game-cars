package game.fish.cars.commands;

import static game.fish.cars.entities.VehicleEntity.DRIVE_DIRECTION_NONE;

import game.fish.cars.entities.VehicleEntity;

public class AccelerateNoneCommand extends Command {
	
	public void execute(VehicleEntity entity){
		entity.inputDriveDirection(DRIVE_DIRECTION_NONE);
	}
}
