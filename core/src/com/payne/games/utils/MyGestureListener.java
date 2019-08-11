package com.payne.games.utils;

import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;


/**
 * Left-click:    Button = 0
 * Right-click:   Button = 1
 */
public class MyGestureListener implements GestureDetector.GestureListener {



    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        System.out.println("touchDown: " + x + "," + y + " | pointer: " + pointer + " | button: " + button);
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        System.out.println("tap: " + x + "," + y + " | count: " + count + " | button: " + button);
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        System.out.println("longPress: " + x + "," + y);
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        System.out.println("fling | velocityX: " + velocityX + " | velocityY: " + velocityY + " | button: " + button);
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        System.out.println("pan: " + x + "," + y + " | deltaX: " + deltaX + " | deltaY: " + deltaY);
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        System.out.println("panStop: " + x + "," + y + " | pointer: " + pointer + " | button: " + button);
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        System.out.println("zoom | initialDistance: " + initialDistance + " | distance: " + distance);
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        System.out.println("pinch");
        return false;
    }

    @Override
    public void pinchStop() {
        System.out.println("pinchStop");
    }
}
