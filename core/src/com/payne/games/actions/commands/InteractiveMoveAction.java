package com.payne.games.actions.commands;

import com.payne.games.gameObjects.GameObject;
import com.payne.games.gameObjects.actors.Actor;
import com.payne.games.map.tiles.Tile;


/**
 * A MoveAction, but with the goal to interact with a specific Actor (aka "recipient").
 */
public class InteractiveMoveAction extends MoveAction {
    private GameObject recipient;


    public InteractiveMoveAction(Actor source, GameObject recipient, Tile from, Tile next, Tile to) {
        super(source, from, next, to);

        this.recipient = recipient;
    }



    @Override
    public boolean executeAction() {

        if(newActorInSight()) // todo:  something requires attention : abort automatic move
            return false;

        // todo: detect state changes on the target (is Actor dead? has Door been unlocked? etc.)

        boolean successfulInteraction = recipient.tryInteractionFrom(source);
        if (successfulInteraction) // the goal was achieved
            return false; // execute the "recipient.interact()" Action instead of this one

        if(next.isAllowingMove()) { // make sure the pre-calculated path is still valid
            move();
            setUpNextMove();

        } else { // the pre-calculated path isn't valid anymore

            /* Rerun the pathfinding algorithm because the pre-calculated Tile is now occupied (does not allow movement). */
            next = controller.findNextTile(from, to); // todo: "to", in case of ranged weapons, isn't necessarily the position of the target... it's any Tile within range

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
        to = controller.baseMapLayer.getTile(recipient.getX(), recipient.getY());

        /* Extract next move. */
        Tile again = controller.findNextMove(next, to);

        /* Assign next Action accordingly. */
        if (again != null) { // not there yet? keep moving!
            controller.actionIssuer.interactiveMove(source, recipient, next, again, to);
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
