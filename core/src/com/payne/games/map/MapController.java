package com.payne.games.map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.payne.games.gameObjects.GameObjectFactory;
import com.payne.games.gameObjects.Hero;
import com.payne.games.logic.GameLogic;
import com.payne.games.logic.MovementSystem;
import com.payne.games.map.generators.MapGenerator;
import com.payne.games.map.renderers.MapRenderer;
import com.payne.games.map.tiles.Tile;
import com.payne.games.map.tilesets.Tileset;


public class MapController {
    private GameLogic gLogic;
    private MapRenderer mapRenderer;
    private OrthographicCamera camera;

    private BaseMapLayer currentLevel;
    private MapGenerator mapGenerator;

    private Hero player;
    private SecondaryMapLayer secondaryMapLayer;
    private GameObjectFactory gameObjectFactory;
    private MovementSystem movementSystem;


    public MapController(GameLogic gameLogic, OrthographicCamera camera) {
        this.gLogic = gameLogic;
        this.camera = camera;

        this.mapGenerator = new MapGenerator(gLogic);
        this.gameObjectFactory = new GameObjectFactory(gLogic);
        this.secondaryMapLayer = new SecondaryMapLayer(gLogic, gameObjectFactory);
        this.mapRenderer = new MapRenderer(gLogic, secondaryMapLayer);
        this.movementSystem = new MovementSystem(gLogic);

        this.player = gameObjectFactory.createHero(0, 0); // todo: this will eventually move somewhere else!
    }


    public BaseMapLayer getCurrentLevel() {
        return currentLevel;
    }

    public SecondaryMapLayer getSecondaryMapLayer() {
        return secondaryMapLayer;
    }

    public void moveHeroLeft() {
        movementSystem.moveLeft(player, currentLevel);
    }

    public void moveHeroRight() {
        movementSystem.moveRight(player, currentLevel);
    }

    public void moveHeroUp() {
        movementSystem.moveUp(player, currentLevel);
    }

    public void moveHeroDown() {
        movementSystem.moveDown(player, currentLevel);
    }

    /**
     * Centers the screen on the Player's hero.
     */
    public void centerOnHero() {
        if(player != null) {
            camera.position.set(gLogic.AESTHETIC_OFFSET + player.getX()*gLogic.TILE_WIDTH,
                    gLogic.AESTHETIC_OFFSET + player.getY()*gLogic.TILE_HEIGHT,
                    0f);
        }
    }

    /**
     * Modifies both the `graphicalMap` attribute of the `BaseMapLayer` class
     * and the Texture used by the `MapRenderer` to display the Tile on the screen.
     *
     * @param newTile A new instance of the Tile to be displayed.
     */
    public void setTile(Tile newTile) {
        currentLevel.setTile(newTile.getX(), newTile.getY(), newTile);
        mapRenderer.assignSingleTileTexture(newTile.getX(), newTile.getY());
    }

    /**
     * Generate and set up a map to be rendered.
     *
     * @param mapWidth Width of the map to be generated, in amount of tiles (not pixels!).
     * @param mapHeight Height of the map to be generated, in amount of tiles (not pixels!).
     * @param tileset The tileset to be used for the rendering.
     */
    public void generateLevel(int mapWidth, int mapHeight, Tileset tileset) {
        currentLevel = mapGenerator.createMap(mapWidth, mapHeight); // generate a base layer
        mapRenderer.setUpBaseLayer(currentLevel, tileset); // assign the graphical representations to base layer's Tiles
        secondaryMapLayer.setUpSecondaryLayer(player, currentLevel); // place secondary layer (Hero, Chests, Keys, etc.)
        centerOnHero();
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
