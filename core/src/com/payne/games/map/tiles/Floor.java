package com.payne.games.map.tiles;


public class Floor extends Tile {
    private boolean isWater;


    public Floor(int x, int y) {
        super(x, y);
    }


    public boolean isWater() {
        return isWater;
    }

    public void setWater(boolean water) {
        isWater = water;
    }
}
