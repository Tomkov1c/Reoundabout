package com.tomkliner.radialhotbar;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.IntValue RADIAL_MENU_RADIUS = BUILDER
        .comment("Distance from center to slot")
        .defineInRange("radialMenuRadius", 80, 40, 200);

    public static final ModConfigSpec.IntValue ITEM_SIZE = BUILDER
        .comment("Size of each item slot icon")
        .defineInRange("itemSize", 32, 16, 64);

    public static final ModConfigSpec.IntValue SLOT_SIZE = BUILDER
        .comment("Size of the slot background texture")
        .defineInRange("slotSize", 32, 16, 64);

    static final ModConfigSpec SPEC = BUILDER.build();
}
