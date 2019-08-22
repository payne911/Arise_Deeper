package com.payne.games.turns.actions;

import com.payne.games.gameObjects.actors.Actor;


public class AttackAction extends Action {
    private Actor recipient;
    private int dmg;


    public AttackAction(Actor source, Actor recipient, int dmg) {
        super(source);

        this.recipient = recipient;
        this.dmg = dmg;
    }


    @Override
    public boolean executeAction() {
        boolean hasDied = recipient.takeHit(dmg);
        if(hasDied) {
            recipient.clearActionsQueue();
            recipient.die(source);
        }
        return true;
    }

    @Override
    public float getFatigueCostMultiplier() {
        return 2;
    }

    @Override
    public String toString() {
        return "AttackAction{" +
                "recipient=" + recipient +
                ", dmg=" + dmg +
                ", source=" + source +
                '}';
    }
}
