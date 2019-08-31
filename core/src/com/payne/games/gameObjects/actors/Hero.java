package com.payne.games.gameObjects.actors;

import com.payne.games.actions.ActionController;
import com.payne.games.logic.Utils;
import com.payne.games.actions.Action;


public class Hero extends Actor {
    private int xp = 0;


    public Hero(ActionController actionController, int x, int y, int maxHp, int staminaRegen, int range) {
        super(actionController, x, y, maxHp, staminaRegen, range);
        setPriority(1);
    }


    public int getXp() {
        return xp;
    }
    public void setXp(int xp) {
        this.xp = xp;
        System.out.println("New XP: " + xp);
    }



    @Override
    public void die(Actor killer) {
        super.die(killer);

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
    public Action extractAction() {
        return null; // do not change this
    }


    @Override
    public boolean tryInteractionFrom(Actor source) {
        boolean withinRange = Utils.straightDistanceBetweenObjects(source, this) <= source.getRange();
        if (withinRange) // Actor is in range: attack!
            controller.actionIssuer.attack(source, this);
        return withinRange;
    }
}
