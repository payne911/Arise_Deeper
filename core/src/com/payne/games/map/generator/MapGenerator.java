package com.payne.games.map.generator;

import com.payne.games.logic.GameLogic;
import com.payne.games.map.LevelMap;
import com.payne.games.map.generator.algos.drunkard.MapCarver;
import com.payne.games.map.tiles.Door;
import com.payne.games.map.tiles.*;

import java.util.Random;


public class MapGenerator {
    private Random rand;
    private GameLogic gLogic;


    public MapGenerator(GameLogic gameLogic) {
        this.gLogic = gameLogic;
        this.rand = new Random(gLogic.getSeed());
    }

    public LevelMap createMap(int mapWidth, int mapHeight) {
        LevelMap level = new LevelMap(mapWidth, mapHeight);


        // todo: generator's algorithm
//        randomStuff(level);
//        genericSingleRoomLevel(level);
        drunkardWalk(level, 25, 60, 0.4f);


        setUpGraphicalGrid(level);
        return level;
    }

    private void setUpGraphicalGrid(LevelMap level) {
        int[][] logicalMap    = level.getLogical_map();
        Tile[][] graphicalMap = level.getGraphical_map();

        for (int i = 0; i < level.getMapHeight(); i++) {
            for (int j = 0; j < level.getMapWidth(); j++) {

                switch (logicalMap[i][j]) {
                    case 0:
                        graphicalMap[i][j] = new Wall(j, i);
                        break;
                    case 1:
                        graphicalMap[i][j] = new Floor(j, i);
                        break;
                    case 2:
                        graphicalMap[i][j] = new Door(j, i);
                        break;
                    default:
                        graphicalMap[i][j] = new Empty(j, i);
                        break;
                }
            }
        }
    }


    /**
     * Drunkard Walk algorithm.
     *
     * @param level the Level to be populated.
     * @param init_x initial position in x for the drunkard.
     * @param init_y initial position in y for the drunkard.
     * @param targetFloorPercent percentage of the level that must be passable.
     */
    private void drunkardWalk(LevelMap level, int init_x, int init_y, float targetFloorPercent) {
        MapCarver drunkardAlgo = new MapCarver(level, init_x, init_y, gLogic.getSeed(), targetFloorPercent);
        drunkardAlgo.walk();
    }


    // the world is one big room.
    private void genericSingleRoomLevel(LevelMap level) {
        int mapHeight = level.getMapHeight();
        int mapWidth = level.getMapWidth();

        for (int i = 0; i < mapHeight; i++) {
            for (int j = 0; j < mapWidth; j++) {

                if(j == 0 || j == mapWidth-1 || i == 0 || i == mapHeight-1) { // if EDGE
                    if(rand.nextDouble() < 0.15) { // slight chance of having a door
                        level.getLogical_map()[i][j] = 2;
                    } else {
                        level.getLogical_map()[i][j] = 0;
                    }

                } else {
                    level.getLogical_map()[i][j] = 1;
                }
            }
        }
    }


    // just a dumb 100% "random" world.
    private void randomStuff(LevelMap level) {
        for (int i = 0; i < level.getMapHeight(); i++) {
            for (int j = 0; j < level.getMapWidth(); j++) {
                level.getLogical_map()[i][j] = rand.nextInt(2);
            }
        }
    }

}
