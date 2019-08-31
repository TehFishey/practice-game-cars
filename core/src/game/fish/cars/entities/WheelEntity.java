package game.fish.cars.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public class WheelEntity extends Entity {

	public WheelEntity(Vector2 position, Vector2 size, World world, float density, int id) {
		super(position, size, BodyDef.BodyType.DynamicBody, world, density, id);
		getBody().setAngularDamping(2f);
		getBody().setLinearDamping(2f);
	}
	
	public void moveWheel (float force) {
		getBody().setAngularVelocity(force);
	}
	
	public void breakWheel (float strength) {
		getBody().setLinearDamping(strength);
	}
	
}
