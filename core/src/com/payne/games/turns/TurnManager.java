package com.payne.games.turns;

import com.badlogic.gdx.utils.BinaryHeap;
import com.payne.games.gameObjects.Actor;
import com.payne.games.gameObjects.Hero;
import com.payne.games.map.SecondaryMapLayer;
import com.payne.games.turns.actions.AttackAction;
import com.payne.games.turns.actions.IAction;
import com.payne.games.turns.actions.MoveAction;


public class TurnManager {
    private BinaryHeap<ActorNode> actorsHeap = new BinaryHeap<>(); // minHeap
    private IAction actionToExecute;
    private SecondaryMapLayer secondaryMapLayer;
    private boolean waitingOnPlayerInput;


    public TurnManager(SecondaryMapLayer secondaryMapLayer) {
        this.secondaryMapLayer = secondaryMapLayer;
    }


    /**
     * Keeps executing turns until certain conditions are met.
     *
     * @return 'true' only if waiting on the player's input.
     */
    public boolean executeTurn() {
        boolean shouldRunAgain;
        do {

            /* Next two lines are the core of the Turn Mechanic. Collect + Execute an action. */
            collectNextAction();
            execute();

            /* See if the we should delay turns, or keep them coming. For a smoother experience.*/
            shouldRunAgain = checkIfShouldRunAnotherTurn();
        } while(shouldRunAgain);

        /* If waiting on input, we want to allow the player to freely explore the map with drag. */
        return waitingOnPlayerInput;
    }


    /**
     * This tries to make the turn-based experience smoother. Each Actor plays their turn individually, and some of
     * those turns aren't very relevant (especially the ones of the Actors we don't even see, for example).
     * Letting the user wait a "turn time" for those actions reduces the playability.
     * todo: This is experimental: trying to reproduce Pixel Dungeon's system. Just `return false;` to disable it.
     *
     * @return 'true' if another turn should be ran.
     */
    private boolean checkIfShouldRunAnotherTurn() {
        if(waitingOnPlayerInput)
            return false;

        boolean lastActionWasAnAttack = (actionToExecute instanceof AttackAction);
        boolean heroIssuedMove = (actionToExecute.getSource() instanceof Hero) && (actionToExecute instanceof MoveAction);

        // todo: don't allow two moves from the same Actor within a turn without blocking in between?
        // todo: maybe ONLY skip "enemy MoveAction" ?

        return !lastActionWasAnAttack && !heroIssuedMove;
    }


    /**
     * Check on all the Actors until one that can act is found.
     * If waiting on the player to decide, this just skips.
     */
    private void collectNextAction() {
        boolean foundNextActor = false;
        while(!foundNextActor) {
            recollectActors(); // keep going until an Actor is ready to take a turn

            Actor currentActor = actorsHeap.peek().actor;
            if(currentActor.notFatigued()) {
                foundNextActor = true;
                actionToExecute = currentActor.extractAction(); // 'null' if waiting on player input
            } else {
                currentActor.regenFatigue();
                actorsHeap.pop();
            }
        }

        waitingOnPlayerInput = (actionToExecute == null);
    }

    /**
     * Ensures the BinaryHeap is not empty.
     * (It becomes empty once every single Actor has taken a turn.)
     */
    private void recollectActors() {
        if(actorsHeap.isEmpty()) {
            for(Actor actor : secondaryMapLayer.getActorLayer()) {
                actorsHeap.add(new ActorNode(actor.getPriority(), actor));
            }
        }
    }


    /**
     * Executes the Action of the Actor who's turn it is.
     * If waiting on player input, nothing happens.
     */
    private void execute() {
        if(!waitingOnPlayerInput) {
            actionToExecute.execute(); // todo: maybe update collectedActors (if someone died or a minion was spawned?)
            actorsHeap.pop();
        }
    }

}
