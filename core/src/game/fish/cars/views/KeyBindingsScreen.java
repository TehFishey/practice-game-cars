package game.fish.cars.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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

public class KeyBindingsScreen implements Screen {
	
	private final CarsGame parent;
	private final KeyBindings keyBindings;
	private final KeyBindingsProcessor keyBindingsProcessor;
	private final Stage stage;
	private final Skin skin;
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
	
	private ChangeListener driveKeyListener;
	private ChangeListener reverseKeyListener;
	private ChangeListener leftKeyListener;
	private ChangeListener rightKeyListener;  
	private ChangeListener brakeKeyListener;  
	private ChangeListener zoomInKeyListener; 
	private ChangeListener zoomOutKeyListener;
	private ChangeListener menuKeyListener;   
	private ChangeListener backListener;
	
	
	public KeyBindingsScreen(final CarsGame parent) {
		this.parent = parent;
		keyBindings = this.parent.getKeyBindings();
		keyBindingsProcessor = new KeyBindingsProcessor();
		stage = new Stage(new ScreenViewport());
		skin = new Skin(Gdx.files.internal("skin/neon-ui.json"));
		
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
		
		driveKeyButton = new TextButton("Change", skin);
		reverseKeyButton = new TextButton("Change", skin);
		leftKeyButton = new TextButton("Change", skin);
		rightKeyButton = new TextButton("Change", skin);
		brakeKeyButton = new TextButton("Change", skin);
		zoomInKeyButton = new TextButton("Change", skin);
		zoomOutKeyButton = new TextButton("Change", skin);
		menuKeyButton = new TextButton("Change", skin);
		backButton = new TextButton("Back", skin);
		
		buildControls();
	}
	
	private void buildControls() {
		driveKeyListener = new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				bindingKey = KEY_DRIVE;
				bindingKeyCurrent = driveKeyCurrent;
				Gdx.input.setInputProcessor(keyBindingsProcessor);
			}
		};
		reverseKeyListener = new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				bindingKey = KEY_REVERSE;
				bindingKeyCurrent = reverseKeyCurrent;
				Gdx.input.setInputProcessor(keyBindingsProcessor);
			}
		};
		leftKeyListener = new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				bindingKey = KEY_LEFT;
				bindingKeyCurrent = leftKeyCurrent;
				Gdx.input.setInputProcessor(keyBindingsProcessor);
			}
		};
		rightKeyListener = new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				bindingKey = KEY_RIGHT;
				bindingKeyCurrent = rightKeyCurrent;
				Gdx.input.setInputProcessor(keyBindingsProcessor);
			}
		};
		brakeKeyListener = new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				bindingKey = KEY_BRAKE;
				bindingKeyCurrent = brakeKeyCurrent;
				Gdx.input.setInputProcessor(keyBindingsProcessor);
			}
		};
		zoomInKeyListener = new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				bindingKey = KEY_ZOOMIN;
				bindingKeyCurrent = zoomInKeyCurrent;
				Gdx.input.setInputProcessor(keyBindingsProcessor);
			}
		};
		zoomOutKeyListener = new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				bindingKey = KEY_ZOOMOUT;
				bindingKeyCurrent = zoomOutKeyCurrent;
				Gdx.input.setInputProcessor(keyBindingsProcessor);
			}
		};
		menuKeyListener = new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				bindingKey = KEY_MENU;
				bindingKeyCurrent = menuKeyCurrent;
				Gdx.input.setInputProcessor(keyBindingsProcessor);
			}
		};
    	backListener = new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				parent.changeScreen(SETTINGS_SCREEN);
			}
		};
    	
		driveKeyButton.addListener(driveKeyListener);
		reverseKeyButton.addListener(reverseKeyListener);
		leftKeyButton.addListener(leftKeyListener);
		rightKeyButton.addListener(rightKeyListener);
		brakeKeyButton.addListener(brakeKeyListener);
		zoomInKeyButton.addListener(zoomInKeyListener);
		zoomOutKeyButton.addListener(zoomOutKeyListener);
		menuKeyButton.addListener(menuKeyListener);
    	backButton.addListener(backListener);
	}
	
	private class KeyBindingsProcessor implements InputProcessor {

		@Override
		public boolean keyDown(int keycode) {
		    keyBindings.setKey(bindingKey, keycode);
		    bindingKeyCurrent.setText(Keys.toString(keycode));
		    Gdx.input.setInputProcessor(stage);
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
