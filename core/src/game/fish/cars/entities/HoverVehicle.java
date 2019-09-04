package game.fish.cars.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.utils.Array;

import static game.fish.cars.Constants.PPM;

public class HoverVehicle extends VehicleEntity {
	
	private float currentAcceleration = 0f;
	
	public HoverVehicle(Body body, World world) {
		super(body, world);
		
		FRICTION_TOLERANCE = 0f;
		FRICTION_MODIFIER = 0.995f;
		
		ANGULAR_DAMPING = 4f;
		LINEAR_DAMPING = 0.3f;
		RESTITUTION = 0.3f;
		
		MAX_ACCELERATION = 120f;
		MAX_REVERSE_ACCELERATION = -60f;
		ACCELERATION_STEP = 10f;
		REVERSE_ACCELERATION_STEP = -5f;
		ACCELERATION_DECAY = 35f;
		BRAKE_STRENGTH = 5f;
		
		getBody().setAngularDamping(ANGULAR_DAMPING);
		getBody().setLinearDamping(LINEAR_DAMPING);
		getBody().getFixtureList().get(0).setRestitution(RESTITUTION);
		
	}
	
	private void processInput() {
		Vector2 engineForce = new Vector2(0,0);
		
		switch (turnDirection) {
		case TURN_DIRECTION_RIGHT:
			getBody().setAngularVelocity(-2f);
			break;
		case TURN_DIRECTION_LEFT:
			getBody().setAngularVelocity(2f);
			break;
		case TURN_DIRECTION_NONE:
			break;
		}
		
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
		getBody().applyForceToCenter(getBody().getWorldVector(engineForce), true);
		
		if (brakesOn) {
			getBody().setAngularDamping(BRAKE_STRENGTH);
			getBody().setLinearDamping(BRAKE_STRENGTH);
		}
		else {
			getBody().setAngularDamping(ANGULAR_DAMPING);
			getBody().setLinearDamping(LINEAR_DAMPING);
		}
	}
	
	@Override
	public void update() {
		super.update();
		processInput();
	}
	
}
