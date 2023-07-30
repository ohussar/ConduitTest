package com.ohussar.conduittest.Blocks.Tank;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.ohussar.conduittest.ConduitMain;
import com.ohussar.conduittest.Core.Rendering.ModRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;

public class TankBlockEntityRenderer implements BlockEntityRenderer<TankBlockEntity> {
    public TankBlockEntityRenderer(BlockEntityRendererProvider.Context context){

    }
    @Override
    public void render(TankBlockEntity entity, float partialTick, PoseStack stack, MultiBufferSource buffer,
                       int combinedLight, int combinedOverlay) {

        if(entity.tank.fluidStored > 0){
            stack.pushPose();
            stack.translate(0.0625f, 0.0625f, 0.0625f);
            stack.scale(0.875f, 0.875f * (float)(entity.tank.fluidStored/entity.tank.tankCapacity), 0.875f);
            ModRenderer.renderFluidBox(new FluidStack(entity.tank.fluid.getFluid(), 1), 0, 0, 0,
                    1, 1, 1, buffer, stack, combinedLight, false);
            stack.popPose();
        }
    }
}
