package com.payne.games.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.utils.Array;
import com.payne.games.gameObjects.actors.Actor;
import com.payne.games.gameObjects.actors.Enemy;
import com.payne.games.gameObjects.actors.Hero;
import com.payne.games.gameObjects.statics.*;
import com.payne.games.inventory.Inventory;
import com.payne.games.logic.DecisionMaking;
import com.payne.games.logic.GameLogic;
import com.payne.games.actions.ActionController;


/**
 * Factory that helps decoupling the visual representation of secondary objects from the logical layers.
 */
public class GameObjectFactory {
    private RandomXS128 rand;
    private ActionController actionController;

    private Texture objects_20p;
    private Texture objects_16p;
    private TextureRegion[][] split_20p;
    private TextureRegion[][] split_16p;


    public GameObjectFactory(ActionController actionController) {
        this.actionController = actionController;
        this.rand = new RandomXS128(GameLogic.RANDOM_ENEMIES ? (int)(Math.random()*1000) : GameLogic.RANDOM_SEED);

        objects_20p = new Texture(Gdx.files.internal("spriteSheets/game_objects_20p.png"));
        split_20p = TextureRegion.split(objects_20p, GameLogic.TILE_BIG_WIDTH, GameLogic.TILE_BIG_HEIGHT);

        objects_16p = new Texture(Gdx.files.internal("spriteSheets/game_objects_16p.png"));
        split_16p = TextureRegion.split(objects_16p, GameLogic.TILE_WIDTH, GameLogic.TILE_HEIGHT);
    }


    /**
     * Used to spawn a random Item.
     *
     * @param x x-coordinate, in Tile units.
     * @param y y-coordinate, in Tile units.
     * @return The randomly select Item.
     */
    public Static createRandomItem(int x, int y) {
        float random = rand.nextFloat();

        Static spawned;
        if(random < .5f)
            spawned = createKey(x, y);
        else
            spawned = createHealthPotion(x, y);

        actionController.secondaryMapLayer.getStaticLayer().add(spawned);
        return spawned;
    }


    public Door createDoor(int x, int y, boolean isLocked) {
        Door door = new Door(actionController, x, y, isLocked);

        /* Assigns the Assets to be able to swap between them according to the Door's state. */
        door.getStates().addAll(split_16p[0][5], split_16p[0][4], split_16p[0][6]);
        door.getStates().shrink();

        actionController.baseMapLayer.getTile(x,y).setAllowingMove(false); // all Doors are created as Closed
        actionController.secondaryMapLayer.getStaticLayer().add(door);
        return door;
    }

    public HealthPotion createHealthPotion(int x, int y) {
        HealthPotion hp = new HealthPotion(actionController, x, y);
        hp.setTexture(split_16p[0][7]);
        actionController.secondaryMapLayer.getStaticLayer().add(hp);
        return hp;
    }

    public Flame createFlame(int x, int y) {
        Flame flame = new Flame(actionController, x, y);
        flame.setTexture(split_20p[0][3]);
        actionController.secondaryMapLayer.getStaticLayer().add(flame);
        actionController.baseMapLayer.getTile(x,y).setAllowingMove(false);
        return flame;
    }

    public Key createKey(int x, int y) {
        Key key = new Key(actionController, x, y);
        key.setTexture(split_16p[0][3]);
        actionController.secondaryMapLayer.getStaticLayer().add(key);
        return key;
    }

    public Chest createChest(int x, int y) {
        Chest chest = new Chest(actionController, x, y);
        chest.setTexture(split_16p[0][2]);
        actionController.baseMapLayer.getTile(x,y).setAllowingMove(false);
        actionController.secondaryMapLayer.getStaticLayer().add(chest);
        return chest;
    }

    public Enemy createEnemy(int x, int y) {
        DecisionMaking ai = new DecisionMaking(actionController);
        Enemy enemy = new Enemy(actionController, x, y, 50, 2, 5, 50, rand.nextBoolean(), ai);
        enemy.setTexture(split_20p[0][7]);
        actionController.baseMapLayer.getTile(x,y).setAllowingMove(false);
        actionController.secondaryMapLayer.getActorLayer().add(enemy);
        return enemy;
    }

    public Hero createHero(int x, int y, Inventory inventory) {
        Hero hero = new Hero(actionController, x, y, 150, 3, 5);
        hero.setTexture(split_20p[0][0]);
        hero.setInventory(inventory);
        return hero;
    }


    /**
     * Clears the GPU's memory properly.
     */
    public void dispose() {
        objects_20p.dispose();
        objects_16p.dispose();
    }

}
