package com.payne.games.logic;

import com.badlogic.gdx.math.GridPoint2;
import com.payne.games.gameObjects.GameObject;
import com.payne.games.map.BaseMapLayer;
import com.payne.games.map.tiles.Tile;


public class MovementSystem {
    private GameLogic gLogic;


    public MovementSystem(GameLogic gameLogic) {
        this.gLogic = gameLogic;
    }


    public void moveLeft(GameObject object, BaseMapLayer level) {
        GridPoint2 delta = new GridPoint2(-1, 0);
        tryMove(delta, object, level);
    }

    public void moveRight(GameObject object, BaseMapLayer level) {
        GridPoint2 delta = new GridPoint2(1, 0);
        tryMove(delta, object, level);
    }

    public void moveUp(GameObject object, BaseMapLayer level) {
        GridPoint2 delta = new GridPoint2(0, 1);
        tryMove(delta, object, level);
    }

    public void moveDown(GameObject object, BaseMapLayer level) {
        GridPoint2 delta = new GridPoint2(0, -1);
        tryMove(delta, object, level);
    }

    private void tryMove(GridPoint2 delta, GameObject object, BaseMapLayer level) {
        Tile movingTo = level.getTile(object.getX() + delta.x, object.getY() + delta.y);
        interact(movingTo, object);
        tryToMoveTo(movingTo, object, delta);
    }

    private void tryToMoveTo(Tile movingTo, GameObject object, GridPoint2 delta) {
        if (movingTo.isAllowingMove()) {
            object.setY(object.getY() + delta.y);
            object.setX(object.getX() + delta.x);
        }
    }

    private void interact(Tile movingTo, GameObject object) {
        if (movingTo.canInteract(object))
            movingTo.interact(object);
    }
}
