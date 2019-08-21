package com.payne.games.logic.systems;

import com.payne.games.gameObjects.actors.Hero;
import com.payne.games.map.BaseMapLayer;
import com.payne.games.map.tiles.Tile;

import java.util.HashSet;


/**
 * todo:  see  https://www.redblobgames.com/articles/visibility/
 */
public class LightingSystem {
    private HashSet<Tile> oldTiles = new HashSet<>();
    private BaseMapLayer level;
    private Hero player;


    public LightingSystem(Hero player) {
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
        visitedTile.setInSight(true);


        // Using "non-intersection" between sets to update the FogOfWar.
        HashSet<Tile> surroundingTiles = level.getNeighborsWithinSquareRange(x, y, rangeOfSight);
        for(Tile t : oldTiles) {
            if(surroundingTiles.contains(t)) { // within range of sight
                t.setInSight(true);
                t.setExplored(true);
            } else {
                t.setInSight(false);
            }
        }
        oldTiles = (HashSet<Tile>)surroundingTiles.clone();

        return surroundingTiles;
    }

}
