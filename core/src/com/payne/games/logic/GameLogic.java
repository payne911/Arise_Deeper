package com.payne.games.logic;


public final class GameLogic { /* Can't be inherited. */

    private GameLogic() { } /* Can't be instantiated. (Can't touch this!) */



    /*  DEBUG and Randomizers */
    public static boolean DEBUG_NO_FOG = false;
    public static final boolean DEBUG_SUBCLASSED_TILES = false;
    public static final boolean DEBUG_GESTURE_PRINT = false;
    public static final boolean DEBUG_ACTION_PRINT = true;
    public static boolean RANDOM_DECISIONS = false;
    public static boolean RANDOM_DRUNKARD = false;
    public static boolean RANDOM_ENEMIES = true;

    // Strings
    public static final String GAME_NAME = "Arise Deeper";
    public static final String SKIN_PATH = "skin/uiskin.json";
    public static final String ATLAS_PATH = "atlas/assets.atlas";
    public static final String LOADING_PATH = "splash_screen.png";
    @Deprecated  public static final String BASIC_TILESET = "spriteSheets/dungeon_tileset.png";

    // seeded "random" generators
    public static final int RANDOM_SEED = 1337;

    // game window
    public static final int AESTHETIC_OFFSET = 16;
    public static final int GAME_WIDTH  = 800;
    public static final int GAME_HEIGHT = 480;

    // tiles
    public static final int TILE_SIZE = 16;
    public static final int TILE_BIG_SIZE = 20;
    public static final int TILE_OFFSET = (TILE_BIG_SIZE - TILE_SIZE)/2;

    // tile wall-connection bitmasks
    public static final int NORTH = 1;  // 0001
    public static final int EAST  = 2;  // 0010
    public static final int WEST  = 4;  // 0100
    public static final int SOUTH = 8;  // 1000

    // camera
    public static final float CAM_ZOOM = 0.5f;
    public static final float CAM_OFFSET = TILE_BIG_SIZE/2f; // TILE_OFFSET + (TILE_SIZE/2f)

    // turn
    public static final float TURN_TIME = 0.07f;
    public static final int TURN_GENERAL_COST = 50;
    public static final float MOVE_SPEED = TILE_SIZE/TURN_TIME;

    // inventory
    public static final int INV_SLOTS = 4;

    // lighting system (Line of Sight / Fog of War)
    public static final float LOS_MIN_ALPHA = 0.4f; // [0,1] range
    public static final float FOG_ALPHA = 0.7f; // [0,1] range
    public static final int SUBDIVISIONS = 5;
}
