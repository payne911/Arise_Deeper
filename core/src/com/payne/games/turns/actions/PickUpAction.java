package com.payne.games.turns.actions;

import com.payne.games.gameObjects.IPickable;
import com.payne.games.gameObjects.actors.Actor;


/**
 * Picks up an item.
 */
public class PickUpAction extends Action {
    private IPickable object;


    public PickUpAction(Actor source, IPickable object) {
        super(source);
        this.object = object;
    }


    @Override
    public float getFatigueCostMultiplier() {
        return 1;
    }

    @Override
    public boolean executeAction() {
//        source.getInventory().addItem(object); // todo: pick up the item
        return false;
    }

    @Override
    public String toString() {
        return "PickUpAction{" +
                "object=" + object +
                ", source=" + source +
                '}';
    }
}
