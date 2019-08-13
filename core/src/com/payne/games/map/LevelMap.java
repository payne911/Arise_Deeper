package com.payne.games.map;

import com.payne.games.map.tiles.*;


public class LevelMap {
    private int mapWidth, mapHeight;

    /**
     * The representations of the 2D arrays are [rows][columns], aka [y][x].
     * (0,0) is at the bottom-left, when looking at a rendered map on the screen.
     */
    private int[][] logical_map; // todo: use "byte[][]" instead for smaller memory usage?
    private Tile[][] graphical_map; // todo: remove from here? (LevelMap should be graphic-agnostic)
    // todo: integrate the "Layers"


    public LevelMap(int mapWidth, int mapHeight) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;

        logical_map = new int[mapHeight][mapWidth];
        graphical_map = new Tile[mapHeight][mapWidth];
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public int[][] getLogical_map() {
        return logical_map;
    }

    public Tile[][] getGraphical_map() {
        return graphical_map;
    }

    /**
     * To obtain a 1D array of the surrounding tiles of a certain position on the LevelMap.
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
                logical_map[y-1][x-1],
                logical_map[y-1][x],
                logical_map[y-1][x+1],
                logical_map[y][x-1],
                logical_map[y][x],
                logical_map[y][x+1],
                logical_map[y+1][x-1],
                logical_map[y+1][x],
                logical_map[y+1][x+1]
        };
    }

    /**
     * Calculates the percentage of tiles that are floors.
     *
     * @return The percentage, ranging from 0 to 1.
     */
    public float floorPercentage() {
        int total_floors = 0;
        for (int i = 0; i < mapHeight; i++) {
            for (int j = 0; j < mapWidth; j++) {
                if (this.logical_map[i][j] == 1) total_floors++; // a floor was encountered
            }
        }
        return ((float)total_floors)/(mapHeight*mapWidth);
    }
}
