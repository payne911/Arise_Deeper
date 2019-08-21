package com.payne.games.turns.actions;

import com.payne.games.gameObjects.actors.Actor;
import com.payne.games.gameObjects.actors.Hero;
import com.payne.games.map.tiles.Tile;
import com.payne.games.pathfinding.MyIndexedGraph;


public class MoveAction extends Action {
    private Tile next, from, to;
    private MyIndexedGraph graph;


    /**
     * A MoveAction will do its best to get an Actor to move to its destination.
     *
     * @param graph Graph built for pathfinding.
     * @param source The Actor that wants to move.
     * @param from The Tile it is actually on.
     * @param next The next Tile it will move to in order to reach its destination.
     * @param to The final Tile, where the Actor actually wants to go.
     */
    public MoveAction(Actor source, MyIndexedGraph graph, Tile from, Tile next, Tile to) {
        super(source);

        this.graph = graph;
        this.from  = from;
        this.next  = next;
        this.to    = to;
    }


    @Override
    public boolean executeAction() {

        if(newActorInSight()) // todo:  something requires attention : abort automatic move (probably to place somewhere)
            return false;


        if(next.isAllowingMove()) { // make sure the pre-calculated path is still valid
            move();
            setUpNextMove();


        } else { // the pre-calculated path isn't valid anymore

            /* Rerun the pathfinding algorithm because the precalculated Tile is now occupied (does not allow movement). */
            next = graph.extractFirstMove(from, to);

            if (next != null) { // new path was found!
                move();
                setUpNextMove();

            } else { // no path exists anymore: the action is canceled, and the Actor has its turn back
                return false;
            }
        }

        return true;
    }


    private boolean newActorInSight() {
        return false; // todo: detect if new enemies appeared in sight
    }


    private void move() {
        from.setAllowingMove(true);
        source.setX(next.getX());
        source.setY(next.getY());
        next.setAllowingMove(false);
    }


    private void setUpNextMove() {

        // todo: activate Traps if stepped on one (and don't issue another MoveAction)

        /* Keep assigning MoveActions until reaching destination. */
        Tile again = graph.extractFirstMove(next, to); // "next", at this point, is the current position of the actor
        if (again != null) {
            MoveAction moveAction = new MoveAction(source, graph, next, again, to);
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
