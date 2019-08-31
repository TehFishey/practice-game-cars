package game.fish.cars.commands;

import static game.fish.cars.entities.CarEntity.TURN_DIRECTION_NONE;

import game.fish.cars.entities.CarEntity;

public class TurnNoneCommand extends Command {
	
	public void execute(CarEntity entity){
		entity.inputTurnDirection(TURN_DIRECTION_NONE);
	}
}
