package com.payne.games.map.renderers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.payne.games.logic.GameLogic;
import com.payne.games.map.BaseMapLayer;
import com.payne.games.map.tilesets.Tileset;


public class MapRenderer {
    private final int AESTHETIC_OFFSET = 16; // todo: not necessary

    private GameLogic gLogic;
    private WallRenderer wallRenderer;
    private Tileset tileset;
    private BaseMapLayer level;


    public MapRenderer(GameLogic gameLogic) {
        this.gLogic = gameLogic;
        this.wallRenderer = new WallRenderer();
    }


    /**
     * Takes care of setting up a Level to be rendered.
     * Determines the Tile map's array size.
     * Then associates a tileset SpriteSheet and prepares the graphical tiles to be rendered
     * (`TextureRegion.split()` extracts individual tiles from the SpriteSheet).
     * And finally assigns a Texture to the instanciated Tile deducted from the LogicalMap.
     *
     * @param level A generated level (the output of the MapGenerator).
     * @param tileset String of the name of a SpriteSheet located in the "core/assets" folder.
     */
    public void setUpLevel(BaseMapLayer level, Tileset tileset) {
        this.level = level;
        this.tileset = tileset;
        this.wallRenderer.setTileset(tileset);

        assignTilesTexture();
    }

    /**
     * Used to build up the Graphical representation of the map from its logical representation.
     * Basically assigns a TextureRegion to a new instance of a Tile.
     */
    private void assignTilesTexture() {
        for (int i = 0; i < level.getMapHeight(); i++) {
            for (int j = 0; j < level.getMapWidth(); j++) {
                assignSingleTileTexture(j, i);
            }
        }
    }

    /**
     * Uses MapRenderer's currently assigned Tileset to assign a Texture to the Tile at the specified coordinate.
     *
     * @param x x-coordinate input.
     * @param y y-coordinate input.
     */
    public void assignSingleTileTexture(int x, int y) {
        level.getTile(x, y).setTexture(tileset);
    }

    /**
     * MUST be called in between a "game.batch.begin()" and a "game.batch.end()".
     * Each layer of the Level are to be rendered.
     *
     * @param batch the instance of "game.batch" on which was called the ".begin()" beforehand
     * @param level the Level to be rendered.
     */
    public void renderLevel(SpriteBatch batch, BaseMapLayer level) {
        /* Drawing the static map (base layer). Disabling blending improves performance. */
        batch.disableBlending();
        for (int i = 0; i < level.getMapHeight(); i++) {
            for (int j = 0; j < level.getMapWidth(); j++) {
                TextureRegion tile = level.getTile(j, i).getTexture();
                batch.draw(tile, AESTHETIC_OFFSET + j*gLogic.TILE_WIDTH, AESTHETIC_OFFSET + i*gLogic.TILE_HEIGHT);
            }
        }
        batch.enableBlending();



        // todo: draw other layers!



//        /* Tests. */
//        Texture tiles = tileset.getTiles();
//        batch.draw(tiles, gLogic.GAME_WIDTH-350, gLogic.GAME_HEIGHT-120, tiles.getWidth(), tiles.getHeight());
//        batch.draw(splitTiles[0][0], 200, 200); // drawing a single tile
    }
}
