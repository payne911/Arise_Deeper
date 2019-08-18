package com.payne.games.logic;

import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.math.GridPoint2;
import com.payne.games.gameObjects.Actor;
import com.payne.games.map.BaseMapLayer;
import com.payne.games.map.SecondaryMapLayer;
import com.payne.games.map.tiles.Tile;
import com.payne.games.pathfinding.MyIndexedGraph;
import com.payne.games.turns.actions.AttackAction;
import com.payne.games.turns.actions.MoveAction;


public class ActionSystem {
    private GameLogic gLogic;
    private BaseMapLayer level;
    private SecondaryMapLayer secondaryMapLayer;
    private MyIndexedGraph indexedGraph;


    public ActionSystem(GameLogic gameLogic) {
        this.gLogic = gameLogic;
    }


    public void setUpIndexedGraph(BaseMapLayer currLevel, SecondaryMapLayer secondaryMapLayer) {
        level = currLevel;
        indexedGraph = new MyIndexedGraph(level);
        this.secondaryMapLayer = secondaryMapLayer;
    }


    /**
     * Assigns the proper Action to the player according to the tap's coordinate.
     *
     * @param player the Hero.
     * @param x x-coordinate of the player's tap.
     * @param y y-coordinate of the player's tap.
     */
    public void checkTap(Actor player, int x, int y) {
        if (!hasInterruptedActions(player)) {
            Actor atPos = secondaryMapLayer.findActorAt(x, y);
            boolean withinRange = level.distanceBetweenTiles(new GridPoint2(player.getX(), player.getY()), new GridPoint2(x, y)) < player.getRange();
            System.out.println(atPos
                    + " | distance: " + level.distanceBetweenTiles(new GridPoint2(player.getX(), player.getY()), new GridPoint2(x, y))
                    + " | playerRange: " + player.getRange()
                    + " | withinRange? " + withinRange);
            if (atPos == null || !withinRange) { // no Actors where clicked, or are within range : just move there
                moveTo(player, x, y);
            } else {
                player.addAction(new AttackAction(player, atPos, 30));
            }
        }
    }


    /**
     * Uses the PathFinding to find a path to the input point from the current location of the input actor.
     * If no path can be found (for example, trying to walk into a wall), nothing happens.
     *
     * @param actor The GameObject that wants to move.
     * @param x destination's x-coordinate.
     * @param y destination's y-coordinate.
     */
    public void moveTo(Actor actor, int x, int y) {

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

    /**
     * If the player had other actions already in queue, those are removed.
     *
     * @param player the Hero of the player.
     * @return 'false' only if there were no actions in the Queue.
     */
    private boolean hasInterruptedActions(Actor player) {
        if(doingSomethingElse(player)) {
            player.getActionsQueue().clear(); // this means we interrupt current actions
            return true;
        }
        return false;
    }

    private boolean doingSomethingElse(Actor actor) {
        return actor.getActionsQueue().size > 0;
    }
}
