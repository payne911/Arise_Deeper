package com.payne.games.assets;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class ImageFactory {

    /* Actors */
    public final TextureRegion knight;
    public final Animation<TextureRegion> hero_red_attack;
    public final Animation<TextureRegion> hero_red_death;
    public final Animation<TextureRegion> hero_red_idle;
    public final Animation<TextureRegion> hero_red_interact;
    public final Animation<TextureRegion> hero_red_walk;

    /* Statics */
    public final TextureRegion key;
    public final TextureRegion chest_16p;
    public final TextureRegion potion_fatigue;
    public final TextureRegion potion_health;
    public final TextureRegion door_closed;
    public final TextureRegion door_locked;
    public final TextureRegion door_open;

    /* Props */
    public final TextureRegion barrel;
    public final TextureRegion box_small;
    public final Animation<TextureRegion> flame;

    /* Alpha */
    public final TextureRegion pixel_full_alpha;
    public final TextureRegion pixel_half_alpha;
    public final TextureRegion halo_small;
    public final TextureRegion halo_medium;
    public final TextureRegion halo_big;

    /* Tileset */
    public final TextureRegion floor_a;
    public final TextureRegion floor_b;
    public final TextureRegion floor_c;
    public final TextureRegion floor_d;
    public final TextureRegion floor_e;
    public final TextureRegion floor_f;
    public final TextureRegion floor_g;
    public final TextureRegion floor_h;
    public final TextureRegion floor_i;
    public final TextureRegion empty;
    public final TextureRegion full_a;
    public final TextureRegion full_b;
    public final TextureRegion full_c;

    public ImageFactory(TextureAtlas atlas) {

        /* Actors */
        knight = atlas.findRegion("knight");
        hero_red_attack = new Animation<TextureRegion>(0.033f, atlas.findRegions("hero_red_attack"), PlayMode.LOOP);
        hero_red_death = new Animation<TextureRegion>(0.033f, atlas.findRegions("hero_red_death"), PlayMode.LOOP);
        hero_red_idle = new Animation<TextureRegion>(0.033f, atlas.findRegions("hero_red_idle"), PlayMode.LOOP);
        hero_red_interact = new Animation<TextureRegion>(0.033f, atlas.findRegions("hero_red_interact"), PlayMode.LOOP);
        hero_red_walk = new Animation<TextureRegion>(0.033f, atlas.findRegions("hero_red_walk"), PlayMode.LOOP);

        /* Statics */
        key = atlas.findRegion("key");
        chest_16p = atlas.findRegion("chest_16p");
        potion_fatigue = atlas.findRegion("potion_fatigue");
        potion_health = atlas.findRegion("potion_health");
        door_closed = atlas.findRegion("door_closed");
        door_locked = atlas.findRegion("door_locked");
        door_open = atlas.findRegion("door_open");

        /* Props */
        barrel = atlas.findRegion("barrel");
        box_small = atlas.findRegion("box_small");
        flame = new Animation<TextureRegion>(0.033f, atlas.findRegions("flame"), PlayMode.LOOP);

        /* Alpha */
        pixel_full_alpha = atlas.findRegion("pixel_full_alpha");
        pixel_half_alpha = atlas.findRegion("pixel_half_alpha");
        halo_small = atlas.findRegion("halo_small");
        halo_medium = atlas.findRegion("halo_medium");
        halo_big = atlas.findRegion("halo_big");

        /* Tileset */
        floor_a = atlas.findRegion("floor_a");
        floor_b = atlas.findRegion("floor_b");
        floor_c = atlas.findRegion("floor_c");
        floor_d = atlas.findRegion("floor_d");
        floor_e = atlas.findRegion("floor_e");
        floor_f = atlas.findRegion("floor_f");
        floor_g = atlas.findRegion("floor_g");
        floor_h = atlas.findRegion("floor_h");
        floor_i = atlas.findRegion("floor_i");
        empty = atlas.findRegion("empty");
        full_a = atlas.findRegion("full_a");
        full_b = atlas.findRegion("full_b");
        full_c = atlas.findRegion("full_c");
    }

}
