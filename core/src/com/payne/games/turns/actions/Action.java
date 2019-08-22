package com.payne.games.turns.actions;

import com.payne.games.gameObjects.actors.Actor;
import com.payne.games.logic.GameLogic;


public abstract class Action implements IAction {
    protected Actor source;


    public Action(Actor source) {
        this.source = source;
    }


    public boolean execute() {
        boolean canceled = !executeAction();
        if(!canceled)
            source.addFatigue(getFatigueCostMultiplier() * GameLogic.TURN_GENERAL_COST);
        return canceled;
    }

    abstract public float getFatigueCostMultiplier();
    abstract public boolean executeAction();

    public Actor getSource(){
        return source;
    }
}
