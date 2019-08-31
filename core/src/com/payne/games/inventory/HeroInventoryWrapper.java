package com.payne.games.inventory;

import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.utils.Array;
import com.payne.games.gameObjects.GameObject;


/**
 * Links the general Inventory model with its UI representation. Acts as a Controller.
 */
public class HeroInventoryWrapper {
    private Array<ImageTextButton> inventorySlots;
    private Inventory inventory;


    public HeroInventoryWrapper(Array<ImageTextButton> inventorySlots, Inventory inventory) {
        this.inventorySlots = inventorySlots;
        this.inventory = inventory;
    }


    /**
     * Used to update the way the Inventory UI is displayed.
     */
    public void render() {
        String amount;
        IPickable item;
        int stackAmount;
        for(int i=0; i<inventorySlots.size; i++) {

            item = inventory.getItem(i);
            if(item == null) {
                amount = "";
                inventorySlots.get(i).getStyle().imageUp = null; // draw nothing
            } else {
                stackAmount = inventory.getSlot(i).getAmount();
                amount = stackAmount > 1  ? stackAmount+"" : ""; // only show a stack amount if more than 1 item
                inventorySlots.get(i).getStyle().imageUp = ((GameObject)item).getDrawable();
            }
            inventorySlots.get(i).getLabel().setText(amount);
        }
    }
}
