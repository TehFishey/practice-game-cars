package game.fish.cars.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class CarEntity extends Entity {
	
	public static final int DRIVE_DIRECTION_NONE = 0;
	public static final int DRIVE_DIRECTION_FORWARD = 1;
	public static final int DRIVE_DIRECTION_BACKWARD = 2;
	public static final int TURN_DIRECTION_NONE = 0;
	public static final int TURN_DIRECTION_LEFT = 1;
	public static final int TURN_DIRECTION_RIGHT = 2;
	
	private int driveDirection = DRIVE_DIRECTION_NONE;
	private int turnDirection = DRIVE_DIRECTION_NONE;
	
	public CarEntity(Body body) {
		super(body);
		getBody().setAngularDamping(0.4f);
		getBody().setLinearDamping(0.4f);
	}

	public void setDriveDirection(int direction) {
		this.driveDirection = direction;
	}
	
	public void setTurnDirection(int direction) {
		this.turnDirection = direction;
	}
	
	private void processInput() {
		Vector2 baseVector = new Vector2();
		
		switch (turnDirection) {
		case TURN_DIRECTION_RIGHT:
			getBody().setAngularVelocity(-2f);
			break;
		case TURN_DIRECTION_LEFT:
			getBody().setAngularVelocity(2f);
			break;
		case TURN_DIRECTION_NONE:
			if (getBody().getAngularVelocity() != 0)
				getBody().setAngularVelocity(0);
		}
		
		switch(driveDirection) {
		case DRIVE_DIRECTION_FORWARD:
			baseVector.set(0, 120f);
			break;
		case DRIVE_DIRECTION_BACKWARD:
			baseVector.set(0, -120f);
			break;
		}
		
		if (!baseVector.isZero()) {
			getBody().applyForceToCenter(getBody().getWorldVector(baseVector), true);
		}
	}
	
	@Override
	public void update() {
		super.update();
		processInput();
	}
	
}
