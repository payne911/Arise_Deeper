package com.payne.games.map.renderers;

import com.badlogic.gdx.graphics.g2d.TextureRegion;


/**
 * Any object that is part of the level and to be rendered on the screen must implement this Interface.
 */
public interface IRenderable {

    /**
     * @return 'false' only if the `MapRenderer` should not draw the object's texture when not within LoS of the hero.
     */
    boolean renderInFog();

    /**
     * @return The "x" coordinate, in Tile units (not pixels). Bottom-left is the (0,0) coordinate.
     */
    int getX();

    /**
     * @return The "y" coordinate, in Tile units (not pixels). Bottom-left is the (0,0) coordinate.
     */
    int getY();

    /**
     * @return The image that will be used to draw this object at its current location.
     */
    TextureRegion getTexture();

    /**
     * Used to determine what the object looks like, visually.
     *
     * @param texture An image which can be used to draw the object at its current location.
     */
    void setTexture(TextureRegion texture);
}
