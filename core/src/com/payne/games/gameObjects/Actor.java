package com.payne.games.gameObjects;

import com.badlogic.gdx.utils.Queue;
import com.payne.games.turns.actions.IAction;


public abstract class Actor extends GameObject {
    protected Queue<IAction> actions = new Queue<>(); // all the Actions the Actor wants to see executed
    private int priority = 2; // used by the MinHeap of the TurnManager to determine who goes first in case of equality

    // states
    private boolean sleeping = false;
    private int rangeOfSight = 6; // how far the Actor can see in a straight line
    private boolean invincible = false;
    private int maxHp;
    private int currHp;
    private int fatigueRegen;
    private int currFatigue = 0; // used by the TurnManager
    private int range;



    public Actor(int x, int y, int maxHp, int fatigueRegen, int range) {
        super(x, y);
        this.maxHp = maxHp;
        this.currHp = maxHp;
        this.fatigueRegen = fatigueRegen;
        this.range = range;
    }



    /**
     * Method used to deal damage to an Actor.
     *
     * @param dmg the amount of damage dealt.
     * @return 'true' if the damage has killed the Actor receiving the damage.
     */
    public boolean takeHit(int dmg) {
        if(!invincible)
            currHp -= dmg;
        System.out.println("Damage taken. New HP: " + Math.max(0,currHp) + "/" + maxHp);
        return isDead();
    }
    abstract public void die(Actor killer);

    public boolean isDead() {
        return currHp <= 0;
    }

    /**
     * Command Pattern implementation. The `TurnManager` ends up collecting the actions from all the Actors in the map.
     * This is where an Enemy's AI takes a decision, among other things.
     *
     * @return The Action this Actor wants to see executed. 'null' only if the engine is waiting for the player to act.
     */
    abstract public IAction extractAction();


    /**
     * Determines whether or not this Actor already has Actions that it wants to take.
     *
     * @return 'false' only if idle (no Actions in the Queue).
     */
    public boolean isOccupied() {
        return actions.size > 0;
    }

    public void clearActionsQueue() {
        actions.clear();
    }

    public IAction getNextAction() {
        if(isOccupied())
            return actions.removeFirst();
        else
            return extractAction();
    }

    public void regenFatigue() {
        currFatigue = Math.max(0, currFatigue - fatigueRegen);
    }

    public void addFatigue(int amount) {
        currFatigue += amount;
    }

    public void addAction(IAction action) {
        actions.addLast(action);
    }

    /**
     * Determines whether or not the Actor can act.
     * @return 'true' if it can act.
     */
    public boolean notFatigued() {
        return currFatigue <= 0;
    }




    /*
        GETTERS/SETTERS
     */

    public Queue<IAction> getActionsQueue() {
        return actions;
    }

    public int getRange() {
        return range;
    }
    public void setRange(int range) {
        this.range = range;
    }

    public boolean isInvincible() {
        return invincible;
    }
    public void setInvincible(boolean invincible) {
        this.invincible = invincible;
    }

    public int getMaxHp() {
        return maxHp;
    }
    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getCurrHp() {
        return currHp;
    }
    public void setCurrHp(int currHp) {
        this.currHp = currHp;
    }

    public int getFatigueRegen() {
        return fatigueRegen;
    }
    public void setFatigueRegen(int fatigueRegen) {
        this.fatigueRegen = fatigueRegen;
    }

    public int getCurrFatigue() {
        return currFatigue;
    }
    public void setCurrFatigue(int currFatigue) {
        this.currFatigue = currFatigue;
    }

    public int getRangeOfSight() {
        return rangeOfSight;
    }
    public void setRangeOfSight(int rangeOfSight) {
        this.rangeOfSight = rangeOfSight;
    }

    public int getPriority() {
        return priority;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isSleeping() {
        return sleeping;
    }
    public void setSleeping(boolean sleeping) {
        this.sleeping = sleeping;
    }




    @Override
    public String toString() {
        return "Actor{" +
                "position= (" + getX() + "," + getY() + ") " +
                ", texture=" + getTexture().toString() +
                '}';
    }
}
