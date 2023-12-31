package com.ohussar.conduittest.Blocks.Machine;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Transformation;
import com.mojang.math.Vector3f;
import com.ohussar.conduittest.ConduitMain;
import com.ohussar.conduittest.Menus.CompactingMachineMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class CompactingMachineScreen extends AbstractContainerScreen<CompactingMachineMenu> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(ConduitMain.MODID,"textures/gui/compacting_machine.png");

    public CompactingMachineScreen(CompactingMachineMenu menu, Inventory inv, Component component) {
        super(menu, inv, component);
    }

    @Override
    protected void renderBg(PoseStack pose, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);

        int x = (width - imageWidth)/2;
        int y = (height - imageHeight)/2;
        this.blit(pose, x, y, 0, 0, 229, imageHeight);

        int gaugex = x + 196;
        int gaugey = y + 38;

        int gaugextex = 243;
        int gaugeytex = 45;
        int gaugetexwidth = 13;
        int gaugetexheight = 3;
        pose.pushPose();

        CompactingMachineEntity entity = this.menu.entity;

        float ff = (float) (entity.tank.pressure/ entity.maxPressure());

        pose.pushPose();
        float alpha = ff*180;

        double gaugeyfixed = gaugey+0.5;
        double gaugexfixed = gaugex+0.5;

        pose.translate(gaugexfixed, gaugeyfixed, 0);
        pose.mulPose(Quaternion.fromXYZDegrees(new Vector3f(0.f, 0.f, alpha)));
        pose.translate(-gaugexfixed, -gaugeyfixed, 0);
        this.blit(pose, gaugex - gaugetexwidth + 2, gaugey-gaugetexheight/2, gaugextex, gaugeytex, gaugetexwidth, gaugetexheight);
        pose.popPose();
    }
    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float delta){
        renderBackground(stack);
        super.render(stack, mouseX, mouseY, delta);
        renderTooltip(stack, mouseX, mouseY);
    }
}
