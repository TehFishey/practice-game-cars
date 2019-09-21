package game.fish.cars.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.beans.PropertyChangeSupport;

import game.fish.cars.CarsGame;
import game.fish.cars.KeyBindings;
import game.fish.cars.commands.AccelerateBackwardCommand;
import game.fish.cars.commands.AccelerateForwardCommand;
import game.fish.cars.commands.AccelerateNoneCommand;
import game.fish.cars.commands.BrakeOffCommand;
import game.fish.cars.commands.BrakeOnCommand;
import game.fish.cars.commands.MenuCommand;
import game.fish.cars.commands.TurnLeftCommand;
import game.fish.cars.commands.TurnNoneCommand;
import game.fish.cars.commands.TurnRightCommand;
import game.fish.cars.commands.ZoomInCommand;
import game.fish.cars.commands.ZoomOutCommand;
import game.fish.cars.entities.CarVehicle;
import game.fish.cars.entities.HoverVehicle;
import game.fish.cars.entities.MotorcycleVehicle;
import game.fish.cars.entities.VehicleEntity;
import game.fish.cars.listeners.AchievementContactListener;
import game.fish.cars.tools.MapLoader;

import static game.fish.cars.Constants.GRAVITY;
import static game.fish.cars.Constants.DEFAULT_ZOOM;
import static game.fish.cars.Constants.PPM;

import static game.fish.cars.Constants.SCREEN;
import static game.fish.cars.Constants.CAR;
import static game.fish.cars.Constants.MAP;

import static game.fish.cars.KeyBindings.KEY_DRIVE;
import static game.fish.cars.KeyBindings.KEY_REVERSE;
import static game.fish.cars.KeyBindings.KEY_LEFT;
import static game.fish.cars.KeyBindings.KEY_RIGHT;
import static game.fish.cars.KeyBindings.KEY_BRAKE;
import static game.fish.cars.KeyBindings.KEY_ZOOMIN;
import static game.fish.cars.KeyBindings.KEY_ZOOMOUT;
import static game.fish.cars.KeyBindings.KEY_MENU;

import static game.fish.cars.entities.CarVehicle.FRONT_WHEEL_DRIVE;
import static game.fish.cars.entities.CarVehicle.ALL_WHEEL_DRIVE;
//import static game.fish.cars.entities.CarEntity.REAR_WHEEL_DRIVE;


public class PlayScreen implements Screen {

	private final CarsGame parent;
	private final KeyBindings keyBindings;
	private final SpriteBatch batch;
	private final World world;
	private final Box2DDebugRenderer b2debug;
	private final OrthographicCamera camera;
	private final Viewport viewport;
	private final MapLoader loader;
	private final PropertyChangeSupport achievementPCS;
	private final AchievementContactListener contactListener;
	private final VehicleEntity player;
	private final Fixture playerFixture;
	
	private final Stage hud;
	private final Skin hudSkin;
	private Label speedDisplay;
	private Label timeDisplay;
	private int speedTracker;
	private int timeTracker;
	
	private final long startTime;
	private final Stage achievementOverlay;

	private final AccelerateForwardCommand driveCommand = new AccelerateForwardCommand();
	private final AccelerateBackwardCommand reverseCommand = new AccelerateBackwardCommand();
	private final AccelerateNoneCommand stopCommand = new AccelerateNoneCommand();
	private final TurnLeftCommand leftCommand = new TurnLeftCommand();
	private final TurnRightCommand rightCommand = new TurnRightCommand();
	private final TurnNoneCommand straightCommand = new TurnNoneCommand();
	private final BrakeOnCommand brakeCommand = new BrakeOnCommand();
	private final BrakeOffCommand unbrakeCommand = new BrakeOffCommand();
	private final ZoomInCommand zoomInCommand = new ZoomInCommand();
	private final ZoomOutCommand zoomOutCommand = new ZoomOutCommand();
	private final MenuCommand menuCommand = new MenuCommand();
	
