package com.payne.games.logic.systems;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.payne.games.gameObjects.IPickable;


public class InventorySystem {
    private Array<TextButton> inventorySlots;


    public InventorySystem(Array<TextButton> inventorySlots) {
        this.inventorySlots = inventorySlots;
    }


    public void addItem(IPickable item) {
        // todo
    }
}
