package com.payne.games.gameObjects;

import com.badlogic.gdx.utils.Queue;
import com.payne.games.turns.actions.IAction;


public abstract class Actor extends GameObject {
    private Queue<IAction> actions = new Queue<>(); // all the Actions the Actor wants to see executed
    private int rangeOfSight = 6; // how far the Actor can see in a straight line
    private int priority = 0; // used by the MinHeap of the TurnManager to determine who goes first


    public Actor(int x, int y) {
        super(x, y);
    }


    public void addAction(IAction action) {
        actions.addLast(action);
    }

    /**
     * Command Pattern implementation. The `TurnManager` ends up collecting the actions from all the Actors in the map.
     *
     * @return The Action this Actor wants to see executed.
     */
    public IAction getAction() {
        if(actions.notEmpty())
            return actions.removeFirst();
        return null;
    }

    public Queue<IAction> getActionsQueue() {
        return actions;
    }

    public int getRangeOfSight() {
        return rangeOfSight;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
