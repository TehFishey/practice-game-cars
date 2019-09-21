package game.fish.cars.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import game.fish.cars.CarsGame;

import static game.fish.cars.Constants.SCREEN;
import static game.fish.cars.Constants.CAR;
import static game.fish.cars.Constants.MAP;

public class MenuInterface extends InterfaceScreen {
	
	private final TextButton newGameButton;
	private final TextButton resumeButton;
	private final TextButton settingsButton;
	private final TextButton achievementsButton;
	private final TextButton exitButton;
	
	public MenuInterface(final CarsGame parent) {
		super(parent);
		
		ChangeListener newGameListener = new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (parent.getScreenExists(SCREEN.PLAY_SCREEN)) parent.clearScreen(SCREEN.PLAY_SCREEN);
				newGameDialog();	
			}
		};
		ChangeListener exitListener = new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				achievementPCS.firePropertyChange("gameEnd",null,true);
				Gdx.app.exit();
			}
		};
		
		resumeButton = buildScreenButton("Resume", skin, this.parent, SCREEN.PLAY_SCREEN);
		settingsButton = buildScreenButton("Settings", skin, this.parent, SCREEN.SETTINGS_SCREEN);
		achievementsButton = buildScreenButton("Achievements", skin, this.parent, SCREEN.ACHIEVEMENTS_SCREEN);
		newGameButton = buildCustomButton("New Game", skin, newGameListener);
		exitButton = buildCustomButton("Quit", skin, exitListener);		
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
		final CheckBox map3Button = new CheckBox("Parking Lot",skin);
		
		fwdButton.setChecked(true);
		carChoiceButtons.add(fwdButton, awdButton, cycButton, hovButton);
		carChoiceButtons.setMaxCheckCount(1);
		carChoiceButtons.setMinCheckCount(1);
		carChoiceButtons.setUncheckLast(true);
		
		map1Button.setChecked(true);
		mapChoiceButtons.add(map1Button,map2Button, map3Button);
		mapChoiceButtons.setMaxCheckCount(1);
		mapChoiceButtons.setMinCheckCount(1);
		mapChoiceButtons.setUncheckLast(true);
		
		final Dialog newGameDialog = new Dialog(" ", skin) {
			
			CAR carChoice = CAR.FWDCAR;
			MAP mapChoice = MAP.MAP1;
			
			protected void result(Object obj) {
				if (obj.equals("fwdCar")) {carChoice = CAR.FWDCAR; cancel();}
				else if (obj.equals("cycCar")) {carChoice = CAR.MOTORCYCLE; cancel();}
				else if (obj.equals("hovCar")) {carChoice = CAR.HOVERCAR; cancel();}
				else if (obj.equals("awdCar")) {carChoice = CAR.AWDCAR; cancel();}
				else if (obj.equals("map1")) {mapChoice = MAP.MAP1; cancel();}
				else if (obj.equals("map2")) {mapChoice = MAP.MAP2; cancel();}
				else if (obj.equals("map3")) {mapChoice = MAP.MAP3; cancel();}
				else {
					achievementPCS.firePropertyChange("carType",null,carChoice);
					achievementPCS.firePropertyChange("mapChoice",null,mapChoice);
					parent.changeScreen(SCREEN.PLAY_SCREEN, carChoice, mapChoice);
				}
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
		newGameDialog.button(map3Button, "map3");
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
		if (parent.getScreenExists(SCREEN.PLAY_SCREEN)) {
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
	public void dispose() {
		stage.dispose();
	}

}
