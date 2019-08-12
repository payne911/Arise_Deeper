package com.payne.games.map;

import com.payne.games.logic.GameLogic;
import com.payne.games.map.generator.MapGenerator;


public class MapController {
    private LevelMap currentLevel;
    private MapRenderer mapRenderer;
    private GameLogic gLogic;
    private MapGenerator mapGenerator;


    public MapController(GameLogic gameLogic) {
        this.gLogic = gameLogic;
    }
}
