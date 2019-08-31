package game.fish.cars.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import game.fish.cars.tools.ShapeFactory;

public abstract class Entity {
	
	private Vector2 forwardSpeed;
	private Vector2 lateralSpeed;
	
	private final Body body;
	
	protected static float FRICTION_TOLERANCE = 100f;
	protected static float FRICTION_MODIFIER = 0.01f;
	
	public Entity (Body body) {
		this.body = body;
	}
	
	public Entity (final Vector2 position, final Vector2 size, final BodyDef.BodyType type, final World world, float density) {
        this.body = ShapeFactory.createRectangle(position, size, type, world, density);
    }
	
	public void update() {
		forwardSpeed = getForwardVelocity();
		lateralSpeed = getLateralVelocity();
		applyFriction();
	}

	public Body getBody() {
		return this.body;
	}
	
	public float getAbsoluteSpeed() {
		Vector2 bodyVelocity = this.body.getLinearVelocity();
		return Math.abs(bodyVelocity.x) + Math.abs(bodyVelocity.y);
	}
	
	private Vector2 getForwardVelocity() {
		Vector2 bodyOrientation = this.body.getWorldVector(new Vector2(1,0));
		Vector2 bodyVelocity = this.body.getLinearVelocity();
		float normalVelocity = bodyOrientation.dot(bodyVelocity);
		return bodyOrientation.scl(normalVelocity);
	}
	
	private Vector2 getLateralVelocity() {
		Vector2 bodyOrientation = this.body.getWorldVector(new Vector2(0,1));
		Vector2 bodyVelocity = this.body.getLinearVelocity();
		float normalVelocity = bodyOrientation.dot(bodyVelocity);
		return bodyOrientation.scl(normalVelocity);
	}
	
	private void applyFriction() {
		if (getAbsoluteSpeed() > FRICTION_TOLERANCE)
			this.body.setLinearVelocity( forwardSpeed.x + lateralSpeed.x * FRICTION_MODIFIER, forwardSpeed.y + lateralSpeed.y * FRICTION_MODIFIER);
		else
			this.body.setLinearVelocity( forwardSpeed.x, forwardSpeed.y);
	}
}
