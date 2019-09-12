package com.payne.games.actions;

import com.payne.games.gameObjects.GameObject;
import com.payne.games.gameObjects.actors.Actor;
import com.payne.games.gameObjects.actors.Hero;
import com.payne.games.gameObjects.statics.Static;
import com.payne.games.logic.Controller;
import com.payne.games.map.BaseMapLayer;
import com.payne.games.map.SecondaryMapLayer;
import com.payne.games.map.tiles.Tile;
import com.payne.games.map.pathfinding.MyIndexedGraph;


/**
 * Acts as a general controller for the Actions and GameObjects (this class is accessible in both of those).
 */
public class ActionController {
    public ActionIssuer actionIssuer = new ActionIssuer();
    public BaseMapLayer baseMapLayer;
    public SecondaryMapLayer secondaryMapLayer;
    private Controller controller;
    private MyIndexedGraph indexedGraph;


    public ActionController(Controller controller) {
        this.controller = controller;
    }


    public void setUpIndexedGraph(BaseMapLayer currLevel) {
        baseMapLayer = currLevel;
        indexedGraph = new MyIndexedGraph(baseMapLayer);
    }

    public void setSecondaryMapLayer(SecondaryMapLayer secondaryMapLayer) {
        this.secondaryMapLayer = secondaryMapLayer;
    }

    /**
     * Used to remove an Actor AND reset the Tile it was standing on so that it is walkable again.
     *
     * @param actor The Actor to be removed.
     */
    public void removeActor(Actor actor) {
        secondaryMapLayer.removeFromActorLayer(actor);
        baseMapLayer.getTile(actor.getX(), actor.getY()).setAllowingMove(true); // todo: unless the Actor is flying?
    }

    /**
     * Removes the specified object. If there was no Actor where it was, the Tile becomes walkable.
     *
     * @param object The object to be removed.
     */
    public void removeStatic(Static object) {
        secondaryMapLayer.removeFromStaticLayer(object);
        Actor actorAt = secondaryMapLayer.findActorAt(object.getX(), object.getY());
        if(actorAt == null)
            baseMapLayer.getTile(object.getX(), object.getY()).setAllowingMove(true);
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
     * @param player The Hero.
     * @param x x-coordinate of the player's tap.
     * @param y y-coordinate of the player's tap.
     */
    public void checkTap(Hero player, int x, int y) {
        if (alreadyHadActionsInQueue(player)) // hero already had other actions : cancel them and abort
            return;

        if (clickedSelf(player, x, y)) // the player clicked his hero
            return;

        if (!baseMapLayer.tileWasExplored(x,y)) // tile wasn't explored : abort
            return;

        if (baseMapLayer.tileIsInSight(x,y)) {

            if (findGameObject(player, x, y)) // finding if a GameObject was clicked for an interaction
                return;

            moveTo(player, x, y); // clicked on a tile with no GameObjects on it : just walk there

        } else { // clicked on an explored (but not in sight) tile : just walk there
            moveTo(player, x, y);
        }
    }

    /**
     * Determines what to do when the player clicked his own Hero.
     * If a Static object is on the same Tile, the Hero will try to interact with it.
     * Else, a turn is skipped. todo: maybe should open the Backpack? Skipping turn would require DOUBLE-click.
     *
     * @param player The Hero.
     * @param x the x-coordinate of the click.
     * @param y the y-coordinate of the click.
     * @return 'true' only if the player clicked his Hero.
     */
    private boolean clickedSelf(Hero player, int x, int y) {
        if (player.getX() == x && player.getY() == y) {

            Static staticAt = secondaryMapLayer.findStaticAt(x, y);
            if(staticAt == null) { // nothing to interact with: skip a turn
                actionIssuer.skipTurn(player);
            } else {
                staticAt.tryInteractionFrom(player);
            }

            return true;
        }
        return false;
    }

    /**
     * First, the Actor is checked because "attacking" takes priority over "picking up" when targeting a Tile which
     * contains both an Actor and a Static object.
     *
     * @param player The player's Hero.
     * @param x x-coordinate of the player's tap.
     * @param y y-coordinate of the player's tap.
     * @return 'true' if the tap was handled.
     */
    private boolean findGameObject(Actor player, int x, int y) {

        /* Actors take priority over Static objects. */
        GameObject objAt = secondaryMapLayer.findActorAt(x, y);
        if(objAt == null)
            objAt = secondaryMapLayer.findStaticAt(x, y);
        if(objAt == null)
            return false; // didn't find a GameObject to interact with : keep handling the tap


        boolean successfulInteraction = objAt.tryInteractionFrom(player);
        if (successfulInteraction)
            return true; // an interaction happened: we're done handling the tap

        Tile from = baseMapLayer.getTile(player.getX(), player.getY());
        Tile to   = baseMapLayer.getTile(x, y);
        Tile next = findNextMove(from, to);
        if(next != null) { // there is a path that leads to the GameObject
            actionIssuer.interactiveMove(player, objAt, from, next, to);
            return true; // we're done handling the tap
        }

        return true; // found a GameObject, but nothing could be done : we're done handling the tap
    }


    /**
     * Non-blocking graph exploration with the pathfinding.
     * Adapts the "to" Tile in case an Actor might be standing there.
     *
     * @param from the initial Tile.
     * @param to the desired destination Tile.
     * @return 'null' only if no path that leads to the desired destination exists OR if already standing at the destination.
     */
    public Tile findNextMove(Tile from, Tile to) {
        Tile next;
        if(to.isAllowingMove()) {
            next = findNextTile(from, to);
        } else { // an Actor is currently standing on the 'to' Tile
            to.setAllowingMove(true);
            next = findNextTile(from, to);
            to.setAllowingMove(false);
        }
        return next;
    }


    /**
     * Uses the PathFinding to find a path to the input point from the current location of the input actor.
     *
     * @param from Initial point.
     * @param to Desired destination.
     * @return 'null' only if no path can be found. Otherwise, returns the next Tile to move to.
     */
    public Tile findNextTile(Tile from, Tile to) {
        return indexedGraph.extractFirstMove(from, to);
    }

    /**
     * If no path can be found (for example, trying to walk into a wall), nothing happens.
     *
     * @param actor The Actor that wants to move.
     * @param x destination's x-coordinate.
     * @param y destination's y-coordinate.
     */
    public void moveTo(Actor actor, int x, int y) {

        Tile from = baseMapLayer.getTile(actor.getX(), actor.getY());
        Tile to   = baseMapLayer.getTile(x, y);

        /* Assigning MoveActions accordingly. */
        Tile next = findNextTile(from, to);
        if(next != null)
            actionIssuer.move(actor, from, next, to);
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
     *
     * @param actor The Actor that will move.
     */
    public void takeOneRandomStep(Actor actor) {
        Tile randomStep = baseMapLayer.getWalkableNeighbors(actor.getX(), actor.getY()).random();
        moveTo(actor, randomStep.getX(), randomStep.getY());
    }


    /**
     * If the player had other actions already in queue, those are removed.
     *
     * @param player The Hero of the player.
     * @return 'false' only if there were no actions in the Queue.
     */
    private boolean alreadyHadActionsInQueue(Hero player) {
        if(player.isOccupied()) {
            player.clearActionsQueue(); // this means we interrupt current actions
            return true;
        }
        return false;
    }

    public Controller getMainController() {
        return controller;
    }
}
