package com.payne.games.map.tiles;

import com.payne.games.map.tilesets.Tileset;

public class Empty extends Tile {

    public Empty(int x, int y) {
        super(x, y);
    }

    @Override
    public void setTexture(Tileset tileset) {
        setTexture(tileset.getEmptyRandomTexture());
    }
}
