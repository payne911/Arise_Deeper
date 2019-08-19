package com.payne.games.map;

import com.badlogic.gdx.utils.Array;
import com.payne.games.gameObjects.*;
import com.payne.games.map.renderers.IRenderable;


public class SecondaryMapLayer {
    private GameObjectFactory objectFactory;
    private Array<IRenderable> inertLayer; // keys, chests, etc.
    private Array<Actor> actorLayer; // hero, enemies, etc.


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
            }
        }
        return deadActors;
    }


    /**
     * Finds the Actor at the (x,y) position.
     * @param x x-coord.
     * @param y y-coord.
     * @return could be 'null' if no Actor is at that position.
     */
    public Actor findActorAt(int x, int y) {
        Actor ret = null;
        for(Actor a : actorLayer) {
            if (a.getX() == x && a.getY() == y) {
                ret = a;
            }
        }
        return ret;
    }


    public Array<IRenderable> getInertLayer() {
        return inertLayer;
    }
    public Array<Actor> getActorLayer() {
        return actorLayer;
    }

    public void removeFromInertLayer(GameObject object) {
        System.out.println("Removed object from InertLayer.");
        inertLayer.removeValue(object, true);
    }
    public void removeFromActorLayer(Actor actor) {
        System.out.println("Remove actor from ActorLayer.");
        actorLayer.removeValue(actor, true);
    }

    public void setUpSecondaryLayer(Hero player, BaseMapLayer currentLevel) {
        inertLayer = new Array<>();
        actorLayer = new Array<>();

        placeHero(player, 25,16);

        // todo: populate level with other Secondary objects
        createChest(31, 13);
        createChest(18, 2);
        createKey(14, 10);
        createEnemy(14, 11);
        createEnemy(29, 14);
        createEnemy(56, 25);

    }

    private void placeHero(Hero player, int x, int y) {
        player.setX(x);
        player.setY(y);
        actorLayer.add(player);
    }
    private void createEnemy(int x, int y) {
        actorLayer.add(objectFactory.createEnemy(x, y));
    }

    // todo: add into Floor's list of objects?
    private void createChest(int x, int y) {
        inertLayer.add(objectFactory.createChest(x, y));
    }
    private void createKey(int x, int y) {
        inertLayer.add(objectFactory.createKey(x, y));
    }
}
