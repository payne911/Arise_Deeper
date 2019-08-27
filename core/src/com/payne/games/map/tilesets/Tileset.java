package com.payne.games.map.tilesets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.RandomXS128;
import com.payne.games.logic.GameLogic;


/**
 * todo: see TexturePacker  https://github.com/crashinvaders/gdx-texture-packer-gui
 */
public abstract class Tileset {
    private RandomXS128 rand;

    private Texture tiles;
    private TextureRegion[][] splitTiles;

    // collections of points within the split tileset's SpriteSheet where each type of tile is (for random picks).
    protected GridPoint2[] floors;
    protected GridPoint2[] floors_N;
    protected GridPoint2[] floors_S;
    protected GridPoint2[] floors_E;
    protected GridPoint2[] floors_W;
    protected GridPoint2[] floors_NE;
    protected GridPoint2[] floors_NW;
    protected GridPoint2[] floors_SE;
    protected GridPoint2[] floors_SW;
    protected GridPoint2[] floors_horiz;
    protected GridPoint2[] floors_vert;
    protected GridPoint2[] walls;
    protected GridPoint2[] walls_N;
    protected GridPoint2[] walls_S;
    protected GridPoint2[] walls_E;
    protected GridPoint2[] walls_W;
    protected GridPoint2[] walls_NE;
    protected GridPoint2[] walls_NW;
    protected GridPoint2[] walls_SE;
    protected GridPoint2[] walls_SW;
    protected GridPoint2[] walls_horiz; // EW
    protected GridPoint2[] walls_vert;  // NS
    protected GridPoint2[] doors;
    protected GridPoint2[] water;
    protected GridPoint2[] empty;


    public Tileset() {
        this.rand = new RandomXS128(GameLogic.RANDOM_SEED);
    }

    /**
     * Sets the tileset to be used in order to map the logical map to a graphical representation.
     *
     * @param tileset String of the name of a SpriteSheet located in the "core/assets" folder.
     */
    protected void setRenderingTileset(String tileset) {
        dispose();
        tiles = new Texture(Gdx.files.internal(tileset));
        splitTiles = TextureRegion.split(tiles, GameLogic.TILE_WIDTH, GameLogic.TILE_HEIGHT);
    }

    /**
     * Returns the TextureRegion at the specified GridPoint2 within the split SpriteSheet Texture.
     *
     * @param positionInTileset Coordinate, with (0,0) starting from top-left.
     * @return A texture that can be drawn on the screen.
     */
    private TextureRegion getTextureFromPoint(GridPoint2 positionInTileset) {
        return splitTiles[positionInTileset.y][positionInTileset.x];
    }

    /**
     * Clears properly the GPU memory.
     */
    public void dispose() {
        if(tiles != null)
            tiles.dispose();
    }

    /**
     * To obtain a random drawable texture taken from the list of possible coordinates set in a concrete Tileset.
     *
     * @param input name of the variable.
     * @return the coordinate of the randomly selected texture.
     */
    private GridPoint2 getRandom(GridPoint2[] input) {
        return input[rand.nextInt(input.length)];
    }







    /* todo: use an array with each index being matched against the bitmask? */

    public TextureRegion getFloorRandomTexture(int bitmask) {
        switch (bitmask) {
            case GameLogic.NORTH:
                return getTextureFromPoint(getRandom(floors_N));
            case GameLogic.EAST:
                return getTextureFromPoint(getRandom(floors_E));
            case GameLogic.WEST:
                return getTextureFromPoint(getRandom(floors_W));
            case GameLogic.SOUTH:
                return getTextureFromPoint(getRandom(floors_S));
            case GameLogic.SOUTH|GameLogic.WEST:
                return getTextureFromPoint(getRandom(floors_SW));
            case GameLogic.SOUTH|GameLogic.EAST:
                return getTextureFromPoint(getRandom(floors_SE));
            case GameLogic.NORTH|GameLogic.WEST:
                return getTextureFromPoint(getRandom(floors_NW));
            case GameLogic.NORTH|GameLogic.EAST:
                return getTextureFromPoint(getRandom(floors_NE));
            default:
                return getTextureFromPoint(getRandom(floors));
        }
    }

    public TextureRegion getWallRandomTexture(int bitmask) {
        switch (bitmask) {
            case GameLogic.NORTH:
                return getTextureFromPoint(getRandom(walls_N));
            case GameLogic.EAST:
                return getTextureFromPoint(getRandom(walls_E));
            case GameLogic.WEST:
                return getTextureFromPoint(getRandom(walls_W));
            case GameLogic.SOUTH:
                return getTextureFromPoint(getRandom(walls_S));
            case GameLogic.SOUTH|GameLogic.WEST:
                return getTextureFromPoint(getRandom(walls_SW));
            case GameLogic.SOUTH|GameLogic.EAST:
                return getTextureFromPoint(getRandom(walls_SE));
            case GameLogic.NORTH|GameLogic.WEST:
                return getTextureFromPoint(getRandom(walls_NW));
            case GameLogic.NORTH|GameLogic.EAST:
                return getTextureFromPoint(getRandom(walls_NE));
            case GameLogic.NORTH|GameLogic.SOUTH:
                return getTextureFromPoint(getRandom(walls_vert));
            case GameLogic.WEST|GameLogic.EAST:
                return getTextureFromPoint(getRandom(walls_horiz));
            default:
                return getTextureFromPoint(getRandom(walls));
        }
    }




    public TextureRegion getWaterRandomTexture(int bitmask) {
        return getTextureFromPoint(getRandom(water));
    }
    public TextureRegion getDoorRandomTexture(int bitmask) {
        return getTextureFromPoint(getRandom(doors));
    }
    public TextureRegion getEmptyRandomTexture(int bitmask) {
        return getTextureFromPoint(getRandom(empty));
    }
}
