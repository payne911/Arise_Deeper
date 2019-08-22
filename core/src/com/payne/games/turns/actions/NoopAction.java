package com.payne.games.turns.actions;

import com.payne.games.gameObjects.actors.Actor;


/**
 * todo : Wastes a turn..?
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
    public float getFatigueCostMultiplier() {
        return 0;
    }

    @Override
    public String toString() {
        return "NoopAction{" +
                "source=" + source +
                '}';
    }
}
