package com.payne.games.turns.actions;

import com.payne.games.gameObjects.actors.Actor;
import com.payne.games.map.tiles.Tile;
import com.payne.games.pathfinding.MyIndexedGraph;


public class MoveAction extends Action {
    private Tile next, from, to;
    private MyIndexedGraph graph;


    public MoveAction(MyIndexedGraph graph, Actor source, Tile from, Tile next, Tile to) {
        super(source);

        this.graph = graph;
        this.from  = from;
        this.next  = next;
        this.to    = to;
    }


    @Override
    public void executeAction() {

        // todo: detect if new enemies appeared in sight (abort)

        from.setAllowingMove(true);
        source.setX(next.getX());
        source.setY(next.getY());
        next.setAllowingMove(false);

        // todo: activate Traps if stepped on one (and don't issue another MoveAction)

        /* Keep assigning MoveActions until reaching destination. */
        Tile again = graph.extractFirstMove(next, to); // "next", at this point, is the current position of the actor
        if (again != null) {
            MoveAction moveAction = new MoveAction(graph, source, next, again, to);
            source.addAction(moveAction);
        }

    }

    @Override
    public int getFatigueCost() {
        return 50;
    }

    @Override
    public String toString() {
        return "MoveAction{" +
                "src=" + getSource() +
                ", movingTo=" + next +
                '}';
    }
}
