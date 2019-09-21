package com.payne.games.gameObjects.statics.entities;

import com.payne.games.actions.ActionController;
import com.payne.games.gameObjects.actors.Actor;
import com.payne.games.gameObjects.statics.Static;
import com.payne.games.rendering.animations.IAnimated;


/**
 * Just a decoration sprite. The Tile becomes non-walkable.
 */
public class Flame extends Static implements IAnimated {


    public Flame(ActionController actionController, int x, int y) {
        super(actionController, x, y);
    }

    @Override
    public boolean renderInFog() {
        return true;
    }

    @Override
    public boolean tryInteractionFrom(Actor source) {
        return false;
    }

    @Override
    public boolean canBeWalkedThrough() {
        return false;
    }

    @Override
    public boolean canBeSeenThrough() {
        return true;
    }
}
