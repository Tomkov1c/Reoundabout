package com.tomkliner.radialhotbar;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.IntValue RADIAL_MENU_RADIUS = BUILDER
            .comment("Radius of the radial menu in pixels")
            .defineInRange("radialMenuRadius", 80, 40, 200);

    public static final ModConfigSpec.IntValue ITEM_SIZE = BUILDER
            .comment("Size of each item slot in the radial menu")
            .defineInRange("itemSize", 32, 16, 64);

    static final ModConfigSpec SPEC = BUILDER.build();
}
