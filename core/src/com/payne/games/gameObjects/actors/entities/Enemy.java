package com.payne.games.gameObjects.actors.entities;

import com.payne.games.actions.ActionController;
import com.payne.games.gameObjects.actors.Actor;
import com.payne.games.logic.DecisionMaking;
import com.payne.games.logic.Utils;
import com.payne.games.actions.Action;


public class Enemy extends Actor {
    private int xpWorth;
    private DecisionMaking ai;


    public Enemy(ActionController actionController, int x, int y, int maxHp, int staminaRegen, int range, int xpWorth, boolean sleeping, DecisionMaking ai) {
        super(actionController, x, y, maxHp, staminaRegen, range);
        setPriority(2);
        this.xpWorth = xpWorth;
        this.ai = ai;
    }


    @Override
    public void die(Actor killer) {
        super.die(killer);

        if(killer instanceof Hero) {
            ((Hero) killer).setXp(((Hero) killer).getXp() + xpWorth);
        }

        controller.removeActor(this);

        System.out.println("An enemy has died.");
    }


    /**
     * Runs the AI to find what decision the Enemy wants to take.
     *
     * @return the Action outputted by the AI.
     */
    @Override
    public Action extractAction() {
        return ai.decide(this);
    }


    @Override
    public boolean tryInteractionFrom(Actor source) {
        boolean withinRange = Utils.straightDistanceBetweenObjects(source, this) <= source.getRange();
        if (withinRange) // Actor is in range: attack!
            controller.actionIssuer.attack(source, this);
        return withinRange;
    }
}
