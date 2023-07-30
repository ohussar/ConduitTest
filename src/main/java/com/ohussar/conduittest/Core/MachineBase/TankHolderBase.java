package com.ohussar.conduittest.Core.MachineBase;

import com.ohussar.conduittest.Blocks.Tank.TankBlockEntity;
import com.ohussar.conduittest.Core.Interfaces.IInsertFluid;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;

import java.util.Optional;

public abstract class TankHolderBase<T extends IInsertFluid> extends BaseEntityBlock implements LiquidBlockContainer, BucketPickup {
    protected TankHolderBase(Properties p_49224_) {
        super(p_49224_);
    }

    @Override
    public ItemStack pickupBlock(LevelAccessor level, BlockPos pos, BlockState state) {
        T entity = (T) level.getBlockEntity(pos);
        if(entity.canInsertAmountAndFluid(-1000, entity.getTank().fluid.getFluid())){
            ItemStack bucket = new ItemStack(entity.getTank().fluid.getFluid().getBucket());
            bucket.setCount(1);
            entity.insertAmountOfFluid(-1000, entity.getTank().fluid.getFluid());
            return bucket;

        }
        return ItemStack.EMPTY;
    }

    @Override
    public Optional<SoundEvent> getPickupSound() {
        return Optional.empty();
    }

    @Override
    public boolean canPlaceLiquid(BlockGetter level, BlockPos pos, BlockState state, Fluid fluid) {
        T entity = (T) level.getBlockEntity(pos);
        boolean aa = entity.canInsertAmountAndFluid(1000, fluid);
        return aa;
    }

    @Override
    public boolean placeLiquid(LevelAccessor getter, BlockPos pos, BlockState ps, FluidState state) {
        T entity = (T) getter.getBlockEntity(pos);
        if(entity.canInsertAmountAndFluid(1000, state.getType())){
            entity.insertAmountOfFluid(1000, state.getType());
            return true;
        }
        return false;
    }
}
