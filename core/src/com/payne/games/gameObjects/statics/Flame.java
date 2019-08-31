package com.payne.games.gameObjects.statics;

import com.payne.games.actions.ActionController;
import com.payne.games.gameObjects.actors.Actor;


/**
 * Just a decoration sprite. The Tile becomes non-walkable.
 * todo: animate
 */
public class Flame extends Static {


    public Flame(ActionController actionController, int x, int y) {
        super(actionController, x, y);
    }

    @Override
    public boolean tryInteractionFrom(Actor source) {
        return false;
    }
}
