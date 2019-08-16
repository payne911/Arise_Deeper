package com.payne.games.map;

import com.badlogic.gdx.utils.Array;
import com.payne.games.gameObjects.*;
import com.payne.games.logic.GameLogic;
import com.payne.games.map.renderers.IRenderable;


public class SecondaryMapLayer {
    private GameLogic gLogic; // todo: maybe not necessary?
    private GameObjectFactory objectFactory;
    private Array<IRenderable> inertLayer; // keys, chests, etc.
    private Array<Actor> actorLayer; // hero, enemies, etc.


    public SecondaryMapLayer(GameLogic gameLogic, GameObjectFactory gameObjectFactory) {
        this.gLogic = gameLogic;
        this.objectFactory = gameObjectFactory;
    }


    public Array<IRenderable> getInertLayer() {
        return inertLayer;
    }
    public Array<Actor> getActorLayer() {
        return actorLayer;
    }

    public void setUpSecondaryLayer(Hero player, BaseMapLayer currentLevel) {
        inertLayer = new Array<>();
        actorLayer = new Array<>();

        placeHero(player, 25,16);

        // todo: populate level with other Secondary objects
        createChest(28, 13);
        createChest(15, 3);
        createKey(14, 10);
    }

    private void placeHero(Hero player, int x, int y) {
        player.setX(x);
        player.setY(y);
        actorLayer.add(player);
    }

    // todo: add into Floor's list of objects?
    private void createChest(int x, int y) {
        inertLayer.add(objectFactory.createChest(x, y));
    }

    private void createKey(int x, int y) {
        inertLayer.add(objectFactory.createKey(x, y));
    }
}
