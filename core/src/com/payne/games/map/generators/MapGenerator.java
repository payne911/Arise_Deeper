package com.payne.games.map.generators;

import com.payne.games.logic.GameLogic;
import com.payne.games.map.BaseMapLayer;
import com.payne.games.map.generators.algos.drunkard.MapCarver;

import java.util.Random;


public class MapGenerator {
    private Random rand;
    private GameLogic gLogic;


    public MapGenerator(GameLogic gameLogic) {
        this.gLogic = gameLogic;
        this.rand = new Random(gLogic.getSeed());
    }

    public BaseMapLayer createMap(int mapWidth, int mapHeight) {
        BaseMapLayer level = new BaseMapLayer(mapWidth, mapHeight);


        // todo: generator's algorithm (Messy BSP Tree and Broguelike too)
//        randomStuff(level);
//        genericSingleRoomLevel(level);
        drunkardWalkAlgo(level,
                rand.nextInt(level.getMapWidth()),
                rand.nextInt(level.getMapHeight()),
                0.28f + rand.nextFloat()/15);


        return level;
    }


    /**
     * Drunkard Walk algorithm.
     *
     * @param level the Level to be populated.
     * @param init_x initial position in x for the drunkard.
     * @param init_y initial position in y for the drunkard.
     * @param targetFloorPercent percentage of the level that must be passable.
     */
    private void drunkardWalkAlgo(BaseMapLayer level, int init_x, int init_y, float targetFloorPercent) {
        MapCarver drunkardAlgo = new MapCarver(level, init_x, init_y, gLogic.getSeed(), targetFloorPercent);
        drunkardAlgo.walk();
    }


    private void floodFill() {
        // todo
    }











    /*
                    BELOW: TESTING FUNCTIONS (not actual proc-gen levels).
     */


    // the world is one big room.
    private void genericSingleRoomLevel(BaseMapLayer level) {
        int mapHeight = level.getMapHeight();
        int mapWidth  = level.getMapWidth();

        for (int i = 0; i < mapHeight; i++) {
            for (int j = 0; j < mapWidth; j++) {

                if(j == 0 || j == mapWidth-1 || i == 0 || i == mapHeight-1) { // if EDGE
                    if(rand.nextDouble() < 0.15) { // slight chance of having a door
                        level.getLogicalMap()[i][j] = 3;
                    } else {
                        level.getLogicalMap()[i][j] = 0;
                    }

                } else {
                    level.getLogicalMap()[i][j] = 1;
                }
            }
        }
    }


    // just a dumb 100% "random" world.
    private void randomStuff(BaseMapLayer level) {
        for (int i = 0; i < level.getMapHeight(); i++) {
            for (int j = 0; j < level.getMapWidth(); j++) {
                level.getLogicalMap()[i][j] = rand.nextInt(3);
            }
        }
    }

}