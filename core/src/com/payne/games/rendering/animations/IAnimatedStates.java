package com.payne.games.rendering.animations;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;


/**
 * Used for any IRenderable that must be animated with different animations based on its current State.
 * @param <E> What designates the State. It is suggested to have an Enum for that.
 */
public interface IAnimatedStates<E> extends IAnimated {
    void setAnimationMap(HashMap<E, Animation<TextureRegion>> animationMap);
    void setState(E newState);
}
