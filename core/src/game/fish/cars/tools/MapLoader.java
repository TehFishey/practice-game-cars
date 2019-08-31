package game.fish.cars.tools;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import static game.fish.cars.Constants.MAP1;

public class MapLoader implements Disposable {

	public static final String MAP_WALLS = "walls";
	public static final String MAP_OBSTACLES = "debris";
	public static final String MAP_PLAYER = "player";
	
	private final World world;
	private final TiledMap map;
	
	
	public MapLoader(World world) {
		this.world = world;
		this.map = new TmxMapLoader().load(MAP1);
		
		final Array<RectangleMapObject> walls = map.getLayers().get(MAP_WALLS).getObjects().getByType(RectangleMapObject.class);
	
		for (RectangleMapObject rObject: walls) {
			Rectangle rectangle = rObject.getRectangle();
			ShapeFactory.createRectangle(
					new Vector2 (rectangle.getX() + rectangle.getWidth()/2, rectangle.getY() + rectangle.getHeight()/2),
					new Vector2 (rectangle.getWidth()/2, rectangle.getHeight()/2),
					BodyDef.BodyType.StaticBody, this.world, 1f);
		}
	}
		
	public Body getPlayer() {
		final Rectangle player = map.getLayers().get(MAP_PLAYER).getObjects().getByType(RectangleMapObject.class).get(0).getRectangle();
		return ShapeFactory.createRectangle(
				new Vector2 (player.getX() + player.getWidth()/2, player.getY() + player.getHeight()/2),
				new Vector2 (player.getWidth()/2, player.getHeight()/2),
				BodyDef.BodyType.DynamicBody, this.world, 0.4f);
		}
	
	public void dispose() {
		map.dispose();
	}
	
}
