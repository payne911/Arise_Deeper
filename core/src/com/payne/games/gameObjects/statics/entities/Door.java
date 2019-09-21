package com.payne.games.gameObjects.statics.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.payne.games.actions.ActionController;
import com.payne.games.gameObjects.actors.Actor;
import com.payne.games.gameObjects.statics.Static;
import com.payne.games.logic.Utils;


public class Door extends Static {
    private Array<TextureRegion> states = new Array<>();
    private boolean isClosed = true;
    private boolean isLocked;


    public Door(ActionController actionController, int x, int y, boolean isLocked) {
        super(actionController, x, y);
        this.isLocked = isLocked;
    }

    @Override
    public boolean renderInFog() {
        return true;
    }

    @Override
    public boolean tryInteractionFrom(Actor source) {
        boolean inRange = Utils.straightDistanceBetweenObjects(source, this) == 1;
        if(!inRange)
            return false;

        if(isLocked) {
            controller.actionIssuer.unlockDoor(source, this);
        } else {
            controller.actionIssuer.toggleDoor(source, this);
        }
        return true;
    }


    /**
     * Alternates between an Open and a Closed door.
     *
     * @return If the door is now closed. (That is, 'false' if it is now open, and 'true' if it is closed.)
     */
    public boolean toggle() {
        isClosed = !isClosed;
        return isClosed;
    }

    @Override
    public boolean canBeWalkedThrough() {
        return !isClosed;
    }

    @Override
    public boolean canBeSeenThrough() {
        return !isClosed;
    }

    public boolean isClosed() {
        return isClosed;
    }
    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public boolean isLocked() {
        return isLocked;
    }
    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    /**
     * @return The list used to contain all the possible graphical representations of a Door.<br>
     * index 0 = 'OPEN', 1 = 'CLOSED', 2 = 'LOCKED'.
     */
    public Array<TextureRegion> getStates() {
        return states;
    }


    /**
     * @return The appropriate Texture according to the state of the Door.
     */
    @Override
    public TextureRegion getTexture() {
        if(isLocked)
            return states.get(2);
        if(isClosed)
            return states.get(1);
        else
            return states.get(0);
    }



    @Override
    public String toString() {
        return "Door{" +
                "isClosed=" + isClosed +
                ", isLocked=" + isLocked +
                '}';
    }
}
