package com.payne.games.map.tilesets;

import com.payne.games.logic.GameLogic;

import java.awt.*;


public class BasicTileset extends Tileset {


    public BasicTileset(GameLogic gameLogic) {
        super(gameLogic);
        setRenderingTileset("dungeon_tileset.png");

        floors = new Point[]{
                new Point(1, 1),
                new Point(1, 2),
                new Point(1, 3),
                new Point(2, 1),
                new Point(2, 2),
                new Point(2, 3),
                new Point(3, 1),
                new Point(3, 2),
                new Point(3, 3)
        };

        walls = new Point[]{
                new Point(7, 4),
                new Point(17, 3)
        };

        doors = new Point[]{ // todo: separate in "locked", "open" and "closed" categories
                new Point(8, 3),
                new Point(9, 3),
                new Point(10, 3)
        };

        water = new Point[]{
                new Point(13, 1)
        };

        empty = new Point[]{
                new Point(11, 2)
        };
    }
}
