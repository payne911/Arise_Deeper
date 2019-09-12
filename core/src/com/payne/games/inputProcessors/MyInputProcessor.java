package com.payne.games.inputProcessors;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.payne.games.logic.Controller;
import com.payne.games.logic.GameLogic;


/**
 * This is more or less the "Desktop" input processor.
 * <br>
 * The `keyTyped(char character)` method will be called repeatedly if a key is KEPT DOWN.
 */
public class MyInputProcessor extends InputAdapter {
    private OrthographicCamera camera;
    private Controller controller;


    public MyInputProcessor(OrthographicCamera camera, Controller controller) {
        this.camera = camera;
        this.controller = controller;
    }


    @Override
    public boolean keyDown(int keycode) {

        switch (keycode) {
            case Input.Keys.SPACE: // todo: should TOGGLE a centerView option?
//                controller.centerOnHero();
                GameLogic.DEBUG_NO_FOG = !GameLogic.DEBUG_NO_FOG;
                break;
            case Input.Keys.ESCAPE:
                controller.saveAndReturnToMainMenu();
                break;
            case Input.Keys.S:
                controller.DEBUG_spawn_enemies();
                break;
            default:
                System.out.println("keyDown_keyCode: " + keycode);
                break;
        }

        return true;
    }

    /**
     * Mouse-wheel scroll.
     *
     * @param amount Either 1 or -1, based on the direction of the scroll.
     * @return 'true' only if the event shouldn't be passed to the next InputProcessor.
     */
    @Override
    public boolean scrolled(int amount) {
        float tmp = camera.zoom + (float)amount/4;
        if (tmp > 0 && tmp < 2)
            camera.zoom = tmp;
        return true;
    }
}
