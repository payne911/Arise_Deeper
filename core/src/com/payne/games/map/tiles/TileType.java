package com.payne.games.map.tiles;


// todo: adjust based on `MapRenderer.assignSingleTileTexture()` method
public enum TileType {

    FLOOR(0),
    WALL(1),
    DOOR(2),
    EMPTY(3);

    private int typeInt;


    private TileType(int typeInt) {
        this.typeInt = typeInt;
    }


    public int getTypeInt() {
        return typeInt;
    }

    public void setTypeInt(int typeInt) {
        this.typeInt = typeInt;
    }

}
