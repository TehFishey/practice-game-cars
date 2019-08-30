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
	private final int id;
	
	private static final float VELOCITY_MOD = 0;
	
	public Entity (Body body) {
		this.body = body;
		id = -1;
	}
	
	public Entity (final Vector2 position, final Vector2 size, final BodyDef.BodyType type, final World world, float density, final int id) {
        this.body = ShapeFactory.createRectangle(position, size, type, world, density);
        this.id = id;
    }
	
	public void update() {
		forwardSpeed = getForwardVelocity();
		lateralSpeed = getLateralVelocity();
		handleDrift();
		applyDrag();
	}

	public Body getBody() {
		return this.body;
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
	
	private void applyDrag() {
		//float normalizedForwardSpeed = this.forwardSpeed.nor();
	}
	
	private void handleDrift() {
		//Vector2 forwardSpeed = getForwardVelocity(body);
		//Vector2 lateralSpeed = getLateralVelocity(body);
		this.body.setLinearVelocity( forwardSpeed.x + lateralSpeed.x * VELOCITY_MOD, forwardSpeed.y + lateralSpeed.y * VELOCITY_MOD);
	}
}
