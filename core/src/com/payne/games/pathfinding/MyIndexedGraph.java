package com.payne.games.pathfinding;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultConnection;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.utils.Array;
import com.payne.games.map.BaseMapLayer;
import com.payne.games.map.tiles.Tile;


public class MyIndexedGraph implements IndexedGraph<Tile> {
    private int nodeCount = 0;
    private MyHeuristic heuristic = new MyHeuristic();


    public MyIndexedGraph(BaseMapLayer currLevel) {
        // todo: currently assumes the initial WalkableTiles set will never expand (e.g. digging a wall).
        for(Tile t: currLevel.getWalkableTiles()) {
            t.setGraphIndex(nodeCount++);
        }
    }


    /**
     * If a path is found, the very first node will be the tile the algorithm started from (where an Actor is).
     *
     * @param movingFrom Tile starting from.
     * @param movingTo Tile wished to finish at.
     * @return A sequence of node to move through. If no path exists, the sequence is empty (but not null).
     */
    public DefaultGraphPath getWholePathToMoveTo(Tile movingFrom, Tile movingTo) {
        DefaultGraphPath<Tile> outputGraphPath = new DefaultGraphPath<>();

        IndexedAStarPathFinder<Tile> pathFinder = new IndexedAStarPathFinder<>(this);
        pathFinder.searchNodePath(movingFrom, movingTo, heuristic, outputGraphPath);

        return outputGraphPath;
    }

    /**
     * Will return the very first Tile an Actor would need to move to in order to get to the desired end point.
     *
     * @param movingFrom Tile starting from.
     * @param movingTo Tile wished to finish at.
     * @return The first Tile required to be moved to. If no path existed, `null` is returned.
     */
    public Tile extractFirstMove(Tile movingFrom, Tile movingTo) {
        DefaultGraphPath<Tile> path = getWholePathToMoveTo(movingFrom, movingTo);
        return path.getCount() > 1 ? path.nodes.get(1) : null; // we output only if there is at least one move to be done
    }



    @Override
    public int getIndex(Tile node) {
        return node.getGraphIndex();
    }

    @Override
    public int getNodeCount() {
        return nodeCount;
    }

    @Override
    public Array<Connection<Tile>> getConnections(Tile fromNode) {
        Array<Connection<Tile>> edges = new Array<>();

        for(Tile tile : fromNode.getNeighbors()) {
            if(tile.isAllowingMove()) {
                DefaultConnection<Tile> edge = new DefaultConnection<>(fromNode, tile);
                edges.add(edge);
            }
        }

        return edges;
    }
}
