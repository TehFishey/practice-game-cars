package game.fish.cars.commands;

import static game.fish.cars.Constants.ZOOM_STEP;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class ZoomOutCommand extends Command {
	
	public void execute(OrthographicCamera camera){
		camera.zoom = Math.min(50f, camera.zoom + ZOOM_STEP);
	}
}
