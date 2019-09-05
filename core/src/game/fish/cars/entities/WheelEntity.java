package game.fish.cars.entities;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public class WheelEntity extends Entity {

	private static final float ANGULAR_DAMPING = 0.5f;
	private static final float LINEAR_DAMPING = 0.5f;
	
	private final VehicleEntity parent;
	
	public WheelEntity(Vector2 position, Vector2 size, World world, float density, VehicleEntity parent) {
		super(position, size, BodyDef.BodyType.DynamicBody, world, density, true);
		
		this.parent = parent;
		getBody().setAngularDamping(ANGULAR_DAMPING);
		getBody().setLinearDamping(ANGULAR_DAMPING);
	}
	
	public void setAngle (float angle) {
		getBody().setTransform(getBody().getPosition(), parent.getBody().getAngle() + angle * MathUtils.degRad);
	}
	
	public void setForce (Vector2 force) {
		getBody().applyForceToCenter(getBody().getWorldVector(force), true);
	}
	
	public void setBrakes (boolean on, float strength) {
		if (on) {
			getBody().setAngularDamping(strength);
			getBody().setLinearDamping(strength);
		}
		else {
			getBody().setAngularDamping(ANGULAR_DAMPING);
			getBody().setLinearDamping(LINEAR_DAMPING);
		}
	}	
}
