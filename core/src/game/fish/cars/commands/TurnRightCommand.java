package game.fish.cars.commands;

import static game.fish.cars.entities.VehicleEntity.TURN_DIRECTION;

import game.fish.cars.entities.VehicleEntity;

public class TurnRightCommand extends Command {
	
	public void execute(VehicleEntity entity){
		entity.inputTurnDirection(TURN_DIRECTION.RIGHT);
	}
}
