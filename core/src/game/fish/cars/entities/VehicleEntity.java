package game.fish.cars.entities;

import com.badlogic.gdx.physics.box2d.World;

import game.fish.cars.tools.MapLoader;

// Vehicle handles all of the vehicle-specific stuff, such as control 'states' (not really states), and stats.
// In generation, Vehicle expects a region that's been declared as a 'Player' on whatever map you are loading to create a body from,
// So it requires both an already-existing box2d World and a MapLoader instance.

// Separating VehicleEntity out like this allows us to easily add a wide variety of very different kinds of vehicles to the game.

public class VehicleEntity extends Entity {

	public static enum DRIVE_DIRECTION {
		NONE,
		FORWARD,
		BACKWARD
	}
	
	public static enum TURN_DIRECTION {
		NONE,
		LEFT,
		RIGHT
	}
	
	protected static float DEFAULT_ANGULAR_DAMPING;
	protected static float DEFAULT_LINEAR_DAMPING;
	protected static float DEFAULT_RESTITUTION;
	
	protected static float MAX_ACCELERATION;
	protected static float MAX_REVERSE_ACCELERATION;
	protected static float ACCELERATION_STEP;
	protected static float REVERSE_ACCELERATION_STEP;
	protected static float ACCELERATION_DECAY;
	protected static float BRAKE_STRENGTH;
	
	protected DRIVE_DIRECTION driveDirection = DRIVE_DIRECTION.NONE;
	protected TURN_DIRECTION turnDirection = TURN_DIRECTION.NONE;
	protected boolean brakesOn = false;
	
	protected final World world;
	protected final MapLoader loader;

	public VehicleEntity(World world, MapLoader loader) {
		super(loader.getPlayer(0,0));
		this.world = world;
		this.loader = loader;
	}	
	
	public VehicleEntity(float sizeX, float sizeY, World world, MapLoader loader) {
		super(loader.getPlayer(sizeX,sizeY));
		this.world = world;
		this.loader = loader;
	}
	
	public void inputDriveDirection(DRIVE_DIRECTION direction) {
		this.driveDirection = direction;
	}
	
	public void inputTurnDirection(TURN_DIRECTION direction) {
		this.turnDirection = direction;
	}
	
	public void inputBrakes(boolean bool) {
		this.brakesOn = bool;
	}
	
	private void processInput() { }

}
