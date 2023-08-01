package com.ohussar.conduittest.Blocks.Tank;

import com.ohussar.conduittest.Blocks.Machine.CompactingMachineEntity;
import com.ohussar.conduittest.ConduitMain;
import com.ohussar.conduittest.Core.FluidTank;
import com.ohussar.conduittest.Core.Interfaces.IInsertFluid;
import com.ohussar.conduittest.Core.Interfaces.ISyncFluidTank;
import com.ohussar.conduittest.Core.MachineBase.AbstractTankHolder;
import com.ohussar.conduittest.Core.Networking.Messages.SyncFluidTank;
import com.ohussar.conduittest.Core.Networking.ModMessages;
import com.ohussar.conduittest.Registering.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.WaterFluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class TankBlockEntity extends AbstractTankHolder {
    public TankBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.TANK_BLOCK_ENTITY.get(), pos, state, new FluidTank(FluidStack.EMPTY, 5000, 0));
    }
    private int tick = 0;
    private boolean firstTick = true;

    public static void tick(Level level, BlockPos pos, BlockState state, TankBlockEntity entity) {
        entity.tick++;
        if((entity.tick >= 5 || entity.firstTick)&& !level.isClientSide()){
            entity.firstTick = false;
            entity.tick = 0;
            ModMessages.sendToClients(new SyncFluidTank(entity.tank, pos));
        }
    }
}
