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
    private BaseMapLayer currLevel;
    private int nodeCount = 0;
    private MyHeuristic heuristic = new MyHeuristic();


    public MyIndexedGraph(BaseMapLayer currLevel) {
        this.currLevel = currLevel;

        // todo: currently assumes the initial WalkableTiles set will never expand (e.g. digging a wall).
        for(Tile t: currLevel.getWalkableTiles()) {
            t.setGraphIndex(nodeCount++);
        }

//        // Excluding edge because always Walls.
//        for(int i=1; i<currLevel.getMapHeight()-1; i++) {
//            for(int j=1; j<currLevel.getMapWidth()-1; j++) {
//                Tile currTile = currLevel.getTile(j, i);
//                if(currTile.isAllowingMove()) {
//                    currTile.setGraphIndex(nodeCount);
//                    nodeCount++;
//                }
//            }
//        }
    }


    public DefaultGraphPath getPathToMoveTo(Tile movingFrom, Tile movingTo) {
        DefaultGraphPath<Tile> outputGraphPath = new DefaultGraphPath<>();

        IndexedAStarPathFinder<Tile> pathFinder = new IndexedAStarPathFinder<>(this);
        pathFinder.searchNodePath(movingFrom, movingTo, heuristic, outputGraphPath);

        return outputGraphPath;
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
        Array<Tile> neighbors = currLevel.getWalkableNeighbors(fromNode.getX(), fromNode.getY());

        for(Tile tile : neighbors) {
            DefaultConnection<Tile> edge = new DefaultConnection<>(fromNode, tile);
            edges.add(edge);
        }

        return edges;
    }
}
