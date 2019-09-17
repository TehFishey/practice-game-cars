package game.fish.observers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import game.fish.cars.CarsGame;
import game.fish.cars.views.PlayScreen;

public class AchievementContactListener implements ContactListener {

	CarsGame game;
	PlayScreen parent;
	Fixture player;
	
	public AchievementContactListener(CarsGame game, PlayScreen parent, Fixture player) {
		this.game = game;
		this.parent = parent;
		this.player = player;
	}
	
	@Override
	public void beginContact(Contact contact) {
		Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        //System.out.print("beginContact between " + fixtureA.toString() + " and " + fixtureB.toString()+"\n");

        if (fixtureA.equals(player)) game.getAchievements().takePropertyChange("contactEvent", fixtureB);
        else if (fixtureB.equals(player)) game.getAchievements().takePropertyChange("contactEvent", fixtureA);       
	}

	@Override
	public void endContact(Contact contact) {
		Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        return;
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}

}
