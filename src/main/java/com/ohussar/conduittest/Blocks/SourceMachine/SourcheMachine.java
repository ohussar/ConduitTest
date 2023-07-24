package com.ohussar.conduittest.Blocks.SourceMachine;

import com.ohussar.conduittest.Registering.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class SourcheMachine extends BaseEntityBlock {
    public SourcheMachine(Properties properties) {
        super(properties);
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SourceMachineEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type_) {
        return createTickerHelper(type_, ModBlockEntities.SOURCE_MACHINE_ENTITY.get(), SourceMachineEntity::tick);
    }
}
