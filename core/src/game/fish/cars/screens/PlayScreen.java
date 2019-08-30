package game.fish.cars.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import game.fish.cars.entities.CarEntity;
import game.fish.cars.tools.MapLoader;

import static game.fish.cars.Constants.GRAVITY;
import static game.fish.cars.Constants.DEFAULT_ZOOM;
import static game.fish.cars.Constants.PPM;

import static game.fish.cars.entities.CarEntity.DRIVE_DIRECTION_NONE;
import static game.fish.cars.entities.CarEntity.DRIVE_DIRECTION_FORWARD;
import static game.fish.cars.entities.CarEntity.DRIVE_DIRECTION_BACKWARD;
import static game.fish.cars.entities.CarEntity.TURN_DIRECTION_NONE;
import static game.fish.cars.entities.CarEntity.TURN_DIRECTION_LEFT;
import static game.fish.cars.entities.CarEntity.TURN_DIRECTION_RIGHT;
import static game.fish.cars.entities.CarEntity.FRONT_WHEEL_DRIVE;
import static game.fish.cars.entities.CarEntity.REAR_WHEEL_DRIVE;
import static game.fish.cars.entities.CarEntity.ALL_WHEEL_DRIVE;

public class PlayScreen implements Screen {

	private final SpriteBatch mBatch;
	private final World mWorld;
	private final Box2DDebugRenderer mB2debug;
	private final OrthographicCamera mCamera;
	private final Viewport mViewport;
	private final CarEntity mPlayer;
	private final MapLoader mLoader;
	
	public PlayScreen() {
		mBatch = new SpriteBatch();
		mWorld = new World(GRAVITY, true);
		mB2debug = new Box2DDebugRenderer();
		mCamera = new OrthographicCamera();
		mViewport = new FitViewport(640 / PPM, 480 / PPM, mCamera);
		mLoader = new MapLoader(mWorld);
		mPlayer = new CarEntity(mLoader.getPlayer(), mWorld, FRONT_WHEEL_DRIVE);
		
		mCamera.zoom = DEFAULT_ZOOM;
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
		mPlayer.update();
		update(delta);
		draw();
	}
		
	
	private void takeInput() {
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			mPlayer.inputDriveDirection(DRIVE_DIRECTION_FORWARD);
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			mPlayer.inputDriveDirection(DRIVE_DIRECTION_BACKWARD);
		}
		else {
			mPlayer.inputDriveDirection(DRIVE_DIRECTION_NONE);
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			mPlayer.inputTurnDirection(TURN_DIRECTION_LEFT);
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			mPlayer.inputTurnDirection(TURN_DIRECTION_RIGHT);
		}
		else {
			mPlayer.inputTurnDirection(TURN_DIRECTION_NONE);
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
			mPlayer.inputBrakes(true);
		}
		else {
			mPlayer.inputBrakes(false);
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
			Gdx.app.exit();
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.MINUS)) {
			mCamera.zoom += 0.4f;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.EQUALS)) {
			mCamera.zoom -= 0.4f;
		}
	}
	
	private void update(final float delta) {
		mCamera.position.set(mPlayer.getBody().getPosition(), 0);
		mCamera.update();
		
		mWorld.step(delta, 6, 2);
	}
		
	private void draw() {
		mBatch.setProjectionMatrix(mCamera.combined);
		mB2debug.render(mWorld, mCamera.combined);
	}
		
	

	@Override
	public void resize(int width, int height) {
		mViewport.update(width, height);

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
		mBatch.dispose();
		mWorld.dispose();
		mB2debug.dispose();
	}

}
