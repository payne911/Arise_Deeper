package com.payne.games.map.generator;

import com.payne.games.logic.GameLogic;
import com.payne.games.map.LevelMap;
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
        for (int i = 0; i < mapHeight; i++) {
            for (int j = 0; j < mapWidth; j++) {
//                randomStuff(level, j, i);
                genericLevel(level, j, i, mapWidth, mapHeight);
            }
        }

        return level;
    }

    private void genericLevel(LevelMap level, int current_X, int current_Y, int mapWidth, int mapHeight) {

        if(current_X == 0 || current_X == mapWidth-1 || current_Y == 0 || current_Y == mapHeight-1) { // if EDGE
            if(rand.nextDouble() < 0.15) { // slight chance of having a door
                level.getLogical_map()[current_Y][current_X] = 2;
                level.getGraphical_map()[current_Y][current_X] = new Door(current_X, current_Y);
            } else {
                level.getLogical_map()[current_Y][current_X] = 0;
                level.getGraphical_map()[current_Y][current_X] = new Wall(current_X, current_Y);
            }

        } else {
            level.getLogical_map()[current_Y][current_X] = 1;
            level.getGraphical_map()[current_Y][current_X] = new Floor(current_X, current_Y);
        }
    }


    // just a dumb 100% "random" world
    private void randomStuff(LevelMap level, int current_X, int current_Y) {
        level.getLogical_map()[current_Y][current_X] = rand.nextInt(2);
        level.getGraphical_map()[current_Y][current_X] = new Floor(current_X, current_Y);
    }

}
