package game.fish.cars.views;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import static game.fish.cars.Constants.PLAY_SCREEN;
import static game.fish.cars.Constants.MENU_SCREEN;
import static game.fish.cars.Constants.SETTINGS_SCREEN;
import static game.fish.cars.Constants.KEYBINDINGS_SCREEN;
import static game.fish.cars.Constants.ACHIEVEMENTS_SCREEN;
import static game.fish.cars.Constants.PATH_ACHIEVEMENT_TRUE;
import static game.fish.cars.Constants.PATH_ACHIEVEMENT_FALSE;

import game.fish.cars.Achievements;
import game.fish.cars.CarsGame;
import game.fish.cars.achievements.Achievement;

public class AchievementsInterface extends InterfaceScreen {
	
	Achievements achievements;
	LinkedHashMap<String, Achievement> achievementHashMap;
	
	Texture completeImageTexture;
	Texture incompleteImageTexture;
	Label titleLabel;
	TextButton backButton;
	
	
	public AchievementsInterface(CarsGame parent) {
		super(parent);
		achievements = parent.getAchievements();
		achievementHashMap = achievements.getAchievements();
		
		completeImageTexture = new Texture(Gdx.files.internal(PATH_ACHIEVEMENT_TRUE));
		incompleteImageTexture = new Texture(Gdx.files.internal(PATH_ACHIEVEMENT_FALSE));
		
		titleLabel = new Label ("Achievements", skin);
		titleLabel.setFontScale(3);
		
		backButton = buildScreenButton("Back", skin, parent, MENU_SCREEN);
	}
	
	
	@Override
	public void show() {
		Table table = new Table();
		Table internalTable = new Table();
		ScrollPane scrollPane = new ScrollPane(internalTable, skin);
		scrollPane.setFadeScrollBars(false);

		//table.setDebug(true);
		table.defaults().left();
		table.setFillParent(true);
		table.add(titleLabel);
		table.row();
		table.add(scrollPane);
		table.row();
		table.add(backButton).center();

		internalTable.defaults().left();
		for (Entry<String, Achievement> achievementEntry : achievementHashMap.entrySet()) {
			
			Achievement entry = achievementEntry.getValue();
			String achievementNameText = entry.getName();
			String achievementDescText = entry.getDesc();
			boolean achievementComplete = entry.getCompleted();
			
			createAchievementCell(internalTable, achievementNameText, achievementDescText, achievementComplete);
		}
		
		stage.clear();
		stage.addActor(table);
		Gdx.input.setInputProcessor(stage);
	}
	
	@Override
	public void dispose() {
		stage.dispose();
		completeImageTexture.dispose();
		incompleteImageTexture.dispose();
	}
	
	private Table createAchievementCell(Table table, String nameText, String labelText, boolean complete) {
		Label achievementNameLabel = new Label(nameText, skin);
		Label achievementDescLabel = new Label(labelText, skin);
		Image achievementImage = new Image(completeImageTexture);
		Table inline = new Table(); 
		
		if (!complete) {
			achievementDescLabel.setText("Achievement Locked!");
			achievementImage = new Image(incompleteImageTexture);
		}
		
		achievementImage.setColor(0, 1, 0, 1);
		
		inline.defaults().left();
		inline.add(achievementNameLabel);
		inline.row();
		inline.add(achievementDescLabel);
		
		table.row().padBottom(20);
		table.add(achievementImage).width(32).height(32).padRight(12);
		table.add(inline);
		
		return table;
	}

}
