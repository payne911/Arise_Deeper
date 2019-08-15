package com.payne.games.map.renderers;

import com.badlogic.gdx.graphics.g2d.TextureRegion;


public interface IRenderable {
    int getX();
    int getY();
    TextureRegion getTexture();
    void setTexture(TextureRegion texture);
}
