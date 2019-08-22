package com.payne.games.turns.actions;

import com.payne.games.gameObjects.actors.Actor;


public interface IAction {

    /**
     * Takes care of executing the action and adding the proper amount of fatigue.
     * todo: eventually, the return type might add a third option: "isDeadMeanwhile", which cancels the Action without giving back control?
     *
     * @return Should the action be canceled?<br>
     * 'false' if the action should consume the Actor's turn.<br>
     * 'true' if the turn should be given back to the Actor.
     */
    boolean execute();

    /**
     * @return The added cost of using this Action.
     */
    float getFatigueCostMultiplier();

    /**
     * @return Has the action achieved its purpose?<br>
     * 'true' if the action should consume the Actor's turn.<br>
     * 'false' if the turn should be given back to the Actor.
     */
    boolean executeAction();

    /**
     * @return The Actor initiating the Action.
     */
    Actor getSource();
}
