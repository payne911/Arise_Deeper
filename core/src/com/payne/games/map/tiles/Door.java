package com.payne.games.map.tiles;

public class Door extends Tile {

    private boolean isLocked, isExit;

    public Door(int x, int y) {
        super(x, y);
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public boolean isExit() {
        return isExit;
    }

    public void setExit(boolean exit) {
        isExit = exit;
    }
}
