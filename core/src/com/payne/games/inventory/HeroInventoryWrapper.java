package com.payne.games.inventory;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.payne.games.actions.ActionController;
import com.payne.games.gameObjects.GameObject;
import com.payne.games.gameObjects.actors.entities.Hero;


/**
 * Links the general Inventory model with its UI representation.
 */
public class HeroInventoryWrapper {
    private Array<ImageTextButton> inventorySlots;
    private Inventory inventory;
    private ActionController actionController;
    private Hero hero;


    public HeroInventoryWrapper(Array<ImageTextButton> inventorySlots, Hero hero, ActionController actionController) {
        this.inventorySlots = inventorySlots;
        this.inventory = hero.getInventory();
        this.actionController = actionController;
        this.hero = hero;

        setUpClickListeners();
    }


    /**
     * Setting up the "DropAction" when clicking a slot containing an item.
     */
    private void setUpClickListeners() {
        for(int i=0; i < inventory.getCapacity(); i++) {
            final int index = i;
            inventorySlots.get(i).addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    IPickable item = inventory.getSlot(index).peek();
                    if(item != null) // if slot isn't empty
                        actionController.actionIssuer.dropItem(hero, item);
                }
            });
        }
    }

    /**
     * Updates the way the Inventory UI is displayed.
     */
    public void render() {
        String amount;
        IPickable item;
        int stackAmount;
        ImageTextButton slot;

        for(int i=0; i<inventorySlots.size; i++) {
            item = inventory.getItem(i);
            slot = inventorySlots.get(i);

            if(item == null) {
                amount = "";
                slot.getStyle().imageUp = null; // draw nothing
            } else {
                stackAmount = inventory.getSlot(i).getAmount();
                amount = stackAmount > 1  ? stackAmount+"" : ""; // only show a stack amount if more than 1 item
                slot.getStyle().imageUp = ((GameObject)item).getDrawable();
            }
            slot.getLabel().setText(amount);
        }
    }
}
