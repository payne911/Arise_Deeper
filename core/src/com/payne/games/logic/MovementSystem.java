package com.payne.games.logic;

import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.math.GridPoint2;
import com.payne.games.gameObjects.GameObject;
import com.payne.games.map.BaseMapLayer;
import com.payne.games.map.tiles.Tile;
import com.payne.games.pathfinding.MyIndexedGraph;


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
     * Uses the PathFinding to find a path to the input point from the current location of the input object.
     * If no path can be found (for example, trying to walk into a wall), nothing happens.
     *
     * @param object The GameObject that wants to move.
     * @param x destination's x-coordinate.
     * @param y destination's y-coordinate.
     */
    public void moveTo(GameObject object, int x, int y) {
        Tile from = level.getTile(object.getX(), object.getY());
        Tile to   = level.getTile(x, y);
        DefaultGraphPath<Tile> path = indexedGraph.getPathToMoveTo(from, to);

        for(Tile t: path.nodes) {
            GridPoint2 delta = new GridPoint2(t.getX() - object.getX(), t.getY() - object.getY());
            moveDelta(object, delta);
        }
    }

    public void moveDelta(GameObject object, GridPoint2 delta) {
        tryMove(delta, object);
    }


    public void moveLeft(GameObject object) {
        GridPoint2 delta = new GridPoint2(-1, 0);
        tryMove(delta, object);
    }

    public void moveRight(GameObject object) {
        GridPoint2 delta = new GridPoint2(1, 0);
        tryMove(delta, object);
    }

    public void moveUp(GameObject object) {
        GridPoint2 delta = new GridPoint2(0, 1);
        tryMove(delta, object);
    }

    public void moveDown(GameObject object) {
        GridPoint2 delta = new GridPoint2(0, -1);
        tryMove(delta, object);
    }

    private void tryMove(GridPoint2 delta, GameObject object) {
        Tile movingTo = level.getTile(object.getX() + delta.x, object.getY() + delta.y);
        interact(movingTo, object);
        tryToMoveTo(movingTo, object, delta);
    }

    private void tryToMoveTo(Tile movingTo, GameObject object, GridPoint2 delta) {
        if (movingTo.isAllowingMove()) {
            object.setY(object.getY() + delta.y);
            object.setX(object.getX() + delta.x);
        }
    }

    private void interact(Tile movingTo, GameObject object) { // todo: should this be in this class?
        if (movingTo.canInteract(object))
            movingTo.interact(object);
    }
}
