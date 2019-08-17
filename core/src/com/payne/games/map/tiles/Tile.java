package com.payne.games.map.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.payne.games.gameObjects.GameObject;
import com.payne.games.map.renderers.IRenderable;
import com.payne.games.map.tilesets.Tileset;


public abstract class Tile implements IRenderable {
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

    public boolean renderInFog() {
        return true;
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
