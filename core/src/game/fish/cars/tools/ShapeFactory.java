package game.fish.cars.tools;

import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import static game.fish.cars.Constants.PPM;

// My first attempt at implementing the Factory pattern.

public class ShapeFactory {
	private ShapeFactory () {};
	
	public static Body createRectangle(final Vector2 position, final Vector2 size, final BodyDef.BodyType type, final World world, final float density, boolean sensor) {
		
		final BodyDef bdef = new BodyDef();
		bdef.position.set(position.x / PPM, position.y / PPM);
		bdef.type = type;
		final Body body = world.createBody(bdef);
		
		final PolygonShape shape = new PolygonShape();
		shape.setAsBox(size.x/PPM,  size.y/PPM);
		
		final FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.isSensor = sensor;
		fdef.density = density;
		
		body.createFixture(fdef);
		
		shape.dispose();
		return body;	
	}
	
	public static Body createPolygon(final PolygonMapObject polyObject, final BodyDef.BodyType type, final World world, final float density) {		
		// Converting Tiled polygon objects to complex shapes in Box2d doesn't seem to work very well,
		// but I think it's a limitation of the engine rather than an error here. I try to stick with rectangles in the maps.
		
		final BodyDef bdef = new BodyDef();
		bdef.type = type;
		final Body body = world.createBody(bdef);
		
		final PolygonShape shape = new PolygonShape();
		float[] verticies = polyObject.getPolygon().getTransformedVertices();
		float[] worldVerticies = new float[verticies.length];
		
		for (int i = 0; i < verticies.length; ++i) {
			worldVerticies[i] = verticies[i] / PPM;
		}
		
		shape.set(worldVerticies);	
		body.createFixture(shape, density);
		
		shape.dispose();
		return body;
	}
}
