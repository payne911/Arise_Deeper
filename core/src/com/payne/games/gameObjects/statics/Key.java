package com.payne.games.gameObjects.statics;

import com.payne.games.map.tiles.Door;


public class Key extends Static {
    private Door associatedDoor;


    public Key(int x, int y) {
        super(x, y);
    }


    @Override
    public boolean interact() {
        // todo: pick it up
        return false;
    }


}
