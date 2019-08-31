package com.payne.games.actions.commands;

import com.payne.games.actions.Action;
import com.payne.games.gameObjects.actors.Actor;
import com.payne.games.gameObjects.statics.Door;


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
        boolean nowClosed = target.toggle(); // todo: should check if there is another Actor standing there when the Door is Open
        if(nowClosed) {

        } else {

        }
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
