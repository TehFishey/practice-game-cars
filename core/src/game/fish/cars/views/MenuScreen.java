package game.fish.cars.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import game.fish.cars.CarsGame;

import static game.fish.cars.Constants.PLAY_SCREEN;
import static game.fish.cars.Constants.SETTINGS_SCREEN;
import static game.fish.cars.Constants.ACHIEVEMENTS_SCREEN;

public class MenuScreen implements Screen {
	
	private final CarsGame parent;
	private final Stage stage;
	private final Table table;
	private final Skin skin;
	private final TextButton playButton;
	private final TextButton settingsButton;
	private final TextButton achievementsButton;
	private final TextButton exitButton;
	private ChangeListener playListener;
	private ChangeListener settingsListener;
	private ChangeListener achievementsListener;
	private ChangeListener exitListener;
	
	public MenuScreen(final CarsGame parent) {
		this.parent = parent;
		stage = new Stage(new ScreenViewport());
		table = new Table();
		skin = new Skin(Gdx.files.internal("skin/neon-ui.json"));
		playButton = new TextButton("Play", skin);
		settingsButton = new TextButton("Settings", skin);
		achievementsButton = new TextButton("Achievements", skin);
		exitButton = new TextButton("Quit", skin);
		
		buildMenu();
		Gdx.input.setInputProcessor(stage);
	}
	
	private void buildMenu() {
		//Replace with some kind of generic buttonMap loop? Useable on other menus?
		playListener = new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				parent.changeScreen(PLAY_SCREEN);
			}
		};
		settingsListener = new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				parent.changeScreen(SETTINGS_SCREEN);
			}
		};
		achievementsListener = new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				parent.changeScreen(ACHIEVEMENTS_SCREEN);
			}
		};
		exitListener = new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.app.exit();
			}
		};
		playButton.addListener(playListener);
		settingsButton.addListener(settingsListener);
		achievementsButton.addListener(achievementsListener);
		exitButton.addListener(exitListener);
				
		table.setFillParent(true);
		table.setDebug(true);
		table.add(playButton).fillX().uniformX();
		table.row().pad(10,0,10,0);
		table.add(settingsButton).fillX().uniformX();
		table.row().pad(10,0,10,0);
		table.add(achievementsButton).fillX().uniformX();
		table.row().pad(10,0,10,0);
		table.add(exitButton).fillX().uniformX();
		table.row().pad(10,0,10,0);
	}
	
	@Override
	public void show() {		
		stage.clear();
		stage.addActor(table);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
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

}
