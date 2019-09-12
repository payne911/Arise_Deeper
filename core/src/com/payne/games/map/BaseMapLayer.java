package com.payne.games.map;

import com.badlogic.gdx.utils.Array;
import com.payne.games.map.tiles.Tile;

import java.util.HashSet;


/**
 * The (mostly) immutable layer of the map.<br>
 * Corresponds to a 2D array of all the Tiles constituting a level.
 */
public class BaseMapLayer {

    /**
     * The representations of the 2D arrays are [rows][columns], aka [y][x].
     * (0,0) is at the bottom-left, when looking at a rendered map on the screen.
     */
    private Tile[][] graphicalMap;
    private Array<Tile> walkableTiles = new Array<>();


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
    public Array<Tile> getWalkableTiles() {
        return walkableTiles;
    }


    /**
     * To determine if the Tile at the input position was ever explored by the player.
     *
     * @param x x tile-coordinate.
     * @param y y tile-coordinate.
     * @return 'true' if the Tile has been seen at least once by the player's hero.
     */
    public boolean tileWasExplored(int x, int y) {
        return getTile(x,y).isExplored();
    }

    /**
     * To determine if the Tile at the input position is currently in sight of the player's hero.
     *
     * @param x x tile-coordinate.
     * @param y y tile-coordinate.
     * @return 'true' if the Tile is currently seen by the hero.
     */
    public boolean tileIsInSight(int x, int y) {
        return getTile(x,y).isInSight();
    }

    /**
     * Basically just a Setter for the GraphicalMap to instantiate a new Tile at a certain coordinate.
     *
     * @param x x-coordinate input.
     * @param y y-coordinate input.
     * @param newTile the Tile instance that will replace the old one.
     */
    public void setTile(int x, int y, Tile newTile) {
        graphicalMap[y][x] = newTile;
    }

    /**
     * Returns the Tile at the desired coordinate.
     * If the coordinate falls outside of the map itself, one of the "edge-tiles" (always a Wall) will be returned.
     *
     * @param x x-coordinate.
     * @param y y-coordinate.
     * @return the Tile at the (x,y) coordinate.
     */
    public Tile getTile(int x, int y) {
        if(x < 0) x = 0;
        if(y < 0) y = 0;
        if(x >= getMapWidth()) x = getMapWidth()-1;
        if(y >= getMapHeight()) y = getMapHeight()-1;
        return graphicalMap[y][x];
    }


    public Tile getNorth(int x, int y) {
        return getTile(x, y+1);
    }
    public Tile getSouth(int x, int y) {
        return getTile(x, y-1);
    }
    public Tile getEast(int x, int y) {
        return getTile(x+1, y);
    }
    public Tile getWest(int x, int y) {
        return getTile(x-1, y);
    }
    public Tile getNorth(Tile t) {
        return getNorth(t.getX(), t.getY());
    }
    public Tile getSouth(Tile t) {
        return getSouth(t.getX(), t.getY());
    }
    public Tile getEast(Tile t) {
        return getEast(t.getX(), t.getY());
    }
    public Tile getWest(Tile t) {
        return getWest(t.getX(), t.getY());
    }


    /**
     * Returns the list of tiles that ALLOW_MOVEMENT surrounding the input coordinate.
     * Used to build a graph for the pathfinding system.
     *
     * @param x x-coordinate of the Tile for which we want to list the neighbors.
     * @param y y-coordinate of the Tile for which we want to list the neighbors.
     * @return An Array of Tile that are the Tiles that allow movement from the input Tile. Does not include the input Tile.
     */
    public Array<Tile> getWalkableNeighbors(int x, int y) {
        Array<Tile> neighbors = new Array<>();

        for (Tile t : getTile(x,y).getNeighbors()) {
            if (t.isAllowingMove())
                neighbors.add(t);
        }

        return neighbors;
    }


    /**
     * Returns all the Tiles surrounding the (x,y) input coordinate, within the specified range.
     * If the range goes outside of the map, those non-existent Tiles are not added (duh!).
     *
     * @param x x-coordinate of the Tile for which we want to list the neighbors.
     * @param y y-coordinate of the Tile for which we want to list the neighbors.
     * @param range Amount of tiles in a straight line, from the middle. "0" includes only the Tile at (x,y).
     * @return All the neighbors, within a Squared range. The center Tile is included.
     */
    public HashSet<Tile> getNeighborsWithinSquareRange(int x, int y, int range) {
        HashSet<Tile> neighbors = new HashSet<>();

        for(int i = -range; i <= range; i++) {          // height
            for(int j = -range; j <= range; j++) {      // width
                neighbors.add(getTile(j+x, i+y));
            }
        }

        return neighbors;
    }

    /**
     * Reinitializes the `walkableTiles` Array with all the Tiles that allow movement.
     * Also sets up the "4-neighbors" Array for each Tile.
     */
    public void computeWalkableTiles() {
        walkableTiles = new Array<>();
        for(int i=0; i<getMapHeight(); i++) {
            for(int j=0; j<getMapWidth(); j++) {
                Tile currTile = getTile(j, i);
                currTile.addNeighbors(getNorth(currTile),
                        getSouth(currTile),
                        getEast(currTile),
                        getWest(currTile));
                if(currTile.isAllowingMove())
                    walkableTiles.add(currTile);
            }
        }
    }
}
