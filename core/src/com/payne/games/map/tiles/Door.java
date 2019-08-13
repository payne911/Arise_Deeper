package com.payne.games.map.tiles;

import com.payne.games.map.tilesets.Tileset;


public class Door extends Tile {

    private boolean isLocked, isExit;

    public Door(int x, int y) {
        super(x, y);
    }

    @Override
    public void setTexture(Tileset tileset) {
        setTexture(tileset.getDoorRandomTexture());
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
