package com.payne.games.logic;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.utils.Array;
import com.payne.games.assets.AssetsPool;
import com.payne.games.gameObjects.GameObjectFactory;
import com.payne.games.gameObjects.actors.ActorState;
import com.payne.games.gameObjects.actors.entities.Hero;
import com.payne.games.inventory.Inventory;
import com.payne.games.actions.ActionController;
import com.payne.games.inventory.HeroInventoryWrapper;
import com.payne.games.lightingSystem.SightSystem;
import com.payne.games.map.BaseMapLayer;
import com.payne.games.map.SecondaryMapLayer;
import com.payne.games.map.generators.MapGenerator;
import com.payne.games.rendering.InterpolationModule;
import com.payne.games.rendering.MapRenderer;
import com.payne.games.map.tiles.Tile;
import com.payne.games.map.tilesets.Tileset;
import com.payne.games.rendering.animations.AnimationManager;
import com.payne.games.screens.GameScreen;
import com.payne.games.turns.TurnManager;


public class Controller {
    private final AssetsPool assetsPool;
    private GameScreen gameScreen;
    private MapRenderer mapRenderer;
    private OrthographicCamera camera;

    // animations
    private AnimationManager animationManager;
    private InterpolationModule interpolationModule;

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



    public Controller(GameScreen gameScreen, OrthographicCamera camera, Array<ImageTextButton> inventorySlots) {
        this.camera         = camera;
        this.gameScreen     = gameScreen;
        this.assetsPool     = gameScreen.getAssetsPool();
        this.inventorySlots = inventorySlots;

        mapGenerator        = new MapGenerator();
        animationManager    = new AnimationManager();
        interpolationModule = new InterpolationModule();
        actionController    = new ActionController(this, animationManager, interpolationModule);
        gameObjectFactory   = new GameObjectFactory(actionController, assetsPool);
        createHero(); // todo: this will change at some point!
        secondaryMapLayer   = new SecondaryMapLayer(gameObjectFactory);
        actionController.setSecondaryMapLayer(secondaryMapLayer);
        sightSystem         = new SightSystem();
        mapRenderer         = new MapRenderer(secondaryMapLayer, assetsPool.halo_medium);
        turnManager         = new TurnManager(secondaryMapLayer);
    }




    /**
     * Uses the Fatigue system in order to figure out who's turn it is.
     * The AI (`DecisionMaking`) determines the actions of the Enemies.
     */
    public void processTurn() {
        boolean waitingOnPlayer = turnManager.executeTurn();
        if (waitingOnPlayer) {
            player.setState(ActorState.IDLE);
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
            camera.position.set(player.getCurrentX() + GameLogic.CAM_OFFSET,
                    player.getCurrentY() + GameLogic.CAM_OFFSET,
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
        sightSystem.prepareLightingOverlay(currentLevel);
        centerOnHero();
    }

    /**
     * MUST be called in between a "game.batch.begin()" and a "game.batch.end()".
     * Each layer of the Level are to be rendered.
     *
     * @param batch the instance of "game.batch" on which was called the ".begin()" beforehand.
     * @param delta amount of time since last render.
     */
    public void renderLevel(SpriteBatch batch, float delta) {
        animationManager.updateAnimations(delta);
        interpolationModule.moveAllObjects(delta);
        mapRenderer.renderLevel(batch, sightSystem.getVisible());

        if(player.isMoving())
            centerOnHero();
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
        sightSystem.updateLighting(currentLevel, player.getX(), player.getY(), player.getRangeOfSight());
    }

    /**
     * Returns to the MainScreenMenu.
     */
    public void saveAndReturnToMainMenu() {
        gameScreen.saveAndReturnToMainMenu();
    }

    /**
     * Called when the player dies.
     */
    public void playerDied() {
        gameScreen.playerDied();
    }

    /**
     * Used to clean the GPU's memory properly (related to `Disposable` interface).
     */
    public void dispose() {
        mapRenderer.dispose();
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
