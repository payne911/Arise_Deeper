package com.payne.games.gameObjects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.payne.games.map.renderers.IRenderable;


/**
 * Anything interactive, that is on top of a Tile.
 */
public abstract class GameObject implements IRenderable {
    private int x, y;
    private TextureRegion texture;


    public GameObject(int x, int y) {
        this.x = x;
        this.y = y;
    }


    /**
     * An interaction with a Pickable item would be to pick it up.<br>
     * An interaction with an Enemy would be to attack it.<br>
     * An interaction with an NPC would be to pop up a dialog.<br>
     * An interaction with a Door would be to try to open it.
     *
     * @return 'true' if the interaction was successful.
     */
    public abstract boolean interact();


    public boolean renderInFog() {
        return false;
    }

    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }

    public TextureRegion getTexture() {
        return texture;
    }
    public void setTexture(TextureRegion texture) {
        this.texture = texture;
    }

    @Override
    public String toString() {
        return "GameObject{" +
                "x=" + x +
                ", y=" + y +
                ", texture=" + texture +
                '}';
    }
}
