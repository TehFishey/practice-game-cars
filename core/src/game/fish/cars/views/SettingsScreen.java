package game.fish.cars.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import java.beans.PropertyChangeSupport;

import game.fish.cars.CarsGame;

import static game.fish.cars.Constants.MENU_SCREEN;
import static game.fish.cars.Constants.KEYBINDINGS_SCREEN;

public class SettingsScreen extends InterfaceScreen {
	
	private final Label titleLabel;
	private final Label musicVolumeLabel;
	private final Label musicEnableLabel;
	
	private final Slider musicVolumeSlider;
	private final CheckBox musicEnableCheckbox;
	private final TextButton backButton;
	private final TextButton keyBindingsButton;
	
	public SettingsScreen(final CarsGame parent) {
		super(parent);
		
		titleLabel = new Label ("Settings", skin);
		musicVolumeLabel = new Label ("Music Volume", skin);
		musicEnableLabel = new Label ("Music Enabled", skin);
		
		EventListener musicVolumeListener = new EventListener() {
    		@Override
    		public boolean handle(Event event) {
    			float newVolume = musicVolumeSlider.getValue();
    			parent.getSettings().setMusicVolume(newVolume); 
    			parent.getMusic().setVolume(newVolume);
    			achievementPCS.firePropertyChange("musicVolume",null,(int)newVolume*10);
    			return false;
    		}
    	};
    	EventListener musicEnableListener = new EventListener() {
    		@Override
    		public boolean handle(Event event) {
    			parent.getSettings().setMusicEnabled(musicEnableCheckbox.isChecked());
    			if (musicEnableCheckbox.isChecked()) {
    				parent.getMusic().play();
    				achievementPCS.firePropertyChange("musicPlaying", null, 1);
    			}
    			else {
    				parent.getMusic().stop();
    				achievementPCS.firePropertyChange("musicPlaying", null, 0);
    			}
    			return false;
    		}
    	};
    	
    	musicVolumeSlider = new Slider(0f, 1f, 0.1f,false, skin);
		musicVolumeSlider.setValue(parent.getSettings().getMusicVolume());
    	musicVolumeSlider.addListener(musicVolumeListener);
    	musicEnableCheckbox = new CheckBox(null, skin);
		musicEnableCheckbox.setChecked(parent.getSettings().getMusicEnabled());
    	musicEnableCheckbox.addListener(musicEnableListener);
		backButton = buildScreenButton("Back", skin, this.parent, MENU_SCREEN);
		keyBindingsButton = buildScreenButton("Key Bindings", skin, this.parent, KEYBINDINGS_SCREEN);
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
	public void dispose() {
		stage.dispose();
	}

}
