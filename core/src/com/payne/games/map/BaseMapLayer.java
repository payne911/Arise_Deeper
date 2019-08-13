package com.payne.games.map;

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
     * To obtain a 1D array of the surrounding tiles of a certain position on the BaseMapLayer.
     *
     * For example, with this logical_map array:
     * [[1, 3, 1, 4, 5],
     *  [1, 0, 2, 2, 4],
     *  [2, 5, 1, 1, 1],
     *  [4, 5, 0, 0, 1],
     *  [3, 1, 1, 1, 5]]
     *
     *  Here, "1" is the tileType of the (0,0) coordinate.
     *
     *  Polling the (2,1) coordinate returns the following result:
     *  [3, 1, 4, 0, 2, 2, 5, 1, 1]
     *
     *  Which came from extracting the following sub-array, and flattening it:
     *  [[3, 1, 4],
     *   [0, 2, 2],
     *   [5, 1, 1]]
     *
     *   todo: edge-cases behavior (use "-1")
     *
     * @param x x-coordinate input.
     * @param y y-coordinate input.
     * @return A flattened array of the tileTypes surrounding the input position.
     */
    public int[] pollSurrounding(int x, int y) {
        return new int[]{
//                graphicalMap[y-1][x-1],
//                graphicalMap[y-1][x],
//                graphicalMap[y-1][x+1],
//                graphicalMap[y][x-1],
//                graphicalMap[y][x],
//                graphicalMap[y][x+1],
//                graphicalMap[y+1][x-1],
//                graphicalMap[y+1][x],
//                graphicalMap[y+1][x+1]
        };
    }
}
