package com.payne.games.utils;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.payne.games.map.MapController;

import java.util.Arrays;


/**
 * The `keyTyped(char character)` method below will be called repeatedly if a key is KEPT DOWN.
 */
public class MyInputProcessor extends InputAdapter {
    private OrthographicCamera camera;
    private MapController mapController;
    private int down_X, down_Y; // used for dragging the map


    public MyInputProcessor(OrthographicCamera camera, MapController mapController) {
        this.camera = camera;
        this.mapController = mapController;
    }


    @Override
    public boolean keyDown(int keycode) {
        System.out.println("keyDown_keyCode: " + keycode);

        // todo: just here for testing purposes... remove eventually
        if(keycode == 62 && mapController.getCurrentLevel() != null) {
            mapController.setTile(3, 0 ,0); // bottom-left tile becomes a random door
        }

        return true;
    }

    /**
     * Mouse-click.
     * (0,0) is at top-left.
     *
     * @param screenX Absolute x position, in pixel.
     * @param screenY Absolute y position, in pixel.
     * @param pointer ?
     * @param button left-click = 0, right-click = 1
     * @return if handled.
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        down_X = screenX;
        down_Y = screenY;
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        int dX = down_X - screenX;
        down_X = screenX;
        int dY = screenY - down_Y;
        down_Y = screenY;
        camera.translate(dX, dY); // todo: adjust according to Zoom !
        return true;
    }

    /**
     * Mouse-wheel scroll.
     * todo: For mobile device, use in conjunction with `InputMultiplexer` and `GestureDetector` to get the pinch.
     * @param amount Either 1 or -1, based on the direction of the scroll.
     * @return If handled.
     */
    @Override
    public boolean scrolled(int amount) {
        camera.zoom += ((double)amount)/4;
        if (camera.zoom <= 0) camera.zoom -= ((double)amount)/4; // deny zooming too much
        return true;
    }
}
