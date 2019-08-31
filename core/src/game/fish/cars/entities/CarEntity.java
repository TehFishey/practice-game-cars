package game.fish.cars.entities;

import com.badlogic.gdx.math.MathUtils;
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
	private static final float WHEEL_LOCK_ANGLE = 20f;
    
	private final Array<WheelEntity> frontWheels = new Array<WheelEntity>();
	private final Array<WheelEntity> rearWheels = new Array<WheelEntity>();
	
	private World world;
	
	private int driveDirection = DRIVE_DIRECTION_NONE;
	private int turnDirection = DRIVE_DIRECTION_NONE;
	private float wheelAngle = 0f;
	
	public CarEntity(Body body, World world) {
		super(body);
		
		this.world = world;
		getBody().setAngularDamping(2f);
		getBody().setLinearDamping(2f);
		constructCar();
	}

	private void constructCar() {
		final WheelEntity wheelOne = new WheelEntity(
			new Vector2(getBody().getPosition().x * PPM + WHEEL_POSITION_X, getBody().getPosition().y * PPM + WHEEL_POSITION_Y), 
			new Vector2(16, 32), 
			this.world, 0.4f, 1);
		final WheelEntity wheelTwo = new WheelEntity(
			new Vector2(getBody().getPosition().x * PPM + -WHEEL_POSITION_X, getBody().getPosition().y * PPM + WHEEL_POSITION_Y), 
			new Vector2(16, 32), 
			this.world, 0.4f, 1);
		final WheelEntity wheelThree = new WheelEntity(
			new Vector2(getBody().getPosition().x * PPM + WHEEL_POSITION_X, getBody().getPosition().y * PPM + -WHEEL_POSITION_Y), 
			new Vector2(16, 32), 
			this.world, 0.4f, 1);
		final WheelEntity wheelFour = new WheelEntity(
			new Vector2(getBody().getPosition().x * PPM + -WHEEL_POSITION_X, getBody().getPosition().y * PPM + -WHEEL_POSITION_Y), 
			new Vector2(16, 32), 
			this.world, 0.4f, 1);
		
		frontWheels.add(wheelOne, wheelTwo);
		rearWheels.add(wheelThree, wheelFour);
		
		for (WheelEntity frontWheel : frontWheels) {
			final RevoluteJointDef jointDef = new RevoluteJointDef();
			jointDef.initialize(this.getBody(), frontWheel.getBody(), frontWheel.getBody().getWorldCenter());
			jointDef.enableMotor = false;
			this.world.createJoint(jointDef);
		}
		for (WheelEntity rearWheel : rearWheels) {
			final PrismaticJointDef jointDef = new PrismaticJointDef();
            jointDef.initialize(this.getBody(), rearWheel.getBody(), rearWheel.getBody().getWorldCenter(), new Vector2(1, 0));
            jointDef.enableLimit = true;
            jointDef.lowerTranslation = jointDef.upperTranslation = 0;
            this.world.createJoint(jointDef);
		}		
		
		
	}

	public void setDriveDirection(int direction) {
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
			for (WheelEntity frontWheel : frontWheels)
				frontWheel.getBody().setTransform(frontWheel.getBody().getPosition(), this.getBody().getAngle() + wheelAngle * MathUtils.degRad);
			break;
		case TURN_DIRECTION_LEFT:
			if (wheelAngle < WHEEL_LOCK_ANGLE) 
				wheelAngle = Math.min(wheelAngle += 2f, WHEEL_LOCK_ANGLE);
			for (WheelEntity frontWheel : frontWheels)	
				frontWheel.getBody().setTransform(frontWheel.getBody().getPosition(), this.getBody().getAngle() + wheelAngle * MathUtils.degRad);
			break;
		case TURN_DIRECTION_NONE:
			if (wheelAngle < 0 && driveDirection != DRIVE_DIRECTION_NONE)
				wheelAngle += 0.5f;
			else if (wheelAngle > 0 && driveDirection != DRIVE_DIRECTION_NONE)
				wheelAngle -= 0.5f;
			for (WheelEntity frontWheel : frontWheels) 
				frontWheel.getBody().setTransform(frontWheel.getBody().getPosition(), this.getBody().getAngle() + wheelAngle * MathUtils.degRad);		
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
		}
		
		if (!baseVector.isZero()) {
			for (WheelEntity frontWheel : frontWheels)
			frontWheel.getBody().applyForceToCenter(frontWheel.getBody().getWorldVector(baseVector), true);
		}
	}
	
	@Override
	public void update() {
		super.update();
		processInput();
	}
	
}
