package com.payne.games.actions.commands;

import com.payne.games.actions.Action;
import com.payne.games.gameObjects.GameObject;
import com.payne.games.gameObjects.actors.Actor;
import com.payne.games.gameObjects.actors.ActorState;
import com.payne.games.inventory.IPickable;


public class DropAction extends Action {
    private IPickable object;


    public DropAction(Actor source, IPickable object) {
        super(source);
        this.object = object;
    }

    @Override
    public float getFatigueCostMultiplier() {
        return 1;
    }

    @Override
    public boolean executeAction() {
        source.setState(ActorState.INTERACTING);
        IPickable dropping = source.getInventory().takeItem(object.getClass());

        /* Placing the dropped item below the Source. */
        ((GameObject) dropping).setX(source.getX());
        ((GameObject) dropping).setY(source.getY());

        return true;
    }


    @Override
    public String toString() {
        return "DropAction{" +
                "object=" + object +
                ", source=" + source +
                '}';
    }
}
