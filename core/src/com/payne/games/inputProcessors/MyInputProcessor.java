package com.payne.games.inputProcessors;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.payne.games.logic.GameLogic;
import com.payne.games.logic.MapController;


/**
 * The `keyTyped(char character)` method will be called repeatedly if a key is KEPT DOWN.
 *
 */
public class MyInputProcessor extends InputAdapter {
    private GameLogic gLogic;
    private OrthographicCamera camera;
    private MapController mapController;
    private int down_relativePixelCoord_TL_X, down_relativePixelCoord_TL_Y; // used for dragging the map


    public MyInputProcessor(GameLogic gameLogic, OrthographicCamera camera, MapController mapController) {
        this.gLogic = gameLogic;
        this.camera = camera;
        this.mapController = mapController;
    }


    @Override
    public boolean keyDown(int keycode) {

        switch (keycode) {
            case 62: // space    todo: should TOGGLE a centerView option
                mapController.centerOnHero();
                break;
            default:
                System.out.println("keyDown_keyCode: " + keycode);
                break;
        }

        return true;
    }

    /**
     * Mouse-click.
     * (0,0) is at top-left of window.
     *
     * @param screenX Relative x position, in pixel. (range: [0, viewportWidth-1])
     * @param screenY Relative y position, in pixel.
     * @param pointer ?
     * @param button left-click = 0, right-click = 1
     * @return if handled.
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        down_relativePixelCoord_TL_X = screenX;
        down_relativePixelCoord_TL_Y = screenY;
        return false;
    }

    /**
     * (0,0) is at the top-left.
     * todo: necessary to implement in `MyGestureListener.pan()` for mobile compatibility?
     *
     * @param screenX relative pixel x-coordinate, where the mouse is currently.
     * @param screenY relative pixel y-coordinate, where the mouse is currently.
     * @param pointer
     * @return
     */
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        int dX = down_relativePixelCoord_TL_X - screenX; // ends up being a difference of 1 pixel
        down_relativePixelCoord_TL_X = screenX;

//        // todo: prevent drag outside of map regions
//        if(camera.position.x + dX > camera.viewportWidth/2) { // prevent going further left
//            System.out.println("YES");
//            down_relativePixelCoord_TL_X = screenX;
//        } else {
//            System.out.println("NO");
//            dX = 0;
//        }

        int dY = screenY - down_relativePixelCoord_TL_Y; // ends up being a difference of 1 pixel
        down_relativePixelCoord_TL_Y = screenY;

        camera.translate(dX*camera.zoom, dY*camera.zoom);
        return false;
    }

    /**
     * Mouse-wheel scroll.
     *
     * @param amount Either 1 or -1, based on the direction of the scroll.
     * @return If handled.
     */
    @Override
    public boolean scrolled(int amount) {
        float tmp = camera.zoom + (float)amount/4;
        if (tmp > 0 && tmp < 2)
            camera.zoom = tmp;
        return true;
    }
}
