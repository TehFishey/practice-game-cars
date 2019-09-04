package game.fish.cars.entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class VehicleEntity extends Entity {

	public static final int DRIVE_DIRECTION_NONE = 0;
	public static final int DRIVE_DIRECTION_FORWARD = 1;
	public static final int DRIVE_DIRECTION_BACKWARD = 2;
	public static final int TURN_DIRECTION_NONE = 0;
	public static final int TURN_DIRECTION_LEFT = 1;
	public static final int TURN_DIRECTION_RIGHT = 2;
	
	protected static float ANGULAR_DAMPING;
	protected static float LINEAR_DAMPING;
	protected static float RESTITUTION;
	
	protected static float MAX_SPEED;
	protected static float MAX_ACCELERATION;
	protected static float MAX_REVERSE_ACCELERATION;
	protected static float ACCELERATION_STEP;
	protected static float REVERSE_ACCELERATION_STEP;
	protected static float ACCELERATION_DECAY;
	protected static float BRAKE_STRENGTH;
	
	protected int driveDirection = DRIVE_DIRECTION_NONE;
	protected int turnDirection = TURN_DIRECTION_NONE;
	protected boolean brakesOn = false;
	
	protected final World world;
	
	public VehicleEntity(Body body, World world) {
		super(body);
		this.world = world;
	}
	
	public void inputDriveDirection(int direction) {
		this.driveDirection = direction;
	}
	
	public void inputTurnDirection(int direction) {
		this.turnDirection = direction;
	}
	
	public void inputBrakes(boolean bool) {
		this.brakesOn = bool;
	}
	
	private void processInput() { }

}
