package com.ohussar.conduittest.Blocks.SourceMachine;

import com.ohussar.conduittest.Core.AllowedDirections;
import com.ohussar.conduittest.Core.FluidTank;
import com.ohussar.conduittest.Core.Networking.Messages.SyncFluidTank;
import com.ohussar.conduittest.Core.Networking.ModMessages;
import com.ohussar.conduittest.Core.SteamTank;
import com.ohussar.conduittest.Core.MachineBase.AbstractSourchMachine;
import com.ohussar.conduittest.Registering.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;

import java.util.UUID;

public class PressureGeneratorEntity extends AbstractSourchMachine {
    public AllowedDirections allowedDirections = new AllowedDirections();
    public PressureGeneratorEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GENERATOR_MACHINE_ENTITY.get(), pos, state, new FluidTank(FluidStack.EMPTY, 1500, 0));
        allowedDirections.setValue("right", true).setValue("left", true).setValue("front", true);
        this.filter.add(Fluids.WATER.getSource());
    }
    private int tick = 0;
    private boolean firstTick = true;
    @Override
    public BlockEntity getBlockEntity(Level level, BlockPos pos) {
        return this;
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, PressureGeneratorEntity entity){
        entity.tickEssential(level, pos,state, entity);
        entity.tick++;
        if((entity.tick >= 5 || entity.firstTick)&& !level.isClientSide()){
            entity.firstTick = false;
            entity.tick = 0;
            ModMessages.sendToClients(new SyncFluidTank(entity.tank, pos));
        }
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
    public double maxPressure() {
        return 1;
    }

    @Override
    public boolean canAttachConduit(BlockPos pos) {
        BlockState block = this.level.getBlockState(worldPosition);
        Direction dir = block.getValue(DirectionalBlock.FACING);

        return allowedDirections.isAllowed(this.worldPosition, pos, dir);
    }

    @Override
    public double maximumPressurePush() {
        return 9.5;
    }

    @Override
    public double pressureDecay(double pressure) {
        if(pressure <= 0){
            pressure = 0.1;
        }
        double decay = Math.pow((this.maximumPressurePush()/2) / pressure, 3);
        if(decay > 1){
            decay = 1;
        }
        return decay;
    }
}
