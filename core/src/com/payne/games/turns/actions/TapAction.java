package com.payne.games.turns.actions;

import com.payne.games.gameObjects.Actor;


public class TapAction extends Action {


    public TapAction(Actor source) {
        super(source);
    }


    @Override
    public void executeAction() {

    }

    @Override
    public int getFatigueCost() {
        return 0;
    }
}
