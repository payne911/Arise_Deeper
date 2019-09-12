package com.payne.games.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Array;
import com.payne.games.AriseDeeper;
import com.payne.games.logic.GameLogic;
import com.payne.games.logic.Controller;
import com.payne.games.map.tilesets.BasicTileset;
import com.payne.games.inputProcessors.MyGestureListener;
import com.payne.games.inputProcessors.MyInputMultiplexer;
import com.payne.games.inputProcessors.MyInputProcessor;


public class GameScreen implements Screen {
    private final AriseDeeper game;
    private final AssetManager assets;
    private OrthographicCamera camera;

    // ui
    private Stage uiStage = new Stage(); // todo: add `new ScreenViewport()` parameter?
    private Table ui      = new Table();
    private Skin skin     = new Skin(Gdx.files.internal(GameLogic.SKIN_FILE));
    private Array<ImageTextButton> inventorySlots = new Array<>();

    // controllers
    private Controller controller;
    private float currTime = 0f; // turn system


    public GameScreen(final AriseDeeper game, final AssetManager assets) {
        System.out.println("game constructor");
        this.game = game;
        this.assets = assets;

        setUpGameScreen();
    }


    /**
     * Order is important.
     */
    private void setUpGameScreen() {
        setUpUi(); // ui
        setUpCamera(); // create the camera and the SpriteBatch
        controller = new Controller(game, this, assets, camera, inventorySlots); // controller
        setUpMap(); // generate the initial level and place the GameObjects (hero, etc.)
    }

    private void setUpUi() {
//        ui.debugTable();
        ui.setFillParent(true);

        setUpInventoryUi();

        uiStage.addActor(ui);
    }

    private void setUpInventoryUi() {
        ui.bottom();
        ui.setTouchable(Touchable.childrenOnly);

        final int SIZE = 3 * GameLogic.TILE_SIZE;
        Table container = new Table();
        container.setTouchable(Touchable.enabled);
        container.defaults().prefSize(SIZE).minSize(SIZE);
        ImageTextButton currSlot;
        for(int i=0; i<GameLogic.INV_SLOTS; i++) {
            inventorySlots.add(new ImageTextButton(""+i, skin));
            currSlot = inventorySlots.get(i);
            currSlot.setStyle(new ImageTextButton.ImageTextButtonStyle(currSlot.getStyle()));
            currSlot.getImageCell().grow();
            currSlot.getLabel().setFontScale(.8f);
            currSlot.getLabelCell().left();
            container.add(currSlot);
        }

        final Value VAL = Value.percentWidth(0.2f, ui);
        ui.add(container).prefWidth(VAL).minWidth(VAL);
    }

    private void setUpCamera() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, GameLogic.GAME_WIDTH, GameLogic.GAME_HEIGHT);
        camera.zoom = GameLogic.CAM_ZOOM;
    }

    private void setUpMap() {
        controller.generateLevel(64, 32, new BasicTileset());
    }

    private void setUpInputProcessors() {
        MyInputProcessor inputProcessor1  = new MyInputProcessor(camera, controller);
        MyGestureListener inputProcessor2 = new MyGestureListener(camera, controller);
        Gdx.input.setInputProcessor(new MyInputMultiplexer(
                uiStage,
                inputProcessor1,
                new GestureDetector(inputProcessor2)));
    }

    /**
     * Called when the player dies.
     */
    public void playerDied() {
        // todo: stuff
        dispose();
        game.setScreen(new MainMenuScreen(game, assets));
    }

    @Override
    public void show() {
        System.out.println("game show");
        Gdx.gl.glClearColor(0, 0, 0, 1); // black background
        setUpInputProcessors();
    }

    /**
     * Performance tuning:<br>
     *
     * SpriteBatch has a constructor that sets the maximum number of sprites that can be buffered before sending
     * to the GPU. If this is too low, it will cause extra calls to the GPU. If this is too high, the SpriteBatch
     * will be using more memory than is necessary.<br>
     *
     * SpriteBatch has a public int field named maxSpritesInBatch. This indicates the highest number of sprites
     * that were sent to the GPU at once over the lifetime of the SpriteBatch. Setting a very large SpriteBatch
     * size and then checking this field can help determine the optimum SpriteBatch size. It should be sized equal
     * to or slightly more than maxSpritesInBatch. This field may be set to zero to reset it at any time.<br>
     *
     * SpriteBatch has a public int field named renderCalls. After end is called, this field indicates how many
     * times a batch of geometry was sent to the GPU between the last calls to begin and end. This occurs when a
     * different texture must be bound, or when the SpriteBatch has cached enough sprites to be full. If the
     * SpriteBatch is sized appropriately and renderCalls is large (more than maybe 15-20), it indicates that
     * many texture binds are occurring.<br>
     *
     * SpriteBatch has an additional constructor that takes a size and a number of buffers. This is an advanced
     * feature that causes vertex buffer objects (VBOs) to be used rather than the usual vertex arrays (VAs).
     * A list of buffers is kept, and each render call uses the next buffer in the list (wrapping around).
     * When maxSpritesInBatch is low and renderCalls is large, this may provide a small performance boost.
     *
     * @param delta Time spent since last render call.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // clear the screen


        /* Turn system. */
        if(currTime >= GameLogic.TURN_TIME) {
            currTime = 0f;
            controller.processTurn();
            controller.updateLighting();
            controller.updateUi();
        } else {
            currTime += delta;
        }


        /* Setting up the rendering. */
        camera.update(); // tell the camera to update its matrices
        game.batch.setProjectionMatrix(camera.combined); // render in the coord system specified by camera


        /* Actual map rendering. */
        game.batch.begin();
        controller.renderLevel(game.batch);

        /* Debugging text. */
        game.font.draw(game.batch,
                "MaxSpritesBatch: " + game.batch.maxSpritesInBatch
                + " | seed: " + GameLogic.RANDOM_SEED
                + " | fps: " + Gdx.graphics.getFramesPerSecond(), 4, 14); // at the bottom-left of the screen

        game.batch.end();


        /* Drawing the UI over the map. */
        uiStage.act(delta);
        uiStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        System.out.println("game resize - width: " + width + " | height: " + height);
//        /*
//        The following resize strategy will ensure that you will always see
//        30 units in the x axis no matter what pixel-width your device has.
//         */
//        camera.viewportWidth  = 30f;                // Viewport of 30 units!
//        camera.viewportHeight = 30f * height/width; // Lets keep things in proportion.
//        camera.update();

//        /* The following resize strategy will show less/more of the world depending on the resolution. */
//        camera.viewportWidth  = width/32f;  //We will see width/32f units!
//        camera.viewportHeight = camera.viewportWidth * height/width;
//        camera.update();

        uiStage.getViewport().update(width, height, true); // todo: no idea if necessary
    }

    /**
     * On Android this method is called when the Home button is pressed or an incoming call is received.
     * On desktop this is called just before dispose() when exiting the application.
     * A good place to save the game state.
     */
    @Override
    public void pause() {
        System.out.println("game pause");
    }

    /**
     * This method is only called on Android, when the application resumes from a paused state.
     */
    @Override
    public void resume() {
        System.out.println("game resume");
    }

    @Override
    public void hide() {
        System.out.println("game hide");
    }

    /**
     * Called when the application is destroyed.
     * It is preceded by a call to pause().
     */
    @Override
    public void dispose() {
        System.out.println("game dispose");
        uiStage.dispose();
        skin.dispose();
        controller.dispose();
    }
}
