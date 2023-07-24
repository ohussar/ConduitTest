package com.ohussar.conduittest.Blocks.SourceMachine;

import com.ohussar.conduittest.Core.SteamTank;
import com.ohussar.conduittest.Core.AbstractSourchMachine;
import com.ohussar.conduittest.Registering.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.UUID;

public class SourceMachineEntity extends AbstractSourchMachine {
    public SourceMachineEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SOURCE_MACHINE_ENTITY.get(), pos, state);
    }

    @Override
    public BlockEntity getBlockEntity(Level level, BlockPos pos) {
        return this;
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, SourceMachineEntity entity){
        entity.tickEssential(level, pos ,state, entity);
    }

    @Override
    public SteamTank[] getTanks() {
        return null;
    }

    @Override
    public SteamTank getMainTank() {
        return null;
    }

    @Override
    public int handleSteamReceived(int amount, boolean simulate) {
        return 0;
    }

    @Override
    public void syncTank(SteamTank tank) {

    }
}
