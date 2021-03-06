package com.payne.games.map.generators;

import com.badlogic.gdx.math.RandomXS128;
import com.payne.games.logic.GameLogic;
import com.payne.games.map.BaseMapLayer;
import com.payne.games.map.generators.algos.drunkard.MapCarver;
import com.payne.games.map.tiles.Floor;
import com.payne.games.map.tiles.Wall;


public class MapGenerator {
    private RandomXS128 rand;


    public MapGenerator() {
        this.rand = new RandomXS128(GameLogic.RANDOM_SEED);
    }

    public BaseMapLayer createMap(int mapWidth, int mapHeight) {
        BaseMapLayer level = new BaseMapLayer(mapWidth, mapHeight);


        // todo: generator's algorithm (Messy BSP Tree and Broguelike too)
//        genericSingleRoomLevel(level);
        drunkardWalkAlgo(level,
                rand.nextInt(level.getMapWidth()),
                rand.nextInt(level.getMapHeight()),
                0.28f + rand.nextFloat()/15);


        level.computeWalkableTiles();
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
        MapCarver drunkardAlgo = new MapCarver(level, init_x, init_y, GameLogic.RANDOM_SEED, targetFloorPercent);
        drunkardAlgo.walk();
    }


    /**
     * todo: supposed to select a few random Floor spot and run a BFS algo to set their "Water" flag.
     */
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
                    level.setTile(j, i, new Wall(j, i));

                } else {
                    level.setTile(j, i, new Floor(j, i));
                }
            }
        }
    }
}
