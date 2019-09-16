package game.fish.cars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Timer;

import game.fish.cars.achievements.Achievement;

public class AchievementOverlay {
	private final Stage stage;
	private final Skin skin;
	private final Group popups;
	
	public AchievementOverlay() {
		stage = new Stage();
		skin = new Skin(Gdx.files.internal("skin/neon-ui.json"));
		popups = new Group();
		stage.addActor(popups);
	}
	
	public Stage getStage() {
		return stage;
	}
	
	public void displayAchievement(Achievement achievement) {
		final Window achievementWindow = new Window("", skin);
		final Timer displayTimer =  new Timer();
		final Label achievementSplat = new Label("Achievement Get!", skin);
		final Label achievementInfo = new Label(achievement.getName(), skin);
		
		achievementWindow.setPosition(640, 0);
		achievementWindow.setSize(200, 80);
		achievementWindow.defaults().left();
		achievementWindow.add(achievementSplat);
		achievementWindow.row();
		achievementWindow.add(achievementInfo);
		
		displayTimer.scheduleTask(new Timer.Task() {
			@Override
		     public void run() {
				achievementWindow.remove();
			}
		}, 4);
		
		for (Actor window : popups.getChildren()) window.moveBy(0, 80);
		popups.addActor(achievementWindow);
	}
	
	public void dispose() {
		stage.dispose();
	}
}
