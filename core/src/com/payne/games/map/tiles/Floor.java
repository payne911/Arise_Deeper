package com.payne.games.map.tiles;

import com.payne.games.gameObjects.GameObject;
import com.payne.games.map.tilesets.Tileset;


public class Floor extends Tile {
    private boolean isWater, hasBlood;


    public Floor(int x, int y) {
        super(x, y);
        setAllowingMove(true);
    }


    @Override
    public void setTexture(Tileset tileset) {
        if(isWater)
            setTexture(tileset.getWaterRandomTexture(getBitmask()));
        else
            setTexture(tileset.getFloorRandomTexture(getBitmask()));
    }

    @Override
    public boolean canInteract(GameObject gameObject) {
        return true;
    }

    @Override
    public void interact(GameObject gameObject) {

    }

    public boolean hasBlood() {
        return hasBlood;
    }
    public void setHasBlood(boolean hasBlood) {
        this.hasBlood = hasBlood;
    }

    public boolean isWater() {
        return isWater;
    }
    public void setWater(boolean water) {
        isWater = water;
    }
}
