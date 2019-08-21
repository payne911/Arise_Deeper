package com.payne.games.turns.actions;

import com.payne.games.gameObjects.actors.Actor;


public interface IAction {
    void execute();
    void executeAction();
    int getFatigueCost();
    Actor getSource();
}
