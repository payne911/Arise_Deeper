package com.payne.games;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;


/**
 * Used for the general access to any asset used in the game.
 * todo: annotations... look into   https://github.com/CypherCove/CoveTools/blob/master/core/src/com/cyphercove/covetools/assets/AssignmentAssetManager.java
 * todo: or...     http://www.codinginsights.blog/libgdx-assetmanager/
 * todo: or...     https://bitbucket.org/dermetfan/libgdx-utils/wiki/net.dermetfan.gdx.assets.AnnotationAssetManager
 */
public class Assets {
    public AssetManager manager = new AssetManager();



    /**
     * Textures (images).
     */
    public static final AssetDescriptor<Texture>
            LOADING_IMAGE = new AssetDescriptor<>("unrelated/click_diagram.png", Texture.class),
            DUNGEON_TILESET = new AssetDescriptor<>("spriteSheets/dungeon_tileset.png", Texture.class),
            IMG_16p = new AssetDescriptor<>("spriteSheets/game_objects_16p.png", Texture.class),
            IMG_20p = new AssetDescriptor<>("spriteSheets/game_objects_20p.png", Texture.class);


    /**
     * Texture Atlas.
     */
    public static final AssetDescriptor<TextureAtlas>
            ATLAS = new AssetDescriptor<>("wip/master_asset_manager/assets.atlas", TextureAtlas.class);


//    /**
//     * Skin (UI).
//     */
//    public static final AssetDescriptor<Skin>
//            UI_SKIN = new AssetDescriptor<>("uiskin.json", Skin.class,
//            new SkinLoader.SkinParameter("uiskin.pack"));




//    public static final AssetDescriptor<TextureAtlas> uiAtlas =
//            new AssetDescriptor<>("ui/uiskin.pack", TextureAtlas.class);
//
//    public static final AssetDescriptor<Skin> uiSkin =
//            new AssetDescriptor<>("ui/uiskin.json", Skin.class,
//                    new SkinLoader.SkinParameter("ui/uiskin.pack"));



    public void loadGameAssets() {
//        manager.load(DUNGEON_TILESET);
//        manager.load(IMG_16p);
//        manager.load(IMG_20p);
//        manager.load(ATLAS);

//        manager.load(uiAtlas);
//        manager.load(uiSkin);
    }



    public void loadLoadingScreen() {
        manager.load(LOADING_IMAGE);
        manager.finishLoading();
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
