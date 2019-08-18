package com.payne.games.turns;

import com.badlogic.gdx.utils.BinaryHeap;
import com.payne.games.gameObjects.Actor;
import com.payne.games.logic.GameLogic;
import com.payne.games.map.SecondaryMapLayer;
import com.payne.games.turns.actions.IAction;


public class TurnManager {
    private GameLogic gLogic;
    private BinaryHeap<ActorNode> actorsHeap = new BinaryHeap<>(); // minHeap
    private IAction actionToExecute;
    private SecondaryMapLayer secondaryMapLayer;


    public TurnManager(GameLogic gameLogic, SecondaryMapLayer secondaryMapLayer) {
        this.gLogic = gameLogic;
        this.secondaryMapLayer = secondaryMapLayer;
    }


    /**
     * Runs until an Actor takes a turn.
     *
     * @return 'false' when waiting on player's input.
     */
    public boolean executeTurn() {
        collectAction();
        return execute();
    }


    /**
     * Check on all the Actors until one that can act is found.
     * If waiting on the player to decide, this just skips.
     */
    private void collectAction() {
        boolean foundNextActor = false;
        while(!foundNextActor) {
            collectActors(); // keep going until an Actor is ready to take a turn

            Actor currentActor = actorsHeap.peek().actor;
            if(currentActor.notFatigued()) {
                foundNextActor = true;
                actionToExecute = currentActor.extractAction(); // 'null' if waiting on player input
            } else {
                currentActor.regenFatigue();
                actorsHeap.pop();
            }
        }

    }

    /**
     * Ensures the BinaryHeap is not empty.
     * (It becomes empty once every single Actor has taken a turn.)
     */
    private void collectActors() {
        if(actorsHeap.isEmpty()) {
            for(Actor actor : secondaryMapLayer.getActorLayer()) {
                actorsHeap.add(new ActorNode(actor.getPriority(), actor));
            }
        }
    }


    /**
     * Executes the Action of the Actor who's turn it is.
     * If waiting on player input, nothing happens.
     *
     * @return 'false' only if waiting on player.
     */
    private boolean execute() {
        if(actionToExecute != null) {
            actionToExecute.execute();
            actorsHeap.pop();
            return true;
        }
        return false;
    }

}
