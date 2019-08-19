package com.payne.games.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.payne.games.AriseDeeper;
import com.payne.games.logic.GameLogic;


/**
 * todo: see   https://ray3k.wordpress.com/software/skin-composer-for-libgdx/        to create custom skin
 * todo: see   https://github.com/czyzby/gdx-skins                                   to choose another pre-made skins
 */
public class MainMenuScreen implements Screen {
    private final AriseDeeper game;

    private Stage stage; // to handle the ui
    private Skin skin; // json style

    // ui elements
    private Table table;
    private TextButton newGameButton;
    private TextButton loadGameButton;
    private TextButton settingsButton;
    private TextButton quitButton;


    public MainMenuScreen(final AriseDeeper game) {
        this.game = game;

        skin = new Skin(Gdx.files.internal(GameLogic.SKIN_FILE));
        stage = new Stage(new ScreenViewport());

        table = new Table();
        initButtons();
        setUpButtonsClickListeners();
        setUpTableLayout();

        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);
    }

    private void initButtons() {
        newGameButton = new TextButton("New Game", skin);
        loadGameButton = new TextButton("Load Game", skin);
        settingsButton = new TextButton("Settings", skin);
        quitButton = new TextButton("Quit", skin);
    }

    private void setUpButtonsClickListeners() {
        newGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
                dispose();
            }
        });
    }

    private void setUpTableLayout() {
//        table.debugTable();

        table.setFillParent(true);
        table.defaults().fillX().pad(20);

        table.add(newGameButton);
        table.row();
        table.add(loadGameButton);
        table.row();
        table.add(settingsButton);
        table.row();
        table.add(quitButton);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
