package game.fish.cars.views;

import static game.fish.cars.Constants.MENU_SCREEN;

import java.util.HashMap;
import java.util.Map.Entry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import game.fish.cars.Achievements;
import game.fish.cars.CarsGame;
import game.fish.cars.achievements.Property;

public class AchievementsScreen extends InterfaceScreen {
	
	Achievements achievements;
	HashMap<String, Property> achievementHashMap;
	
	Label titleLabel;
	TextButton backButton;
	
	public AchievementsScreen(CarsGame parent) {
		super(parent);
		achievements = parent.getAchievements();
		achievementHashMap = achievements.getAchievements();
		
		titleLabel = new Label ("Achievements", skin);
		
		backButton = buildScreenButton("Back", skin, parent, MENU_SCREEN);
	}
	
	
	@Override
	public void show() {
		Table table = new Table();
		Table internalTable = new Table();
		ScrollPane scrollPane = new ScrollPane(table, skin);
		scrollPane.setActor(internalTable);

		//table.setDebug(true);
		table.setFillParent(true);
		table.add(titleLabel);
		table.row();
		table.add(scrollPane);
		table.row();
		table.add(backButton);

		for (Entry achievementEntry : achievementHashMap.entrySet()) {
			
			Property entry = (Property) achievementEntry.getValue();
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
		Label completionLabel = new Label("incomplete", skin);
		
		if (complete) completionLabel.setText("complete");
		
		table.row();
		table.add(achievementNameLabel, completionLabel).pad(0,0,0,15);
		table.add(achievementDescLabel);
		
		return table;
	}

}
