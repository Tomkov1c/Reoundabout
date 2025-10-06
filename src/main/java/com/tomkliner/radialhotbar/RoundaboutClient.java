package com.tomkliner.radialhotbar;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;

@Mod(value = Roundabout.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = Roundabout.MODID, value = Dist.CLIENT)
public class RoundaboutClient {
    
    public RoundaboutClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        NeoForge.EVENT_BUS.register(new RadialMenuHandler());
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        Roundabout.LOGGER.info("Roundabout Client Setup Complete");
    }

    @SubscribeEvent
    static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(KeyBindings.OPEN_RADIAL_MENU);
        Roundabout.LOGGER.info("Registered Radial Menu Keybinding");
    }
}