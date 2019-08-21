package com.payne.games.turns.actions;

import com.payne.games.gameObjects.actors.Actor;


/**
 * Wastes a turn..?
 */
public class NoopAction extends Action {


    public NoopAction(Actor source) {
        super(source);
    }


    @Override
    public boolean executeAction() {
        return true;
    }

    @Override
    public int getFatigueCost() {
        return 0;
    }
}
