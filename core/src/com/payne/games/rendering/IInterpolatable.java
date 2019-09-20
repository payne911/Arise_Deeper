package com.payne.games.rendering;

import com.payne.games.rendering.animations.IObservable;

/**
 * Allows a movement to be non-instantaneous, passing through
 * intermediary coordinates within the grid's tile system.
 */
public interface IInterpolatable extends IRenderable, IObservable {

    /**
     * @return Amount of pixels from the source movement, in the direction
     * of the movement, since the move began.
     */
    float getInterpolatedX();

    /**
     * @return Amount of pixels from the source movement, in the direction
     * of the movement, since the move began.
     */
    float getInterpolatedY();

    /**
     * The "interpolated" value is the amount of pixels from the source movement,
     * in the direction of the movement, since the move began.
     *
     * @param interpolatedX how far from the initial point the entity now is.
     */
    void setInterpolatedX(float interpolatedX);

    /**
     * The "interpolated" value is the amount of pixels from the source movement,
     * in the direction of the movement, since the move began.
     *
     * @param interpolatedY how far from the initial point the entity now is.
     */
    void setInterpolatedY(float interpolatedY);

    /**
     * @return The tile-coordinate the entity is currently moving toward.
     */
    int getMovingToX();

    /**
     * @return The tile-coordinate the entity is currently moving toward.
     */
    int getMovingToY();

    /**
     * @param movingToX The "movingTo" value is the tile-coordinate where the entity wants to go to.
     */
    void setMovingToX(int movingToX);

    /**
     * @param movingToY The "movingTo" value is the tile-coordinate where the entity wants to go to.
     */
    void setMovingToY(int movingToY);

    /**
     * @return Pixel-value used for drawing the entity, at any given moment
     * (thus includes the intermediary positions).
     */
    float getCurrentX();

    /**
     * @return Pixel-value used for drawing the entity, at any given moment
     * (thus includes the intermediary positions).
     */
    float getCurrentY();
}
