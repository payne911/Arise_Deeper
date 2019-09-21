package com.payne.games.gameObjects.statics;

import com.payne.games.actions.ActionController;
import com.payne.games.gameObjects.GameObject;


/**
 * GameObjects that will not move.
 */
public abstract class Static extends GameObject {


    public Static(ActionController actionController, int x, int y) {
        super(actionController, x, y);
    }

    @Override
    public boolean canBeWalkedThrough() {
        return true; // by default, a Static can be walked through
    }

    @Override
    public boolean canBeSeenThrough() {
        return true;
    }
}
