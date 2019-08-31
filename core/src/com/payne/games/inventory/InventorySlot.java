package com.payne.games.inventory;


import com.badlogic.gdx.utils.Array;

/**
 * An Inventory Slot can only contain a single Item Type, though if it is stackable,
 * many instances can be stored within the same Slot.
 */
public class InventorySlot {
    private Array<IPickable> items = new Array<>();


    public InventorySlot(IPickable item) {
        addItem(item);
    }

    public InventorySlot() {
    }


    /**
     * Determines if the input item can be stacked within this Slot.<br>
     * If there are no items, the slot is not a candidate.
     *
     * @param stackable The item we want to stack.
     * @return 'true' only if the Type and amount allow the item to be added to the current stack of this slot.
     */
    public boolean canAddStack(IStackable stackable) {
        if(!(peek() instanceof IStackable))
            return false;

        return (contains(stackable) && getAmount() < stackable.getMaximumStackAmount());
    }

    /**
     * To check whether or not this InventorySlot contains a certain Type of item. Uses the class names, and not references.
     *
     * @param checked An instance of the item type to be verified against.
     * @return 'true' only if the Inventory already contains at least one item of the same type as the input.
     */
    boolean contains(IPickable checked) {
        return notEmpty() && (peek().getClass() == checked.getClass());
    }

    /**
     * To check whether or not this InventorySlot contains a certain Type of item.
     *
     * @param checked The class of the item type to be verified against.
     * @return 'true' only if the Inventory already contains at least one item of the same type as the input.
     */
    boolean contains(Class<? extends IPickable> checked) {
        return notEmpty() && (peek().getClass() == checked);
    }

    /**
     * Clears the list of Items contained in this Slot.
     */
    public void reset() {
        items.clear();
    }





    /**
     * Adds the input item, without verification of whether or not it is the same type as the other items.<br>
     * The verification is the responsibility of the `Inventory` that contains a Collection of Slots.
     *
     * @param item The item to be added.
     */
    void addItem(IPickable item) {
        items.add(item);
    }

    /**
     * To remove and obtain the last item contained in this Slot. Basically calls "pop()" on the list.
     *
     * @return 'null' if the Slot is empty. Otherwise, returns the instance of the last Item contained in this Slot.
     */
    public IPickable removeItem() {
        return notEmpty() ? items.pop() : null;
    }

    /**
     * Gives access to the first Item placed in the Slot, without removing it.
     *
     * @return 'null' if the Slot is empty. Otherwise, returns the instance of the first Item contained in this Slot.
     */
    public IPickable peek() {
        return notEmpty() ? items.get(0) : null;
    }





    /**
     * @return The size of the collection of Items contained in this Slot.
     */
    public int getAmount() {
        return items.size;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public boolean notEmpty() {
        return items.notEmpty();
    }
}
