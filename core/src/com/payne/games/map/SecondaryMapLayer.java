package com.payne.games.map;

import com.badlogic.gdx.utils.Array;
import com.payne.games.gameObjects.*;
import com.payne.games.gameObjects.statics.Static;
import com.payne.games.gameObjects.actors.entities.Hero;
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
     * Spawns a random item at the designated area.
     *
     * @param x x-coordinate, in Tile units.
     * @param y y-coordinate, in Tile units.
     */
    public void spawnRandomItem(int x, int y) {
        objectFactory.createRandomItem(x, y);
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
        objectFactory.createChest(31, 13);
        objectFactory.createChest(18, 2);

        objectFactory.createKey(14, 10);
        objectFactory.createKey(27, 16);
        objectFactory.createKey(20, 14);
        objectFactory.createKey(18, 18);

        objectFactory.createDoor(25, 18, true);
        objectFactory.createDoor(28, 21, false);

        objectFactory.createFlame(30, 20);

        objectFactory.createHealthPotion(21, 15);

        DEBUG_spawn_enemies();
    }


    public void DEBUG_spawn_enemies() {
        objectFactory.createEnemy(14, 11);
        objectFactory.createEnemy(29, 14);
        objectFactory.createEnemy(25, 13);
        objectFactory.createEnemy(56, 25);
    }

    private void placeHero(Hero player, int x, int y) {
        player.setX(x);
        player.setY(y);
        actorLayer.add(player);
        currentLevel.getTile(x,y).setAllowingMove(false);
    }
}
