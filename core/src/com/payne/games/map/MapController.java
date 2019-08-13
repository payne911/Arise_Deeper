package com.payne.games.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.payne.games.logic.GameLogic;
import com.payne.games.map.generator.MapGenerator;
import com.payne.games.map.renderers.MapRenderer;
import com.payne.games.map.tilesets.Tileset;


public class MapController {
    private LevelMap currentLevel;
    private com.payne.games.map.renderers.MapRenderer mapRenderer;
    private GameLogic gLogic;
    private MapGenerator mapGenerator;


    public MapController(GameLogic gameLogic) {
        this.gLogic = gameLogic;

        this.mapGenerator = new MapGenerator(gLogic);
        this.mapRenderer = new MapRenderer(gLogic);
    }


    public LevelMap getCurrentLevel() {
        return currentLevel;
    }


    /**
     * Modifies both the logical AND graphical representation of the LevelMap object.
     *
     * @param tileType
     * @param x
     * @param y
     */
    public void setTile(int tileType, int x, int y) {
        currentLevel.getLogical_map()[y][x] = tileType;
        mapRenderer.assignSingleTileTexture(x, y);
    }

    /**
     * Generate and set up a map to be rendered.
     *
     * @param mapWidth Width of the map to be generated, in amount of tiles (not pixels!).
     * @param mapHeight Height of the map to be generated, in amount of tiles (not pixels!).
     * @param tileset The tileset to be used for the rendering.
     */
    public void createMap(int mapWidth, int mapHeight, Tileset tileset) {
        this.currentLevel = mapGenerator.createMap(mapWidth, mapHeight);
        mapRenderer.setUpLevel(currentLevel, tileset);
    }

    /**
     * MUST be called in between a "game.batch.begin()" and a "game.batch.end()".
     * Each layer of the Level are to be rendered.
     *
     * @param batch the instance of "game.batch" on which was called the ".begin()" beforehand.
     */
    public void renderLevel(SpriteBatch batch) {
        mapRenderer.renderLevel(batch, currentLevel);
    }
}
