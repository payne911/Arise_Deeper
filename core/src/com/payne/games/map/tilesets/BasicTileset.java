package com.payne.games.map.tilesets;

import com.badlogic.gdx.math.GridPoint2;
import com.payne.games.logic.GameLogic;


public class BasicTileset extends Tileset {


    public BasicTileset() {
        super();
        setRenderingTileset(GameLogic.BASIC_TILESET);

        floors = new GridPoint2[]{ // used for 0 connections and a few exceptions
                new GridPoint2(1, 1),
                new GridPoint2(1, 2),
                new GridPoint2(1, 3),
                new GridPoint2(2, 1),
                new GridPoint2(2, 2),
                new GridPoint2(2, 3),
                new GridPoint2(3, 1),
                new GridPoint2(3, 2),
                new GridPoint2(3, 3),
                new GridPoint2(19, 3), // increasing probability of "empty" floor (repeated 3 times)
                new GridPoint2(19, 3),
                new GridPoint2(19, 3)
        };

        floors_N = new GridPoint2[]{
                new GridPoint2(1,0),
                new GridPoint2(2,0),
                new GridPoint2(3,0)
        };

        floors_S = new GridPoint2[]{
                new GridPoint2(1,4),
                new GridPoint2(2,4),
                new GridPoint2(3,4)
        };

        floors_E = new GridPoint2[]{
                new GridPoint2(4,1),
                new GridPoint2(4,2),
                new GridPoint2(4,3)
        };

        floors_W = new GridPoint2[]{
                new GridPoint2(0,1),
                new GridPoint2(0,2),
                new GridPoint2(0,3)
        };

        floors_NE = new GridPoint2[]{
                new GridPoint2(4,0)
        };

        floors_NW = new GridPoint2[]{
                new GridPoint2(0,0)
        };

        floors_SE = new GridPoint2[]{
                new GridPoint2(4,4)
        };

        floors_SW = new GridPoint2[]{
                new GridPoint2(0,4)
        };

//        floors_horiz = new GridPoint2[]{
//                new GridPoint2(5,4)
//        };
//
//        floors_vert = new GridPoint2[]{
//                new GridPoint2(5,4)
//        };







        walls = new GridPoint2[]{ // used for when there are more than 2 connections
                new GridPoint2(7, 4),
                new GridPoint2(17, 3)
        };

        walls_N = new GridPoint2[]{
                new GridPoint2(5,4)
        };

        walls_E = new GridPoint2[]{
                new GridPoint2(6,3)
        };

        walls_W = new GridPoint2[]{
                new GridPoint2(7, 3)
        };

        walls_S = new GridPoint2[]{
                new GridPoint2(5,3)
        };

        walls_vert = new GridPoint2[]{
                new GridPoint2(5,1),
                new GridPoint2(7,1),
        };

        walls_horiz = new GridPoint2[]{
                new GridPoint2(6,0),
                new GridPoint2(6,2),
        };

        walls_SE = new GridPoint2[]{
                new GridPoint2(5,0)
        };

        walls_NE = new GridPoint2[]{
                new GridPoint2(5,2)
        };

        walls_NW = new GridPoint2[]{
                new GridPoint2(7,2)
        };

        walls_SW = new GridPoint2[]{
                new GridPoint2(7,0)
        };






        doors = new GridPoint2[]{ // todo: separate in "locked", "open" and "closed" categories
                new GridPoint2(8, 3),
                new GridPoint2(9, 3),
                new GridPoint2(10, 3)
        };

        water = new GridPoint2[]{
                new GridPoint2(13, 1)
        };

        empty = new GridPoint2[]{
                new GridPoint2(11, 2)
        };
    }
}
