package com.payne.games.map.tilesets;

import com.badlogic.gdx.math.GridPoint2;
import com.payne.games.logic.GameLogic;


public class BasicTileset extends Tileset {


    public BasicTileset(GameLogic gameLogic) {
        super(gameLogic);
        setRenderingTileset("dungeon_tileset.png");

        floors = new GridPoint2[]{
                new GridPoint2(1, 1),
                new GridPoint2(1, 2),
                new GridPoint2(1, 3),
                new GridPoint2(2, 1),
                new GridPoint2(2, 2),
                new GridPoint2(2, 3),
                new GridPoint2(3, 1),
                new GridPoint2(3, 2),
                new GridPoint2(3, 3),
                new GridPoint2(19, 3)
        };

        walls = new GridPoint2[]{
                new GridPoint2(7, 4),
                new GridPoint2(17, 3)
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
