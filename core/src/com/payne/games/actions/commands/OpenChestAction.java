package com.payne.games.actions.commands;

import com.payne.games.actions.Action;
import com.payne.games.gameObjects.actors.Actor;
import com.payne.games.gameObjects.statics.Chest;


/**
 * Opens a Chest.<br>
 * Removes the Chest and places a random Item on the Floor where it was.
 */
public class OpenChestAction extends Action {
    private Chest target;


    public OpenChestAction(Actor source, Chest target) {
        super(source);
        this.target = target;
    }


    @Override
    public float getFatigueCostMultiplier() {
        return 1;
    }

    @Override
    public boolean executeAction() {
        controller.secondaryMapLayer.spawnRandomItem(target.getX(), target.getY());
        controller.removeStatic(target);
        return true;
    }


    @Override
    public String toString() {
        return "OpenChestAction{" +
                "target=" + target +
                ", source=" + source +
                '}';
    }
}
