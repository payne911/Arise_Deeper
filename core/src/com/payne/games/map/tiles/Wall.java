package com.payne.games.map.tiles;

import com.payne.games.gameObjects.GameObject;
import com.payne.games.map.tilesets.Tileset;


public class Wall extends Tile {

    public Wall(int x, int y) {
        super(x, y);
        super.setAllowingMove(false);
    }

    @Override
    public void setAllowingMove(boolean allowingMove) {
        // we do not allow for Walls to be moved through
    }

    @Override
    public void setTexture(Tileset tileset) {
        setTexture(tileset.getWallRandomTexture(getBitmask()));
    }

    @Override
    public boolean canInteract(GameObject gameObject) {
        return false;
    }

    @Override
    public void interact(GameObject gameObject) {

    }
}
