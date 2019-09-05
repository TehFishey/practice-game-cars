package game.fish.cars.views;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import game.fish.cars.CarsGame;

public abstract class InterfaceScreen implements Screen{

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	protected TextButton buildScreenButton(final String buttonLabel, final Skin skin, final CarsGame game, final int screen) {
		ChangeListener listener = new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.changeScreen(screen);
			}
		};
		TextButton button = new TextButton(buttonLabel, skin);
		button.addListener(listener);
		return button;
	}
	
	protected TextButton buildCustomButton(final String buttonLabel, final Skin skin, final ChangeListener listener) {
		TextButton button = new TextButton(buttonLabel, skin);
		button.addListener(listener);
		return button;
	}

}
