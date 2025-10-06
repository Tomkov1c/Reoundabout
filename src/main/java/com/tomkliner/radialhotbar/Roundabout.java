package com.tomkliner.radialhotbar;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

@Mod(Roundabout.MODID)
public class Roundabout {
    public static final String MODID = "roundabout";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Roundabout(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        LOGGER.info("Roundabout Radial Menu Mod Initialized");
    }
}