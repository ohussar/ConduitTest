package com.ohussar.conduittest.Core.HUD;

import com.mojang.blaze3d.systems.RenderSystem;
import com.ohussar.conduittest.ConduitMain;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class InfoHudOverlay {
    public static final ResourceLocation OVERLAY = new ResourceLocation(ConduitMain.MODID,"textures/gui/overlay.png");


    public static final IGuiOverlay INFO_OVERLAY = (((gui, poseStack, partialTick, screenWidth, screenHeight) ->{
        int x = screenWidth / 2 - 128/2;
        int y = 60;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, OVERLAY);

        GuiComponent.blit(poseStack, x, y, 0, 0, 128, 32, 128, 32);
    }));

}
