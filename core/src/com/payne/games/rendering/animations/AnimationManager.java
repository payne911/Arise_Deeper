package com.payne.games.rendering.animations;

import java.util.ArrayList;
import java.util.List;


public class AnimationManager implements IObserver<IAnimated> {
    private List<IAnimated> animations = new ArrayList<>();


    public AnimationManager() {
    }

    public void updateAnimations(float delta) {
        for(IAnimated animation : animations) {
            animation.incrementDelta(delta);
            animation.setTexture(animation.getAnimation().getKeyFrame(animation.getDelta()));
        }
    }

    @Override
    public void update() {

    }

    @Override
    public void add(IAnimated listed) {
        animations.add(listed);
    }
}
