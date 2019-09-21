package game.fish.cars.commands;

import static game.fish.cars.Constants.SCREEN;

import game.fish.cars.CarsGame;

public class MenuCommand extends Command {
	
	public void execute(CarsGame game){
		game.changeScreen(SCREEN.MENU_SCREEN);
	}
}
