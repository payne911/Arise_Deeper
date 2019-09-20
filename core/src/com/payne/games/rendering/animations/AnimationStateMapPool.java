package com.payne.games.rendering.animations;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.payne.games.assets.AssetsPool;
import com.payne.games.gameObjects.actors.ActorState;

import java.util.HashMap;


public class AnimationStateMapPool {
    public final HashMap<ActorState, Animation<TextureRegion>> heroAnimations;
    public final HashMap<ActorState, Animation<TextureRegion>> knightAnimations;



    public AnimationStateMapPool(AssetsPool assetsPool) {

        /* Hero */
        heroAnimations = new HashMap<>();
        heroAnimations.put(ActorState.IDLE, assetsPool.hero_red_idle);
        heroAnimations.put(ActorState.MOVING, assetsPool.hero_red_walk);
        heroAnimations.put(ActorState.ATTACKING, assetsPool.hero_red_attack);
        heroAnimations.put(ActorState.ATTACKED, null); // todo: draw this one ?
        heroAnimations.put(ActorState.INTERACTING, assetsPool.hero_red_interact);
        heroAnimations.put(ActorState.DYING, assetsPool.hero_red_death);
        heroAnimations.put(ActorState.SLEEPING, assetsPool.hero_red_death);

        /* Knight */
        knightAnimations = new HashMap<>();
        Animation<TextureRegion> placeHolderAnim = new Animation<>(0.25f, assetsPool.knight);
        knightAnimations.put(ActorState.IDLE, placeHolderAnim);
        knightAnimations.put(ActorState.MOVING, placeHolderAnim);
        knightAnimations.put(ActorState.ATTACKING, placeHolderAnim);
        knightAnimations.put(ActorState.ATTACKED, null); // todo: draw this one ?
        knightAnimations.put(ActorState.INTERACTING, placeHolderAnim);
        knightAnimations.put(ActorState.DYING, placeHolderAnim);
        knightAnimations.put(ActorState.SLEEPING, placeHolderAnim);

    }
}
