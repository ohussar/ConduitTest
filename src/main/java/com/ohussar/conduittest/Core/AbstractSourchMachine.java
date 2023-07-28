package com.ohussar.conduittest.Core;

import com.ohussar.conduittest.Blocks.Conduit.ConduitBlockEntity;
import com.ohussar.conduittest.Blocks.Conduit.ConduitStructureManager;
import com.ohussar.conduittest.ConduitMain;
import com.ohussar.conduittest.Core.Interfaces.ConduitExtractable;
import com.ohussar.conduittest.Core.Interfaces.ISteamCapabilityProvider;
import com.ohussar.conduittest.Core.Interfaces.ISteamGenerationProvider;
import com.ohussar.conduittest.Core.Networking.Messages.SyncTank;
import com.ohussar.conduittest.Core.Networking.ModMessages;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public abstract class AbstractSourchMachine extends BlockEntity implements ISteamCapabilityProvider, ISteamGenerationProvider {
    public UUID id;
    private int ticksCounted = 0;
    private int tickGenerated = 0;
    public double machineGeneration = 30;
    public SteamTank tank;

    public AbstractSourchMachine(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        id = UUID.randomUUID();
        tank = new SteamTank(1500, 0, 1.5, 30);
    }
    @Override
    public void syncTank(SteamTank t) {
        this.tank = t;
    }
    @Override
    public Type getExtractionType() {
        return Type.SOURCE;
    }

    @Override
    public SteamTank getMainTank() {
        return this.tank;
    }

    @Override
    public void load(CompoundTag p_155245_) {
        super.load(p_155245_);
        id = p_155245_.getUUID("machineid");
        tank.loadNbt(p_155245_);
    }

    @Override
    public double providePressure(ConduitStructureManager manager) {
        return this.machineGeneration;
    }

    @Override
    protected void saveAdditional(CompoundTag p_187471_) {
        p_187471_.putUUID("machineid", id);
        tank.saveNbt(p_187471_);
        super.saveAdditional(p_187471_);
    }

    public void tickEssential(Level level, BlockPos pos, BlockState state, AbstractSourchMachine entity){
        ticksCounted++;
        if(ticksCounted == 5 && !level.isClientSide()) {
            ticksCounted = 0;
            ModMessages.sendToClients(new SyncTank(entity.tank, entity.worldPosition));
        }

        this.tank.storage+=machineGeneration;

    }
}
