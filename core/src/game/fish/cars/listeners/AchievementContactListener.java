package game.fish.cars.listeners;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;

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
        
        Array contactInfo = new Array();

        if (fixtureA.equals(player)) {
        	contactInfo.add(fixtureA, fixtureB);
        	achievements.takePropertyChange("contactEvent", contactInfo);
        }
        else if (fixtureB.equals(player)) {
        	contactInfo.add(fixtureB, fixtureA);
        	achievements.takePropertyChange("contactEvent", contactInfo);       
        }
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
