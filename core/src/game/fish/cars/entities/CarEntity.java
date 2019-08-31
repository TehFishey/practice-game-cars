package game.fish.cars.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.utils.Array;

import static game.fish.cars.Constants.PPM;

public class CarEntity extends Entity {
	
	public static final int DRIVE_DIRECTION_NONE = 0;
	public static final int DRIVE_DIRECTION_FORWARD = 1;
	public static final int DRIVE_DIRECTION_BACKWARD = 2;
	public static final int TURN_DIRECTION_NONE = 0;
	public static final int TURN_DIRECTION_LEFT = 1;
	public static final int TURN_DIRECTION_RIGHT = 2;
	public static final int FRONT_WHEEL_DRIVE = 0;
	public static final int REAR_WHEEL_DRIVE = 1;
	public static final int ALL_WHEEL_DRIVE = 2;
	
	private static final float WHEEL_POSITION_X = 64f;
    private static final float WHEEL_POSITION_Y = 80f;
	private static final float WHEEL_LOCK_ANGLE = 30f;
	
	private static final float ANGULAR_DAMPING = 6f;
	private static final float LINEAR_DAMPING = 0.5f;
	private static final float RESTITUTION = 0.2f;
	
	private static final float MAX_ACCELERATION = 120f;
	private static final float MAX_REVERSE_ACCELERATION = -60f;
	private static final float ACCELERATION_STEP = 15f;
	private static final float REVERSE_ACCELERATION_STEP = -7.5f;
	private static final float ACCELERATION_DECAY = 45f;
	private static final float BRAKE_STRENGTH = 90f;
    
	private final Array<WheelEntity> frontWheels = new Array<WheelEntity>();
	private final Array<WheelEntity> rearWheels = new Array<WheelEntity>();
	private final Array<WheelEntity> driveWheels = new Array<WheelEntity>();
	
	private final World world;
	private final int drive;
	
	private int driveDirection = DRIVE_DIRECTION_NONE;
	private int turnDirection = TURN_DIRECTION_NONE;
	private boolean brakesOn = false;
	
	private float currentAcceleration = 0f;
	private float wheelAngle = 0f;
	
