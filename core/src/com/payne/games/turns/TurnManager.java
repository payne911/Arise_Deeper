package com.payne.games.turns;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.BinaryHeap;
import com.payne.games.gameObjects.Actor;
import com.payne.games.logic.GameLogic;
import com.payne.games.turns.actions.IAction;


public class TurnManager {
    private GameLogic gLogic;
    private BinaryHeap<ActionNode> actions = new BinaryHeap<>();


    public TurnManager(GameLogic gameLogic) {
        this.gLogic = gameLogic;
    }


    /**
     * Extract all the actions that are to be executed.
     *
     * @param actors All the actors of the current level.
     */
    public void process(Array<Actor> actors) {
        for(Actor actor : actors) {
            IAction action = actor.getAction();
            if(action != null)
                actions.add(new ActionNode(actor.getPriority(), action));
        }
    }

    public void execute() {
        while(actions.size > 0) {
            IAction toExecute = actions.pop().action;
            toExecute.execute();
        }
    }

}
