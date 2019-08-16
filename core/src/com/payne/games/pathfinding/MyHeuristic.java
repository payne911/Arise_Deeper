package com.payne.games.pathfinding;

import com.badlogic.gdx.ai.pfa.Heuristic;
import com.payne.games.map.tiles.Tile;


public class MyHeuristic implements Heuristic<Tile> {

    @Override
    public float estimate(Tile node, Tile endNode) {
        return (Math.abs(endNode.getX() - node.getX()) + Math.abs(endNode.getY() - node.getY()));
    }
}
