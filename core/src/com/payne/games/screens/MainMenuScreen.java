package com.payne.games.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.payne.games.AriseDeeper;
import com.payne.games.logic.GameLogic;


/**
 * todo: see   https://ray3k.wordpress.com/software/skin-composer-for-libgdx/        to create custom skin
 * todo: see   https://github.com/czyzby/gdx-skins                                   to choose another pre-made skins
 */
public class MainMenuScreen implements Screen {
    private final AriseDeeper game;
    private final AssetManager assets;

    private Stage stage; // to handle the ui
    private Skin skin;   // json style

    // ui elements
    private Table table;
    private TextButton newGameButton;
    private TextButton resumeGameButton;
    private TextButton settingsButton;
    private TextButton quitButton;


    public MainMenuScreen(final AriseDeeper game, final AssetManager assets) {
        System.out.println("menu constructor");
        this.game   = game;
        this.assets = assets;

        skin = new Skin(Gdx.files.internal(GameLogic.SKIN_FILE));
        stage = new Stage(new ScreenViewport());
        table = new Table();
        initButtons();
        checkConditionsOnButtons();
        setUpButtonsClickListeners();
        setUpTableLayout();
        stage.addActor(table);
    }

    private void initButtons() {
        newGameButton = new TextButton("New Game", skin);
        resumeGameButton = new TextButton("Resume Game", skin);
        settingsButton = new TextButton("Settings", skin);
        quitButton = new TextButton("Quit", skin);
    }

    /**
     * Makes sure the "Resume Game" button is only available when it should.
     */
    private void checkConditionsOnButtons() {
        if(game.getPreviousScreen() == null) {
            resumeGameButton.setTouchable(Touchable.disabled);
            resumeGameButton.setDisabled(true);
        } else {
            resumeGameButton.setTouchable(Touchable.enabled);
            resumeGameButton.setDisabled(false);
        }
    }

    private void setUpButtonsClickListeners() {
        newGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(game.getPreviousScreen() instanceof GameScreen)
                    game.getPreviousScreen().dispose();
                game.setNewScreen(new GameScreen(game, assets));
            }
        });

        resumeGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.returnToPreviousScreen();
            }
        });
    }

    private void setUpTableLayout() {
//        table.debugTable();

        table.setFillParent(true);
        table.defaults().fillX().pad(20);

        table.add(newGameButton);
        table.row();
        table.add(resumeGameButton);
        table.row();
        table.add(settingsButton);
        table.row();
        table.add(quitButton);
    }

    @Override
    public void show() {
        System.out.println("menu show");
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.input.setInputProcessor(stage);
        checkConditionsOnButtons();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        System.out.println("menu resize - width: " + width + " | height: " + height);
        stage.getViewport().update(width, height, true); // todo: no idea if necessary
    }

    @Override
    public void pause() {
        System.out.println("menu pause");
    }

    @Override
    public void resume() {
        System.out.println("menu resume");
    }

    @Override
    public void hide() {
        System.out.println("menu hide");
    }

    @Override
    public void dispose() {
        System.out.println("menu dispose");
        stage.dispose();
        skin.dispose();
    }
}
