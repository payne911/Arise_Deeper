package com.payne.games.map;

import com.badlogic.gdx.utils.Array;
import com.payne.games.map.tiles.Tile;


/**
 * The (mostly) immutable layer of the map. Things that don't
 */
public class BaseMapLayer {

    /**
     * The representations of the 2D arrays are [rows][columns], aka [y][x].
     * (0,0) is at the bottom-left, when looking at a rendered map on the screen.
     */
    private Tile[][] graphicalMap;


    public BaseMapLayer(int mapWidth, int mapHeight) {
        graphicalMap = new Tile[mapHeight][mapWidth];
    }

    public int getMapWidth() {
        return graphicalMap[0].length;
    }

    public int getMapHeight() {
        return graphicalMap.length;
    }

    public Tile[][] getGraphicalMap() {
        return graphicalMap;
    }

    /**
     * Basically just a Setter for the GraphicalMap to instanciate a new Tile at a certain coordinate.
     *
     * @param x x-coordinate input.
     * @param y y-coordinate input.
     * @param newTile the Tile instance that will replace the old one.
     */
    public void setTile(int x, int y, Tile newTile) {
        graphicalMap[y][x] = newTile;
    }

    public Tile getTile(int x, int y) {
        return graphicalMap[y][x];
    }

    /**
     * Returns the list of tiles that ALLOW_MOVEMENT surrounding the input coordinate.
     * Used to build a graph for the pathfinding system.
     *
     * @param x x-coordinate of the Tile for which we want to list the neighbors.
     * @param y y-coordinate of the Tile for which we want to list the neighbors.
     * @return an Array of Tile that are the Tiles that allow movement from the input Tile.
     */
    public Array<Tile> getNeighbors(int x, int y) {
        Array<Tile> neighbors = new Array<>();

        Array<Tile> tmp = new Array<>();
        tmp.addAll(graphicalMap[y][x-1],
                graphicalMap[y][x+1],
                graphicalMap[y+1][x],
                graphicalMap[y-1][x]);
        for(Tile t : tmp) {
            if(t.isAllowingMove())
                neighbors.add(t);
        }

        return neighbors;
    }
}
