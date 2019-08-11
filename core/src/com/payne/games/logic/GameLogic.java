package com.payne.games.logic;

import com.payne.games.AriseDeeper;


public class GameLogic {

    private final AriseDeeper game;
    private int currentLevel = 0;

    // seeded "random" level generator
    private int randSeed = 1337;

    // game window
    public final int GAME_WIDTH  = 800;
    public final int GAME_HEIGHT = 480;

    // tiles
    public final int TILE_WIDTH  = 16;
    public final int TILE_HEIGHT = 16;

    // camera
    public final int CAM_WIDTH  = 10;
    public final int CAM_HEIGHT = 10;


    public GameLogic(final AriseDeeper game) {
        this.game = game;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public int getSeed() {
        return randSeed;
    }
}
