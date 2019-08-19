package com.payne.games.logic;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;


public class InventorySystem {
    private Array<TextButton> inventorySlots;


    public InventorySystem(Array<TextButton> inventorySlots) {
        this.inventorySlots = inventorySlots;
    }
}
