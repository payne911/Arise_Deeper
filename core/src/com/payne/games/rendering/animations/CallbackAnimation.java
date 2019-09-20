package com.payne.games.rendering.animations;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;


public class CallbackAnimation extends Animation<TextureRegion> {

    public CallbackAnimation(float frameDuration, Array<TextureRegion> keyFrames, PlayMode playMode) {
        super(frameDuration, keyFrames, playMode);
    }

}
