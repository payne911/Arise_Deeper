package com.payne.games.map;

import com.payne.games.map.tilesets.Tileset;


/**
 * Used to detect the orientation of the connections on Wall Tiles, allowing to render the proper TextureRegion.
 */
public class WallRenderer {
    private Tileset tileset;


    public WallRenderer() {
    }


    public void setTileset(Tileset tileset) {
        this.tileset = tileset;
    }
}
