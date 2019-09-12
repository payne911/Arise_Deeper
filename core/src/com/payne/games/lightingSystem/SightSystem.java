package com.payne.games.lightingSystem;

import com.badlogic.gdx.math.MathUtils;
import com.payne.games.logic.GameLogic;
import com.payne.games.map.BaseMapLayer;
import com.payne.games.map.tiles.Tile;


/**
 * todo:  see  https://www.redblobgames.com/articles/visibility/
 * todo: coupling/dependency could be reduced to the "Tile" class instead
 */
public class SightSystem {

    private double[][] resistance; // assign 1.0 to positions that block vision
    private double[][] visible;    // this gets filled with alpha overlay values

    private int width;
    private int height;


    public SightSystem() {
    }


    /**
     * Must be called when a new map is generated, before calling the other methods.
     *
     * @param currLevel Instance of the newly generated level.
     */
    public void prepareLightingOverlay(BaseMapLayer currLevel) {
        width      = currLevel.getMapWidth();
        height     = currLevel.getMapHeight();
        visible    = new double[height][width];
        resistance = new double[height][width];
    }

    /**
     * The "resistance map" defines to what extent a Tile is to block the
     * propagation of light. A value of "1.0" blocks the light completely.
     * A value of "0.0" lets the light pass through completely.
     */
    private void updateResistanceMap(BaseMapLayer currLevel) {
        for(int i=0; i < height; i++) {        // height
            for(int j=0; j < width; j++) {     // width
                if(currLevel.getTile(j,i).isAllowingMove())
                    resistance[i][j] = 0.0;
                else
                    resistance[i][j] = 1.0;
            }
        }
    }

    /**
     * Updates both the Line of Sight of the hero, and the corresponding Fog of War.
     * Manages the "explored" variable of the Tiles as well.
     */
    public void updateLighting(BaseMapLayer currLevel, int player_X, int player_Y, int sightRangeRadius) {
        updateResistanceMap(currLevel);
        FieldOfView.reuseFOV(resistance, visible,player_X, player_Y, sightRangeRadius);
        Tile currTile;
        for(int i=0; i < height; i++) {          // height
            for(int j = 0; j < width; j++) {     // width
                currTile = currLevel.getTile(j,i);
                currTile.setFogAlpha(
                        MathUtils.map(0f, 1f,
                                GameLogic.LOS_MIN_ALPHA, 1f,
                                (float)visible[i][j]));
                if(currTile.isInSight())
                    currTile.setExplored(true);
            }
        }
    }

}
