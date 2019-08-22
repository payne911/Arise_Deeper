package com.payne.games.turns.actions;

import com.payne.games.gameObjects.actors.Actor;


/**
 * Awakens a sleeping Actor.
 */
public class WakeUpAction extends Action {


    public WakeUpAction(Actor source) {
        super(source);
    }


    @Override
    public boolean executeAction() {
        System.out.println("Awakened: " + getSource().toString());
        // todo: nothing to do?
        return true;
    }

    @Override
    public float getFatigueCostMultiplier() {
        return 1.4f;
    }

    @Override
    public String toString() {
        return "WakeUpAction{" +
                "source=" + source +
                '}';
    }
}
