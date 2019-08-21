package com.payne.games.turns.actions;

import com.payne.games.gameObjects.actors.Actor;


public class NoopAction extends Action {


    public NoopAction(Actor source) {
        super(source);
    }


    @Override
    public void executeAction() {

    }

    @Override
    public int getFatigueCost() {
        return 50;
    }
}