	public PlayScreen(CarsGame parent, CAR carChoice, MAP mapChoice) {
		this.parent = parent;
		keyBindings = this.parent.getKeyBindings();
		batch = new SpriteBatch();
		world = new World(GRAVITY, true);
		b2debug = new Box2DDebugRenderer();
		camera = new OrthographicCamera();
		viewport = new FitViewport(640 / PPM, 480 / PPM, camera);
		loader = new MapLoader(world, mapChoice);
		achievementPCS = new PropertyChangeSupport(this);
		achievementPCS.addPropertyChangeListener(parent.getAchievementListener());
		
		hud = new Stage();
		hudSkin = new Skin(Gdx.files.internal("skin/neon-ui.json"));
		speedDisplay = new Label("Speed: ", hudSkin);
		speedDisplay.setPosition(0, 460);
		timeDisplay = new Label("Time: ", hudSkin);
		timeDisplay.setPosition(0, 440);
		hud.addActor(speedDisplay);
		hud.addActor(timeDisplay);
		
		startTime = System.currentTimeMillis();
		achievementOverlay = parent.getAchievementOverlay().getStage();
		
		switch (carChoice) {
		case FWDCAR:
			player = new CarVehicle(world, loader, FRONT_WHEEL_DRIVE);
			break;
		case AWDCAR:
			player = new CarVehicle(world, loader, ALL_WHEEL_DRIVE);
			break;
		case MOTORCYCLE:
			player = new MotorcycleVehicle(world, loader);
			break;
		case HOVERCAR:
			player = new HoverVehicle(world, loader);
			break;
		default:
			player = new CarVehicle(world, loader, FRONT_WHEEL_DRIVE);
		}
		
		playerFixture = player.getBody().getFixtureList().first();
		contactListener = new AchievementContactListener(parent.getAchievements(), this, playerFixture);
		world.setContactListener(contactListener);
		camera.zoom = DEFAULT_ZOOM;
	}

	public void show() {
	}

	public void render(float delta) {
		// screen loop
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		takeInput();
		player.update();
		update(delta);
		updateTrackers();
		drawWorld();
		drawHud();
		achievementOverlay.draw();
	}
		
	
	private void takeInput() {
		if (keyBindings.isKeyBindingPressed(KEY_DRIVE)) driveCommand.execute(player);
		else if (keyBindings.isKeyBindingPressed(KEY_REVERSE)) reverseCommand.execute(player);
		else stopCommand.execute(player);
		
		if (keyBindings.isKeyBindingPressed(KEY_LEFT)) leftCommand.execute(player);
		else if (keyBindings.isKeyBindingPressed(KEY_RIGHT)) rightCommand.execute(player);
		else straightCommand.execute(player);
		
		if (keyBindings.isKeyBindingPressed(KEY_BRAKE)) brakeCommand.execute(player);
		else unbrakeCommand.execute(player);
		
		if (keyBindings.isKeyBindingPressed(KEY_MENU)) menuCommand.execute(parent);
		
		if (keyBindings.isKeyBindingPressed(KEY_ZOOMIN)) zoomInCommand.execute(camera);
		else if (keyBindings.isKeyBindingPressed(KEY_ZOOMOUT)) zoomOutCommand.execute(camera);
	}
	
	private void update(final float delta) {		
		camera.position.set(player.getBody().getPosition(), 0);
		camera.update();
		
		world.step(delta, 6, 2);
	}
	
	private void updateTrackers() {
		speedTracker = Math.round(player.getAbsoluteSpeed());
		if (speedTracker >= 100f) achievementPCS.firePropertyChange("playerSpeed",null,speedTracker);
		
		long timeMillis = System.currentTimeMillis() - startTime;
		int timeSecs = (int) Math.round((float) timeMillis * 0.001);
		if ((timeSecs > timeTracker)) { 
			timeTracker = timeSecs;
			achievementPCS.firePropertyChange("timeElapsed",null,timeTracker);
		}
		
		if (camera.zoom == 50f || camera.zoom == 2f) achievementPCS.firePropertyChange("cameraZoom",null,camera.zoom);
	}
	
	private void drawWorld() {
		batch.setProjectionMatrix(camera.combined);
		b2debug.render(world, camera.combined);
	}
		
	private void drawHud() {
		String currentSpeed = "Speed : " + String.valueOf(speedTracker);
		speedDisplay.setText(currentSpeed);
		String currentTime = "Time : " + String.valueOf(timeTracker); 
		timeDisplay.setText(currentTime);

		hud.getViewport().apply();
		hud.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		hud.draw();
	}

	public void resize(int width, int height) {
		viewport.update(width, height);
		hud.getViewport().update(width, height, true);
		achievementOverlay.getViewport().update(width, height, true);

	}

	public void dispose() {
		batch.dispose();
		world.dispose();
		hud.dispose();
		b2debug.dispose();
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

}
