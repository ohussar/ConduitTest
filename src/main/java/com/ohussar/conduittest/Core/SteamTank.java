package com.ohussar.conduittest.Core;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.contents.NbtContents;

public class SteamTank {

    public int maxCapacity;
    public double storage;
    public double pressure;
    public double stablePressure;
    public double temperature;

    public SteamTank(int maxCapacity, int level, double stablePressure, double temp){
        this.maxCapacity = maxCapacity;
        this.storage = level;
        this.stablePressure = stablePressure;
        this.pressure = this.stablePressure;
        this.temperature = temp;
    }

    public CompoundTag saveNbt(CompoundTag nbt){

        CompoundTag tag = new CompoundTag();
        tag.putInt("maxCapacity"      , this.maxCapacity);
        tag.putDouble("storage"          , this.storage);
        tag.putDouble("stablePressure", this.stablePressure);
        tag.putDouble("pressure"      , this.pressure);
        tag.putDouble("temp", this.temperature);
        nbt.put("SteamTank", tag);

        return nbt;
    }

    public void loadNbt(CompoundTag nbt){
        CompoundTag tag =     nbt.getCompound("SteamTank");
        this.maxCapacity =    tag.getInt("maxCapacity");
        this.storage =        tag.getDouble("storage");
        this.stablePressure = tag.getDouble("stablePressure");
        this.pressure =       tag.getDouble("pressure");
        this.temperature =    tag.getDouble("temp");
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeInt(maxCapacity);
        buf.writeDouble(storage);
        buf.writeDouble(stablePressure);
        buf.writeDouble(pressure);
        buf.writeDouble(temperature);
    }

    public void fromBytes(FriendlyByteBuf buf){
        maxCapacity = buf.readInt();
        storage = buf.readDouble();
        stablePressure = buf.readDouble();
        pressure = buf.readDouble();
        temperature = buf.readDouble();
    }
}
