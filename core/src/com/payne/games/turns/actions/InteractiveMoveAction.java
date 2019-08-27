package com.payne.games.turns.actions;

import com.payne.games.gameObjects.GameObject;
import com.payne.games.gameObjects.actors.Actor;
import com.payne.games.map.BaseMapLayer;
import com.payne.games.map.tiles.Tile;
import com.payne.games.pathfinding.MyIndexedGraph;


/**
 * A MoveAction, but with the goal to interact with a specific Actor (aka "recipient").
 */
public class InteractiveMoveAction extends MoveAction {
    private GameObject recipient;
    private BaseMapLayer baseMapLayer;


    public InteractiveMoveAction(Actor source, GameObject recipient, BaseMapLayer baseMapLayer,
                                 MyIndexedGraph graph, Tile from, Tile next, Tile to) {
        super(source, graph, from, next, to);

        this.baseMapLayer = baseMapLayer;
        this.recipient = recipient;
    }



    @Override
    public boolean executeAction() {

        if(newActorInSight()) // todo:  something requires attention : abort automatic move (probably to place somewhere)
            return false;

        boolean successfulInteraction = recipient.tryInteractionFrom(source);
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

        /* Since the Actor might have moved, we recompute where the "to" Tile is. */
        to = baseMapLayer.getTile(recipient.getX(), recipient.getY());

        /* Extract next move. */
        Tile again = findNextMove();

        /* Assign next Action accordingly. */
        if (again != null) { // not there yet? keep moving!
            InteractiveMoveAction interactiveMoveAction =
                    new InteractiveMoveAction(source, recipient, baseMapLayer, graph, next, again, to);
            source.addAction(interactiveMoveAction);
        } else if (next == to) { // right at the desired destination? try to interact
            recipient.tryInteractionFrom(source);
        }
    }




    @Override
    public String toString() {
        return "InteractiveMoveAction{" +
                "recipient=" + recipient +
                ", next=" + next +
                ", from=" + from +
                ", to=" + to +
                ", source=" + source +
                '}';
    }
}
