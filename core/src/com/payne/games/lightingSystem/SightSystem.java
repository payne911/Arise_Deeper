package com.payne.games.lightingSystem;

import com.badlogic.gdx.math.MathUtils;
import com.payne.games.logic.GameLogic;
import com.payne.games.map.BaseMapLayer;
import com.payne.games.map.tiles.Tile;

import static com.payne.games.logic.GameLogic.SUBDIVISIONS; // static because SUB shows up often


/**
 * Subcell visibility system, tracking light using {@link FieldOfView} but with a larger 
 * grid and vision radius to smooth out blocks.
 * 
 * todo:  see  https://www.redblobgames.com/articles/visibility/
 * todo: coupling/dependency could be reduced to the "Tile" class instead
 * todo: possibly do  https://stackoverflow.com/questions/45948732/2d-tile-based-smooth-lighting/45949202#45949202
 */
public class SightSystem {
    private double[][] resistance; // assign 1.0 to positions that block vision
    private double[][] visible;    // this gets filled with alpha overlay values

    private int width;     // actual tile width, normal cells
    private int height;    // actual tile height, normal cells
    private int widthBig;  // width times the subdivision amount; how many subcells wide the map is
    private int heightBig; // height times the subdivision amount; how many subcells high the map is
    
    
    public SightSystem() {
    }
    
    
    /**
     * The latest visibility grid, as a 2D double array with larger dimensions 
     * than the map by a factor of the subdivision amount.
     */
    public double[][] getVisible() {
        return visible;
    }

    /**
     * Must be called when a new map is generated, before calling the other methods.
     *
     * @param currLevel Instance of the newly generated level.
     */
    public void prepareLightingOverlay(BaseMapLayer currLevel) {
        width      = currLevel.getMapWidth();
        height     = currLevel.getMapHeight();
        widthBig   = width * SUBDIVISIONS;
        heightBig  = height * SUBDIVISIONS;
        visible    = new double[heightBig][widthBig];
        resistance = new double[heightBig][widthBig];
    }

    /**
     * The "resistance map" defines to what extent a Tile is to block the
     * propagation of light. A value of "1.0" blocks the light completely.
     * A value of "0.0" lets the light pass through completely.
     */
    private void updateResistanceMap(BaseMapLayer currLevel) {
        for (int i = 0; i < height; i++) {        // height
            for (int j = 0; j < width; j++) {     // width
                if (currLevel.getTile(j, i).isSeeThrough()) {
                    /* todo: This step may not be necessary; if a cell can change from 
                    disallowing moves to allowing moves, then this is needed. */
                    for (int y = 0; y < SUBDIVISIONS; y++) {
                        for (int x = 0; x < SUBDIVISIONS; x++) {
                            // sets a 5x5 block when SUBDIVISIONS is 5
                            resistance[i * SUBDIVISIONS + y][j * SUBDIVISIONS + x] = 0.0;
                        }
                    }
                } else {
                    for (int y = 0; y < SUBDIVISIONS; y++) {
                        for (int x = 0; x < SUBDIVISIONS; x++) {
                            // sets a 5x5 block when SUBDIVISIONS is 5
                            resistance[i * SUBDIVISIONS + y][j * SUBDIVISIONS + x] = 1.0;
                        }
                    }
                }
            }
        }
    }

    /**
     * Updates both the Line of Sight of the hero, and the corresponding Fog of War.<br>
     * Manages the "explored" variable of the Tiles as well.
     */
    public void updateLighting(BaseMapLayer currLevel, int player_X, int player_Y, int sightRangeRadius) {
        updateResistanceMap(currLevel);
        /*
        Since resistance and visible use a larger map size, we offset the player position into the center 
        subcell of that larger map. {@code player_X * SUBDIVISIONS} would put the player on an edge subcell; 
        adding {@code SUBDIVISIONS / 2} puts him in the middle.
        Sight radius must also be multiplied by SUBDIVISIONS to see the correct distance. There may be quirks 
        with the actual radius because of how FieldOfView considers radius 1 to only include the player 
        (I think), but radius 5 (for SUBDIVISIONS == 5) would see halfway into the next adjacent cell. 
        A possible tweak would be to give as the last parameter: {@code sightRangeRadius * SUBDIVISIONS - SUBDIVISIONS / 2}.
        */
        FieldOfView.reuseFOV(resistance, visible, player_X * SUBDIVISIONS + SUBDIVISIONS / 2, player_Y * SUBDIVISIONS + SUBDIVISIONS / 2, sightRangeRadius * SUBDIVISIONS);
        Tile currTile;
        for(int i=0; i < height; i++) {          // height
            for(int j = 0; j < width; j++) {     // width
                currTile = currLevel.getTile(j,i);
                float alpha = 0; // we consider the fog of war alpha of a cell as the greatest visibility of any of its subcells.
                for (int y = 0; y < SUBDIVISIONS; y++) {
                    for (int x = 0; x < SUBDIVISIONS; x++) {
                        if(visible[i*SUBDIVISIONS+y][j*SUBDIVISIONS+x] > 0.0) { // if an individual subcell is visible...
                            currTile.setExplored(true); // consider the whole cell as explored.
                            // again, the alpha for a cell is the max of all its subcell alpha values.
                            alpha = Math.max(alpha, (float)visible[i*SUBDIVISIONS+y][j*SUBDIVISIONS+x]);
                            // note: we can't use the average here because walls have only their edge subcells lit.
                        }
                    }
                }
                
                /* Per cell, we set the alpha to a value in the appropriate range. */
                currTile.setFogAlpha(
                        MathUtils.map(0f, 1f,
                                GameLogic.LOS_MIN_ALPHA, 1f,
                                alpha));
            }
        }
    }

}
