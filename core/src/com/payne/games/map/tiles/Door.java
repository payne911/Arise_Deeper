package com.payne.games.map.tiles;

import com.payne.games.gameObjects.GameObject;
import com.payne.games.map.tilesets.Tileset;

// todo: maybe should be a GameObject ?
public class Door extends Tile {

    private boolean isExit;

    public Door(int x, int y) {
        super(x, y);
        setAllowingMove(false);
    }

    @Override
    public void setTexture(Tileset tileset) {
        setTexture(tileset.getDoorRandomTexture());
    }

    @Override
    public boolean canInteract(GameObject gameObject) {
        return false;
    }

    @Override
    public void interact(GameObject gameObject) {

    }

    public boolean isExit() {
        return isExit;
    }

    public void setExit(boolean exit) {
        isExit = exit;
    }
}
