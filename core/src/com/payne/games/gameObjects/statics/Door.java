package com.payne.games.gameObjects.statics;

import com.payne.games.actions.ActionController;
import com.payne.games.gameObjects.actors.Actor;
import com.payne.games.logic.Utils;


public class Door extends Static {
    boolean isClosed = true;
    boolean isLocked;


    public Door(ActionController actionController, int x, int y, boolean isLocked) {
        super(actionController, x, y);
        this.isLocked = isLocked;
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
        System.out.println("Door was toggled. Now closed? " + isClosed);
        return isClosed;
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


    @Override
    public String toString() {
        return "Door{" +
                "isClosed=" + isClosed +
                ", isLocked=" + isLocked +
                '}';
    }
}
