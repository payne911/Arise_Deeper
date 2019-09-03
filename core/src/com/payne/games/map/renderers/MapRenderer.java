package com.payne.games.map.renderers;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.payne.games.gameObjects.actors.Actor;
import com.payne.games.gameObjects.statics.Static;
import com.payne.games.logic.GameLogic;
import com.payne.games.logic.Utils;
import com.payne.games.logic.systems.SightSystem;
import com.payne.games.map.BaseMapLayer;
import com.payne.games.map.SecondaryMapLayer;
import com.payne.games.map.tiles.Tile;
import com.payne.games.map.tilesets.Tileset;


public class MapRenderer {
    // Temporary HP bars    todo: remove this and implement with SpriteSheet ?
    private final int HP_WIDTH  = (int)(GameLogic.TILE_SIZE *.75);
    private final int HP_HEIGHT = GameLogic.TILE_SIZE/6;
    private final Texture HP_BACKGROUND = createProceduralTexture(1, 0, 0, .7f);
    private final Texture HP_PROGRESS   = createProceduralTexture(0, 1, 0, .7f);

    // Base layer
    private SubclassTileAssigner subclassTileAssigner;
    private Tileset tileset;
    private BaseMapLayer level;

    // Secondary layer
    private SecondaryMapLayer secondaryMapLayer;

    // Fog of War
    private SightSystem sightSystem;


    public MapRenderer(SecondaryMapLayer secondaryMapLayer, SightSystem sightSystem) {
        this.secondaryMapLayer = secondaryMapLayer;
        this.subclassTileAssigner = new SubclassTileAssigner();
        this.sightSystem = sightSystem;
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
        for (Static gameObject : secondaryMapLayer.getStaticLayer()) {
            drawAtMapCoordinate(batch, gameObject);
        }
        for (Actor gameObject : secondaryMapLayer.getActorLayer()) {
            drawAtMapCoordinate(batch, gameObject);

            /* HP Bars. */
            drawAtOffsetCoordinate(batch, gameObject, HP_BACKGROUND, HP_WIDTH);
            drawAtOffsetCoordinate(batch, gameObject, HP_PROGRESS, (float)gameObject.getCurrHp()/gameObject.getMaxHp()*HP_WIDTH);
        }


        // todo: draw other layers (Overlays, missiles, etc.)

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
                    Utils.tileToPixels(toRender.getX()),
                    Utils.tileToPixels(toRender.getY()));
    }

    /**
     * Used temporarily to help draw HP bars. todo: actually integrate it properly through SpriteSheet and TextureRegion
     *
     * @param r red value percentage (between 0 and 1).
     * @param g green value percentage (between 0 and 1).
     * @param b blue value percentage (between 0 and 1).
     * @param a alpha value percentage (between 0 and 1).
     * @return a Texture created procedurally.
     */
    private Texture createProceduralTexture(float r, float g, float b, float a) {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(r, g, b, a);
        pixmap.fill();
        Texture tmp = new Texture(pixmap);
        pixmap.dispose();
        return tmp;
    }

    /**
     * Used temporarily to help draw HP bars. todo: actually integrate it properly through SpriteSheet and TextureRegion
     *
     * @param batch the SpriteBatch to draw with.
     * @param owner the Actor which will be used to draw relative to its position.
     * @param toRender the Texture to be drawn.
     * @param x_stretch stretch along the x axis, in pixels.
     */
    private void drawAtOffsetCoordinate(SpriteBatch batch, Actor owner, Texture toRender, float x_stretch) {
        boolean shouldDraw = determineFogOfWarOverlay(batch, owner);

        if(shouldDraw)
            batch.draw(toRender,
                    GameLogic.AESTHETIC_OFFSET + (int)(GameLogic.TILE_SIZE*.125) + owner.getX()*GameLogic.TILE_SIZE,
                    GameLogic.AESTHETIC_OFFSET - (int)(GameLogic.TILE_SIZE*.1)   + owner.getY()*GameLogic.TILE_SIZE,
                    x_stretch,
                    HP_HEIGHT);
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


    /**
     * Clears the GPU's memory properly.
     */
    public void dispose() {
        HP_BACKGROUND.dispose();
        HP_PROGRESS.dispose();
        tileset.dispose();
    }
}
