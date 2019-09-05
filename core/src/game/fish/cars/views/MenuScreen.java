package game.fish.cars.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import game.fish.cars.CarsGame;

import static game.fish.cars.Constants.PLAY_SCREEN;
import static game.fish.cars.Constants.SETTINGS_SCREEN;
import static game.fish.cars.Constants.ACHIEVEMENTS_SCREEN;

import static game.fish.cars.Constants.CAR_FWDCAR;
import static game.fish.cars.Constants.CAR_AWDCAR;
import static game.fish.cars.Constants.CAR_MOTORCYCLE;
import static game.fish.cars.Constants.CAR_HOVERCAR;

import static game.fish.cars.Constants.MAP_MAP1;
import static game.fish.cars.Constants.MAP_MAP2;
import static game.fish.cars.Constants.MAP_MAP3;

public class MenuScreen implements Screen {
	
	private final CarsGame parent;
	private final Stage stage;
	private final Skin skin;
	
	private final TextButton newGameButton;
	private final TextButton resumeButton;
	private final TextButton settingsButton;
	private final TextButton achievementsButton;
	private final TextButton exitButton;
	
	private ChangeListener newGameListener;
	private ChangeListener resumeListener;
	private ChangeListener settingsListener;
	private ChangeListener achievementsListener;
	private ChangeListener exitListener;
	
	public MenuScreen(final CarsGame parent) {
		this.parent = parent;
		stage = new Stage(new ScreenViewport());
		skin = new Skin(Gdx.files.internal("skin/neon-ui.json"));
		
		newGameButton = new TextButton("New Game", skin);
		resumeButton = new TextButton("Resume", skin);
		settingsButton = new TextButton("Settings", skin);
		achievementsButton = new TextButton("Achievements", skin);
		exitButton = new TextButton("Quit", skin);
		
		buildControls();
	}
	
	private void buildControls() {
		//Replace with some kind of generic buttonMap loop? Usable on other menus?
		newGameListener = new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (parent.getScreenExists(PLAY_SCREEN)) parent.clearScreen(PLAY_SCREEN);
				newGameDialog();	
			}
		};
		resumeListener = new ChangeListener() {
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
		newGameButton.addListener(newGameListener);
		resumeButton.addListener(resumeListener);
		settingsButton.addListener(settingsListener);
		achievementsButton.addListener(achievementsListener);
		exitButton.addListener(exitListener);
	}
	
	private void newGameDialog() {

		final ButtonGroup<CheckBox> carChoiceButtons = new ButtonGroup<>();
		final ButtonGroup<CheckBox> mapChoiceButtons = new ButtonGroup<>();
		
		final CheckBox fwdButton = new CheckBox("Front-Wheel Drive",skin);
		final CheckBox awdButton = new CheckBox("Four-Wheel Drive",skin);
		final CheckBox cycButton = new CheckBox("Motorcycle",skin);
		final CheckBox hovButton = new CheckBox("Hover Car",skin);
		final CheckBox map1Button = new CheckBox("City",skin);
		final CheckBox map2Button = new CheckBox("Race Track",skin);
		
		fwdButton.setChecked(true);
		carChoiceButtons.add(fwdButton, awdButton, cycButton, hovButton);
		carChoiceButtons.setMaxCheckCount(1);
		carChoiceButtons.setMinCheckCount(1);
		carChoiceButtons.setUncheckLast(true);
		
		map1Button.setChecked(true);
		mapChoiceButtons.add(map1Button,map2Button);
		mapChoiceButtons.setMaxCheckCount(1);
		mapChoiceButtons.setMinCheckCount(1);
		mapChoiceButtons.setUncheckLast(true);
		
		final Dialog newGameDialog = new Dialog(" ", skin) {
			
			int carChoice = 0;
			int mapChoice = 0;
			
			protected void result(Object obj) {
				if (obj.equals("fwdCar")) {carChoice = CAR_FWDCAR; cancel();}
				else if (obj.equals("cycCar")) {carChoice = CAR_MOTORCYCLE; cancel();}
				else if (obj.equals("hovCar")) {carChoice = CAR_HOVERCAR; cancel();}
				else if (obj.equals("awdCar")) {carChoice = CAR_AWDCAR; cancel();}
				else if (obj.equals("map1")) {mapChoice = MAP_MAP1; cancel();}
				else if (obj.equals("map2")) {mapChoice = MAP_MAP2; cancel();}
				else parent.changeScreen(PLAY_SCREEN, carChoice, mapChoice);
			}
		};
		
		newGameDialog.getButtonTable().add(new Label("Car Type", skin));
		newGameDialog.getButtonTable().add(new Label("Map", skin));
		newGameDialog.getButtonTable().row();
		newGameDialog.button(fwdButton, "fwdCar");
		newGameDialog.button(map1Button, "map1");
		newGameDialog.getButtonTable().row();
		newGameDialog.button(awdButton, "awdCar");
		newGameDialog.button(map2Button, "map2");
		newGameDialog.getButtonTable().row();
		newGameDialog.button(cycButton, "cycCar");
		newGameDialog.getButtonTable().row();
		newGameDialog.button(hovButton, "hovCar");
		newGameDialog.getButtonTable().row();
		newGameDialog.button("Play!", "play");
		newGameDialog.show(stage);
	}
	
	@Override
	public void show() {
		Table table = new Table();
		
		//table.setDebug(true);
		table.setFillParent(true);
		table.add(newGameButton).fillX().uniformX();
		table.row().pad(10,0,10,0);
		if (parent.getScreenExists(PLAY_SCREEN)) {
			table.add(resumeButton).fillX().uniformX();
			table.row().pad(10,0,10,0);
		}
		table.add(settingsButton).fillX().uniformX();
		table.row().pad(10,0,10,0);
		table.add(achievementsButton).fillX().uniformX();
		table.row().pad(10,0,10,0);
		table.add(exitButton).fillX().uniformX();
		table.row().pad(10,0,10,0);
		
		stage.clear();
		stage.addActor(table);
		Gdx.input.setInputProcessor(stage);
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
