package game.fish.cars.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import game.fish.cars.tools.MapLoader;

// HoverVehicles are glorified WheelEntities with VehicleEntity methods and very low friction.
// They're probably the only vehicle that works right.

public class HoverVehicle extends VehicleEntity {
	
	private float currentAcceleration = 0f;
	
	public HoverVehicle(World world, MapLoader loader) {
		super(world, loader);
		
		FRICTION_TOLERANCE = 0f;
		FRICTION_MODIFIER = 0.995f;
		
		DEFAULT_ANGULAR_DAMPING = 4f;
		DEFAULT_LINEAR_DAMPING = 0.3f;
		DEFAULT_RESTITUTION = 0.3f;
		
		MAX_ACCELERATION = 120f;
		MAX_REVERSE_ACCELERATION = -60f;
		ACCELERATION_STEP = 10f;
		REVERSE_ACCELERATION_STEP = -5f;
		ACCELERATION_DECAY = 35f;
		BRAKE_STRENGTH = 5f;
		
		getBody().setAngularDamping(DEFAULT_ANGULAR_DAMPING);
		getBody().setLinearDamping(DEFAULT_LINEAR_DAMPING);
		getBody().getFixtureList().get(0).setRestitution(DEFAULT_RESTITUTION);
		
	}
	
	private void processInput() {
		Vector2 engineForce = new Vector2(0,0);
		
		switch (turnDirection) {
		case RIGHT:
			getBody().setAngularVelocity(-2f);
			break;
		case LEFT:
			getBody().setAngularVelocity(2f);
			break;
		case NONE:
			break;
		}
		
		switch(driveDirection) {
		case FORWARD:
			if (currentAcceleration < 0)
				currentAcceleration = Math.min(currentAcceleration + ACCELERATION_DECAY, 0);
			if (currentAcceleration < MAX_ACCELERATION)
				currentAcceleration = Math.min(currentAcceleration + ACCELERATION_STEP, MAX_ACCELERATION);
			break;
		case BACKWARD:
			if (currentAcceleration > 0)
				currentAcceleration = Math.max(currentAcceleration - ACCELERATION_DECAY, 0);
			if (currentAcceleration > MAX_REVERSE_ACCELERATION)
				currentAcceleration = Math.max(currentAcceleration + REVERSE_ACCELERATION_STEP , MAX_REVERSE_ACCELERATION);	
			break;
		case NONE:
			if (currentAcceleration > 0)
				currentAcceleration = Math.max(currentAcceleration - ACCELERATION_DECAY, 0);
			else if (currentAcceleration < 0)
				currentAcceleration = Math.min(currentAcceleration + ACCELERATION_DECAY, 0);
			break;
		}
		engineForce.set(0, currentAcceleration);
		getBody().applyForceToCenter(getBody().getWorldVector(engineForce), true);
		
		if (brakesOn) {
			getBody().setAngularDamping(DEFAULT_ANGULAR_DAMPING + BRAKE_STRENGTH);
			getBody().setLinearDamping(DEFAULT_LINEAR_DAMPING + BRAKE_STRENGTH);
		}
		else {
			getBody().setAngularDamping(DEFAULT_ANGULAR_DAMPING);
			getBody().setLinearDamping(DEFAULT_LINEAR_DAMPING);
		}
	}
	
	@Override
	public void update() {
		super.update();
		processInput();
	}
	
}
