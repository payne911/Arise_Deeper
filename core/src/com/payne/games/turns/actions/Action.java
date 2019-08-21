package com.payne.games.turns.actions;

import com.payne.games.gameObjects.actors.Actor;


public abstract class Action implements IAction {
    protected Actor source;


    public Action(Actor source) {
        this.source = source;
    }


    public boolean execute() {
        boolean canceled = !executeAction();
        if(!canceled)
            source.addFatigue(getFatigueCost());
        return canceled;
    }

    abstract public boolean executeAction();
    abstract public int getFatigueCost();

    public Actor getSource(){
        return source;
    }
}
