package com.ohussar.conduittest.Blocks.SourceMachine;

import com.ohussar.conduittest.Core.AllowedDirections;
import com.ohussar.conduittest.Core.SteamTank;
import com.ohussar.conduittest.Core.AbstractSourchMachine;
import com.ohussar.conduittest.Registering.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.UUID;

public class GeneratorMachineEntity extends AbstractSourchMachine {
    public AllowedDirections allowedDirections = new AllowedDirections();
    public GeneratorMachineEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GENERATOR_MACHINE_ENTITY.get(), pos, state);
        allowedDirections.setValue("right", true);
    }

    @Override
    public BlockEntity getBlockEntity(Level level, BlockPos pos) {
        return this;
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, GeneratorMachineEntity entity){
        entity.tickEssential(level, pos ,state, entity);
    }

    @Override
    public SteamTank[] getTanks() {
        return new SteamTank[]{tank};
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
    public boolean canReceivePressure() {
        return false;
    }

    @Override
    public boolean canAttachConduit(BlockPos pos) {
        BlockState block = this.level.getBlockState(worldPosition);
        Direction dir = block.getValue(DirectionalBlock.FACING);

        return allowedDirections.isAllowed(this.worldPosition, pos, dir);
    }
}
