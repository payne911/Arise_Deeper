package com.payne.games.gameObjects.statics;

import com.payne.games.gameObjects.IPickable;
import com.payne.games.gameObjects.actors.Actor;
import com.payne.games.logic.Utils;
import com.payne.games.turns.actions.PickUpAction;


public class Key extends Static implements IPickable {


    public Key(int x, int y) {
        super(x, y);
    }


    @Override
    public boolean interact(Actor source) {
        boolean onSameTile = Utils.occupySameTile(source, this);
        if (onSameTile) // Actor is in range: pick up
            source.addAction(new PickUpAction(source, this));
        return onSameTile;
    }


}
