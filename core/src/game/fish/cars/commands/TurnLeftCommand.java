package game.fish.cars.commands;

import static game.fish.cars.entities.CarEntity.TURN_DIRECTION_LEFT;

import game.fish.cars.entities.CarEntity;

public class TurnLeftCommand extends Command {
	
	public void execute(CarEntity entity){
		entity.inputTurnDirection(TURN_DIRECTION_LEFT);
	}
}
