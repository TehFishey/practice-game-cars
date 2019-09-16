package game.fish.cars.views;

import static game.fish.cars.Constants.MENU_SCREEN;
import static game.fish.cars.Constants.PATH_ACHIEVEMENT_TRUE;
import static game.fish.cars.Constants.PATH_ACHIEVEMENT_FALSE;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import game.fish.cars.Achievements;
import game.fish.cars.CarsGame;
import game.fish.cars.achievements.Achievement;

public class AchievementsScreen extends InterfaceScreen {
	
	Achievements achievements;
	LinkedHashMap<String, Achievement> achievementHashMap;
	
	Texture completeImageTexture;
	Texture incompleteImageTexture;
	Label titleLabel;
	TextButton backButton;
	
	
	public AchievementsScreen(CarsGame parent) {
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
		//internalTable.setFillParent(true);
		for (Entry achievementEntry : achievementHashMap.entrySet()) {
			
			Achievement entry = (Achievement) achievementEntry.getValue();
			String achievementNameText = (String) entry.getName();
			String achievementDescText = (String) entry.getDesc();
			boolean achievementComplete = (boolean) entry.isCompleted();
			
			createAchievementCell(internalTable, achievementNameText, achievementDescText, achievementComplete);
		}
		
		stage.clear();
		stage.addActor(table);
		Gdx.input.setInputProcessor(stage);
	}
	
	@Override
	public void dispose() {
		stage.dispose();
	}
	
	private Table createAchievementCell(Table table, String nameText, String labelText, boolean complete) {
		Label achievementNameLabel = new Label(nameText, skin);
		Label achievementDescLabel = new Label(labelText, skin);
		Image achievementImage = new Image(completeImageTexture);
		Table inline = new Table(); 
		
		if (!complete) {
			achievementDescLabel.setText("");
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
