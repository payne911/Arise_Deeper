package com.payne.games.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.RandomXS128;
import com.payne.games.gameObjects.actors.Enemy;
import com.payne.games.gameObjects.actors.Hero;
import com.payne.games.gameObjects.statics.Chest;
import com.payne.games.gameObjects.statics.Key;
import com.payne.games.logic.DecisionMaking;
import com.payne.games.logic.GameLogic;
import com.payne.games.logic.systems.ActionSystem;


/**
 * Factory that helps decoupling the visual representation of secondary objects from the logical layers.
 */
public class GameObjectFactory {
    private RandomXS128 rand;
    private ActionSystem actionSystem;

    private Texture objects_20p;
    private Texture objects_16p;
    private TextureRegion[][] split_20p;
    private TextureRegion[][] split_16p;


    public GameObjectFactory(ActionSystem actionSystem) {
        this.actionSystem = actionSystem;
        this.rand = new RandomXS128(GameLogic.RANDOM_ENEMIES ? (int)(Math.random()*1000) : GameLogic.RANDOM_SEED);

        objects_20p = new Texture(Gdx.files.internal("game_objects_20p.png"));
        split_20p = TextureRegion.split(objects_20p, GameLogic.TILE_BIG_WIDTH, GameLogic.TILE_BIG_HEIGHT);

        objects_16p = new Texture(Gdx.files.internal("game_objects_16p.png"));
        split_16p = TextureRegion.split(objects_16p, GameLogic.TILE_WIDTH, GameLogic.TILE_HEIGHT);
    }


    public Chest createChest(int x, int y) {
        Chest chest = new Chest(x, y);
        chest.setTexture(split_16p[0][2]);
        return chest;
    }

    public Hero createHero(int x, int y) {
        Hero hero = new Hero(x, y, 150, 3, 5);
        hero.setTexture(split_20p[0][0]);
        return hero;
    }

    public Enemy createEnemy(int x, int y) {
        DecisionMaking ai = new DecisionMaking(actionSystem);
        Enemy enemy = new Enemy(x, y, 50, 2, 5, 50, rand.nextBoolean(), ai);
        enemy.setTexture(split_20p[0][7]);
        return enemy;
    }

    public Key createKey(int x, int y) {
        Key key = new Key(x, y);
        key.setTexture(split_16p[0][3]);
        return key;
    }

    /**
     * Clears the GPU's memory properly.
     */
    public void dispose() {
        objects_20p.dispose();
        objects_16p.dispose();
    }

}
