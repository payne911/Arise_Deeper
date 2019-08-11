package com.payne.games.map.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;


public abstract class Tile {

    private int x, y;
    private TextureRegion texture;

    public Tile(int x, int y){
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
