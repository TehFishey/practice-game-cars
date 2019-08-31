package game.fish.cars.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import game.fish.cars.CarsGame;
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
import game.fish.cars.entities.CarEntity;
import game.fish.cars.tools.MapLoader;

import static game.fish.cars.Constants.GRAVITY;
import static game.fish.cars.Constants.DEFAULT_ZOOM;
import static game.fish.cars.Constants.PPM;

import static game.fish.cars.entities.CarEntity.FRONT_WHEEL_DRIVE;
//import static game.fish.cars.entities.CarEntity.REAR_WHEEL_DRIVE;
//import static game.fish.cars.entities.CarEntity.ALL_WHEEL_DRIVE;

public class PlayScreen implements Screen {

	private CarsGame parent;
	private final SpriteBatch batch;
	private final World world;
	private final Box2DDebugRenderer b2debug;
	private final OrthographicCamera camera;
	private final Viewport viewport;
	private final CarEntity player;
	private final MapLoader loader;
	
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
	
	public PlayScreen(CarsGame parent) {
		this.parent = parent;
		batch = new SpriteBatch();
		world = new World(GRAVITY, true);
		b2debug = new Box2DDebugRenderer();
		camera = new OrthographicCamera();
		viewport = new FitViewport(640 / PPM, 480 / PPM, camera);
		loader = new MapLoader(world);
		player = new CarEntity(loader.getPlayer(), world, FRONT_WHEEL_DRIVE);
		
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
		draw();
	}
		
	
	private void takeInput() {
		if (Gdx.input.isKeyPressed(Input.Keys.W)) driveCommand.execute(player);
		else if (Gdx.input.isKeyPressed(Input.Keys.S)) reverseCommand.execute(player);
		else stopCommand.execute(player);
		
		if (Gdx.input.isKeyPressed(Input.Keys.A)) leftCommand.execute(player);
		else if (Gdx.input.isKeyPressed(Input.Keys.D)) rightCommand.execute(player);
		else straightCommand.execute(player);
		
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) brakeCommand.execute(player);
		else unbrakeCommand.execute(player);
		
		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) menuCommand.execute(parent);
		
		if (Gdx.input.isKeyPressed(Input.Keys.MINUS)) zoomInCommand.execute(camera);
		else if (Gdx.input.isKeyPressed(Input.Keys.EQUALS)) zoomOutCommand.execute(camera);
	}
	
	private void update(final float delta) {
		camera.position.set(player.getBody().getPosition(), 0);
		camera.update();
		
		world.step(delta, 6, 2);
	}
		
	private void draw() {
		batch.setProjectionMatrix(camera.combined);
		b2debug.render(world, camera.combined);
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
