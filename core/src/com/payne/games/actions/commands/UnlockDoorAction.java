package com.payne.games.actions.commands;

import com.payne.games.gameObjects.actors.Actor;
import com.payne.games.gameObjects.statics.Door;
import com.payne.games.gameObjects.statics.Key;
import com.payne.games.inventory.IPickable;


public class UnlockDoorAction extends ToggleDoorAction {


    public UnlockDoorAction(Actor source, Door target) {
        super(source, target);
    }


    @Override
    public float getFatigueCostMultiplier() {
        return 1;
    }

    @Override
    public boolean executeAction() {
        IPickable key = source.getInventory().takeItem(Key.class);
        if (key == null) {
            return false;
        } else {
            target.setLocked(false);
            boolean nowClosed = target.toggle();
            return true;
        }
    }


    @Override
    public String toString() {
        return "UnlockDoorAction{" +
                "target=" + target +
                ", source=" + source +
                '}';
    }
}