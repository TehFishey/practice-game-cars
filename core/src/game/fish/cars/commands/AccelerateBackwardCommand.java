package game.fish.cars.commands;

import static game.fish.cars.entities.VehicleEntity.DRIVE_DIRECTION;

import game.fish.cars.entities.VehicleEntity;

public class AccelerateBackwardCommand extends Command {
	
	public void execute(VehicleEntity entity){
		entity.inputDriveDirection(DRIVE_DIRECTION.BACKWARD);
	}
}