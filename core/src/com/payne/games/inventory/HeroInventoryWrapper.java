package com.payne.games.inventory;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;


/**
 * Links the general Inventory model with its UI representation. Acts as a Controller.
 */
public class HeroInventoryWrapper {
    private Array<TextButton> inventorySlots;
    private Inventory inventory;


    public HeroInventoryWrapper(Array<TextButton> inventorySlots, Inventory inventory) {
        this.inventorySlots = inventorySlots;
        this.inventory = inventory;
    }


    /**
     * Used to update the way the Inventory UI is displayed.
     */
    public void render() {
        for(int i=0; i<inventorySlots.size; i++) {
            // ((IRenderable) inventory.getSlot(i).peek()).getTexture();    todo: for when using ImageButton !

            String tmp_txt;
            IPickable item = inventory.getItem(i);
            if(item == null) {
                tmp_txt = "";
            } else {
                String amount = "("+inventory.getSlot(i).getAmount()+")";
                tmp_txt = amount + inventory.getItem(i).getClass().getSimpleName();
            }
            inventorySlots.get(i).setText(tmp_txt);
        }
    }
}
