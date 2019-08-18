package com.payne.games.logic;

import com.badlogic.gdx.math.GridPoint2;
import com.payne.games.map.tiles.Tile;


/**
 * Purely static class.
 */
public class Utils {


    /**
     * To obtain the difference between two tiles, when moving from the first to the second Tile.
     * For example: first=(2,3) and second=(5,2) give output=(3,-1).
     *
     * @param first origin Tile.
     * @param second destination Tile.
     * @return the GridPoint2 object that contains the 2D distance that separates the input.
     */
    public static GridPoint2 deltaOfTiles(Tile first, Tile second) {
        return new GridPoint2(second.getX() - first.getX(), second.getY() - first.getY());
    }

    /**
     * Gives the straight-line distance, in tile units, between two points.
     *
     * @param from the From coordinate.
     * @param to the To coordinate.
     * @return straight-line distance.
     */
    public static double straightDistanceBetweenMiddleOfTiles(GridPoint2 from, GridPoint2 to) {
        double deltaX = Math.pow(to.x - from.x, 2);
        double deltaY = Math.pow(to.y - from.y, 2);

        return Math.sqrt(deltaX + deltaY);
    }
}