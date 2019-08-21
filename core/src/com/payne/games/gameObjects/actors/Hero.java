package com.payne.games.gameObjects.actors;

import com.payne.games.logic.systems.InventorySystem;
import com.payne.games.turns.actions.IAction;


public class Hero extends Actor {
    private InventorySystem inventory;
    private int xp = 0;


    public Hero(int x, int y, int maxHp, int staminaRegen, int range) {
        super(x, y, maxHp, staminaRegen, range);
        setPriority(1);
    }


    public int getXp() {
        return xp;
    }
    public void setXp(int xp) {
        this.xp = xp;
        System.out.println("New XP: " + xp);
    }

    public void setInventory(InventorySystem inventory) {
        this.inventory = inventory;
    }



    @Override
    public void die(Actor killer) {
        System.out.println("You have died.");
    }


    /**
     * Returns the action the player wants to do. The action itself is filled via the InputProcessor, so if this
     * particular method is called (which means it returns `null`), it is because the player has not submitted
     * any actions and the engine is waiting for one (aka it's the player's turn).
     *
     * @return 'null' only if the player still hasn't made a decision.
     */
    @Override
    public IAction extractAction() {
        return null;
    }


    @Override
    public boolean interact() {
        // todo: attack me
        return false;
    }
}
