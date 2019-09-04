package game.fish.cars.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.utils.Array;

import static game.fish.cars.Constants.PPM;

public class CarVehicle extends VehicleEntity {
	
	public static final int FRONT_WHEEL_DRIVE = 0;
	public static final int REAR_WHEEL_DRIVE = 1;
	public static final int ALL_WHEEL_DRIVE = 2;
	
	private static final float WHEEL_POSITION_X = 64f;
    private static final float WHEEL_POSITION_Y = 80f;
	private static final float WHEEL_LOCK_ANGLE = 30f; //40f?

	private final Array<WheelEntity> frontWheels = new Array<WheelEntity>();
	private final Array<WheelEntity> rearWheels = new Array<WheelEntity>();
	private final Array<WheelEntity> driveWheels = new Array<WheelEntity>();
	
	private float WHEEL_TURN_RATE = 2f;
	private float WHEEL_RESET_RATE = 2f;
	
	private float currentAcceleration = 0f;
	private float wheelAngle = 0f;
	
	private final int drive;
	
	public CarVehicle(Body body, World world, int drive) {
		super(body, world);
		this.drive = drive;
		
		ANGULAR_DAMPING = 6f;
		LINEAR_DAMPING = 0.5f;
		RESTITUTION = 0.2f;
		
		MAX_ACCELERATION = 160f;
		MAX_REVERSE_ACCELERATION = -60f;
		ACCELERATION_STEP = 5f;
		REVERSE_ACCELERATION_STEP = -5f;
		ACCELERATION_DECAY = 20f;
		BRAKE_STRENGTH = 90f;
		
		getBody().setAngularDamping(ANGULAR_DAMPING);
		getBody().setLinearDamping(LINEAR_DAMPING);
		getBody().getFixtureList().get(0).setRestitution(RESTITUTION);
		
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
	}
	
	private void processInput() {
		Vector2 engineForce = new Vector2();
		
		switch (turnDirection) {
		case TURN_DIRECTION_RIGHT:
			if (wheelAngle > -WHEEL_LOCK_ANGLE) 
				wheelAngle = Math.max(wheelAngle -= WHEEL_TURN_RATE, -WHEEL_LOCK_ANGLE);
			break;
		case TURN_DIRECTION_LEFT:
			if (wheelAngle < WHEEL_LOCK_ANGLE) 
				wheelAngle = Math.min(wheelAngle += WHEEL_TURN_RATE, WHEEL_LOCK_ANGLE);
			break;
		case TURN_DIRECTION_NONE:
			if (wheelAngle < 0)
				wheelAngle += WHEEL_RESET_RATE;
			else if (wheelAngle > 0)
				wheelAngle -= WHEEL_RESET_RATE;
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
	
	@Override
	public void update() {
		super.update();
		processInput();
	}
	
}
