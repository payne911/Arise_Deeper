package com.payne.games.map.renderers;

import com.badlogic.gdx.graphics.g2d.TextureRegion;


public interface IRenderable {

    /**
     * @return 'false' only if the `MapRenderer` should not draw the object's texture when not within LoS of the hero.
     */
    boolean renderInFog();

    int getX();
    int getY();
    TextureRegion getTexture();
    void setTexture(TextureRegion texture);
}
