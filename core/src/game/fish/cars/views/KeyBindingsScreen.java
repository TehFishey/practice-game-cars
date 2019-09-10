package game.fish.cars.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import java.beans.PropertyChangeSupport;

import game.fish.cars.CarsGame;
import game.fish.cars.KeyBindings;

import static game.fish.cars.Constants.SETTINGS_SCREEN;

import static game.fish.cars.KeyBindings.KEY_DRIVE;
import static game.fish.cars.KeyBindings.KEY_REVERSE;
import static game.fish.cars.KeyBindings.KEY_LEFT;
import static game.fish.cars.KeyBindings.KEY_RIGHT;
import static game.fish.cars.KeyBindings.KEY_BRAKE;
import static game.fish.cars.KeyBindings.KEY_ZOOMIN;
import static game.fish.cars.KeyBindings.KEY_ZOOMOUT;
import static game.fish.cars.KeyBindings.KEY_MENU;

public class KeyBindingsScreen extends InterfaceScreen {
	
	private final KeyBindings keyBindings;
	private final KeyBindingsProcessor keyBindingsProcessor;
	private String bindingKey;
	private Label bindingKeyCurrent;
	
	private final Label titleLabel;
	private final Label driveKeyLabel;
	private final Label reverseKeyLabel;
	private final Label leftKeyLabel;
	private final Label rightKeyLabel;
	private final Label brakeKeyLabel;
	private final Label zoomInKeyLabel;
	private final Label zoomOutKeyLabel;
	private final Label menuKeyLabel;
	
	private Label driveKeyCurrent;
	private Label reverseKeyCurrent;
	private Label leftKeyCurrent;
	private Label rightKeyCurrent;
	private Label brakeKeyCurrent;
	private Label zoomInKeyCurrent;
	private Label zoomOutKeyCurrent;
	private Label menuKeyCurrent;
	
	private final TextButton driveKeyButton;
	private final TextButton reverseKeyButton;
	private final TextButton leftKeyButton;
	private final TextButton rightKeyButton;
	private final TextButton brakeKeyButton;
	private final TextButton zoomInKeyButton;
	private final TextButton zoomOutKeyButton;
	private final TextButton menuKeyButton;
	private final TextButton backButton;
	
	public KeyBindingsScreen(final CarsGame parent) {
		super(parent);	
		keyBindings = this.parent.getKeyBindings();
		keyBindingsProcessor = new KeyBindingsProcessor();
		
		titleLabel = new Label ("Key Bindings", skin);
		driveKeyLabel = new Label ("Accelerate", skin);
		reverseKeyLabel = new Label ("Reverse", skin);
		leftKeyLabel = new Label ("Turn Left", skin);
		rightKeyLabel = new Label ("Turn Right", skin);
		brakeKeyLabel = new Label ("Brake", skin);
		zoomInKeyLabel = new Label ("Zoom In", skin);
		zoomOutKeyLabel = new Label ("Zoom Out", skin);
		menuKeyLabel = new Label ("Menu", skin);
		
		driveKeyCurrent = new Label (Keys.toString(keyBindings.getKeyBinding(KEY_DRIVE)), skin);
		reverseKeyCurrent = new Label (Keys.toString(keyBindings.getKeyBinding(KEY_REVERSE)), skin);
		leftKeyCurrent = new Label (Keys.toString(keyBindings.getKeyBinding(KEY_LEFT)), skin);
		rightKeyCurrent = new Label (Keys.toString(keyBindings.getKeyBinding(KEY_RIGHT)), skin);
		brakeKeyCurrent = new Label (Keys.toString(keyBindings.getKeyBinding(KEY_BRAKE)), skin);
		zoomInKeyCurrent = new Label (Keys.toString(keyBindings.getKeyBinding(KEY_ZOOMIN)), skin);
		zoomOutKeyCurrent = new Label (Keys.toString(keyBindings.getKeyBinding(KEY_ZOOMOUT)), skin);
		menuKeyCurrent = new Label (Keys.toString(keyBindings.getKeyBinding(KEY_MENU)), skin);
		
		driveKeyButton = buildKeyButton("Change", skin, KEY_DRIVE, driveKeyCurrent);
		reverseKeyButton = buildKeyButton("Change", skin, KEY_REVERSE, reverseKeyCurrent);
		leftKeyButton = buildKeyButton("Change", skin, KEY_LEFT, leftKeyCurrent);
		rightKeyButton = buildKeyButton("Change", skin, KEY_RIGHT, rightKeyCurrent);
		brakeKeyButton = buildKeyButton("Change", skin, KEY_BRAKE, brakeKeyCurrent);
		zoomInKeyButton = buildKeyButton("Change", skin, KEY_ZOOMIN, zoomInKeyCurrent);
		zoomOutKeyButton = buildKeyButton("Change", skin, KEY_ZOOMOUT, zoomOutKeyCurrent);
		menuKeyButton = buildKeyButton("Change", skin, KEY_MENU, menuKeyCurrent);
		backButton = buildScreenButton("Back", skin, parent, SETTINGS_SCREEN);
	}

	@Override
	public void show() {
		Table table = new Table();

		//table.setDebug(true);
		table.setFillParent(true);
		table.add(titleLabel);
		table.row();
		table.add(driveKeyLabel);
		table.add(driveKeyButton);
		table.add(driveKeyCurrent).pad(0,0,0,15);
		table.add(brakeKeyLabel);
		table.add(brakeKeyButton);
		table.add(brakeKeyCurrent);
		table.row();
		table.add(reverseKeyLabel);
		table.add(reverseKeyButton);
		table.add(reverseKeyCurrent).pad(0,0,0,15);
		table.add(zoomInKeyLabel);
		table.add(zoomInKeyButton);
		table.add(zoomInKeyCurrent);
		table.row();
		table.add(leftKeyLabel);
		table.add(leftKeyButton);
		table.add(leftKeyCurrent).pad(0,0,0,15);
		table.add(zoomOutKeyLabel);
		table.add(zoomOutKeyButton);
		table.add(zoomOutKeyCurrent);
		table.row();
		table.add(rightKeyLabel);
		table.add(rightKeyButton);
		table.add(rightKeyCurrent).pad(0,0,0,15);
		table.add(menuKeyLabel);
		table.add(menuKeyButton);
		table.add(menuKeyCurrent);
		table.row();
		table.add(backButton);
		
		stage.clear();
		stage.addActor(table);
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void dispose() {
		stage.dispose();
	}

	private TextButton buildKeyButton(final String buttonLabel, final Skin skin, final String bindKey, final Label currentKeyLabel) {
		ChangeListener listener = new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				bindingKey = bindKey;
				bindingKeyCurrent = currentKeyLabel;
				Gdx.input.setInputProcessor(keyBindingsProcessor);
			}
		};
		TextButton button = new TextButton(buttonLabel, skin);
		button.addListener(listener);
		return button;
	}
	
	private class KeyBindingsProcessor implements InputProcessor {

		@Override
		public boolean keyDown(int keycode) {
		    keyBindings.setKeyBinding(bindingKey, keycode);
		    bindingKeyCurrent.setText(Keys.toString(keycode));
		    Gdx.input.setInputProcessor(stage);
		    achievementPCS.firePropertyChange("keyChanged",null,bindingKey);
		    return true;
		}

		@Override
		public boolean keyUp(int keycode) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean keyTyped(char character) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean touchDown(int screenX, int screenY, int pointer, int button) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean touchDragged(int screenX, int screenY, int pointer) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean mouseMoved(int screenX, int screenY) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean scrolled(int amount) {
			// TODO Auto-generated method stub
			return false;
		}
	}
	
}


