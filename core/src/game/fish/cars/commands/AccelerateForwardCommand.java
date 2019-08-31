package game.fish.cars.commands;

import static game.fish.cars.entities.CarEntity.DRIVE_DIRECTION_FORWARD;

import game.fish.cars.entities.CarEntity;

public class AccelerateForwardCommand extends Command {
	
	public void execute(CarEntity entity){
		entity.inputDriveDirection(DRIVE_DIRECTION_FORWARD);
	}
}
