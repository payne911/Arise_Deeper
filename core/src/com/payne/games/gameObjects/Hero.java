package com.payne.games.gameObjects;


import com.payne.games.turns.actions.IAction;

public class Hero extends Actor {
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

    @Override
    public void die(Actor killer) {
        System.out.println("You have died.");
    }


    /**
     * Returns the action the player wants to do.
     * @return 'null' only if the player still hasn't made a decision.
     */
    @Override
    public IAction extractAction() {
        if(actions.notEmpty())
            return actions.removeFirst();
        return null;
    }

}
