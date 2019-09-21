package com.payne.games.lightingSystem;

import com.badlogic.gdx.math.MathUtils;
import com.payne.games.logic.GameLogic;
import com.payne.games.map.BaseMapLayer;
import com.payne.games.map.tiles.Tile;

import static com.payne.games.logic.GameLogic.SUBDIVISIONS;


/**
 * todo:  see  https://www.redblobgames.com/articles/visibility/
 * todo: coupling/dependency could be reduced to the "Tile" class instead
 * todo: possibly do  https://stackoverflow.com/questions/45948732/2d-tile-based-smooth-lighting/45949202#45949202
 */
public class SightSystem {

    private double[][] resistance; // assign 1.0 to positions that block vision
    private double[][] visible;    // this gets filled with alpha overlay values

    private int width;
    private int height;
    private int widthBig;
    private int heightBig;
    
    public SightSystem() {
    }

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
                    // this step may not be necessary; if a cell can change from disallowing moves to allowing moves,
                    // then this is needed.
                    for (int y = 0; y < SUBDIVISIONS; y++) {
                        for (int x = 0; x < SUBDIVISIONS; x++) {
                            resistance[i * SUBDIVISIONS + y][j * SUBDIVISIONS + x] = 0.0;
                        }
                    }
                } else {
                    for (int y = 0; y < SUBDIVISIONS; y++) {
                        for (int x = 0; x < SUBDIVISIONS; x++) {
                            resistance[i * SUBDIVISIONS + y][j * SUBDIVISIONS + x] = 1.0;
                        }
                    }
                }
            }
        }
    }

    /**
     * Updates both the Line of Sight of the hero, and the corresponding Fog of War.
     * Manages the "explored" variable of the Tiles as well.
     */
    public void updateLighting(BaseMapLayer currLevel, int player_X, int player_Y, int sightRangeRadius) {
        updateResistanceMap(currLevel);
        FieldOfView.reuseFOV(resistance, visible, player_X * SUBDIVISIONS + SUBDIVISIONS / 2, player_Y * SUBDIVISIONS + SUBDIVISIONS / 2, sightRangeRadius * SUBDIVISIONS);
        Tile currTile;
        for(int i=0; i < height; i++) {          // height
            for(int j = 0; j < width; j++) {     // width
                currTile = currLevel.getTile(j,i);
                float alpha = 0;
                for (int y = 0; y < SUBDIVISIONS; y++) {
                    for (int x = 0; x < SUBDIVISIONS; x++) {
                        if(visible[i* SUBDIVISIONS+y][j* SUBDIVISIONS+x] > 0.0)
                        {
                            currTile.setExplored(true);
                            alpha = Math.max(alpha, (float)visible[i* SUBDIVISIONS+y][j* SUBDIVISIONS+x]);
                        }
                    }
                }
                currTile.setFogAlpha(
                        MathUtils.map(0f, 1f,
                                GameLogic.LOS_MIN_ALPHA, 1f,
                                alpha));
            }
        }
    }

}
