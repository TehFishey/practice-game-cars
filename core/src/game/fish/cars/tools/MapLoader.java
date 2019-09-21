package game.fish.cars.tools;

import com.badlogic.gdx.maps.objects.PolygonMapObject;
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

import game.fish.cars.Constants.MAP;

import static game.fish.cars.Constants.PATH_MAP1;
import static game.fish.cars.Constants.PATH_MAP2;
import static game.fish.cars.Constants.PATH_MAP3;

public class MapLoader implements Disposable {

	public static final String MAP_WALLS = "walls";
	public static final String MAP_OBSTACLES = "debris";
	public static final String MAP_PLAYER = "player";
	
	private final World world;
	private TiledMap map;
	
	
	public MapLoader(World world, MAP mapChoice) {
		this.world = world;
		
		switch (mapChoice) {
		case MAP1:
			this.map = new TmxMapLoader().load(PATH_MAP1);
			break;
		case MAP2:
			this.map = new TmxMapLoader().load(PATH_MAP2);
			break;
		case MAP3:
			this.map = new TmxMapLoader().load(PATH_MAP3);
			break;
		default:
			this.map = new TmxMapLoader().load(PATH_MAP1);
		}
		
		final Array<RectangleMapObject> wallRects = map.getLayers().get(MAP_WALLS).getObjects().getByType(RectangleMapObject.class);
		final Array<PolygonMapObject> wallPolys = map.getLayers().get(MAP_WALLS).getObjects().getByType(PolygonMapObject.class);
		
		for (RectangleMapObject rObject: wallRects) {
			Rectangle rectangle = rObject.getRectangle();
			ShapeFactory.createRectangle(
					new Vector2 (rectangle.getX() + rectangle.getWidth()/2, rectangle.getY() + rectangle.getHeight()/2),
					new Vector2 (rectangle.getWidth()/2, rectangle.getHeight()/2),
					BodyDef.BodyType.StaticBody, this.world, 1f, false);
		}
		
		for (PolygonMapObject pObject: wallPolys) {
			ShapeFactory.createPolygon(pObject, BodyDef.BodyType.StaticBody, this.world, 1f);
		}
	}
		
	public Body getPlayer(float x, float y) {
		final Rectangle player = map.getLayers().get(MAP_PLAYER).getObjects().getByType(RectangleMapObject.class).get(0).getRectangle();
		float shapeX;
		float shapeY;
		if (x > 0) shapeX = x; else shapeX = player.getWidth();
		if (y > 0) shapeY = y; else shapeY = player.getHeight();
		
		return ShapeFactory.createRectangle(
				new Vector2 (player.getX() + shapeX/2, player.getY() + shapeY/2),
				new Vector2 (shapeX/2, shapeY/2),
				BodyDef.BodyType.DynamicBody, this.world, 0.4f, false);
		}
	
	public void dispose() {
		map.dispose();
	}
	
}
