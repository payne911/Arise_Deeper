package com.payne.games.map;

import com.payne.games.map.tiles.*;


/**
 * The (mostly) immutable layer of the map. Things that don't
 */
public class BaseMapLayer {

    /**
     * The representations of the 2D arrays are [rows][columns], aka [y][x].
     * (0,0) is at the bottom-left, when looking at a rendered map on the screen.
     */
    private int[][] logicalMap; // todo: use "byte[][]" instead for smaller memory usage?
    private Tile[][] graphicalMap;
    // todo: integrate the "Layers"


    public BaseMapLayer(int mapWidth, int mapHeight) {
        logicalMap = new int[mapHeight][mapWidth];
        graphicalMap = new Tile[mapHeight][mapWidth];
    }

    public int getMapWidth() {
        return logicalMap[0].length;
    }

    public int getMapHeight() {
        return logicalMap.length;
    }

    public int[][] getLogicalMap() {
        return logicalMap;
    }

    public Tile[][] getGraphicalMap() {
        return graphicalMap;
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
                logicalMap[y-1][x-1],
                logicalMap[y-1][x],
                logicalMap[y-1][x+1],
                logicalMap[y][x-1],
                logicalMap[y][x],
                logicalMap[y][x+1],
                logicalMap[y+1][x-1],
                logicalMap[y+1][x],
                logicalMap[y+1][x+1]
        };
    }

    /**
     * Calculates the percentage of tiles that are floors.
     *
     * @return The percentage, ranging from 0 to 1.
     */
    public float floorPercentage() {
        int totalFloors = 0;
        for (int i = 0; i < getMapHeight(); i++) {
            for (int j = 0; j < getMapWidth(); j++) {
                if (this.logicalMap[i][j] == 1) totalFloors++; // a floor was encountered
            }
        }
        return ((float)totalFloors)/(getMapHeight()*getMapWidth());
    }
}
