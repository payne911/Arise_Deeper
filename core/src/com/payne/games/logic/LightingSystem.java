package com.payne.games.logic;

import com.payne.games.gameObjects.Hero;
import com.payne.games.map.BaseMapLayer;
import com.payne.games.map.tiles.Tile;

import java.util.HashSet;


/**
 * todo:  see  https://www.redblobgames.com/articles/visibility/
 */
public class LightingSystem {
    private GameLogic gLogic;
    private BaseMapLayer level;
    private Hero player;


    public LightingSystem(GameLogic gameLogic, Hero player) {
        this.gLogic = gameLogic;
        this.player = player;
    }


    public void setUpLightingOverlay(BaseMapLayer currLevel) {
        level = currLevel;
    }

    /**
     * Updates the FogOfWar information and the lighting surround the Player.
     *
     * @return a HashSet containing all the Tiles that are within a Square-Range of sight from the player.
     */
    public HashSet<Tile> updateLighting() {
        int rangeOfSight = player.getRangeOfSight();
        int x = player.getX();
        int y = player.getY();

        Tile visitedTile = level.getTile(x, y);
        visitedTile.setSeen(true);


        // Lazily checking if Tiles within "rangeOfSight + 1" are outside of "rangeOfSight" to remove the `seen` flag.
        HashSet<Tile> surroundingTiles = level.getNeighborsWithinSquareRange(x, y, rangeOfSight);
        HashSet<Tile> oldTiles = level.getNeighborsWithinSquareRange(x, y, rangeOfSight+1); // todo: more efficient please!
        for(Tile t : oldTiles) {
            if(surroundingTiles.contains(t)) { // within range of sight
                t.setSeen(true);
                t.setExplored(true);
            } else {
                t.setSeen(false);
            }
        }

        return surroundingTiles;
    }

}
