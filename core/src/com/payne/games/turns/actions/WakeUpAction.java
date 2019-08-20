package com.payne.games.turns.actions;

import com.payne.games.gameObjects.Actor;


public class WakeUpAction extends Action {


    public WakeUpAction(Actor source) {
        super(source);
    }


    @Override
    public void executeAction() {
        System.out.println("Awakened: " + getSource().toString());
        // todo: nothing to do?
    }

    @Override
    public int getFatigueCost() {
        return 70;
    }
}
