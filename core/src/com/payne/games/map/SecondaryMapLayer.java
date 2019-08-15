package com.payne.games.map;

import com.payne.games.gameObjects.Chest;
import com.payne.games.gameObjects.GameObjectFactory;
import com.payne.games.gameObjects.Hero;
import com.payne.games.gameObjects.Key;
import com.payne.games.logic.GameLogic;
import com.payne.games.map.renderers.IRenderable;

import java.util.ArrayList;


public class SecondaryMapLayer {
    private GameLogic gLogic; // todo: maybe not necessary?
    private GameObjectFactory objectFactory;
    private ArrayList<IRenderable> secondaryLayer;


    public SecondaryMapLayer(GameLogic gameLogic, GameObjectFactory gameObjectFactory) {
        this.gLogic = gameLogic;
        this.objectFactory = gameObjectFactory;
    }


    public ArrayList<IRenderable> getSecondaryLayer() {
        return secondaryLayer;
    }

    public void setUpSecondaryLayer(Hero player, BaseMapLayer currentLevel) {
        secondaryLayer = new ArrayList<>();

        placeHero(player, 25,16);
        secondaryLayer.add(player);

        // todo: populate level with other Secondary objects
        secondaryLayer.add(createChest(28, 13));
        secondaryLayer.add(createChest(15, 3));
        secondaryLayer.add(createKey(14, 10));
    }

    private void placeHero(Hero player, int x, int y) {
        player.setX(x);
        player.setY(y);
    }

    // todo: add into Floor's list of objects?
    private Chest createChest(int x, int y) {
        return objectFactory.createChest(x, y);
    }

    private Key createKey(int x, int y) {
        return objectFactory.createKey(x, y);
    }
}
