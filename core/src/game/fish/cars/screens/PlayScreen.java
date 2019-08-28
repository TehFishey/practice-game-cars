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

import game.fish.cars.tools.MapLoader;
import game.fish.cars.tools.ShapeFactory;

import static game.fish.cars.Constants.GRAVITY;
import static game.fish.cars.Constants.DEFAULT_ZOOM;
import static game.fish.cars.Constants.PPM;

public class PlayScreen implements Screen {

	private static final int DRIVE_DIRECTION_NONE = 0;
	private static final int DRIVE_DIRECTION_FORWARD = 1;
	private static final int DRIVE_DIRECTION_BACKWARD = 2;
	private static final int TURN_DIRECTION_NONE = 0;
	private static final int TURN_DIRECTION_LEFT = 1;
	private static final int TURN_DIRECTION_RIGHT = 2;
	
	private int mDriveDirection = DRIVE_DIRECTION_NONE;
	private int mTurnDirection = DRIVE_DIRECTION_NONE;
	
	private static final float VELOCITY_MOD = 0;
	
	private final SpriteBatch mBatch;
	private final World mWorld;
	private final Box2DDebugRenderer mB2debug;
	private final OrthographicCamera mCamera;
	private final Viewport mViewport;
	private final Body mPlayer;
	private final MapLoader mLoader;
	
	public PlayScreen() {
		mBatch = new SpriteBatch();
		mWorld = new World(GRAVITY, true);
		mB2debug = new Box2DDebugRenderer();
		mCamera = new OrthographicCamera();
		mViewport = new FitViewport(640 / PPM, 480 / PPM, mCamera);
		mLoader = new MapLoader(mWorld);
		mPlayer = mLoader.getPlayer();
		
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
		processInput();
		handleDrift(mPlayer);
		update(delta);
		draw();
	}
		
	
	private void takeInput() {
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			mDriveDirection = DRIVE_DIRECTION_FORWARD;
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			mDriveDirection = DRIVE_DIRECTION_BACKWARD;
		}
		else {
			mDriveDirection = DRIVE_DIRECTION_NONE;
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			mTurnDirection = TURN_DIRECTION_LEFT;
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			mTurnDirection = TURN_DIRECTION_RIGHT;
		}
		else {
			mTurnDirection = TURN_DIRECTION_NONE;
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
	
	private void processInput() {
		Vector2 baseVector = new Vector2();
		
		switch (mTurnDirection) {
		case TURN_DIRECTION_RIGHT:
			mPlayer.setAngularVelocity(-2f);
			break;
		case TURN_DIRECTION_LEFT:
			mPlayer.setAngularVelocity(2f);
			break;
		case TURN_DIRECTION_NONE:
			if (mPlayer.getAngularVelocity() != 0)
				mPlayer.setAngularVelocity(0);
		}
		
		switch(mDriveDirection) {
		case DRIVE_DIRECTION_FORWARD:
			baseVector.set(0, 120f);
			break;
		case DRIVE_DIRECTION_BACKWARD:
			baseVector.set(0, -120f);
			break;
		}
		
		if (!baseVector.isZero()) {
			mPlayer.applyForceToCenter(mPlayer.getWorldVector(baseVector), true);
		}
	}
	
	private Vector2 getForwardVelocity(Body body) {
		Vector2 bodyOrientation = body.getWorldVector(new Vector2(1,0));
		Vector2 bodyVelocity = body.getLinearVelocity();
		float normalVelocity = bodyOrientation.dot(bodyVelocity);
		return bodyOrientation.scl(normalVelocity);
	}
	
	private Vector2 getLateralVelocity(Body body) {
		Vector2 bodyOrientation = body.getWorldVector(new Vector2(0,1));
		Vector2 bodyVelocity = body.getLinearVelocity();
		float normalVelocity = bodyOrientation.dot(bodyVelocity);
		return bodyOrientation.scl(normalVelocity);
	}
	
	private void handleDrift(Body body) {
		Vector2 forwardSpeed = getForwardVelocity(body);
		Vector2 lateralSpeed = getLateralVelocity(body);
		mPlayer.setLinearVelocity( forwardSpeed.x + lateralSpeed.x * VELOCITY_MOD, forwardSpeed.y + lateralSpeed.y * VELOCITY_MOD);
	}
	
	private void update(final float delta) {
		mCamera.position.set(mPlayer.getPosition(), 0);
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
