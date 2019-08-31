package com.payne.games.gameObjects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.payne.games.gameObjects.actors.Actor;
import com.payne.games.actions.ActionController;
import com.payne.games.map.renderers.IRenderable;


/**
 * Anything interactive, that is on top of a Tile.
 */
public abstract class GameObject implements IRenderable {
    private int x, y;
    private TextureRegion texture;
    private Drawable drawable;
    protected ActionController controller; // acts as a Controller linking the GameObjects with the MapLayers


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

    @Override
    public boolean renderInFog() {
        return false;
    }

    @Override
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }

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
