package com.payne.games.map.tilesets;

import com.payne.games.map.BaseMapLayer;
import com.payne.games.map.tiles.Floor;
import com.payne.games.map.tiles.Tile;
import com.payne.games.map.tiles.Wall;


/**
 * Used to detect the orientation of the connections on Wall Tiles, allowing to render the proper TextureRegion.
 */
public class SubclassTileAssigner {
    private BaseMapLayer level;


    public SubclassTileAssigner() {
    }


    public void setLevel(BaseMapLayer level) {
        this.level = level;
    }

    /**
     * Modifies the Tile according to its surroundings.
     *
     * @param tile the Tile to be modified.
     */
    public void patternMatchSurroundings(Tile tile) {
        if(tile instanceof Wall)
            onWall((Wall)tile);
        if(tile instanceof Floor)
            onFloor((Floor)tile);
    }

    private void onWall(Wall tile) {
        tile.setNorthConnected(level.getNorth(tile) instanceof Wall);
        tile.setSouthConnected(level.getSouth(tile) instanceof Wall);
        tile.setEastConnected (level.getEast(tile)  instanceof Wall);
        tile.setWestConnected (level.getWest(tile)  instanceof Wall);
    }

    private void onFloor(Floor tile) {
        tile.setNorthConnected(level.getNorth(tile) instanceof Wall);
        tile.setSouthConnected(level.getSouth(tile) instanceof Wall);
        tile.setEastConnected (level.getEast(tile)  instanceof Wall);
        tile.setWestConnected (level.getWest(tile)  instanceof Wall);
    }
}
