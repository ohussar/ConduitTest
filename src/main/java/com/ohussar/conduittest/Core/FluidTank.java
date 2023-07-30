package com.ohussar.conduittest.Core;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

public class FluidTank {

    public FluidStack fluid;
    public int tankCapacity = 0;
    public double fluidStored = 0;

    public FluidTank(FluidStack fluid, int tankCapacity, double fluidStored){
        this.fluid = fluid;
        this.tankCapacity = tankCapacity;
        this.fluidStored = fluidStored;
    }
    public CompoundTag saveNbt(CompoundTag nbt){
        CompoundTag tag = new CompoundTag();
        tag.putDouble("amountStored", this.fluidStored);
        tag.putDouble("tankCapacity", this.tankCapacity);
        tag.putString("fluid", ForgeRegistries.FLUIDS.getKey(this.fluid.getFluid()).toString());
        nbt.put("FluidTank", tag);
        return nbt;
    }

    public void loadNbt(CompoundTag nbt){
        CompoundTag tag =     nbt.getCompound("FluidTank");
        this.fluidStored =    tag.getDouble("amountStored");
        this.tankCapacity =        tag.getInt("tankCapacity");
        ResourceLocation fluidName = new ResourceLocation(tag.getString("fluid"));
        Fluid fluid = ForgeRegistries.FLUIDS.getValue(fluidName);
        this.fluid = new FluidStack(fluid, 1);
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeDouble(fluidStored);
        buf.writeInt(tankCapacity);
        this.fluid.writeToPacket(buf);
    }

    public void fromBytes(FriendlyByteBuf buf){
        this.fluidStored = buf.readDouble();
        this.tankCapacity = buf.readInt();
        this.fluid = FluidStack.readFromPacket(buf);
    }
}
