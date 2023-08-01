package com.ohussar.conduittest.Core.MachineBase;

import com.ohussar.conduittest.Blocks.Conduit.ConduitStructureManager;
import com.ohussar.conduittest.Core.FluidTank;
import com.ohussar.conduittest.Core.Interfaces.ISteamCapabilityProvider;
import com.ohussar.conduittest.Core.Interfaces.ISteamGenerationProvider;
import com.ohussar.conduittest.Core.Networking.Messages.SyncTank;
import com.ohussar.conduittest.Core.Networking.ModMessages;
import com.ohussar.conduittest.Core.SteamTank;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.UUID;

public abstract class AbstractSourchMachine extends AbstractTankHolder implements ISteamCapabilityProvider, ISteamGenerationProvider {
    public UUID id;
    private int ticksCounted = 0;
    private int tickGenerated = 0;
    public double machineGeneration = 30;
    public SteamTank steamTank;

    public AbstractSourchMachine(BlockEntityType<?> type, BlockPos pos, BlockState state, FluidTank t) {
        super(type, pos, state, t);
        id = UUID.randomUUID();
        steamTank = new SteamTank(1500, 0, 1.5, 30);
    }
    @Override
    public void syncTank(SteamTank t) {
        this.steamTank = t;
    }
    @Override
    public Type getExtractionType() {
        return Type.SOURCE;
    }

    @Override
    public SteamTank getMainTank() {
        return this.steamTank;
    }

    @Override
    public void load(CompoundTag p_155245_) {
        super.load(p_155245_);
        id = p_155245_.getUUID("machineid");
        steamTank.loadNbt(p_155245_);
    }

    @Override
    public double providePressure(ConduitStructureManager manager) {
        return this.machineGeneration;
    }

    @Override
    protected void saveAdditional(CompoundTag p_187471_) {
        p_187471_.putUUID("machineid", id);
        steamTank.saveNbt(p_187471_);
        super.saveAdditional(p_187471_);
    }

    public void tickEssential(Level level, BlockPos pos, BlockState state, AbstractSourchMachine entity){
        ticksCounted++;
        if(ticksCounted == 5 && !level.isClientSide()) {
            ticksCounted = 0;
            ModMessages.sendToClients(new SyncTank(entity.steamTank, entity.worldPosition));
        }

        this.steamTank.storage+=machineGeneration;

    }
}
