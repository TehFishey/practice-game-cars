package game.fish.cars.views;

import java.beans.PropertyChangeSupport;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import game.fish.cars.CarsGame;

public abstract class InterfaceScreen implements Screen {

	protected final CarsGame parent;
	protected final Stage stage;
	protected final Stage overlayStage;
	protected final Skin skin;
	protected final PropertyChangeSupport achievementPCS;
	
	public InterfaceScreen(CarsGame parent) {
		this.parent = parent;
		stage = new Stage(new ScreenViewport());
		overlayStage = parent.getAchievementOverlay().getStage();
		skin = new Skin(Gdx.files.internal("skin/neon-ui.json"));
		achievementPCS = new PropertyChangeSupport(this);
		achievementPCS.addPropertyChangeListener(parent.getAchievementListener());
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
		overlayStage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
		overlayStage.getViewport().update(width, height, true);
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
		stage.dispose();
		
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
