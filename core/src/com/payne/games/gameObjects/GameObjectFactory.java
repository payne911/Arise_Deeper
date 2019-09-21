package com.payne.games.gameObjects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.RandomXS128;
import com.payne.games.assets.AssetsPool;
import com.payne.games.gameObjects.actors.Actor;
import com.payne.games.gameObjects.actors.ActorState;
import com.payne.games.gameObjects.actors.entities.*;
import com.payne.games.gameObjects.statics.Static;
import com.payne.games.gameObjects.statics.entities.*;
import com.payne.games.inventory.Inventory;
import com.payne.games.logic.DecisionMaking;
import com.payne.games.logic.GameLogic;
import com.payne.games.actions.ActionController;
import com.payne.games.map.tiles.Tile;
import com.payne.games.rendering.animations.AnimationStateMapPool;
import com.payne.games.rendering.animations.IAnimatedStates;

import java.util.HashMap;


/**
 * Factory that helps decoupling the visual representation of secondary objects from the logical layers.
 */
public class GameObjectFactory {
    private RandomXS128 rand;
    private ActionController actionController;

    private final AnimationStateMapPool animationPool;
    private final AssetsPool assetsPool;


    public GameObjectFactory(ActionController actionController, AssetsPool assetsPool) {
        this.animationPool = new AnimationStateMapPool(assetsPool);
        this.actionController = actionController;
        this.assetsPool = assetsPool;
        this.rand = new RandomXS128(GameLogic.RANDOM_ENEMIES ? (int)(Math.random()*1000) : GameLogic.RANDOM_SEED);
    }


    /**
     * Updates the walkability of the Tile on which the GameObject was created.<br>
     * Also adds the GameObject to the appropriate MapLayer list, and sets up the Observers.
     *
     * @param gameObject the GameObject being created.
     */
    private void commonCreator(GameObject gameObject) {

        /* Update walkability and visibility of the Tile on which GameObject was created. */
        Tile currTile = actionController.baseMapLayer.getTile(gameObject.getX(),gameObject.getY());
        currTile.setAllowingMove(gameObject.canBeWalkedThrough());
        currTile.setSeeThrough(gameObject.canBeSeenThrough());
        
        /* Add the GameObject to the proper Layer. */
        if(gameObject instanceof Actor) {
            actionController.secondaryMapLayer
                    .getActorLayer().add((Actor)gameObject);
        } else if(gameObject instanceof Static){
            actionController.secondaryMapLayer
                    .getStaticLayer().add((Static)gameObject);
        }

        /* Sets up the slight offset in case the TextureRegion is bigger than the minimal Tile size. */
        if(gameObject.getTexture().getRegionWidth() != GameLogic.TILE_SIZE)
            gameObject.setPermanentOriginOffset(GameLogic.TILE_OFFSET);

        /* Sets up the Observer pattern. */
        actionController.initObservers(gameObject);
    }

    /**
     * Ordered sequence of initializing methods for an State-based animation.
     *
     * @param animated the entity to be animated.
     * @param state its initial state.
     * @param animMap a mapping between each state and its corresponding animation.
     */
    private void initAnimatedState(IAnimatedStates<ActorState> animated,
                                   ActorState state,
                                   HashMap<ActorState, Animation<TextureRegion>> animMap) {
        animated.setAnimationMap(animMap);
        animated.setState(state);
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

        return spawned;
    }


    public Door createDoor(int x, int y, boolean isLocked) {
        Door door = new Door(actionController, x, y, isLocked);

        /* Assigns the Assets to be able to swap between them according to the Door's state. */
        door.getStates().addAll(assetsPool.door_open, assetsPool.door_closed, assetsPool.door_locked);
        door.getStates().shrink();

        commonCreator(door);
        return door;
    }

    public HealthPotion createHealthPotion(int x, int y) {
        HealthPotion hp = new HealthPotion(actionController, x, y);
        hp.setTexture(assetsPool.potion_health);

        commonCreator(hp);
        return hp;
    }

    public Flame createFlame(int x, int y) {
        Flame flame = new Flame(actionController, x, y);
        flame.setAnimation(assetsPool.flame);

        commonCreator(flame);
        return flame;
    }

    public Key createKey(int x, int y) {
        Key key = new Key(actionController, x, y);
        key.setTexture(assetsPool.key);

        commonCreator(key);
        return key;
    }

    public Chest createChest(int x, int y) {
        Chest chest = new Chest(actionController, x, y);
        chest.setTexture(assetsPool.chest_16p);

        commonCreator(chest);
        return chest;
    }

    public Enemy createEnemy(int x, int y) {
        DecisionMaking ai = new DecisionMaking(actionController);
        Enemy enemy = new Enemy(actionController, x, y, 50, 2, 5, 50, rand.nextBoolean(), ai);
        initAnimatedState(enemy, ActorState.SLEEPING, animationPool.knightAnimations);

        commonCreator(enemy);
        return enemy;
    }

    public Hero createHero(int x, int y, Inventory inventory) {
        Hero hero = new Hero(actionController, x, y, 150, 3, 5);
        hero.setInventory(inventory);
        initAnimatedState(hero, ActorState.IDLE, animationPool.heroAnimations);

        /* The Hero is somewhat special so its construction is slightly different. */
        hero.setPermanentOriginOffset(GameLogic.TILE_OFFSET);
        actionController.initObservers(hero);

        return hero;
    }

}
