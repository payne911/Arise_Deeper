package com.payne.games.actions.commands;

import com.payne.games.actions.Action;
import com.payne.games.gameObjects.actors.Actor;
import com.payne.games.gameObjects.statics.entities.Door;


/**
 * Opens or closes a Door.
 */
public class ToggleDoorAction extends Action {
    protected Door target;


    public ToggleDoorAction(Actor source, Door target) {
        super(source);
        this.target = target;
    }


    @Override
    public float getFatigueCostMultiplier() {
        return .7f;
    }

    @Override
    public boolean executeAction() {
        target.toggle();
        controller.baseMapLayer.getTile(target.getX(), target.getY()).setAllowingMove(target.canBeWalkedThrough());
        return true;
    }


    @Override
    public String toString() {
        return "ToggleDoorAction{" +
                "target=" + target +
                ", source=" + source +
                '}';
    }
}
