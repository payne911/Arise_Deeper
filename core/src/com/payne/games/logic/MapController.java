package com.payne.games.logic;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.payne.games.AriseDeeper;
import com.payne.games.gameObjects.GameObjectFactory;
import com.payne.games.gameObjects.actors.Hero;
import com.payne.games.logic.systems.ActionSystem;
import com.payne.games.logic.systems.InventorySystem;
import com.payne.games.logic.systems.SightSystem;
import com.payne.games.map.BaseMapLayer;
import com.payne.games.map.SecondaryMapLayer;
import com.payne.games.map.generators.MapGenerator;
import com.payne.games.map.renderers.MapRenderer;
import com.payne.games.map.tiles.Tile;
import com.payne.games.map.tilesets.Tileset;
import com.payne.games.screens.MainMenuScreen;
import com.payne.games.logic.systems.TurnManager;


public class MapController {
    private AriseDeeper game;
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
    private SightSystem sightSystem;

    // movements and turns
    private ActionSystem actionSystem;
    private TurnManager turnManager;

    // inventory
    private InventorySystem inventorySystem;
    private Array<TextButton> inventorySlots;


    public MapController(AriseDeeper game, OrthographicCamera camera, Array<TextButton> inventorySlots) {
        this.game = game;
        this.camera = camera;
        this.inventorySlots = inventorySlots;

        this.mapGenerator = new MapGenerator();
        this.actionSystem = new ActionSystem();
        this.gameObjectFactory = new GameObjectFactory(actionSystem);
        createHero(); // todo: this will change at some point!
        this.secondaryMapLayer = new SecondaryMapLayer(gameObjectFactory);
        this.sightSystem = new SightSystem(player);
        this.mapRenderer = new MapRenderer(secondaryMapLayer, sightSystem);
        this.turnManager = new TurnManager(secondaryMapLayer, sightSystem);
    }


    public BaseMapLayer getCurrentLevel() {
        return currentLevel;
    }
    public SecondaryMapLayer getSecondaryMapLayer() {
        return secondaryMapLayer;
    }


    /**
     * Uses the Fatigue system in order to figure out who's turn it is.
     * The AI (`DecisionMaking`) determines the actions of the Enemies.
     */
    public void processTurn() {
        boolean waitingOnPlayer = turnManager.executeTurn();
        if (!waitingOnPlayer) {
            centerOnHero();
            secondaryMapLayer.removeDeadActors();
        }
    }

    /**
     * Create the Hero, and assign its Inventory.
     */
    public void createHero() {
        player = gameObjectFactory.createHero(0, 0);
        inventorySystem = new InventorySystem(inventorySlots);
        player.setInventory(inventorySystem);
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
        sightSystem.setUpLightingOverlay(currentLevel);
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
        sightSystem.updateLighting();
    }

    /**
     * Returns to the MainScreenMenu.
     * todo: should save state, etc.!
     */
    public void returnToMainMenu() {
        game.setScreen(new MainMenuScreen(game));
    }






    /*
        DEBUG
     */


    public void DEBUG_spawn_enemies() {
        secondaryMapLayer.DEBUG_spawn_enemies();
    }
}
