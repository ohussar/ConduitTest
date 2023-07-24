package com.ohussar.conduittest.Core;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.contents.NbtContents;

public class SteamTank {

    public int maxCapacity;
    public int storage;
    public double pressure;
    public double stablePressure;

    public SteamTank(int maxCapacity, int level, double stablePressure){
        this.maxCapacity = maxCapacity;
        this.storage = level;
        this.stablePressure = stablePressure;
        this.pressure = this.stablePressure;
    }

    public CompoundTag saveNbt(CompoundTag nbt){

        CompoundTag tag = new CompoundTag();
        tag.putInt("maxCapacity"      , this.maxCapacity);
        tag.putInt("storage"          , this.storage);
        tag.putDouble("stablePressure", this.stablePressure);
        tag.putDouble("pressure"      , this.pressure);

        nbt.put("SteamTank", tag);

        return nbt;
    }

    public void loadNbt(CompoundTag nbt){
        CompoundTag tag =     nbt.getCompound("SteamTank");
        this.maxCapacity =    tag.getInt("maxCapacity");
        this.storage =        tag.getInt("storage");
        this.stablePressure = tag.getDouble("stablePressure");
        this.pressure =       tag.getDouble("pressure");
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeInt(maxCapacity);
        buf.writeInt(storage);
        buf.writeDouble(stablePressure);
        buf.writeDouble(pressure);
    }

    public void fromBytes(FriendlyByteBuf buf){
        maxCapacity = buf.readInt();
        storage = buf.readInt();
        stablePressure = buf.readDouble();
        pressure = buf.readDouble();
    }
}
