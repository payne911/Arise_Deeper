package com.payne.games.rendering;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.payne.games.map.tilesets.SubclassTileAssigner;
import com.payne.games.gameObjects.actors.Actor;
import com.payne.games.gameObjects.statics.Static;
import com.payne.games.logic.GameLogic;
import com.payne.games.logic.Utils;
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

    // Light overlay effect
    private TextureRegion light;

    
    public MapRenderer(SecondaryMapLayer secondaryMapLayer, TextureRegion light) {
        this.secondaryMapLayer = secondaryMapLayer;
        this.subclassTileAssigner = new SubclassTileAssigner();
        this.light = light;
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
     * @param visible a 2D double array, larger than the level, that stores 0.0 for unseen subcells 
     *                and values up to 1.0 for subcells that are lit
     */
    public void renderLevel(SpriteBatch batch, double[][] visible) {
        
        /* Drawing the static map (base layer). */
        for (int i = 0; i < level.getMapHeight(); i++) {
            for (int j = 0; j < level.getMapWidth(); j++) {
                drawAtMapCoordinate(batch, level.getTile(j, i));
            }
        }

        for (int y = 0; y < visible.length; y++) {
            for (int x = 0; x < visible[y].length; x++) {
                if (visible[y][x] > 0.0) {
                    batch.setColor(1f, 1f, 1f, (float) visible[y][x] * 0.0625f);
                    batch.draw(light, 8f + 16f / GameLogic.SUBDIVISIONS * x, 8f + 16f / GameLogic.SUBDIVISIONS * y);
                }
            }
        }
        /*
        todo: possible surround the Tile rendering with "disableBlending" and then add the stretched
        lightMap with GaussianBlur over-top?
        https://github.com/crashinvaders/gdx-vfx
         */


        /* Drawing the secondary layer. */
        for (Static gameObject : secondaryMapLayer.getStaticLayer()) {
            drawAtMapCoordinate(batch, gameObject);
        }
        for (Actor gameObject : secondaryMapLayer.getActorLayer()) {
            drawAtMapCoordinate(batch, gameObject);

            /* HP Bars. */
            drawStretched(batch, gameObject, HP_BACKGROUND, HP_WIDTH);
            drawStretched(batch, gameObject, HP_PROGRESS, (float)gameObject.getCurrHp()/gameObject.getMaxHp()*HP_WIDTH);
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
                    Utils.tileToPixels(toRender.getX()) - toRender.getPermanentOriginOffset(),
                    Utils.tileToPixels(toRender.getY()) - toRender.getPermanentOriginOffset());
    }

    /**
     * Draws the TextureRegion using the map's coordinate system (tile coordinates, not pixel coordinates).
     *
     * @param batch Used to draw.
     * @param toRender A renderable object.
     */
    private void drawAtMapCoordinate(SpriteBatch batch, IInterpolatable toRender) {
        boolean shouldDraw = determineFogOfWarOverlay(batch, toRender);

        if(shouldDraw)
            batch.draw(toRender.getTexture(),
                    toRender.getCurrentX(),
                    toRender.getCurrentY());
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
     * todo: or look into  https://github.com/earlygrey/shapedrawer  to integrate between batch 'being' and 'end'.
     *
     * @param batch the SpriteBatch to draw with.
     * @param owner the Actor which will be used to draw relative to its position.
     * @param toRender the Texture to be drawn.
     * @param x_stretch stretch along the x axis, in pixels.
     */
    private void drawStretched(SpriteBatch batch, Actor owner, Texture toRender, float x_stretch) {
        boolean shouldDraw = determineFogOfWarOverlay(batch, owner);

        if(shouldDraw)
            batch.draw(toRender,
                    owner.getCurrentX() + owner.getPermanentOriginOffset() + (int)(GameLogic.TILE_SIZE*.125),
                    owner.getCurrentY() - (int)(GameLogic.TILE_SIZE*.1),
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
        if(GameLogic.DEBUG_NO_FOG) { // render everything
            batch.setColor(1,1,1,1);
            return true;
        }
        
        Tile tile = level.getTile(renderable.getX(), renderable.getY());
        if (tile.isInSight()) {
//            if(renderable instanceof Tile)
//                batch.setColor(0.4f, 0.4f, 0.4f, 1f);
//            else
//                batch.setColor(1f, 1f, 1f, 1f);
            batch.setColor(1,1,1,1); // in plain sight
        }
        else if (tile.isExplored() && renderable.renderInFog())
            batch.setColor(0.65f,0.2f,0.65f,GameLogic.FOG_ALPHA); // in the fog of war
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
