package com.tomkliner.radialhotbar;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.config.ModConfigEvent;

public class ConfigHandler {
    public ConfigHandler(RadialMenuRenderer renderer) {
        
    }

    @SubscribeEvent
    public void onConfigReload(ModConfigEvent.Reloading event) {
        if (event.getConfig().getSpec() == Config.SPEC) {
            RadialMenuRenderer.updateFromConfig();
        }
    }

    @SubscribeEvent
    public void onConfigLoad(ModConfigEvent.Loading event) {
        if (event.getConfig().getSpec() == Config.SPEC) {
            RadialMenuRenderer.updateFromConfig();
        }
    }

}
