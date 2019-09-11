package com.payne.games.map.pathfinding;

import com.badlogic.gdx.ai.pfa.Heuristic;
import com.payne.games.map.tiles.Tile;


public class MyHeuristic implements Heuristic<Tile> {

    @Override
    public float estimate(Tile node, Tile endNode) {
        // todo: if change from 4-direction control to 8-direction (diagonal moves allowed), change to euclidian?
        return (Math.abs(endNode.getX() - node.getX()) + Math.abs(endNode.getY() - node.getY()));
    }
}
