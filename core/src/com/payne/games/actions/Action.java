package com.payne.games.actions;

import com.payne.games.gameObjects.actors.Actor;
import com.payne.games.logic.GameLogic;


/**
 * Actions are encapsulated behaviors originating from an Actor, destined to be executed at possibly a later time.<br>
 * Each Actor has a Queue of Actions, and the TurnManager picks the first one to execute it when the Actor's turn has come.
 */
public abstract class Action {
    protected Actor source;
    protected ActionController controller; // allows the Actions to manipulate the MapLayers


    public Action(Actor source) {
        this.source = source;
        controller = source.getController();
    }


    /**
     * Takes care of executing the action and adding the proper amount of fatigue.
     * todo: eventually, the return type might add a third option: "isDeadMeanwhile", which cancels the Action without giving back control?
     *
     * @return Should the action be canceled?<br>
     * 'false' if the action should consume the Actor's turn.<br>
     * 'true' if the turn should be given back to the Actor.
     */
    public boolean execute() {
        boolean canceled = !executeAction();
        if(!canceled)
            source.addFatigue(getFatigueCostMultiplier() * GameLogic.TURN_GENERAL_COST);
        return canceled;
    }

    /**
     * @return The multiplier applied to the "turn cost" of using this Action.
     */
    abstract public float getFatigueCostMultiplier();

    /**
     * When an interaction CAN happen between two objects, this action determines what happens.
     * Thus, there should be no checking on things such as whether or not the Source and the Target are within range.
     *
     * @return Has the action achieved its purpose?<br>
     * 'true' if the action should consume the Actor's turn.<br>
     * 'false' if the turn should be given back to the Actor.
     */
    abstract public boolean executeAction();

    /**
     * @return The Actor initiating the Action.
     */
    public Actor getSource(){
        return source;
    }
}
