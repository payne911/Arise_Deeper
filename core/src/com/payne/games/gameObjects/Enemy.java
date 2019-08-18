package com.payne.games.gameObjects;


import com.badlogic.gdx.math.GridPoint2;
import com.payne.games.turns.actions.IAction;
import com.payne.games.turns.actions.MoveAction;

public class Enemy extends Actor {
    private int xpWorth;


    public Enemy(int x, int y, int maxHp, int staminaRegen, int range, int xpWorth) {
        super(x, y, maxHp, staminaRegen, range);
        setPriority(2);
        this.xpWorth = xpWorth;
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
     * @return the Action outputted by the AI.
     */
    @Override
    public IAction extractAction() {
        return new MoveAction(this, new GridPoint2(0,1)); // todo: integrate AI here
    }

}
