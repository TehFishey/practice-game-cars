package game.fish.cars.commands;

import static game.fish.cars.entities.CarEntity.DRIVE_DIRECTION_NONE;

import game.fish.cars.entities.CarEntity;

public class AccelerateNoneCommand extends Command {
	
	public void execute(CarEntity entity){
		entity.inputDriveDirection(DRIVE_DIRECTION_NONE);
	}
}
