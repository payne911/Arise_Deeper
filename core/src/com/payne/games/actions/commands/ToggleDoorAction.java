package com.payne.games.actions.commands;

import com.payne.games.actions.Action;
import com.payne.games.gameObjects.actors.Actor;
import com.payne.games.gameObjects.actors.ActorState;
import com.payne.games.gameObjects.statics.entities.Door;
import com.payne.games.map.tiles.Tile;


/**
 * Opens or closes a Door.
 */
public class ToggleDoorAction extends Action {
    protected Door target;


    public ToggleDoorAction(Actor source, Door target) {
        super(source);
        this.target = target;
    }


    @Override
    public float getFatigueCostMultiplier() {
        return .7f;
    }

    @Override
    public boolean executeAction() {
        source.setState(ActorState.INTERACTING);
        target.toggle();

        /* Updating walkability and visibility. */
        Tile currTile = controller.baseMapLayer.getTile(target.getX(), target.getY());
        currTile.setAllowingMove(target.canBeWalkedThrough());
        currTile.setSeeThrough(target.canBeSeenThrough());

        return true;
    }


    @Override
    public String toString() {
        return "ToggleDoorAction{" +
                "target=" + target +
                ", source=" + source +
                '}';
    }
}
