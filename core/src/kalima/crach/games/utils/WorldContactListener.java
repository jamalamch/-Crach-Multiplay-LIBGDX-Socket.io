package kalima.crach.games.utils;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import kalima.crach.games.kalimaCrach;
import kalima.crach.games.entites.Crystal;
import kalima.crach.games.entites.Entity;

public class WorldContactListener implements ContactListener {

	
	
	
	@Override
	public void beginContact(Contact contact) {
		
		
        final Fixture fixA ;
        final Fixture fixB ;
        final Entity entityA;
        final Entity entityB;
        fixA = contact.getFixtureA();
        fixB = contact.getFixtureB();
        
        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
        
        switch (cDef){
        case kalimaCrach.ALPHA_BOX   | kalimaCrach.LAMAS_CRACH:
        case kalimaCrach.ALPHA_BOX   | kalimaCrach.PLACE_BOX:
        case kalimaCrach.CRYSTAL_BIT |kalimaCrach.LAMAS_CRACH :
        case kalimaCrach.FRENDS_PLAY | kalimaCrach.LAMAS_CRACH:
        case kalimaCrach.FRENDS_PLAY | kalimaCrach.CRACH_BIT :
        case kalimaCrach.FRENDS_PLAY | kalimaCrach.HEAD_CRACH:
        	
           entityA = entityA(fixA);
           entityB = entityB(fixB);
        if (entityA != null & entityB != null) {
            entityA.onContactStart(entityB);
            entityB.onContactStart(entityA);

        }
        break;
        
        case kalimaCrach.CRYSTAL_BIT | kalimaCrach.ALPHA_BOX:
        case kalimaCrach.CRYSTAL_BIT | kalimaCrach.CRACH_BIT:
            entityA = entityA(fixA);
            entityB = entityB(fixB);
            if (entityA != null & entityB != null) {
            if( entityA instanceof Crystal ) {
                Crystal.creator.SetDestoryEntity(entityA);
            }
            else {
                Crystal.creator.SetDestoryEntity(entityB);
            } 	
        }
        break ;
        case kalimaCrach.FIRE_BIT | kalimaCrach.FRENDS_PLAY:
        case kalimaCrach.FIRE_BIT | kalimaCrach.ALPHA_BOX:
            entityA = entityA(fixA);
            entityB = entityB(fixB);
            if (entityA != null & entityB != null) {
            if( entityA instanceof Crystal ) {
                Crystal.creator.SetDestoryEntity(entityA);
            }
            else {
                Crystal.creator.SetDestoryEntity(entityB);
            } 
          }
        break ;
        case kalimaCrach.FIRE_BIT | kalimaCrach.CRYSTAL_BIT:
            entityA = entityA(fixA);
            entityB = entityB(fixB);
            Crystal.creator.SetDestoryEntity(entityA);
            Crystal.creator.SetDestoryEntity(entityB);
        break;

        	
        }
	}

	@Override
	public void endContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();
        
        final Entity entityA;
        final Entity entityB;
        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef){
        case kalimaCrach.ALPHA_BOX| kalimaCrach.LAMAS_CRACH:
        case kalimaCrach.ALPHA_BOX|kalimaCrach.PLACE_BOX:

         entityA = entityA(fixA);
         entityB = entityB(fixB);
        if (entityA != null & entityB != null) {
            entityA.onContactEnd(entityB);
            entityB.onContactEnd(entityA);
        }
        }
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {

	}

    private static Entity entityA(Fixture fixt) {
        final Object dataA = fixt.getBody().getUserData();
        return dataA instanceof Entity ? (Entity) dataA : null;
    }

    private static Entity entityB(Fixture fixt) {
        final Object dataB = fixt.getBody().getUserData();
        return dataB instanceof Entity ? (Entity) dataB : null;
    }
	
}
