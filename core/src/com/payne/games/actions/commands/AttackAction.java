package com.payne.games.actions.commands;

import com.payne.games.actions.Action;
import com.payne.games.gameObjects.actors.Actor;
import com.payne.games.gameObjects.actors.ActorState;


public class AttackAction extends Action {
    private Actor target;
    private int dmg;


    public AttackAction(Actor source, Actor target, int dmg) {
        super(source);

        this.target = target;
        this.dmg = dmg;
    }


    @Override
    public boolean executeAction() {
        source.setState(ActorState.ATTACKING);
        target.takeHit(source, dmg);
        return true;
    }

    @Override
    public float getFatigueCostMultiplier() {
        return 2;
    }



    @Override
    public String toString() {
        return "AttackAction{" +
                "recipient=" + target +
                ", dmg=" + dmg +
                ", source=" + source +
                '}';
    }
}
