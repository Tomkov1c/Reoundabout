package com.tomkliner.radialhotbar;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.platform.InputConstants;

import net.minecraft.client.KeyMapping;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.settings.KeyConflictContext;

public class KeyBindings {
    public static final ResourceLocation CATEGORY = ResourceLocation.fromNamespaceAndPath("roundabout", "radial_menu");
    
    public static final KeyMapping OPEN_RADIAL_MENU = new KeyMapping(
        "key.roundabout.open_radial_menu",
        KeyConflictContext.IN_GAME,
        InputConstants.Type.KEYSYM,
        GLFW.GLFW_KEY_R,
        new KeyMapping.Category(CATEGORY)
    );
}