package com.payne.games.turns.actions;

import com.payne.games.gameObjects.actors.Actor;


public class TapAction extends Action {


    public TapAction(Actor source) {
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