	public CarEntity(Body body, World world, int drive) {
		super(body);
		this.world = world;
		this.drive = drive;
				
		final WheelEntity wheelFL = new WheelEntity(
			new Vector2(getBody().getPosition().x * PPM + WHEEL_POSITION_X, getBody().getPosition().y * PPM + WHEEL_POSITION_Y), 
			new Vector2(16, 32), 
			this.world, 0.4f, this);
		final WheelEntity wheelFR = new WheelEntity(
			new Vector2(getBody().getPosition().x * PPM + -WHEEL_POSITION_X, getBody().getPosition().y * PPM + WHEEL_POSITION_Y), 
			new Vector2(16, 32), 
			this.world, 0.4f, this);
		final WheelEntity wheelRL = new WheelEntity(
			new Vector2(getBody().getPosition().x * PPM + WHEEL_POSITION_X, getBody().getPosition().y * PPM + -WHEEL_POSITION_Y), 
			new Vector2(16, 32), 
			this.world, 0.4f, this);
		final WheelEntity wheelRR = new WheelEntity(
			new Vector2(getBody().getPosition().x * PPM + -WHEEL_POSITION_X, getBody().getPosition().y * PPM + -WHEEL_POSITION_Y), 
			new Vector2(16, 32), 
			this.world, 0.4f, this);
		
		frontWheels.add(wheelFL, wheelFR);
		rearWheels.add(wheelRL, wheelRR);
		
		for (WheelEntity frontWheel : frontWheels) {
			final RevoluteJointDef jointDef = new RevoluteJointDef();
			jointDef.initialize(this.getBody(), frontWheel.getBody(), frontWheel.getBody().getWorldCenter());
			this.world.createJoint(jointDef);
		}
		for (WheelEntity rearWheel : rearWheels) {
			final PrismaticJointDef jointDef = new PrismaticJointDef();
            jointDef.initialize(this.getBody(), rearWheel.getBody(), rearWheel.getBody().getWorldCenter(), new Vector2(1, 0));
            jointDef.enableLimit = true;
            jointDef.lowerTranslation = jointDef.upperTranslation = 0;
            this.world.createJoint(jointDef);
		}
		
		switch (drive) {
		case FRONT_WHEEL_DRIVE:
			driveWheels.addAll(frontWheels);
			break;
		case REAR_WHEEL_DRIVE:
			driveWheels.addAll(rearWheels);
			break;
		case ALL_WHEEL_DRIVE:
			driveWheels.addAll(frontWheels);
			driveWheels.addAll(rearWheels);
			break;
		}
		
		getBody().setAngularDamping(ANGULAR_DAMPING);
		getBody().setLinearDamping(LINEAR_DAMPING);
		getBody().getFixtureList().get(0).setRestitution(RESTITUTION);
		
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
	
	private void processInput() {
		Vector2 engineForce = new Vector2();
		
		switch (turnDirection) {
		case TURN_DIRECTION_RIGHT:
			if (wheelAngle > -WHEEL_LOCK_ANGLE) 
				wheelAngle = Math.max(wheelAngle -= 2f, -WHEEL_LOCK_ANGLE);
			break;
		case TURN_DIRECTION_LEFT:
			if (wheelAngle < WHEEL_LOCK_ANGLE) 
				wheelAngle = Math.min(wheelAngle += 2f, WHEEL_LOCK_ANGLE);
			break;
		case TURN_DIRECTION_NONE:
			if (wheelAngle < 0)
				wheelAngle += 1f;
			else if (wheelAngle > 0)
				wheelAngle -= 1f;
			break;
		}
		for (WheelEntity frontWheel : frontWheels)
			frontWheel.setAngle(wheelAngle);
		
		switch(driveDirection) {
		case DRIVE_DIRECTION_FORWARD:
			if (currentAcceleration < 0)
				currentAcceleration = Math.min(currentAcceleration + ACCELERATION_DECAY, 0);
			if (currentAcceleration < MAX_ACCELERATION)
				currentAcceleration = Math.min(currentAcceleration + ACCELERATION_STEP, MAX_ACCELERATION);
			break;
		case DRIVE_DIRECTION_BACKWARD:
			if (currentAcceleration > 0)
				currentAcceleration = Math.max(currentAcceleration - ACCELERATION_DECAY, 0);
			if (currentAcceleration > MAX_REVERSE_ACCELERATION)
				currentAcceleration = Math.max(currentAcceleration + REVERSE_ACCELERATION_STEP , MAX_REVERSE_ACCELERATION);	
			break;
		case DRIVE_DIRECTION_NONE:
			if (currentAcceleration > 0)
				currentAcceleration = Math.max(currentAcceleration - ACCELERATION_DECAY, 0);
			else if (currentAcceleration < 0)
				currentAcceleration = Math.min(currentAcceleration + ACCELERATION_DECAY, 0);
			break;
		}
		
		engineForce.set(0, currentAcceleration);
		for (WheelEntity driveWheel : driveWheels)
			driveWheel.setForce(engineForce);
		
		for (WheelEntity driveWheel : driveWheels)
			driveWheel.setBrakes(brakesOn, BRAKE_STRENGTH);
	}
	
	/*private void adjustMomentum() {
		if (drive == FRONT_WHEEL_DRIVE) {
			for (WheelEntity frontWheel : frontWheels)
				frontWheel.setForce(new Vector2(0, currentAcceleration * 0.75f));
			for (WheelEntity rearWheel : rearWheels)
				rearWheel.setForce(new Vector2(0, currentAcceleration * 0.25f));
		}
		else if (drive == REAR_WHEEL_DRIVE) {
			for (WheelEntity rearWheel : rearWheels)
				rearWheel.setForce(new Vector2(0, currentAcceleration * 0.75f));
			for (WheelEntity frontWheel : frontWheels)
				frontWheel.setForce(new Vector2(0, currentAcceleration * 0.25f));
		}
		else {
			for (WheelEntity driveWheel : driveWheels)
				driveWheel.setForce(new Vector2(0, currentAcceleration * 0.75f));
		}
	}*/
	
	@Override
	public void update() {
		super.update();
		processInput();
		//adjustMomentum();
	}
	
}
