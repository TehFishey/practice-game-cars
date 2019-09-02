package game.fish.cars.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import game.fish.cars.CarsGame;

import static game.fish.cars.Constants.MENU_SCREEN;
import static game.fish.cars.Constants.KEYBINDINGS_SCREEN;

public class SettingsScreen implements Screen {
	
	private final CarsGame parent;
	private final Stage stage;
	private final Skin skin;
	
	private final Label titleLabel;
	private final Label musicVolumeLabel;
	private final Label musicEnableLabel;
	
	private final Slider musicVolumeSlider;
	private final CheckBox musicEnableCheckbox;
	private final TextButton backButton;
	private final TextButton keyBindingsButton;
	
	private EventListener musicVolumeListener;
	private EventListener musicEnableListener;
	private ChangeListener backListener;
	private ChangeListener keyBindingsListener;
	
	public SettingsScreen(final CarsGame parent) {
		this.parent = parent;
		stage = new Stage(new ScreenViewport());
		skin = new Skin(Gdx.files.internal("skin/neon-ui.json"));
		
		titleLabel = new Label ("Settings", skin);
		musicVolumeLabel = new Label ("Music Volume", skin);
		musicEnableLabel = new Label ("Music Enabled", skin);
		
		musicVolumeSlider = new Slider(0f, 1f, 0.1f,false, skin);
		musicVolumeSlider.setValue(parent.getSettings().getMusicVolume());
		musicEnableCheckbox = new CheckBox(null, skin);
		musicEnableCheckbox.setChecked(parent.getSettings().getMusicEnabled());
		backButton = new TextButton("Back", skin);
		keyBindingsButton = new TextButton("Key Bindings", skin);
		
		buildControls();
	}
	
	private void buildControls() {
		musicVolumeListener = new EventListener() {
    		@Override
    		public boolean handle(Event event) {
    			parent.getSettings().setMusicVolume(musicVolumeSlider.getValue());
    			parent.getMusic().setVolume(musicVolumeSlider.getValue());
    			return false;
    		}
    	};
    	musicEnableListener = new EventListener() {
    		@Override
    		public boolean handle(Event event) {
    			parent.getSettings().setMusicEnabled(musicEnableCheckbox.isChecked());
    			if (musicEnableCheckbox.isChecked()) parent.getMusic().play();
    			else parent.getMusic().stop();
    			return false;
    		}
    	};
    	backListener = new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				parent.changeScreen(MENU_SCREEN);
			}
		};
		keyBindingsListener = new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				parent.changeScreen(KEYBINDINGS_SCREEN);
			}
		};
    	
    	musicVolumeSlider.addListener(musicVolumeListener);
    	musicEnableCheckbox.addListener(musicEnableListener);
    	backButton.addListener(backListener);
    	keyBindingsButton.addListener(keyBindingsListener);
	}
	
	@Override
	public void show() {
		Table table = new Table();

		//table.setDebug(true);
		table.setFillParent(true);
		table.add(titleLabel);
		table.row();
		table.add(musicVolumeLabel);
		table.add(musicVolumeSlider);
		table.row();
		table.add(musicEnableLabel);
		table.add(musicEnableCheckbox);
		table.row();
		table.add(backButton);
		table.add(keyBindingsButton);
		
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
