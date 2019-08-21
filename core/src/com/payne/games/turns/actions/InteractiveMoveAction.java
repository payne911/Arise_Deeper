package com.payne.games.turns.actions;

import com.payne.games.gameObjects.actors.Actor;


/**
 * A MoveAction, but with the goal to interact with a specific GameObject.
 */
public class InteractiveMoveAction extends Action {


    public InteractiveMoveAction(Actor source) {
        super(source);
    }


    @Override
    public void executeAction() {
        // todo
    }

    @Override
    public int getFatigueCost() {
        return 50;
    }
}
