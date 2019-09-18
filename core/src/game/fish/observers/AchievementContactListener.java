package game.fish.observers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import game.fish.cars.Achievements;
import game.fish.cars.views.PlayScreen;

public class AchievementContactListener implements ContactListener {

	Achievements achievements;
	PlayScreen parent;
	Fixture player;
	
	public AchievementContactListener(Achievements achievements, PlayScreen parent, Fixture player) {
		this.achievements = achievements;
		this.parent = parent;
		this.player = player;
	}
	
	@Override
	public void beginContact(Contact contact) {
		Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if (fixtureA.equals(player)) achievements.takePropertyChange("contactEvent", fixtureB);
        else if (fixtureB.equals(player)) achievements.takePropertyChange("contactEvent", fixtureA);       
	}

	@Override
	public void endContact(Contact contact) {
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
