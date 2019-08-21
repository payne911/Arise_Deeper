package com.payne.games.turns.actions;

import com.payne.games.gameObjects.actors.Actor;


public abstract class Action implements IAction {
    protected Actor source;


    public Action(Actor source) {
        this.source = source;
    }


    /**
     * Takes care of executing the action and adding the proper amount of fatigue.
     */
    public void execute() {
        source.addFatigue(getFatigueCost());
        executeAction();
    }

    abstract public void executeAction();
    abstract public int getFatigueCost();

    public Actor getSource(){
        return source;
    }
}
