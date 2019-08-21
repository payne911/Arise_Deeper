package com.payne.games.map.renderers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.payne.games.gameObjects.actors.Actor;
import com.payne.games.logic.GameLogic;
import com.payne.games.logic.systems.LightingSystem;
import com.payne.games.map.BaseMapLayer;
import com.payne.games.map.SecondaryMapLayer;
import com.payne.games.map.tiles.Tile;
import com.payne.games.map.tilesets.Tileset;


public class MapRenderer {

    // Base layer
    private SubclassTileAssigner subclassTileAssigner;
    private Tileset tileset;
    private BaseMapLayer level;

    // Secondary layer
    private SecondaryMapLayer secondaryMapLayer;

    // Fog of War
    private LightingSystem lightingSystem;


    public MapRenderer(SecondaryMapLayer secondaryMapLayer, LightingSystem lightingSystem) {
        this.secondaryMapLayer = secondaryMapLayer;
        this.subclassTileAssigner = new SubclassTileAssigner();
        this.lightingSystem = lightingSystem;
    }


    /**
     * Takes care of setting up a Level to be rendered.
     * Determines the Tile map's array size.
     * Then associates a tileset SpriteSheet and prepares the graphical tiles to be rendered
     * (`TextureRegion.split()` extracts individual tiles from the SpriteSheet).
     * And finally assigns a Texture to the instantiated Tile deducted from the LogicalMap.
     *
     * @param level A generated level (the output of the MapGenerator).
     * @param tileset String of the name of a SpriteSheet located in the "core/assets" folder.
     */
    public void setUpBaseLayer(BaseMapLayer level, Tileset tileset) {
        this.level = level;
        this.tileset = tileset;
        subclassTileAssigner.setLevel(level);

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
        Tile tile = level.getTile(x, y);
        if(GameLogic.DEBUG_SUBCLASSED_TILES) subclassTileAssigner.patternMatchSurroundings(tile); // looks at the 4-direction tiles (N,S,E,W)
        tile.setTexture(tileset);
    }

    /**
     * MUST be called in between a "game.batch.begin()" and a "game.batch.end()".
     * Each layer of the Level are to be rendered.
     *
     * @param batch the instance of "game.batch" on which was called the ".begin()" beforehand
     */
    public void renderLevel(SpriteBatch batch) {

        /* Drawing the static map (base layer). Disabling blending improves performance. */
        batch.disableBlending();
        for (int i = 0; i < level.getMapHeight(); i++) {
            for (int j = 0; j < level.getMapWidth(); j++) {
                drawAtMapCoordinate(batch, level.getTile(j, i));
            }
        }
        batch.enableBlending();


        /* Drawing the secondary layer. */
        for (IRenderable gameObject : secondaryMapLayer.getStaticLayer()) {
            drawAtMapCoordinate(batch, gameObject);
        }
        for (Actor gameObject : secondaryMapLayer.getActorLayer()) {
            drawAtMapCoordinate(batch, gameObject);
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
        boolean shouldDraw = determineFogOfWarOverlay(batch, toRender);

        if(shouldDraw)
            batch.draw(toRender.getTexture(),
                    GameLogic.AESTHETIC_OFFSET + toRender.getX()*GameLogic.TILE_WIDTH,
                    GameLogic.AESTHETIC_OFFSET + toRender.getY()*GameLogic.TILE_HEIGHT);
    }


    /**
     * Determines how the rendering should be done, based on variables related to exploration and line of sight.
     *
     * @param batch instance of SpriteBatch.
     * @param renderable a renderable class against which the Tile's variables below it will be checked.
     * @return 'false' only if the SpriteBatch should not attempt to draw the IRenderable object.
     */
    private boolean determineFogOfWarOverlay(SpriteBatch batch, IRenderable renderable) {
        if(GameLogic.DEBUG_NO_FOG) {
            batch.setColor(1,1,1,1);
            return true;
        }

        Tile tile = level.getTile(renderable.getX(), renderable.getY());
        if (tile.isInSight())
            batch.setColor(1,1,1,1); // in plain sight
        else if (tile.isExplored() && renderable.renderInFog())
            batch.setColor(0.65f,0.2f,0.65f,0.5f); // in the fog of war
        else {
            batch.setColor(0, 0, 0, 0); // in the darkness
            return false; // do not draw!
        }

        return true;
    }
}
