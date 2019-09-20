package com.payne.games.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.payne.games.logic.GameLogic;


/**
 * Used for the general access to any asset used in the game.
 * todo: annotations... look into   https://github.com/CypherCove/CoveTools/blob/master/core/src/com/cyphercove/covetools/assets/AssignmentAssetManager.java
 * todo: or...     http://www.codinginsights.blog/libgdx-assetmanager/
 * todo: or...     https://bitbucket.org/dermetfan/libgdx-utils/wiki/net.dermetfan.gdx.assets.AnnotationAssetManager
 */
public class Assets {
    public AssetManager manager = new AssetManager();
    public AssetsPool pool;



    /**
     * The image displayed while loading the assets.
     */
    public static final AssetDescriptor<Texture> LOADING_IMAGE =
            new AssetDescriptor<>(GameLogic.LOADING_PATH, Texture.class);


    /**
     * Texture Atlas: contains all the game's assets.
     */
    public static final AssetDescriptor<TextureAtlas> ATLAS =
            new AssetDescriptor<>(GameLogic.ATLAS_PATH, TextureAtlas.class);


    /**
     * Skin (UI).
     */
    public static final AssetDescriptor<Skin> UI_SKIN =
            new AssetDescriptor<>(GameLogic.SKIN_PATH, Skin.class);


    /**
     * Asynchronous loading of all the rest of the assets.
     */
    public void loadGameAssets() {
        manager.load(ATLAS);
        manager.load(UI_SKIN);
    }


    /**
     * Synchronous loading to obtain the initial image to
     * display while loading the rest of the assets.
     */
    public void loadLoadingScreen() {
        manager.load(LOADING_IMAGE);
        manager.finishLoading();
    }

    public void setUpAssetsPool() {
        pool = new AssetsPool(manager.get(ATLAS));
    }



    public void dispose() {
        manager.dispose();
    }



    /*
    todo:        to load stuff, use...

    someTexture = assets.manager.get(Assets.someTexture);
    atlas = assets.manager.get(Assets.uiAtlas);
    skin = assets.manager.get(Assets.uiSkin);


    The same rules apply for looking up regions in your atlas, best is to do this
    a single time for each region in your atlas. You have to pass around the Assets
    object around to anywhere you need to load assets. It makes it tempting to make
    it public static but it can cause issues.
     */
}
