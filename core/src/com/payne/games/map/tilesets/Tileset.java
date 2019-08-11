package com.payne.games.map.tilesets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.payne.games.logic.GameLogic;

import java.awt.*;
import java.util.Random;


public abstract class Tileset {
    protected GameLogic gLogic;
    protected Random rand;

    protected Texture tiles;
    protected TextureRegion[][] splitTiles;

    // collections of points within the split tileset's spritesheet where each type of tile is (for random picks).
    protected Point[] floors;
    protected Point[] walls;
    protected Point[] doors;
    protected Point[] water;
    protected Point[] empty;


    public Tileset(GameLogic gameLogic) {
        this.gLogic = gameLogic;
        this.rand = new Random(gLogic.getSeed());
    }

    /**
     * Sets the tileset to be used in order to map the logical map to a graphical representation.
     *
     * @param tileset String of the name of a SpriteSheet located in the "core/assets" folder.
     */
    protected void setRenderingTileset(String tileset) {
        tiles = new Texture(Gdx.files.internal(tileset));
        splitTiles = TextureRegion.split(tiles, gLogic.TILE_WIDTH, gLogic.TILE_HEIGHT);
    }

    public Texture getTiles() {
        return tiles;
    }

    private Point getRandomDoor() {
        return doors[rand.nextInt(doors.length)];
    }

    private Point getRandomWall() {
        return walls[rand.nextInt(walls.length)];
    }

    private Point getRandomFloor() {
        return floors[rand.nextInt(floors.length)];
    }

    private Point getRandomWater() {
        return water[rand.nextInt(water.length)];
    }

    private Point getRandomEmpty() {
        return empty[rand.nextInt(empty.length)];
    }

    private TextureRegion getTextureFromPoint(Point positionInTileset) {
        return splitTiles[positionInTileset.y][positionInTileset.x];
    }

    public TextureRegion getFloorRandomTexture() {
        return getTextureFromPoint(getRandomFloor());
    }

    public TextureRegion getWallRandomTexture() {
        return getTextureFromPoint(getRandomWall());
    }

    public TextureRegion getWaterRandomTexture() {
        return getTextureFromPoint(getRandomWater());
    }

    public TextureRegion getDoorRandomTexture() {
        return getTextureFromPoint(getRandomDoor());
    }

    public TextureRegion getEmptyRandomTexture() {
        return getTextureFromPoint(getRandomEmpty());
    }
}
