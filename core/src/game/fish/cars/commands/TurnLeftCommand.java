package game.fish.cars.commands;

import static game.fish.cars.entities.VehicleEntity.TURN_DIRECTION_LEFT;

import game.fish.cars.entities.VehicleEntity;

public class TurnLeftCommand extends Command {
	
	public void execute(VehicleEntity entity){
		entity.inputTurnDirection(TURN_DIRECTION_LEFT);
	}
}
