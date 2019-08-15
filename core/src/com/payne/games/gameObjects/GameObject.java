package com.payne.games.gameObjects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.payne.games.map.renderers.IRenderable;


public abstract class GameObject implements IRenderable {
    private int x, y;
    private TextureRegion texture;


    public GameObject(int x, int y) {
        this.x = x;
        this.y = y;
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
}
