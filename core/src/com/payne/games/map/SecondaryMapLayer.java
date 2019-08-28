package com.payne.games.map;

import com.badlogic.gdx.utils.Array;
import com.payne.games.gameObjects.*;
import com.payne.games.gameObjects.statics.Static;
import com.payne.games.gameObjects.actors.Hero;
import com.payne.games.gameObjects.actors.Actor;


public class SecondaryMapLayer {
    private BaseMapLayer currentLevel;
    private GameObjectFactory objectFactory;
    private Array<Static> staticLayer; // keys, chests, etc.
    private Array<Actor> actorLayer;   // hero, enemies, etc.


    public SecondaryMapLayer(GameObjectFactory gameObjectFactory) {
        this.objectFactory = gameObjectFactory;
    }


    /**
     * Removes all the dead Actors from the SecondaryLayer.
     *
     * @return the Collection of all the dead actors that were removed.
     */
    public Array<Actor> removeDeadActors() {
        Array<Actor> deadActors = new Array<>();
        for(Actor actor : actorLayer) {
            if(actor.isDead()) {
                removeFromActorLayer(actor);
                deadActors.add(actor);
                currentLevel.getTile(actor.getX(), actor.getY()).setAllowingMove(true); // todo: unless the Actor is flying?
            }
        }
        return deadActors;
    }


    /**
     * Finds the Actor at the (x,y) position.
     *
     * @param x x-coord.
     * @param y y-coord.
     * @return Could be 'null' if no Actor is at that position.
     */
    public Actor findActorAt(int x, int y) {
        Actor actorAt = null;
        for(Actor a : actorLayer) {
            if (a.getX() == x && a.getY() == y) {
                actorAt = a;
            }
        }
        return actorAt;
    }

    /**
     * Finds the Static object at the (x,y) position.
     *
     * @param x x-coord.
     * @param y y-coord.
     * @return Could be 'null' if no Actor is at that position.
     */
    public Static findStaticAt(int x, int y) {
        Static staticAt = null;
        for(Static s : staticLayer) {
            if (s.getX() == x && s.getY() == y) {
                staticAt = s;
            }
        }
        return staticAt;
    }


    public Array<Static> getStaticLayer() {
        return staticLayer;
    }
    public Array<Actor> getActorLayer() {
        return actorLayer;
    }

    public void removeFromStaticLayer(Static object) {
        System.out.println("Removed an object from InertLayer.");
        staticLayer.removeValue(object, true);
    }
    public void removeFromActorLayer(Actor actor) {
        System.out.println("Remove an actor from ActorLayer.");
        actorLayer.removeValue(actor, true);
    }

    public void setUpSecondaryLayer(Hero player, BaseMapLayer currentLevel) {
        this.currentLevel = currentLevel;
        staticLayer = new Array<>();
        actorLayer = new Array<>();

        placeHero(player, 25,16);

        // todo: populate level with other Secondary objects
        createChest(31, 13);
        createChest(18, 2);
        createKey(14, 10);
        createKey(27, 16);
        createKey(20, 14);
        createKey(18, 18);

        DEBUG_spawn_enemies();

    }


    public void DEBUG_spawn_enemies() {
        createEnemy(14, 11);
        createEnemy(29, 14);
        createEnemy(25, 13);
        createEnemy(56, 25);
    }

    private void placeHero(Hero player, int x, int y) {
        player.setX(x);
        player.setY(y);
        actorLayer.add(player);
        currentLevel.getTile(x,y).setAllowingMove(false);
    }
    private void createEnemy(int x, int y) {
        actorLayer.add(objectFactory.createEnemy(x, y));
        currentLevel.getTile(x,y).setAllowingMove(false);
    }


    // todo: add into Floor's list of objects?
    private void createChest(int x, int y) {
        staticLayer.add(objectFactory.createChest(x, y));
        currentLevel.getTile(x,y).setAllowingMove(false); // todo: uncertain...
    }
    private void createKey(int x, int y) {
        staticLayer.add(objectFactory.createKey(x, y));
    }
}
