package com.payne.games.turns.actions;

import com.payne.games.gameObjects.Actor;


public interface IAction {
    void execute();
    void executeAction();
    int getFatigueCost();
    Actor getSource();
}
