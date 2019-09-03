package com.payne.games.logic;


public final class GameLogic { /* Can't be inherited. */

    private GameLogic() { } /* Can't be instantiated. (Can't touch this!) */



    /*  DEBUG and Randomizers */
    public static final boolean DEBUG_NO_FOG = false;
    public static final boolean DEBUG_SUBCLASSED_TILES = false;
    public static final boolean DEBUG_GESTURE_PRINT = false;
    public static final boolean DEBUG_ACTION_PRINT = true;
    public static boolean RANDOM_DECISIONS = false;
    public static boolean RANDOM_DRUNKARD = false;
    public static boolean RANDOM_ENEMIES = true;

    // Strings
    public static final String GAME_NAME = "Arise Deeper";
    public static final String BASIC_TILESET = "spriteSheets/dungeon_tileset.png";
    public static final String SKIN_FILE = "skin/uiskin.json";

    // seeded "random" generators
    public static final int RANDOM_SEED = 1337;

    // game window
    public static final int AESTHETIC_OFFSET = 16;
    public static final int GAME_WIDTH  = 800;
    public static final int GAME_HEIGHT = 480;

    // tiles
    public static final int TILE_SIZE = 16;
    public static final int TILE_BIG_SIZE = 20;

    // tile wall-connection bitmasks
    public static final int NORTH = 1;  // 0001
    public static final int EAST  = 2;  // 0010
    public static final int WEST  = 4;  // 0100
    public static final int SOUTH = 8;  // 1000

    // camera
    public static final float CAM_ZOOM = 0.5f;

    // turn
    public static final float TURN_TIME = 0.07f;
    public static final int TURN_GENERAL_COST = 50;

    // inventory
    public static final int INV_SLOTS = 4;

}
