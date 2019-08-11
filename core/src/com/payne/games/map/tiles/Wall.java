package com.payne.games.map.tiles;


public class Wall extends Tile {
    boolean southConnected, northConnected, eastConnected, westConnected;


    public Wall(int x, int y) {
        super(x, y);
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
