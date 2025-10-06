package com.tomkliner.radialhotbar;

import java.lang.reflect.Field;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public class RadialMenuRenderer {
    private static final int RADIUS = 80;
    private static final int ITEM_SIZE = 32;
    private static final int SLOT_COUNT = 9;
    private static final int SLOT_RENDER_SIZE = 32; 
    private static final int ACTUAL_TEXTURE_SIZE = 32;

    // Texture locations - can be overridden by resource packs
    private static final ResourceLocation SLOT_TEXTURE = ResourceLocation.fromNamespaceAndPath("roundabout", "textures/gui/radial_slot.png");
    private static final ResourceLocation SLOT_HOVERED_TEXTURE = ResourceLocation.fromNamespaceAndPath("roundabout", "textures/gui/radial_slot_hovered.png");

    private int hoveredSlot = -1;
    private double mouseStartX = 0;
    private double mouseStartY = 0;
    private static Field selectedField = null;

    static {
        try {
            selectedField = Inventory.class.getDeclaredField("selected");
            selectedField.setAccessible(true);
        } catch (Exception e) {
            Roundabout.LOGGER.error("Failed to access Inventory.selected field", e);
        }
    }

    public void onMenuOpen() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.mouseHandler != null) {
            mouseStartX = mc.mouseHandler.xpos();
            mouseStartY = mc.mouseHandler.ypos();
        }
        hoveredSlot = -1;
    }

    public void onMenuClose() {
        if (hoveredSlot >= 0 && hoveredSlot < SLOT_COUNT) {
            Minecraft mc = Minecraft.getInstance();
            LocalPlayer player = mc.player;
            if (player != null) {
                Inventory inventory = player.getInventory();

                try {
                    if (selectedField != null) {
                        selectedField.setInt(inventory, hoveredSlot);
                    }
                } catch (Exception e) {
                    Roundabout.LOGGER.error("Failed to set selected slot", e);
                }

                // Send packet to server for multiplayer sync
                if (mc.getConnection() != null) {
                    mc.getConnection().send(new net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket(hoveredSlot));
                }
            }
        }
        hoveredSlot = -1;
    }

    public void render(GuiGraphics graphics, float partialTick) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player == null) return;

        int screenWidth = mc.getWindow().getGuiScaledWidth();
        int screenHeight = mc.getWindow().getGuiScaledHeight();
        int centerX = screenWidth / 2;
        int centerY = screenHeight / 2;

        double mouseX = mc.mouseHandler.xpos() * screenWidth / mc.getWindow().getScreenWidth() - centerX;
        double mouseY = mc.mouseHandler.ypos() * screenHeight / mc.getWindow().getScreenHeight() - centerY;

        hoveredSlot = getHoveredSlot(mouseX, mouseY);

        Inventory inventory = player.getInventory();
        MultiBufferSource.BufferSource bufferSource = mc.renderBuffers().bufferSource();

        for (int i = 0; i < SLOT_COUNT; i++) {
            double angle = (Math.PI * 2 * i / SLOT_COUNT) - Math.PI / 2;
            int x = centerX + (int) (Math.cos(angle) * RADIUS);
            int y = centerY + (int) (Math.sin(angle) * RADIUS);

            boolean isHovered = (i == hoveredSlot);
            ResourceLocation texture = isHovered ? SLOT_HOVERED_TEXTURE : SLOT_TEXTURE;

            graphics.blit(
                RenderPipelines.GUI_TEXTURED,
                texture,
                x - SLOT_RENDER_SIZE / 2,
                y - SLOT_RENDER_SIZE / 2,
                0.0f, 0.0f,
                SLOT_RENDER_SIZE,
                SLOT_RENDER_SIZE,
                SLOT_RENDER_SIZE,
                SLOT_RENDER_SIZE
            );

            // Draw item
            ItemStack stack = inventory.getItem(i);
            if (!stack.isEmpty()) {
                graphics.renderItem(stack, x - ITEM_SIZE / 2 + 8, y - ITEM_SIZE / 2 + 8);
                graphics.renderItemDecorations(mc.font, stack, x - ITEM_SIZE / 2 + 8, y - ITEM_SIZE / 2 + 8);
            }

            // Draw slot number
            String slotNum = String.valueOf(i + 1);
            int textX = x - mc.font.width(slotNum) / 2;
            int textY = y + ITEM_SIZE / 2 + 4;
            graphics.drawString(mc.font, slotNum, textX, textY, isHovered ? 0xFFFFFF00 : 0xFFFFFFFF);
        }

        bufferSource.endBatch();
    }

    private int getHoveredSlot(double mouseX, double mouseY) {
        double distance = Math.sqrt(mouseX * mouseX + mouseY * mouseY);

        if (distance < 20) return -1;

        double angle = Math.atan2(mouseY, mouseX);
        angle += Math.PI / 2;
        if (angle < 0) angle += Math.PI * 2;

        double slotAngle = Math.PI * 2 / SLOT_COUNT;
        return (int) Math.round(angle / slotAngle) % SLOT_COUNT;
    }
}