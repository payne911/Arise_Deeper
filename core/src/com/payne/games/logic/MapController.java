package com.payne.games.logic;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.payne.games.gameObjects.GameObjectFactory;
import com.payne.games.gameObjects.Hero;
import com.payne.games.map.BaseMapLayer;
import com.payne.games.map.SecondaryMapLayer;
import com.payne.games.map.generators.MapGenerator;
import com.payne.games.map.renderers.MapRenderer;
import com.payne.games.map.tiles.Tile;
import com.payne.games.map.tilesets.Tileset;
import com.payne.games.turns.TurnManager;


public class MapController {
    private MapRenderer mapRenderer;
    private OrthographicCamera camera;

    // map's primary layer
    private BaseMapLayer currentLevel;
    private MapGenerator mapGenerator;

    // map's secondary layer
    private Hero player;
    private SecondaryMapLayer secondaryMapLayer;
    private GameObjectFactory gameObjectFactory;

    // map's tertiary layer
    private LightingSystem lightingSystem;

    // movements and turns
    private ActionSystem actionSystem;
    private TurnManager turnManager;


    public MapController(OrthographicCamera camera) {
        this.camera = camera;

        this.mapGenerator = new MapGenerator();
        this.gameObjectFactory = new GameObjectFactory();
        this.player = gameObjectFactory.createHero(0, 0); // todo: this will eventually move somewhere else!
        this.secondaryMapLayer = new SecondaryMapLayer(gameObjectFactory);
        this.lightingSystem = new LightingSystem(player);
        this.mapRenderer = new MapRenderer(secondaryMapLayer, lightingSystem);
        this.actionSystem = new ActionSystem();
        this.turnManager = new TurnManager(secondaryMapLayer);
    }


    public BaseMapLayer getCurrentLevel() {
        return currentLevel;
    }

    public SecondaryMapLayer getSecondaryMapLayer() {
        return secondaryMapLayer;
    }


    /**
     * Uses the Fatigue system in order to figure out who's turn it is.
     * The AI determines the actions of the Enemies.
     */
    public void processTurn() {
        boolean waitingOnPlayer = turnManager.executeTurn();
        if (!waitingOnPlayer) {
            centerOnHero();
            secondaryMapLayer.removeDeadActors();
        }
    }


    /**
     * Assigns the proper Action that goes with the tap.
     *
     * @param x x-coordinate input from the player.
     * @param y y-coordinate input from the player.
     */
    public void playerTapped(int x, int y) {
        actionSystem.checkTap(player, x, y);
    }

    /**
     * Centers the screen on the Player's hero.
     */
    public void centerOnHero() {
        if(player != null) {
            camera.position.set(GameLogic.AESTHETIC_OFFSET + player.getX()*GameLogic.TILE_WIDTH,
                    GameLogic.AESTHETIC_OFFSET + player.getY()*GameLogic.TILE_HEIGHT,
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
        actionSystem.setUpIndexedGraph(currentLevel, secondaryMapLayer); // set up the graph for pathfinding
        lightingSystem.setUpLightingOverlay(currentLevel);
        centerOnHero();
    }

    /**
     * MUST be called in between a "game.batch.begin()" and a "game.batch.end()".
     * Each layer of the Level are to be rendered.
     *
     * @param batch the instance of "game.batch" on which was called the ".begin()" beforehand.
     */
    public void renderLevel(SpriteBatch batch) {
        mapRenderer.renderLevel(batch);
    }

    /**
     * Used to update the Fog Of War surrounding the player.
     * Only called after the player has taken an action since the situation doesn't change in between.
     * todo: this might change to being called within `mapRenderer.renderLevel()` if some spells change the lighting.
     */
    public void updateLighting() {
        lightingSystem.updateLighting();
    }
}
