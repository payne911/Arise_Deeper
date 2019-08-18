package com.payne.games.turns.actions;

import com.payne.games.gameObjects.Actor;


public class AttackAction extends Action {
    private Actor recipient;
    private Actor source;
    private int dmg;


    public AttackAction(Actor source, Actor recipient, int dmg) {
        super(source);

        this.recipient = recipient;
        this.source = source;
        this.dmg = dmg;
    }


    @Override
    public void executeAction() {
        boolean hasDied = recipient.takeHit(dmg);
        if(hasDied) {
            recipient.clearActionsQueue();
            recipient.die(source);
        }
    }

    @Override
    public int getFatigueCost() {
        return 100;
    }
}
