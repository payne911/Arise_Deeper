package com.payne.games.inventory;

import com.badlogic.gdx.utils.Array;


/**
 * An Inventory is a collection of Slots that each can hold an Item Type.<br>
 * Stackable items will be stacked within the same slot.
 */
public class Inventory {
    private int capacity; // maximum amount of slots within the Inventory
    private Array<InventorySlot> itemSlots; // each slot contains a single Item Type


    /**
     * An empty Inventory.
     *
     * @param capacity the maximum amount of different Item Types that can be fit in this Inventory.
     */
    public Inventory(int capacity) {
        this.capacity = capacity;
        this.itemSlots = new Array<>(capacity);

        clear();
    }

//    /**
//     * An Inventory with the listed items added to it.
//     *
//     * @param capacity maximum amount of slots.
//     * @param itemsToCopy list of items to include in the Inventory.
//     */
//    public Inventory(int capacity, Array<InventorySlot> itemsToCopy) {
//        this.capacity = capacity;
//        this.slots = new Array<>(itemsToCopy); // todo: ensure it is a proper "clone" without references to the original?
//    }
//
//    /**
//     * An Inventory with the listed items added to it.
//     *
//     * @param capacity maximum amount of slots.
//     * @param slots items to add to the Inventory.
//     * @throws Exception thrown if there aren't enough slots to add all the items listed.
//     */
//    public Inventory(int capacity, InventorySlot... slots) throws Exception {
//        if(slots.length > capacity)
//             throw new Exception("Not enough slots for all these items to be initialized.");
//        this.capacity = capacity;
//        this.slots = new Array<>(slots);
//    }
//
//    /**
//     * An Inventory with the listed items added to it.
//     * The capacity of the inventory is of the amount of Item Types it is initialized with.
//     *
//     * @param slots items to add to the Inventory.
//     */
//    public Inventory(InventorySlot... slots) {
//        this.capacity = slots.length;
//        this.slots = new Array<>(slots);
//    }








    /**
     * Places the item in the Inventory at an available index.
     *
     * @param item The item to be added.
     * @return 'true' if the item was properly added. 'false' if the item couldn't be added.
     */
    public boolean addItem(IPickable item) {
        int index = getFreeIndex(item);
        if(index < 0) // couldn't find a free slot
            return false;

        getSlot(index).addItem(item);
        return true;
    }

    /**
     * Removes and returns the item of the specified type from the slot which
     * contains the least amount of that item.<br>
     * Uses the class name.
     *
     * @param item The researched item.
     * @return 'null' if no item of that type was found, else the item that was removed is returned.
     */
    public IPickable takeItem(Class<? extends IPickable> item) {
        int index = findIndex(item);
        if(index < 0) // couldn't find item
            return null;

        return getSlot(index).removeItem();
    }

    /**
     * To find the index of a specified item. Uses the class name.<br>
     * Looks through the Inventory to find the Slot which contains the least
     * amount of that item.<br>
     * In other words, if two stackable slots of the same item exist,
     * the smallest one is returned.
     *
     * @param item The researched item.
     * @return The index that designates which InventorySlots contains the specified item.
     *         Returns "-1" if the Inventory doesn't contain such item.
     */
    private int findIndex(Class<? extends IPickable> item) {
        int min   = Integer.MAX_VALUE;
        int index = -1;

        InventorySlot currSlot;
        for (int i = 0; i < capacity; i++) {
            currSlot = getSlot(i);
            if (currSlot.contains(item) && currSlot.getAmount() < min) {
                index = i;
                min = currSlot.getAmount();
            }
        }

        return index;
    }

    /**
     * To know if the Inventory is full.
     *
     * @return 'true' only if there is no space for any new item to be inserted.
     */
    public boolean isFull() {
        for(int i=0; i<capacity; i++) {
            if(getSlot(i) != null)
                return false;
        }
        return true;
    }

    /**
     * Clears the whole inventory.
     */
    public void clear() {
        itemSlots.clear();
        for(int i=0; i<capacity; i++) {
            itemSlots.add(new InventorySlot());
        }
    }

    /**
     * To obtain an index that could be associated on the input item.
     *
     * @param item The item for which we are looking for an available slot.
     * @return The index that is free. "-1" if there is no available slot.
     */
    public int getFreeIndex(IPickable item) {

        if(item instanceof IStackable) { // if it's Stackable, we must try to Stack first
            for (int i = 0; i < capacity; i++) {
                if (getSlot(i).canAddStack((IStackable)item)) // must be proper type, and have enough space to stack
                    return i;
            }
        }

        return getFreeIndex(); // couldn't find any pre-allocated slot, find a free one
    }

    /**
     * To obtain an index which isn't associated with an item.
     *
     * @return The index that is free. "-1" if there is no available slot.
     */
    public int getFreeIndex() {
        for (int i = 0; i < capacity; i++) {
            if (getSlot(i).isEmpty())
                return i;
        }
        return -1;
    }

    /**
     * Returns the item at the desired index. However, the item is not removed from the slot!
     *
     * @param index Index of the desired item.
     * @return The item at that index. Could be 'null' if there is no item.
     */
    public IPickable getItem(int index) {
        return getSlot(index).peek();
    }

    /**
     * @param index Index of the desired Slot.
     * @return The InventorySlot at the specified index.
     */
    public InventorySlot getSlot(int index) {
        return itemSlots.get(index);
    }

    public int getCapacity() {
        return capacity;
    }

    public Array<InventorySlot> getItemSlots() {
        return itemSlots;
    }
}
