package com.payne.games.turns.actions;

import com.payne.games.gameObjects.actors.Actor;
import com.payne.games.map.tiles.Tile;
import com.payne.games.pathfinding.MyIndexedGraph;


/**
 * A MoveAction, but with the goal to interact with a specific GameObject.
 */
public class InteractiveMoveAction extends Action {
    private Actor recipient;
    private Tile next, from, to;
    private MyIndexedGraph graph;


    public InteractiveMoveAction(Actor source, Actor recipient, MyIndexedGraph graph, Tile from, Tile next, Tile to) {
        super(source);
        this.recipient = recipient;

        this.graph = graph;
        this.from  = from;
        this.next  = next;
        this.to    = to;
    }


    @Override
    public boolean executeAction() {
        // todo
        return true;
    }

    @Override
    public int getFatigueCost() {
        return 50;
    }
}
