package com.payne.games.gameObjects.statics.entities;

import com.payne.games.actions.ActionController;
import com.payne.games.gameObjects.actors.Actor;
import com.payne.games.gameObjects.statics.Static;
import com.payne.games.logic.Utils;


/**
 * Chests contain a random Item, to be spawned when they are opened.
 */
public class Chest extends Static {


    public Chest(ActionController actionController, int x, int y) {
        super(actionController, x, y);
    }

    @Override
    public boolean canBeWalkedThrough() {
        return false;
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

        controller.actionIssuer.openChest(source, this);
        return true;
    }
}
