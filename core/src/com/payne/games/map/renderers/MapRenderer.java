package com.payne.games.map.renderers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.payne.games.logic.GameLogic;
import com.payne.games.map.BaseMapLayer;
import com.payne.games.map.SecondaryMapLayer;
import com.payne.games.map.tilesets.Tileset;


public class MapRenderer {
    private GameLogic gLogic;

    // Base layer
    private WallRenderer wallRenderer;
    private Tileset tileset;
    private BaseMapLayer level;

    // Secondary layer
    private SecondaryMapLayer secondaryMapLayer;


    public MapRenderer(GameLogic gameLogic, SecondaryMapLayer secondaryMapLayer) {
        this.gLogic = gameLogic;
        this.secondaryMapLayer = secondaryMapLayer;
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
    public void setUpBaseLayer(BaseMapLayer level, Tileset tileset) {
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
                drawAtMapCoordinate(batch, level.getTile(j, i));
            }
        }
        batch.enableBlending();


        /* Drawing the secondary layer (interactive GameObjects such as Hero, Keys and Chests). */
        for (IRenderable gameObject : secondaryMapLayer.getSecondaryLayer()) {
            drawAtMapCoordinate(batch, gameObject); // todo: other approach so that Hero is on top of all layers
        }


        // todo: draw other layers (Overlays, HP bars, missiles, etc.)

    }

    /**
     * Draws the TextureRegion using the map's coordinate system (tile coordinates, not pixel coordinates).
     *
     * @param batch Used to draw.
     * @param toRender A renderable object.
     */
    private void drawAtMapCoordinate(SpriteBatch batch, IRenderable toRender) {
        batch.draw(toRender.getTexture(),
                gLogic.AESTHETIC_OFFSET + toRender.getX()*gLogic.TILE_WIDTH,
                gLogic.AESTHETIC_OFFSET + toRender.getY()*gLogic.TILE_HEIGHT);
    }
}
