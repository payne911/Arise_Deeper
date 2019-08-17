package com.payne.games.logic;

import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.math.GridPoint2;
import com.payne.games.gameObjects.Actor;
import com.payne.games.map.BaseMapLayer;
import com.payne.games.map.tiles.Tile;
import com.payne.games.pathfinding.MyIndexedGraph;
import com.payne.games.turns.actions.MoveAction;


public class MovementSystem {
    private GameLogic gLogic;
    private BaseMapLayer level;
    private MyIndexedGraph indexedGraph;


    public MovementSystem(GameLogic gameLogic) {
        this.gLogic = gameLogic;
    }

    public void setUpIndexedGraph(BaseMapLayer currLevel) {
        level = currLevel;
        indexedGraph = new MyIndexedGraph(level);
    }


    /**
     * Uses the PathFinding to find a path to the input point from the current location of the input actor.
     * If no path can be found (for example, trying to walk into a wall), nothing happens.
     *
     * If the Actor already had Actions in Queue (such as already being moving), they are canceled.
     *
     * @param actor The GameObject that wants to move.
     * @param x destination's x-coordinate.
     * @param y destination's y-coordinate.
     */
    public void moveTo(Actor actor, int x, int y) {
        if(doingSomethingElse(actor)) {
            actor.getActionsQueue().clear(); // this means we interrupt current actions
            return;
        }

        Tile from = level.getTile(actor.getX(), actor.getY());
        Tile to   = level.getTile(x, y);
        DefaultGraphPath<Tile> path = indexedGraph.getPathToMoveTo(from, to); // find path

        /* Assigning MoveActions accordingly. */
        for(int i=0; i<path.getCount()-1; i++) { // takes care of not doing anything if `path` is empty
            GridPoint2 delta = deltaOfTiles(path.nodes.get(i), path.nodes.get(i+1));
            MoveAction moveAction = new MoveAction(actor, delta);
            actor.addAction(moveAction); // todo: the last MoveAction should also trigger a `Tile.interact()`
        }
    }

    private GridPoint2 deltaOfTiles(Tile first, Tile second) {
        return new GridPoint2(second.getX() - first.getX(), second.getY() - first.getY());
    }

    private boolean doingSomethingElse(Actor actor) {
        return actor.getActionsQueue().size > 0;
    }
}
