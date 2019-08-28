package com.payne.games.turns.actions;

import com.payne.games.gameObjects.GameObject;
import com.payne.games.inventory.IPickable;
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
        boolean success = source.getInventory().addItem(object);
        System.out.println("Pick up success: " + success);

        if(success){
            ((GameObject)object).placeOutsideOfMap();
        }

        return true;
    }

    @Override
    public String toString() {
        return "PickUpAction{" +
                "object=" + object +
                ", source=" + source +
                '}';
    }
}
