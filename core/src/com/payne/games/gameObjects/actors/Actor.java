package com.payne.games.gameObjects.actors;

import com.badlogic.gdx.utils.Queue;
import com.payne.games.actions.ActionController;
import com.payne.games.gameObjects.GameObject;
import com.payne.games.inventory.Inventory;
import com.payne.games.actions.Action;
import com.payne.games.rendering.animations.IAnimatedStates;


public abstract class Actor extends GameObject implements IAnimatedStates<ActorState> {
    protected Queue<Action> actions = new Queue<>(); // all the Actions the Actor wants to see executed
    private int priority = 2; // used by the MinHeap of the TurnManager to determine who goes first in case of equality

    // states
    private ActorState currentState;
    private int rangeOfSight = 6; // how far the Actor can see in a straight line (1 means only its own cell)
    private boolean invincible = false;
    private int maxHp;
    private int currHp;
    private int fatigueRegen;
    private int currFatigue = 0; // used by the TurnManager

    // equipment
    private Inventory inventory;
    private int range; // attacking range of the currently equipped primary weapon
    private int dmg = 30; // damage of the currently equipped primary weapon



    public Actor(ActionController actionController, int x, int y, int maxHp, int fatigueRegen, int range) {
        super(actionController, x, y);
        this.maxHp = maxHp;
        this.currHp = maxHp;
        this.fatigueRegen = fatigueRegen;
        this.range = range;
    }


    @Override
    public void setState(ActorState state) {
        if(currentState != state) {
            currentState = state;
            setAnimation(animationMap.get(currentState));
            resetDelta();

            notifyObservers();
        }
    }

    @Override
    public boolean canBeWalkedThrough() {
        return false; // by default, an Actor takes space on a tile
    }

    @Override
    public boolean canBeSeenThrough() {
        return false;
    }

    /**
     * Method used to deal damage to an Actor.
     *
     * @param attacker The Actor that is the source of the damage.
     * @param dmg The amount of damage dealt.
     * @return 'true' if the damage has killed the Actor receiving the damage.
     */
    public boolean takeHit(Actor attacker, int dmg) {
        if(!invincible)
            currHp -= dmg;
        if(isDead())
            die(attacker);
        return isDead();
    }

    /**
     * Should be called whenever an Actor dies.
     *
     * @param killer The Actor that was the source of the death.
     */
    public void die(Actor killer) {
        clearActionsQueue();
        setState(ActorState.DYING);
        // todo: clear all actions (of other Actors) that related to this now-dead Actor
    }

    public boolean isDead() {
        return currHp <= 0;
    }

    /**
     * Command Pattern implementation. The `TurnManager` ends up collecting the actions from all the Actors in the map.
     * This is where an Enemy's AI takes a decision, among other things.
     *
     * @return The Action this Actor wants to see executed. 'null' only if the engine is waiting for the player to act.
     */
    abstract public Action extractAction();


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

    public Action getNextAction() {
        if(isOccupied())
            return actions.removeFirst();
        else
            return extractAction();
    }

    public void regenFatigue() {
        currFatigue = Math.max(0, currFatigue - fatigueRegen);
    }

    public void addFatigue(float amount) {
        currFatigue += amount;
    }

    public void addAction(Action action) {
        if (action != null)
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

    public Queue<Action> getActionsQueue() {
        return actions;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
    public Inventory getInventory() {
        return inventory;
    }

    public int getRange() {
        return range;
    }
    public void setRange(int range) {
        this.range = range;
    }

    public int getDmg() {
        return dmg;
    }
    public void setDmg(int dmg) {
        this.dmg = dmg;
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

    /**
     * Range of sight includes the source's position. This means that if the value
     * is 1, that the source will only see the Tile it is standing on.
     *
     * @return Amount of tiles, in straight line, that the Actor can see.
     */
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
        return currentState == ActorState.SLEEPING;
    }
    public boolean isMoving() {
        return currentState == ActorState.MOVING;
    }


    @Override
    public String toString() {
        return "Actor{" +
                "position=(" + getX() + "," + getY() + ") " +
                ", texture=" + getTexture().toString() +
                ", currentState=" + currentState +
                '}';
    }
}
