package com.payne.games.map.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.payne.games.gameObjects.GameObject;
import com.payne.games.logic.GameLogic;
import com.payne.games.rendering.IRenderable;
import com.payne.games.map.tilesets.Tileset;


public abstract class Tile implements IRenderable {
    private int bitmask = 0; // wall connections
    private int x, y;
    private boolean allowingMove;
    private Array<Tile> neighbors = new Array<>();

    // view
    private TextureRegion texture;

    // pathfinding
    private int graphIndex = -1;
    private boolean explored = false;

    // fog of war overlay
    private float fogAlpha = 0;
    public float getFogAlpha() {
        return fogAlpha;
    }
    public void setFogAlpha(float fogAlpha) {
        this.fogAlpha = fogAlpha;
    }

    @Override
    public int getPermanentOriginOffset() {
        return 0;
    }

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }


    /**
     * Used to improve performance in the pathfinding. (Prevents from having to allocate memory many times per `render()` call.)
     *
     * @return The 4 neighbors of this Tile (N, S, E, W).
     */
    public Array<Tile> getNeighbors() {
        return neighbors;
    }

    public void addNeighbors(Tile... tiles) {
        neighbors.addAll(tiles);
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


    /**
     * A Tile is "explored" if it has been seen at least once by the player.
     *
     * @return 'true' if this Tile was seen at least once by the player.
     */
    public boolean isExplored() {
        return explored;
    }
    public void setExplored(boolean explored) {
        this.explored = explored;
    }

    /**
     * A Tile is "in sight" if it is within line of sight of the player's hero. It also means the Tile is lit up.
     *
     * @return 'true' if the tile is within sight.
     */
    public boolean isInSight() {
        return fogAlpha>GameLogic.LOS_MIN_ALPHA;
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

    @Override
    public void setX(int x) {
        this.x = x;
    }
    @Override
    public void setY(int y) {
        this.y = y;
    }
    @Override
    public int getX() {
        return x;
    }
    @Override
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
                ", explored=" + explored +
                ", fogAlpha=" + fogAlpha +
                '}';
    }
}
