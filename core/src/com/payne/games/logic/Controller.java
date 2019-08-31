package com.payne.games.logic;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.payne.games.AriseDeeper;
import com.payne.games.gameObjects.GameObjectFactory;
import com.payne.games.gameObjects.actors.Hero;
import com.payne.games.inventory.Inventory;
import com.payne.games.actions.ActionController;
import com.payne.games.inventory.HeroInventoryWrapper;
import com.payne.games.logic.systems.SightSystem;
import com.payne.games.map.BaseMapLayer;
import com.payne.games.map.SecondaryMapLayer;
import com.payne.games.map.generators.MapGenerator;
import com.payne.games.map.renderers.MapRenderer;
import com.payne.games.map.tiles.Tile;
import com.payne.games.map.tilesets.Tileset;
import com.payne.games.screens.MainMenuScreen;
import com.payne.games.turns.TurnManager;


public class Controller {
    private final AssetManager assets;
    private final AriseDeeper game;
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
    private ActionController actionController;
    private TurnManager turnManager;

    // inventory
    private HeroInventoryWrapper heroInventoryWrapper;
    private Array<ImageTextButton> inventorySlots;



    public Controller(final AriseDeeper game, final AssetManager assets,
                      OrthographicCamera camera, Array<ImageTextButton> inventorySlots) {
        this.game = game;
        this.assets = assets;
        this.camera = camera;
        this.inventorySlots = inventorySlots;

        this.mapGenerator = new MapGenerator();
        this.actionController = new ActionController();
        this.gameObjectFactory = new GameObjectFactory(actionController);
        createHero(); // todo: this will change at some point!
        this.secondaryMapLayer = new SecondaryMapLayer(gameObjectFactory);
        actionController.setSecondaryMapLayer(secondaryMapLayer);
        this.sightSystem = new SightSystem(player);
        this.mapRenderer = new MapRenderer(secondaryMapLayer, sightSystem);
        this.turnManager = new TurnManager(secondaryMapLayer, sightSystem);
    }




    /**
     * Uses the Fatigue system in order to figure out who's turn it is.
     * The AI (`DecisionMaking`) determines the actions of the Enemies.
     */
    public void processTurn() {
        boolean waitingOnPlayer = turnManager.executeTurn();
        if (!waitingOnPlayer) {
            centerOnHero();
        } else {
            // todo: save game state
        }
    }

    /**
     * Create the Hero, and assign its Inventory.
     * todo: Load Hero if selected in MainMenuScreen.
     */
    public void createHero() {
        player = gameObjectFactory.createHero(0, 0, new Inventory(4));
        heroInventoryWrapper = new HeroInventoryWrapper(inventorySlots, player.getInventory());
    }


    /**
     * Assigns the proper Action that goes with the tap.
     *
     * @param x x-coordinate input from the player.
     * @param y y-coordinate input from the player.
     */
    public void playerTapped(int x, int y) {
        actionController.checkTap(player, x, y);
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
        actionController.setUpIndexedGraph(currentLevel); // set up the graph for pathfinding
        secondaryMapLayer.setUpSecondaryLayer(player, currentLevel); // place secondary layer (Hero, Chests, Keys, etc.)
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
     * Updates certain elements of the UI.
     */
    public void updateUi() {
        heroInventoryWrapper.render();
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
        game.setScreen(new MainMenuScreen(game, assets));
    }

    /**
     * Used to clean the GPU's memory properly (related to `Disposable` interface).
     */
    public void dispose() {
        mapRenderer.dispose();
        gameObjectFactory.dispose();
    }




    /*
        DEBUG
     */


    /**
     * Pressing "S" while playing the game will spawn new enemies.
     * Can cause bugs if enemies are spawned over other ones.
     * Very temporary debug function.
     */
    public void DEBUG_spawn_enemies() {
        secondaryMapLayer.DEBUG_spawn_enemies();
    }
}
