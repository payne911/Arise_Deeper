package com.payne.games.logic;

import com.badlogic.gdx.math.RandomXS128;
import com.payne.games.gameObjects.actors.Actor;
import com.payne.games.actions.ActionController;
import com.payne.games.actions.Action;
import com.payne.games.actions.commands.NoopAction;
import com.payne.games.actions.commands.WakeUpAction;
import com.payne.games.gameObjects.actors.ActorState;


public class DecisionMaking {
    // todo: add personality traits for more random behaviors across same Enemy-type?
    private ActionController actionController;
    private RandomXS128 rand;


    public DecisionMaking(ActionController actionController) {
        this.actionController = actionController;
        this.rand = new RandomXS128(GameLogic.RANDOM_DECISIONS ? (int)(Math.random()*1000) : GameLogic.RANDOM_SEED);
    }


    /**
     * Returns the Action the Actor is to take.
     * Only called if the Actor had no Actions queued up already.
     *
     * @param src the Actor to whom the Action is associated.
     * @return the Action the Actor will take.
     */
    public Action decide(Actor src) {
        if(src.isOccupied())
            return src.getNextAction();

        if(src.isSleeping()) {
            boolean shouldWakeUp = rand.nextBoolean();
            if(shouldWakeUp)
                return new WakeUpAction(src);
            else
                return new NoopAction(src);
        } else {
            actionController.moveToRandomPoint(src);
            return src.getNextAction();
        }
    }
}
