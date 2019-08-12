package com.payne.games.map;

import com.payne.games.map.tiles.*;


public class LevelMap {
    private int mapWidth, mapHeight;

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

    public void setLogical_map(int[][] logical_map) {
        this.logical_map = logical_map;
    }

    public void setLogicalTile(int type, int x, int y) {
        this.logical_map[y][x] = type;

        Tile graphicalTile;
        switch (type) {
            case 0:
                graphicalTile = new Floor(x,y);
                break;
            case 1:
                graphicalTile = new Floor(x,y);
                break;
            default:
                graphicalTile = new Empty(x,y);
                break;
        }
        this.graphical_map[y][x] = graphicalTile;
    }

    public Tile[][] getGraphical_map() {
        return graphical_map;
    }

    public void setGraphical_map(Tile[][] graphical_map) {
        this.graphical_map = graphical_map;
    }

    /**
     * To obtain a 1D array of the surrounding tiles of a certain position on the LevelMap.
     * The (0,0) position is considered
     *
     * For example, with this logical_map array:
     * [[1, 3, 1, 4, 5],
     *  [1, 0, 2, 2, 4],
     *  [2, 3, 3, 4, 1],
     *  [4, 5, 0, 0, 1],
     *  [3, 1, 1, 1, 5]]
     *
     *
     *
     * @param x
     * @param y
     * @return
     */
    public int[] pollSurrounding(int x, int y) {
        return new int[]{};
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
