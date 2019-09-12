package com.payne.games.lightingSystem;

import java.util.Arrays;


/**
 * This class provides methods for calculating Field of View in grids. Field of
 * View (FOV) algorithms determine how much area surrounding a point can be
 * seen. They return a two dimensional array of doubles, representing the amount
 * of view (almost always sight) which the origin has of each cell.
 * <br>
 * The input resistanceMap is considered the percent of opacity. This resistance
 * is on top of the resistance applied from the light spreading out. You can
 * obtain a resistance map by looping over all the x,y positions in your
 * grid-based map and running a switch statement on each cell, assigning a double
 * to the same x,y position in a double[][]. The value should be between 0.0
 * (unblocked) for things light passes through, 1.0 (blocked) for things light
 * can't pass at all, and possibly other values for translucent obstacles.
 * <br>
 * The returned light map is considered the percent of light in the cells.
 * <br>
 * Currently, all implementations provide percentage levels of light from 0.0
 * (unlit) to 1.0 (fully lit).
 * <br>
 * All solvers perform bounds checking so solid borders in the map are not
 * required.
 *
 * @author Eben Howard - http://squidpony.com - howard@squidpony.com
 * @author Tommy Ettinger
 */
public class FieldOfView {

    /**
     * Unneeded.
     */
    protected FieldOfView() {
    }

    /**
     * Fills a 2D double array with the given value.
     * @param array a 2D double array that will be modified
     * @param value the value to fill {@code array} with
     */
    public static void fill(double[][] array, double value)
    {
        for (int i = 0; i < array.length; i++) {
            Arrays.fill(array[i], value);
        }
    }

    public static double radius (double x, double y) {
        return Math.sqrt(x * x + y * y);
    }

    /**
     * Calculates the Field Of View for the provided map from the given x, y
     * coordinates. Assigns to, and returns, a light map where the values
     * represent a percentage of fully lit. Always uses shadowcasting FOV,
     * which allows this method to be static since it doesn't need to keep any
     * state around, and can reuse the state the user gives it via the
     * {@code light} parameter. The values in light are always cleared before
     * this is run, because prior state can make this give incorrect results.
     * <br>
     * The starting point for the calculation is considered to be at the center
     * of the origin cell. Radius determinations based on Euclidean
     * calculations. Values in {@code light} will be 1.0 at the light source
     * and will decrease steadily as they get further away.
     *
     * @param resistanceMap the grid of cells to calculate on; 1.0 resists all light, 0.0 does not resist
     * @param light the grid of cells which will represent the alpha overlay (amount of light)
     * @param startX the horizontal component of the starting location
     * @param startY the vertical component of the starting location
     * @param radius the distance the light will extend to
     * @return the computed light grid
     */
    public static double[][] reuseFOV(double[][] resistanceMap, double[][] light, int startX, int startY, double radius)
    {
        double decay = 1 / radius;
        fill(light, 0);
        light[startY][startX] = Math.min(1.0, radius); //make the starting space full power unless radius is tiny

        final int height = light.length, width = light[0].length;
        shadowCast(1, 1.0, 0.0, 0, 1, 1, 0, radius, startY, startX, decay, light, resistanceMap, 0, 0, height, width);
        shadowCast(1, 1.0, 0.0, 1, 0, 0, 1, radius, startY, startX, decay, light, resistanceMap, 0, 0, height, width);
        shadowCast(1, 1.0, 0.0, 0, 1, -1, 0, radius, startY, startX, decay, light, resistanceMap, 0, 0, height, width);
        shadowCast(1, 1.0, 0.0, 1, 0, 0, -1, radius, startY, startX, decay, light, resistanceMap, 0, 0, height, width);
        shadowCast(1, 1.0, 0.0, 0, -1, -1, 0, radius, startY, startX, decay, light, resistanceMap, 0, 0, height, width);
        shadowCast(1, 1.0, 0.0, -1, 0, 0, -1, radius, startY, startX, decay, light, resistanceMap, 0, 0, height, width);
        shadowCast(1, 1.0, 0.0, 0, -1, 1, 0, radius, startY, startX, decay, light, resistanceMap, 0, 0, height, width);
        shadowCast(1, 1.0, 0.0, -1, 0, 0, 1, radius, startY, startX, decay, light, resistanceMap, 0, 0, height, width);
        return light;
    }

    private static void shadowCast(int row, double start, double end, int xx, int xy, int yx, int yy,
                                   double radius, int startY, int startX, double decay, double[][] lightMap,
                                   double[][] map, int minY, int minX, int maxY, int maxX) {
        double newStart = 0;
        if (start < end) {
            return;
        }
        boolean blocked = false;
        for (int distance = row; distance <= radius && distance < maxY - minY + maxX - minX && !blocked; distance++) {
            int deltaX = -distance;
            for (int deltaY = -distance; deltaY <= 0; deltaY++) {
                int currentY = startY + deltaY * xx + deltaX * xy;
                int currentX = startX + deltaY * yx + deltaX * yy;
                double leftSlope = (deltaY - 0.5f) / (deltaX + 0.5f);
                double rightSlope = (deltaY + 0.5f) / (deltaX - 0.5f);

                if (!(currentY >= minY && currentX >= minX && currentY < maxY && currentX < maxX) || start < rightSlope) {
                    continue;
                } else if (end > leftSlope) {
                    break;
                }
                double deltaRadius = radius(deltaY, deltaX);
                //check if it's within the lightable area and light if needed
                if (deltaRadius <= radius) {
                    lightMap[currentY][currentX] = 1.0 - decay * deltaRadius;
                }

                if (blocked) { //previous cell was a blocking one
                    if (map[currentY][currentX] >= 1) {//hit a wall
                        newStart = rightSlope;
                    } else {
                        blocked = false;
                        start = newStart;
                    }
                } else {
                    if (map[currentY][currentX] >= 1 && distance < radius) {//hit a wall within sight line
                        blocked = true;
                        shadowCast(distance + 1, start, leftSlope, xx, xy, yx, yy, radius, startY, startX, decay,
                                lightMap, map, minY, minX, maxY, maxX); // recurse with different initial settings
                        newStart = rightSlope;
                    }
                }
            }
        }
    }
}