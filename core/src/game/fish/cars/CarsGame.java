package game.fish.cars;

import com.badlogic.gdx.Game;

import game.fish.cars.screens.PlayScreen;

//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CarsGame extends Game {
	public void create() {
		setScreen(new PlayScreen());
	}

	@Override
	public void render() {
		super.render();
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}
}
