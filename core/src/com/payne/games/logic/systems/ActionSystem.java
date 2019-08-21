package com.payne.games.logic.systems;

import com.badlogic.gdx.math.GridPoint2;
import com.payne.games.gameObjects.actors.Actor;
import com.payne.games.gameObjects.actors.Hero;
import com.payne.games.logic.Utils;
import com.payne.games.map.BaseMapLayer;
import com.payne.games.map.SecondaryMapLayer;
import com.payne.games.map.tiles.Tile;
import com.payne.games.pathfinding.MyIndexedGraph;
import com.payne.games.turns.actions.AttackAction;
import com.payne.games.turns.actions.MoveAction;


public class ActionSystem {
    private BaseMapLayer baseMapLayer;
    private SecondaryMapLayer secondaryMapLayer;
    private MyIndexedGraph indexedGraph;


    public ActionSystem() {
    }


    public void setUpIndexedGraph(BaseMapLayer currLevel, SecondaryMapLayer secondaryMapLayer) {
        baseMapLayer = currLevel;
        indexedGraph = new MyIndexedGraph(baseMapLayer);
        this.secondaryMapLayer = secondaryMapLayer;
    }


    /**
     * Assigns the proper Action to the player according to the tap's coordinate.<br>
     * If Actions were already in the process, they are canceled.<br>
     * If the tap happened outside of the explored regions, nothing happens.<br>
     * If the tap happened on an Actor in sight, the hero will try to go interact with the Actor.<br>
     * If the tap happened on an Item in sight, the hero will try to go pick it up.<br>
     * If the tap happened on a movable square, the hero will try to move there.<br>
     * If the tap happened on anything else, nothing happens.
     *
     * @param player the Hero.
     * @param x x-coordinate of the player's tap.
     * @param y y-coordinate of the player's tap.
     */
    public void checkTap(Hero player, int x, int y) {
        if (!hasInterruptedActions(player)) { // hero already had other actions: cancel them

            if (!baseMapLayer.tileWasExplored(x,y)) // tile wasn't explored: abort
                return;

            if (baseMapLayer.tileIsInSight(x,y)) {

                if (findGameObject(player, x, y)) // finding if a GameObject might be in range for interaction
                    return;

                moveTo(player, x, y); // clicked on a tile with no GameObjects on it : just walk there

            } else { // clicked on an explored (but not in sight) tile : just walk there
                moveTo(player, x, y);
            }
        }
    }

    /**
     * First, the Actor is checked because attacking takes priority over picking up when targeting a Tile which
     * contains both an Actor and a Static object.
     *
     * @param player The player's Hero.
     * @param x x-coordinate of the player's tap.
     * @param y y-coordinate of the player's tap.
     * @return 'true' if the tap was handled.
     */
    private boolean findGameObject(Actor player, int x, int y) {
        if (findActor(player, x, y)) // finding if an Actor might be in range
            return true;

        return findStatic(player, x, y); // trying to interact with a static object
    }

    private boolean findStatic(Actor player, int x, int y) {
        // todo
        return false;
    }


    private boolean findActor(Actor player, int x, int y) {
        Actor actorAt = secondaryMapLayer.findActorAt(x, y);
        if (actorAt != null) {
            boolean withinRange = Utils.straightDistanceBetweenMiddleOfTiles(new GridPoint2(player.getX(), player.getY()), new GridPoint2(x, y)) < player.getRange();
            if (!withinRange) { // Actor not in range: just move over there and try to interact
                moveTo(player, x, y); // todo: should be INTERACTIVE move
            } else { // Actor is in range: attack!
                player.addAction(new AttackAction(player, actorAt, 30));
            }

            return true; // found an actor : we're done handling the tap
        }
        return false; // didn't find an actor : keep handling the tap
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

        Tile from = baseMapLayer.getTile(actor.getX(), actor.getY());
        Tile to   = baseMapLayer.getTile(x, y);

        /* Assigning MoveActions accordingly. */
        Tile next = indexedGraph.extractFirstMove(from, to);
        if(next != null) {
            MoveAction moveAction = new MoveAction(actor, indexedGraph, from, next, to);
            actor.addAction(moveAction);
        }
    }

    /**
     * Selects a random walkable tile in the level, and then issues a queue of MoveActions to get the actor to move there.
     *
     * @param actor The actor that will move to a random point.
     */
    public void moveToRandomPoint(Actor actor) {
        Tile randomTile = baseMapLayer.getWalkableTiles().random();
        moveTo(actor, randomTile.getX(), randomTile.getY());
    }

    /**
     * Gets the Actor to take a step in a random (walkable) direction.
     * @param actor the Actor that will move.
     */
    public void takeOneRandomStep(Actor actor) {
        Tile randomStep = baseMapLayer.getWalkableNeighbors(actor.getX(), actor.getY()).random();
        moveTo(actor, randomStep.getX(), randomStep.getY());
    }


    /**
     * If the player had other actions already in queue, those are removed.
     *
     * @param player the Hero of the player.
     * @return 'false' only if there were no actions in the Queue.
     */
    private boolean hasInterruptedActions(Hero player) {
        if(player.isOccupied()) {
            player.clearActionsQueue(); // this means we interrupt current actions
            return true;
        }
        return false;
    }
}
