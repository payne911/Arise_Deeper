package com.payne.games.gameObjects.actors;

import com.payne.games.logic.DecisionMaking;
import com.payne.games.logic.Utils;
import com.payne.games.turns.actions.AttackAction;
import com.payne.games.turns.actions.IAction;


public class Enemy extends Actor {
    private int xpWorth;
    private DecisionMaking ai;


    public Enemy(int x, int y, int maxHp, int staminaRegen, int range, int xpWorth, boolean sleeping, DecisionMaking ai) {
        super(x, y, maxHp, staminaRegen, range);
        setPriority(2);
        setSleeping(sleeping);
        this.xpWorth = xpWorth;
        this.ai = ai;
    }


    @Override
    public void die(Actor killer) {
        if(killer instanceof Hero) {
            ((Hero) killer).setXp(((Hero) killer).getXp() + xpWorth);
        }

        System.out.println("An enemy has died.");
    }


    /**
     * Runs the AI to find what decision the Enemy wants to take.
     *
     * @return the Action outputted by the AI.
     */
    @Override
    public IAction extractAction() {
        return ai.decide(this);
    }


    @Override
    public boolean interact(Actor source) {
        boolean withinRange = Utils.straightDistanceBetweenObjects(source, this) < source.getRange();
        if (withinRange) { // Actor is in range: attack!
            source.addAction(new AttackAction(source, this, source.getDmg()));
        }
        return withinRange;
    }
}