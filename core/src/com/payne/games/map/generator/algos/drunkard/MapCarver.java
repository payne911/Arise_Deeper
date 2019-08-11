package com.payne.games.map.generator.algos.drunkard;

import com.payne.games.map.LevelMap;

import java.util.Random;


public class MapCarver {
    private LevelMap level;
    private Drunkard drunk;
    private Random rand;
    private double targetFloorPercent;

    /* Set to 'true' if the generator should actually try to be random. */
    private final boolean RANDOM_SEED = false;


    /**
     * Used to start up the Drunkard algo.
     *
     * @param level a LevelMap object that will be populated properly.
     * @param init_x initial X position of the drunkard.
     * @param init_y initial Y position of the drunkard.
     * @param seed seed for (reproducible) pseudo-random generation.
     * @param targetFloorPercent target percentage point for the amount of floors in the level.
     */
    public MapCarver(LevelMap level, int init_x, int init_y, int seed, float targetFloorPercent) {
        this.level = level;
        this.targetFloorPercent = targetFloorPercent;

        this.drunk = new Drunkard(init_x, init_y);
        dig(init_x, init_y);

        rand = new Random(RANDOM_SEED ? (int)(Math.random()*1000) : seed);
    }

    /**
     * Get the drunkard to dig until the desired amount of floor-percentage is reached.
     */
    public void walk() {
        initMap(level);

        do {
            randomWalk();
        } while(level.floorPercentage() < targetFloorPercent);
    }

    /**
     * To make sure the whole level begins with 100% impassable terrain (Walls).
     * @param level the LevelMap object that will be initialized properly.
     */
    private void initMap(LevelMap level) {
        for (int i = 0; i < level.getMapHeight(); i++) {
            for (int j = 0; j < level.getMapWidth(); j++) {
                level.getLogical_map()[i][j] = 0; // 0 = Wall
            }
        }
    }

    private void randomWalk() {
        int directionIndex = rand.nextInt(Direction4.values().length); // select a random Direction-index
        Direction4 dir = Direction4.values()[directionIndex]; // extract the Direction associated with the index

        move(dir); // act on the random walk (move + dig);
    }

    /**
     * Moves the drunkard in a direction (North, East, South, or West).
     * @param direction a 2D Point containing the delta for each coordinate.
     */
    private void move(Direction4 direction) {
        int test_x = this.drunk.getX() + direction.getX();
        int test_y = this.drunk.getY() + direction.getY();

        // Restrict movement to edges of the map.
        if (test_x < 0 || test_y < 0 || test_x > level.getMapWidth()-1 || test_y > level.getMapHeight()-1)
            return;

        // Move the drunkard.
        this.drunk.setX(test_x);
        this.drunk.setY(test_y);

        // Dig (transform a Wall into a Floor).
        dig(test_x, test_y);
    }

    /**
     * The Tile where the Drunkard stands is "dug out".
     * A Wall that was placed there becomes a Floor.
     * A Floor remains a Floor.
     *
     * @param x x position on the map.
     * @param y y position on the map.
     */
    private void dig(int x, int y) {
        System.out.println("Digging: " + "(" + x + "," + y + ")");
        level.getLogical_map()[y][x] = 1; // 1 = Floor
    }
}
