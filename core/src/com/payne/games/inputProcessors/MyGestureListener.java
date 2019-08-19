package com.payne.games.inputProcessors;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.payne.games.logic.GameLogic;
import com.payne.games.logic.MapController;


/**
 * The `float x` are the same as MyInputProcessor's `screenX` and others.
 *
 * Left-click:              Button = 0
 * Right-click:             Button = 1
 * Middle-scroll-click:     Button = 2
 */
public class MyGestureListener implements GestureDetector.GestureListener {
    private OrthographicCamera camera;
    private MapController mapController;

    private final boolean DEBUG_PRINT = false;


    public MyGestureListener(OrthographicCamera camera, MapController mapController) {
        this.camera = camera;
        this.mapController = mapController;
    }


    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        if(DEBUG_PRINT) System.out.println("touchDown: " + x + "," + y + " | pointer: " + pointer + " | button: " + button);
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        if(DEBUG_PRINT) System.out.println("tap: " + x + "," + y + " | count: " + count + " | button: " + button);

        final double OFFSET = GameLogic.AESTHETIC_OFFSET;
        final double ZOOM = camera.zoom;

        double coordX = (camera.position.x + x*ZOOM - OFFSET - ((double)camera.viewportWidth/2)*ZOOM)  / GameLogic.TILE_WIDTH;
        double coordY = (camera.position.y - y*ZOOM - OFFSET + ((double)camera.viewportHeight/2)*ZOOM) / GameLogic.TILE_HEIGHT;

        System.out.println("Tile coordinate: (" + (int)coordX + ", " + (int)coordY + ")");

        // todo: be wiser! tap.location: if monster -> AttackAction, if Door -> DoorAction, if Item -> PickUpAction
        mapController.playerTapped((int)coordX, (int)coordY);

        return true;
    }

    @Override
    public boolean longPress(float x, float y) {
        if(DEBUG_PRINT) System.out.println("longPress: " + x + "," + y);
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        if(DEBUG_PRINT) System.out.println("fling | velocityX: " + velocityX + " | velocityY: " + velocityY + " | button: " + button);
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        if(DEBUG_PRINT) System.out.println("pan: " + x + "," + y + " | deltaX: " + deltaX + " | deltaY: " + deltaY);
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        if(DEBUG_PRINT) System.out.println("panStop: " + x + "," + y + " | pointer: " + pointer + " | button: " + button);
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        if(DEBUG_PRINT) System.out.println("zoom | initialDistance: " + initialDistance + " | distance: " + distance);
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        // todo: implement "Zoom" for mobiles ?
        if(DEBUG_PRINT) System.out.println("pinch");
        return false;
    }

    @Override
    public void pinchStop() {
        if(DEBUG_PRINT) System.out.println("pinchStop");
    }
}
