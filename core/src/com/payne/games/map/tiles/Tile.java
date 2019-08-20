package com.payne.games.map.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.payne.games.gameObjects.GameObject;
import com.payne.games.logic.GameLogic;
import com.payne.games.map.renderers.IRenderable;
import com.payne.games.map.tilesets.Tileset;


public abstract class Tile implements IRenderable {
    private int bitmask = 0; // wall connections
    private int x, y;
    private TextureRegion texture;
    private boolean allowingMove;

    // pathfinding
    private int graphIndex = -1;
    private boolean seen = false;
    private boolean explored = false;


    // todo: figure out if the parameters are necessary (do the Tiles need to know their position?)
    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }


    /**
     * Whether or not to render the Tile when it has been explored, but is now not being seen (aka "in the fog of war").
     *
     * @return 'false' only if it should be completely black (hidden).
     */
    public boolean renderInFog() {
        return true;
    }

    /**
     * The bitmask helps represent the wall-connections surrounding the Tile in a compact way.
     * For example, "00001011" means this Tile has wall-connections on its North, East and West faces.
     *
     * @return The bitmask, using GameLogic's constants. A bit set to '1' means it is connected to that orientation.
     */
    public int getBitmask() {
        return bitmask;
    }

    /**
     * Let's you know if the specified directions of this Tile are connected to Walls.
     * For example, a query could be<br>
     * "boolean connectedNorthAndWest = isConnectedTo(GameLogic.NORTH | GameLogic.WEST);"
     *
     * @param bits MUST be coming from GameLogic (NORTH, SOUTH, EAST and WEST).
     * @return 'true' if it is connected to the orientation given.
     */
    public boolean isConnectedTo(int bits) {
        return (bitmask & bits) == bits;
    }

    /**
     * Adds a wall-connection to the bitmask of this Tile.
     *
     * @param bits MUST be coming from GameLogic (NORTH, SOUTH, EAST and WEST).
     */
    public void addConnectionTo(int bits) {
        bitmask |= bits;
    }

    /**
     * Removes a wall-connection to the bitmask of this Tile.
     *
     * @param bits MUST be coming from GameLogic (NORTH, SOUTH, EAST and WEST).
     */
    public void removeConnectionTo(int bits) {
        bitmask &= ~bits;
    }

    /**
     * Helper method to decide whether or not to remove or add a bitmask wall-connection.
     *
     * @param addConnectionTo 'true' if you want to add the connection, 'false' if you want to remove it.
     * @param connectionDirection MUST be coming from GameLogic (NORTH, SOUTH, EAST and WEST).
     */
    private void generalBitmaskSetter(boolean addConnectionTo, int connectionDirection) {
        if (addConnectionTo)
            addConnectionTo(connectionDirection);
        else
            removeConnectionTo(connectionDirection);
    }


    public boolean isSouthConnected() {
        return isConnectedTo(GameLogic.SOUTH);
    }
    public boolean isNorthConnected() {
        return isConnectedTo(GameLogic.NORTH);
    }
    public boolean isEastConnected() {
        return isConnectedTo(GameLogic.EAST);
    }
    public boolean isWestConnected() {
        return isConnectedTo(GameLogic.WEST);
    }
    public void setSouthConnected(boolean addConnection) {
        generalBitmaskSetter(addConnection, GameLogic.SOUTH);
    }
    public void setNorthConnected(boolean addConnection) {
        generalBitmaskSetter(addConnection, GameLogic.NORTH);
    }
    public void setEastConnected(boolean addConnection) {
        generalBitmaskSetter(addConnection, GameLogic.EAST);
    }
    public void setWestConnected(boolean addConnection) {
        generalBitmaskSetter(addConnection, GameLogic.WEST);
    }








    public boolean isExplored() {
        return explored;
    }
    public void setExplored(boolean explored) {
        this.explored = explored;
    }

    public boolean isSeen() {
        return seen;
    }
    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public int getGraphIndex() {
        return graphIndex;
    }
    public void setGraphIndex(int graphIndex) {
        this.graphIndex = graphIndex;
    }

    public boolean isAllowingMove() {
        return allowingMove;
    }
    public void setAllowingMove(boolean allowingMove) {
        this.allowingMove = allowingMove;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public TextureRegion getTexture() {
        return texture;
    }
    public void setTexture(TextureRegion texture) {
        this.texture = texture;
    }


    abstract public void setTexture(Tileset tileset);
    abstract public boolean canInteract(GameObject gameObject);
    abstract public void interact(GameObject gameObject);


    @Override
    public String toString() {
        return "Tile{" +
                "x=" + x +
                ", y=" + y +
                ", allowingMove=" + allowingMove +
                ", graphIndex=" + graphIndex +
                ", seen=" + seen +
                '}';
    }
}
