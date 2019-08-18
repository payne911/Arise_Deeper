package com.payne.games.turns.actions;

import com.badlogic.gdx.math.GridPoint2;
import com.payne.games.gameObjects.Actor;


public class MoveAction extends Action {
    private Actor recipient;
    private GridPoint2 delta;


    public MoveAction(Actor recipient, GridPoint2 delta) {
        super(recipient);

        this.recipient = recipient;
        this.delta = delta;
    }


    @Override
    public void executeAction() {
        recipient.setY(recipient.getY() + delta.y);
        recipient.setX(recipient.getX() + delta.x);
    }

    @Override
    public int getFatigueCost() {
        return 50;
    }

    @Override
    public String toString() {
        return "MoveAction{" +
                "recipient=" + recipient +
                ", delta=" + delta +
                '}';
    }
}
