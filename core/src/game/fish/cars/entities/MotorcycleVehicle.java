package game.fish.cars.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.utils.Array;

import game.fish.cars.tools.MapLoader;

import static game.fish.cars.Constants.PPM;

public class MotorcycleVehicle extends VehicleEntity {
	
	private static final float WHEEL_POSITION_X = 0f;
    private static final float WHEEL_POSITION_Y = 80f;
	private static final float WHEEL_LOCK_ANGLE = 40f;

	private WheelEntity frontWheel;
	private WheelEntity rearWheel;
	private Array<WheelEntity> wheels = new Array<>();
	
	private float WHEEL_TURN_RATE = 4f;
	private float WHEEL_RESET_RATE = 4f;
	private float TURN_DAMPING_MOD = 8f;
	
	private float currentAcceleration = 0f;
	private float wheelAngle = 0f;
		
	public MotorcycleVehicle(World world, MapLoader loader) {
		super(60f, 160f, world, loader);
		
		DEFAULT_ANGULAR_DAMPING = 6f;
		DEFAULT_LINEAR_DAMPING = 0.5f;
		DEFAULT_RESTITUTION = 0.05f;
		
		MAX_ACCELERATION = 100f;
		MAX_REVERSE_ACCELERATION = -45f;
		ACCELERATION_STEP = 5f;
		REVERSE_ACCELERATION_STEP = -5f;
		ACCELERATION_DECAY = 40f;
		BRAKE_STRENGTH = 60f;
		
		getBody().setAngularDamping(DEFAULT_ANGULAR_DAMPING);
		getBody().setLinearDamping(DEFAULT_LINEAR_DAMPING);
		getBody().getFixtureList().get(0).setRestitution(DEFAULT_RESTITUTION);
		
		frontWheel = new WheelEntity(
			new Vector2(getBody().getPosition().x * PPM + WHEEL_POSITION_X, getBody().getPosition().y * PPM + WHEEL_POSITION_Y), 
			new Vector2(12, 24), 
			this.world, 0.4f, this);
		rearWheel = new WheelEntity(
			new Vector2(getBody().getPosition().x * PPM + WHEEL_POSITION_X, getBody().getPosition().y * PPM + -WHEEL_POSITION_Y), 
			new Vector2(12, 24), 
			this.world, 0.4f, this);
		
		wheels.add(frontWheel, rearWheel);
		
		final RevoluteJointDef frontJoint = new RevoluteJointDef();
		frontJoint.initialize(this.getBody(), frontWheel.getBody(), frontWheel.getBody().getWorldCenter());
		this.world.createJoint(frontJoint);

		final PrismaticJointDef rearJoint = new PrismaticJointDef();
        rearJoint.initialize(this.getBody(), rearWheel.getBody(), rearWheel.getBody().getWorldCenter(), new Vector2(1, 0));
        rearJoint.enableLimit = true;
        rearJoint.lowerTranslation = rearJoint.upperTranslation = 0;
        this.world.createJoint(rearJoint);
	}
	
	private void processInput() {
		Vector2 engineForce = new Vector2();
		
		switch (turnDirection) {
		case TURN_DIRECTION_RIGHT:
			if (wheelAngle > -WHEEL_LOCK_ANGLE) 
				wheelAngle = Math.max(wheelAngle -= WHEEL_TURN_RATE, -WHEEL_LOCK_ANGLE);
			getBody().setLinearDamping(DEFAULT_LINEAR_DAMPING + TURN_DAMPING_MOD);
			break;
		case TURN_DIRECTION_LEFT:
			if (wheelAngle < WHEEL_LOCK_ANGLE) 
				wheelAngle = Math.min(wheelAngle += WHEEL_TURN_RATE, WHEEL_LOCK_ANGLE);
			getBody().setLinearDamping(DEFAULT_LINEAR_DAMPING + TURN_DAMPING_MOD);
			break;
		case TURN_DIRECTION_NONE:
			if (wheelAngle < 0)
				wheelAngle += WHEEL_RESET_RATE;
			else if (wheelAngle > 0)
				wheelAngle -= WHEEL_RESET_RATE;
			getBody().setLinearDamping(DEFAULT_LINEAR_DAMPING);
			break;
		}
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
		for (WheelEntity wheel : wheels)
			wheel.setForce(engineForce);
		
		for (WheelEntity wheel : wheels)
			wheel.setBrakes(brakesOn, BRAKE_STRENGTH);
	}
	
	@Override
	public void update() {
		super.update();
		for (WheelEntity wheel : wheels) wheel.update();
		processInput();
	}
	
}
