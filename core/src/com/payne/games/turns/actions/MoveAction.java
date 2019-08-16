package com.payne.games.turns.actions;

import com.badlogic.gdx.math.GridPoint2;
import com.payne.games.gameObjects.GameObject;


public class MoveAction implements IAction {
    private GameObject recipient;
    private GridPoint2 delta;


    public MoveAction(GameObject recipient, GridPoint2 delta) {
        this.recipient = recipient;
        this.delta = delta;
    }


    @Override
    public void execute() {
        recipient.setY(recipient.getY() + delta.y);
        recipient.setX(recipient.getX() + delta.x);
    }

    @Override
    public String toString() {
        return "MoveAction{" +
                "recipient=" + recipient +
                ", delta=" + delta +
                '}';
    }
}
