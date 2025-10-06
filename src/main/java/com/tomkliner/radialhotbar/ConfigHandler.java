package com.tomkliner.radialhotbar;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.config.ModConfigEvent;

public class ConfigHandler {
    public ConfigHandler(RadialMenuRenderer renderer) {
        
    }

    @SubscribeEvent
    public void onConfigReload(ModConfigEvent.Reloading event) {
        if (event.getConfig().getSpec() == Config.SPEC) {
            Roundabout.LOGGER.info("[Roundabout] Config reloaded. Updated radial menu values: radius={}, slotSize={}, itemSize={}",
                    Config.RADIAL_MENU_RADIUS.get(),
                    Config.SLOT_SIZE.get(),
                    Config.ITEM_SIZE.get());

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
