package com.payne.games.rendering;

import com.badlogic.gdx.utils.Array;
import com.payne.games.logic.GameLogic;

import java.util.HashMap;
import java.util.Iterator;


/**
 * Actuates a fluid movement for its registered GameObjects.
 */
public class InterpolationModule {
    private Array<IInterpolatable> moving = new Array<>();
    private Iterator<IInterpolatable> iterator;
    private IInterpolatable current;
    private HashMap<IInterpolatable, Float> timeSheet = new HashMap<>();
    private float newValue;


    public void moveAllObjects(float delta) {
        iterator = moving.iterator();
        while(iterator.hasNext()) {
            current = iterator.next();
            newValue = timeSheet.get(current) + delta;

            if(newValue >= GameLogic.TURN_TIME)
                finish(current);
            else
                update(current, delta);
        }
    }

    private void update(IInterpolatable moved, float delta) {
        timeSheet.put(moved, newValue);

        float d = GameLogic.MOVE_SPEED * delta;
        float deltaX = (moved.getMovingToX() - moved.getX()) * d;
        float deltaY = (moved.getMovingToY() - moved.getY()) * d;

        moved.setInterpolatedX(moved.getInterpolatedX() + deltaX);
        moved.setInterpolatedY(moved.getInterpolatedY() + deltaY);
    }

    private void finish(IInterpolatable moved) {
        timeSheet.remove(moved);
        iterator.remove();
        moved.setX(moved.getMovingToX());
        moved.setY(moved.getMovingToY());
        moved.setInterpolatedX(0);
        moved.setInterpolatedY(0);
    }

    public void add(IInterpolatable listed) {
        if(!timeSheet.containsKey(listed)) {
            moving.add(listed);
            timeSheet.put(listed, 0f);
        }
    }
}
