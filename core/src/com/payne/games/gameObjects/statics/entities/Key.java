package com.payne.games.gameObjects.statics.entities;

import com.payne.games.actions.ActionController;
import com.payne.games.gameObjects.statics.Static;
import com.payne.games.inventory.IStackable;
import com.payne.games.gameObjects.actors.Actor;
import com.payne.games.logic.Utils;


public class Key extends Static implements IStackable {


    public Key(ActionController actionController, int x, int y) {
        super(actionController, x, y);
    }


    @Override
    public boolean tryInteractionFrom(Actor source) {
        boolean onSameTile = Utils.occupySameTile(source, this);
        if (onSameTile) // Actor is in range: pick up
            controller.actionIssuer.pickUp(source, this);
        return onSameTile;
    }


    @Override
    public int getMaximumStackAmount() {
        return 3;
    }
}
