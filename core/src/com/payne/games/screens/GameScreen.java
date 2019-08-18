package com.payne.games.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.payne.games.AriseDeeper;
import com.payne.games.logic.GameLogic;
import com.payne.games.logic.MapController;
import com.payne.games.map.tilesets.BasicTileset;
import com.payne.games.inputProcessors.MyGestureListener;
import com.payne.games.inputProcessors.MyInputMultiplexer;
import com.payne.games.inputProcessors.MyInputProcessor;


public class GameScreen implements Screen {
    private final AriseDeeper game;
    private OrthographicCamera camera;

    private GameLogic gLogic;
    private MapController mapController;

    private float currTime = 0f; // turn system


    public GameScreen(final AriseDeeper game) {
        this.game = game;
        this.gLogic = new GameLogic(game);

        Gdx.gl.glClearColor(0, 0, 0, 1); // black background

        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, gLogic.GAME_WIDTH, gLogic.GAME_HEIGHT);
        camera.zoom = gLogic.CAM_ZOOM;

        this.mapController = new MapController(gLogic, camera);

        // generate the initial level and place the GameObjects (hero, etc.)
        mapController.generateLevel(64, 32, new BasicTileset(gLogic));

        // input processors
        MyInputProcessor inputProcessor1  = new MyInputProcessor(gLogic, camera, mapController);
        MyGestureListener inputProcessor2 = new MyGestureListener(gLogic, camera, mapController);
        Gdx.input.setInputProcessor(new MyInputMultiplexer(
                inputProcessor1,
                new GestureDetector(inputProcessor2)));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // clear the screen


        /* Turn system. */
        if(currTime >= gLogic.TURN_TIME) {
            currTime = 0f;
            mapController.processTurn();
            mapController.updateLighting();
        } else {
            currTime += delta;
        }


        /* Setting up the rendering. */
        camera.update(); // tell the camera to update its matrices
        game.batch.setProjectionMatrix(camera.combined); // render in the coord system specified by camera


        /* Actual map rendering. */
        game.batch.begin();
        mapController.renderLevel(game.batch);

        /*
        This indicates the highest number of sprites that were sent to the GPU at once over the lifetime
        of the SpriteBatch. Setting a very large SpriteBatch size and then checking this field can help
        determine the optimum SpriteBatch size. It should be sized equal to or slightly more than
        maxSpritesInBatch. This field may be set to zero to reset it at any time.
         */
        game.font.draw(game.batch,
                "MaxSpritesBatch: " + game.batch.maxSpritesInBatch
                + " | seed: " + gLogic.getSeed()
                + " | fps: " + Gdx.graphics.getFramesPerSecond(), 4, 14); // at the bottom-left of the screen

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
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
    }

    /**
     * On Android this method is called when the Home button is pressed or an incoming call is received.
     * On desktop this is called just before dispose() when exiting the application.
     * A good place to save the game state.
     */
    @Override
    public void pause() {

    }

    /**
     * This method is only called on Android, when the application resumes from a paused state.
     */
    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    /**
     * Called when the application is destroyed.
     * It is preceded by a call to pause().
     */
    @Override
    public void dispose() {

    }
}
