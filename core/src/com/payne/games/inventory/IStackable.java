package com.payne.games.inventory;

/**
 * Determines if multiple items of the same type can be stacked on the same Inventory slot.<br>
 * This means, for example, that 2 Keys could take up only a single slot.
 */
public interface IStackable extends IPickable {

    /**
     * @return The maximum amount of stacked items of the same type, within a single slot of the Inventory.
     */
    int getMaximumStackAmount();
}
