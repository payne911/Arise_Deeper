package com.payne.games.map.tilesets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.payne.games.logic.GameLogic;

import java.util.Random;


public abstract class Tileset {
    protected GameLogic gLogic;
    protected Random rand;

    protected Texture tiles;
    protected TextureRegion[][] splitTiles;

    // collections of points within the split tileset's SpriteSheet where each type of tile is (for random picks).
    protected GridPoint2[] floors;
    protected GridPoint2[] walls;
    protected GridPoint2[] doors;
    protected GridPoint2[] water;
    protected GridPoint2[] empty;


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

    private GridPoint2 getRandomDoor() {
        return doors[rand.nextInt(doors.length)];
    }

    private GridPoint2 getRandomWall() {
        return walls[rand.nextInt(walls.length)];
    }

    private GridPoint2 getRandomFloor() {
        return floors[rand.nextInt(floors.length)];
    }

    private GridPoint2 getRandomWater() {
        return water[rand.nextInt(water.length)];
    }

    private GridPoint2 getRandomEmpty() {
        return empty[rand.nextInt(empty.length)];
    }

    private TextureRegion getTextureFromPoint(GridPoint2 positionInTileset) {
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
