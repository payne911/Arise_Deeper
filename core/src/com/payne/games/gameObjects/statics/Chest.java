package com.payne.games.gameObjects.statics;


import com.payne.games.gameObjects.actors.Actor;

public class Chest extends Static {


    public Chest(int x, int y) {
        super(x, y);
    }


    @Override
    public boolean interact(Actor source) {
        // todo: open me
        return false;
    }
}
