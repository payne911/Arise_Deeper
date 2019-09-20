package com.payne.games.rendering.animations;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.payne.games.rendering.IRenderable;


/**
 * To allow an IRenderable to be animated.
 */
public interface IAnimated extends IRenderable, IObservable {
    float getDelta();
    void incrementDelta(float delta);
    Animation<TextureRegion> getAnimation();
}
