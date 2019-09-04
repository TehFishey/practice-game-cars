package game.fish.cars.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

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
import game.fish.cars.entities.VehicleEntity;
import game.fish.cars.tools.MapLoader;

import static game.fish.cars.Constants.GRAVITY;
import static game.fish.cars.Constants.DEFAULT_ZOOM;
import static game.fish.cars.Constants.PPM;

import static game.fish.cars.Constants.CAR_FWDCAR;
import static game.fish.cars.Constants.CAR_AWDCAR;
import static game.fish.cars.Constants.CAR_HOVERCAR;

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
	private final VehicleEntity player;
	private final MapLoader loader;
	
	private final Stage hud;
	private final Skin hudSkin;
	private Label speedDisplay;

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
	
	public PlayScreen(CarsGame parent, int carChoice, int mapChoice) {
		this.parent = parent;
		keyBindings = this.parent.getKeyBindings();
		batch = new SpriteBatch();
		world = new World(GRAVITY, true);
		b2debug = new Box2DDebugRenderer();
		camera = new OrthographicCamera();
		viewport = new FitViewport(640 / PPM, 480 / PPM, camera);
		loader = new MapLoader(world, mapChoice);
		
		hud = new Stage();
		hudSkin = new Skin(Gdx.files.internal("skin/neon-ui.json"));
		speedDisplay = new Label("Speed: ", hudSkin);
		speedDisplay.setPosition(0, 460);
		hud.addActor(speedDisplay);
		
		switch (carChoice) {
		case CAR_FWDCAR:
			player = new CarVehicle(loader.getPlayer(), world, FRONT_WHEEL_DRIVE);
			break;
		case CAR_AWDCAR:
			player = new CarVehicle(loader.getPlayer(), world, ALL_WHEEL_DRIVE);
			break;
		case CAR_HOVERCAR:
			player = new HoverVehicle(loader.getPlayer(), world);
			break;
		default:
			player = new CarVehicle(loader.getPlayer(), world, FRONT_WHEEL_DRIVE);
		}
		
		camera.zoom = DEFAULT_ZOOM;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub 

	}

	@Override
	public void render(float delta) {
		// screen loop
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		takeInput();
		player.update();
		update(delta);
		drawWorld();
		drawHud();
	}
		
	
	private void takeInput() {
		if (keyBindings.checkKeyBinding(KEY_DRIVE)) driveCommand.execute(player);
		else if (keyBindings.checkKeyBinding(KEY_REVERSE)) reverseCommand.execute(player);
		else stopCommand.execute(player);
		
		if (keyBindings.checkKeyBinding(KEY_LEFT)) leftCommand.execute(player);
		else if (keyBindings.checkKeyBinding(KEY_RIGHT)) rightCommand.execute(player);
		else straightCommand.execute(player);
		
		if (keyBindings.checkKeyBinding(KEY_BRAKE)) brakeCommand.execute(player);
		else unbrakeCommand.execute(player);
		
		if (keyBindings.checkKeyBinding(KEY_MENU)) menuCommand.execute(parent);
		
		if (keyBindings.checkKeyBinding(KEY_ZOOMIN)) zoomInCommand.execute(camera);
		else if (keyBindings.checkKeyBinding(KEY_ZOOMOUT)) zoomOutCommand.execute(camera);
	}
	
	private void update(final float delta) {		
		camera.position.set(player.getBody().getPosition(), 0);
		camera.update();
		
		world.step(delta, 6, 2);
	}
	
	private void drawWorld() {
		batch.setProjectionMatrix(camera.combined);
		b2debug.render(world, camera.combined);
	}
		
	private void drawHud() {
		String currentSpeed = "Speed : " + Math.round(player.getAbsoluteSpeed()); 
		speedDisplay.setText(currentSpeed);

		hud.getViewport().apply();
		hud.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		hud.draw();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);

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
		batch.dispose();
		world.dispose();
		b2debug.dispose();
	}

}
