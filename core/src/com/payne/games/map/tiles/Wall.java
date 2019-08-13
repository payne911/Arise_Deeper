package com.payne.games.map.tiles;


import com.payne.games.map.tilesets.Tileset;

public class Wall extends Tile {
    private boolean southConnected, northConnected, eastConnected, westConnected;


    public Wall(int x, int y) {
        super(x, y);
    }

    @Override
    public void setTexture(Tileset tileset) {
        setTexture(tileset.getWallRandomTexture());
    }


    public boolean isSouthConnected() {
        return southConnected;
    }

    public void setSouthConnected(boolean southConnected) {
        this.southConnected = southConnected;
    }

    public boolean isNorthConnected() {
        return northConnected;
    }

    public void setNorthConnected(boolean northConnected) {
        this.northConnected = northConnected;
    }

    public boolean isEastConnected() {
        return eastConnected;
    }

    public void setEastConnected(boolean eastConnected) {
        this.eastConnected = eastConnected;
    }

    public boolean isWestConnected() {
        return westConnected;
    }

    public void setWestConnected(boolean westConnected) {
        this.westConnected = westConnected;
    }
}
