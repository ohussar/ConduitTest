package com.ohussar.conduittest.Blocks.Conduit.Fluid;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.Nullable;

public class FluidPipe extends BaseEntityBlock {
    public static final IntegerProperty NORTH = IntegerProperty.create("north",0, 1);
    public static final IntegerProperty EAST = IntegerProperty.create("east",0, 1);
    public static final IntegerProperty SOUTH = IntegerProperty.create("south",0, 1);
    public static final IntegerProperty WEST = IntegerProperty.create("west",0, 1);
    public static final IntegerProperty UP = IntegerProperty.create("up",0, 1);
    public static final IntegerProperty DOWN = IntegerProperty.create("down",0, 1);

    protected FluidPipe(Properties properties) {
        super(properties);
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> blockState) {
        blockState.add(NORTH, EAST, SOUTH, WEST, UP, DOWN);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        return null;
    }
}
