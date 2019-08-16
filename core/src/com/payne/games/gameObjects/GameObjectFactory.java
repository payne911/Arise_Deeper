package com.payne.games.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.payne.games.logic.GameLogic;


/**
 * Factory that helps decoupling the visual representation of secondary objects from the logical layers.
 */
public class GameObjectFactory {
    private GameLogic gLogic;

    private Texture objects_20p;
    private Texture objects_16p;
    private TextureRegion[][] split_20p;
    private TextureRegion[][] split_16p;


    public GameObjectFactory(GameLogic gameLogic) {
        this.gLogic = gameLogic;

        objects_20p = new Texture(Gdx.files.internal("game_objects_20p.png"));
        split_20p = TextureRegion.split(objects_20p, gLogic.TILE_BIG_WIDTH, gLogic.TILE_BIG_HEIGHT);

        objects_16p = new Texture(Gdx.files.internal("game_objects_16p.png"));
        split_16p = TextureRegion.split(objects_16p, gLogic.TILE_WIDTH, gLogic.TILE_HEIGHT);
    }


    public Chest createChest(int x, int y) {
        Chest chest = new Chest(x, y);
        chest.setTexture(split_16p[0][2]);
        return chest;
    }

    public Hero createHero(int x, int y) {
        Hero hero = new Hero(x, y);
        hero.setTexture(split_20p[0][0]);
        return hero;
    }

    public Hero createEnemy(int x, int y) {
        Hero hero = new Hero(x, y);
        hero.setTexture(split_20p[0][7]);
        return hero;
    }

    public Key createKey(int x, int y) {
        Key key = new Key(x, y);
        key.setTexture(split_16p[0][3]);
        return key;
    }

}
