package com.payne.games.map.tiles;

import com.payne.games.gameObjects.GameObject;
import com.payne.games.map.tilesets.Tileset;

import java.util.ArrayList;


public class Floor extends Tile {
    private boolean isWater, hasBlood;
    private ArrayList<GameObject> objectsOnThisTile = new ArrayList<>();


    public Floor(int x, int y) {
        super(x, y);
    }

    @Override
    public void setTexture(Tileset tileset) {
        if(isWater)
            setTexture(tileset.getWaterRandomTexture());
        else
            setTexture(tileset.getFloorRandomTexture());
    }


    public ArrayList<GameObject> getObjectsOnThisTile() {
        return objectsOnThisTile;
    }

    public void addObject(GameObject gameObject) {
        objectsOnThisTile.add(gameObject);
    }

    public void removeObject(GameObject gameObject) {
        objectsOnThisTile.remove(gameObject);
    }


    public boolean isHasBlood() {
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
