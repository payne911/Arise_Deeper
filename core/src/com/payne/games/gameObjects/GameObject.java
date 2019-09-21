package com.payne.games.gameObjects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.payne.games.actions.ActionController;
import com.payne.games.gameObjects.actors.Actor;
import com.payne.games.gameObjects.actors.ActorState;
import com.payne.games.logic.Utils;
import com.payne.games.rendering.IInterpolatable;
import com.payne.games.rendering.animations.IObserver;

import java.util.HashMap;


/**
 * Anything placed on top of a Tile. Usually interactive.<br>
 * Extracts from more specific classes certain things that are related to the View.
 */
public abstract class GameObject implements IInterpolatable {
    private Array<IObserver> observers = new Array<>();
    private int x, y;
    protected ActionController controller; // acts as a Controller linking the GameObjects with the MapLayers

    /* View-related. */
    private int permanentOriginOffset; // used when entity is bigger than a normal Tile
    private float interpolatedX, interpolatedY; // used for interpolated movement's intermediary offset coordinates (in pixels)
    private int movingToX, movingToY; // used for interpolated movement's coordinate goal (in tile-coordinate)
    private TextureRegion texture; // used when drawn on the screen, over the map
    private Drawable drawable; // used by the Inventory for the "Slot" image representation
    private float delta; // used to know which frame of the animation to display
    protected HashMap<ActorState, Animation<TextureRegion>> animationMap;
    private Animation<TextureRegion> animation;


    /**
     * A GameObject can be interacted with, in some way or another.<br>
     * The (0,0) is at the bottom-left.
     *
     * @param controller the Controller that gives access to the different map layers.
     * @param x x-coordinate, in Tile units (not pixels).
     * @param y y-coordinate, in Tile units (not pixels).
     */
    public GameObject(ActionController controller, int x, int y) {
        this.controller = controller;
        this.x = x;
        this.y = y;

        /* View-related initializations. */
        delta = 0;
        interpolatedX = 0;
        interpolatedY = 0;
        permanentOriginOffset = 0;
    }





    /**
     * Used to move the GameObject to an unreachable place. Used, for example, after an item was picked from the ground.
     */
    public void placeOutsideOfMap() {
        x = -2;
        y = -2;
    }

    /**
     * If the interaction is possible, the `source` parameter will have
     * the appropriate `Action` added at the end of its queue.<br>
     * This usually includes a check on how far the Actor is from the object.<br>
     * For example, usually, you can't pick up a Key unless you are right on top of it.<br><br>
     *
     * An interaction with a Pickable item would be to pick it up.<br>
     * An interaction with an Enemy would be to attack it.<br>
     * An interaction with an NPC would be to pop up a dialog.<br>
     * An interaction with a Door would be to try to open it.
     *
     * @param source the initiator of the interaction.
     * @return 'true' if the interaction was successful.
     */
    public abstract boolean tryInteractionFrom(Actor source);

    /**
     * Determines whether or not the GameObject can be walked through. Important for the pathfinding.
     *
     * @return 'true' if Actors can pass through a Tile on which this GameObject is.
     */
    public abstract boolean canBeWalkedThrough();
    
    /**
     * Determines whether or not the GameObject can be seen through. Important for the sight system.
     *
     * @return 'true' if the sight system should apply no resistance to its raycasting going through this Actor.<br>
     *         In other words, 'true' if the object can be seen through...
     */
    public abstract boolean canBeSeenThrough();


    @Override
    public boolean renderInFog() {
        return false;
    }



    // =========================================================================
    // |            TEXTURES AND ANIMATIONS


    @Override
    public TextureRegion getTexture() {
        return texture;
    }

    @Override
    public void setTexture(TextureRegion texture) {
        this.texture = texture;
        this.drawable = new TextureRegionDrawable(texture);
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public float getDelta() {
        return delta;
    }
    public void incrementDelta(float delta) {
        this.delta += delta;
    }
    public void resetDelta() {
        delta = 0;
    }

    public Animation<TextureRegion> getAnimation() {
        return animation;
    }

    public void setAnimationMap(HashMap<ActorState, Animation<TextureRegion>> animationMap) {
        this.animationMap = animationMap;
    }

    /**
     * Changes the Animation currently associated with the GameObject.<br>
     * Automatically changes the TextureRegion to the first frame.
     *
     * @param animation An animation that will be played for this GameObject
     */
    public void setAnimation(Animation<TextureRegion> animation) {
        this.animation = animation;
        setTexture(animation.getKeyFrame(delta));
    }


    // =========================================================================
    // |            OBSERVER PATTERN


    @Override
    public void notifyObservers() {
        for(IObserver observer : observers) {
            observer.update();
        }
    }

    @Override
    public void registerObserver(IObserver observer) {
        observers.add(observer);
    }

    @Override
    public void unregisterObserver(IObserver observer) {
        observers.removeValue(observer, true);
    }


    // =========================================================================
    // |            POSITION (LOGICAL and VISUAL)


    @Override
    public int getPermanentOriginOffset() {
        return permanentOriginOffset;
    }
    public void setPermanentOriginOffset(int permanentOriginOffset) {
        this.permanentOriginOffset = permanentOriginOffset;
    }


    @Override
    public int getX() {
        return x;
    }
    @Override
    public void setX(int x) {
        this.x = x;
    }
    @Override
    public int getY() {
        return y;
    }
    @Override
    public void setY(int y) {
        this.y = y;
    }


    @Override
    public float getInterpolatedX() {
        return interpolatedX;
    }
    @Override
    public void setInterpolatedX(float interpolatedX) {
        this.interpolatedX = interpolatedX;
    }
    @Override
    public float getInterpolatedY() {
        return interpolatedY;
    }
    @Override
    public void setInterpolatedY(float interpolatedY) {
        this.interpolatedY = interpolatedY;
    }

    @Override
    public int getMovingToX() {
        return movingToX;
    }
    @Override
    public int getMovingToY() {
        return movingToY;
    }
    @Override
    public void setMovingToX(int movingToX) {
        this.movingToX = movingToX;
    }
    @Override
    public void setMovingToY(int movingToY) {
        this.movingToY = movingToY;
    }

    @Override
    public float getCurrentX() {
        return Utils.tileToPixels(x) + interpolatedX - permanentOriginOffset;
    }
    @Override
    public float getCurrentY() {
        return Utils.tileToPixels(y) + interpolatedY - permanentOriginOffset;
    }

// =========================================================================
    // |


    public ActionController getController() {
        return controller;
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
