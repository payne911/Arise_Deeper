package com.payne.games.turns.actions;

import com.payne.games.gameObjects.actors.Actor;
import com.payne.games.map.tiles.Tile;
import com.payne.games.pathfinding.MyIndexedGraph;


/**
 * A MoveAction, but with the goal to interact with a specific Actor.
 */
public class ActorInteractionMoveAction extends MoveAction {
    private Actor recipient;


    public ActorInteractionMoveAction(Actor source, Actor recipient, MyIndexedGraph graph, Tile from, Tile next, Tile to) {
        super(source, graph, from, next, to);
        this.recipient = recipient;
    }



    @Override
    public boolean executeAction() {

        if(newActorInSight()) // todo:  something requires attention : abort automatic move (probably to place somewhere)
            return false;

        boolean successfulInteraction = recipient.interact(source);
        if (successfulInteraction) // the goal was achieved
            return false; // execute the "recipient.interact()" Action instead of this one

        if(next.isAllowingMove()) { // make sure the pre-calculated path is still valid
            move();
            setUpNextMove();

        } else { // the pre-calculated path isn't valid anymore

            /* Rerun the pathfinding algorithm because the pre-calculated Tile is now occupied (does not allow movement). */
            next = graph.extractFirstMove(from, to); // todo: "to", in case of ranged weapons, isn't necessarily the position of the target... it's any Tile within range


            if (next != null) { // new path was found!
                move();
                setUpNextMove();

            } else { // no path exists anymore: the action is canceled, and the Actor has its turn back
                return false;
            }
        }

        return true;
    }

    private void setUpNextMove() {

        // todo: activate Traps if stepped on one (and don't issue another MoveAction)

        /* Keep assigning MoveActions until reaching destination. */
        // todo: "to" needs to be updated since the Actor could have moved.
        to.setAllowingMove(true);
        Tile again = graph.extractFirstMove(next, to); // "next", at this point, is the current position of the actor
        to.setAllowingMove(false);
        if (again != null) {
            ActorInteractionMoveAction actorInteractionMoveAction = new ActorInteractionMoveAction(source, recipient, graph, next, again, to);
            source.addAction(actorInteractionMoveAction);
        }
    }




    @Override
    public String toString() {
        return "ActorInteractionMoveAction{" +
                "recipient=" + recipient +
                ", next=" + next +
                ", from=" + from +
                ", to=" + to +
                ", source=" + source +
                '}';
    }
}
