package com.ohussar.conduittest.Blocks.SourceMachine;

import com.ohussar.conduittest.Blocks.Tank.TankBlockEntity;
import com.ohussar.conduittest.Core.CommonFunctions;
import com.ohussar.conduittest.Core.MachineBase.TankHolderBase;
import com.ohussar.conduittest.Registering.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class PressureGenerator extends TankHolderBase<PressureGeneratorEntity> {

    public static final DirectionProperty FACING = DirectionalBlock.FACING;
    public final VoxelShape shape = Shapes.or(
            Block.box(0, 0, 0, 16, 10, 16),
            Block.box(0, 10, 0, 16, 16, 7),
            Block.box(2, 10, 8,  7, 22, 13)
    );
    public PressureGenerator(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH));
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder){
        builder.add(FACING);
    }
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {

        Direction[] dir = Direction.orderedByNearest(context.getPlayer());
        Direction desiredDir = null;
        for(int k = 0; k < dir.length; k++){
            if(dir[k] == Direction.UP || dir[k] == Direction.DOWN){
                continue;
            }else if(desiredDir == null){
                desiredDir = dir[k];
                break;
            }
        }

        return this.defaultBlockState().setValue(FACING, desiredDir);
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new PressureGeneratorEntity(pos, state);
    }

    @Override
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    @Override
    public VoxelShape getShape(BlockState state_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        if(state_.getValue(FACING).equals(Direction.EAST)){
            return CommonFunctions.rotateShape(Direction.WEST, Direction.NORTH, shape);
        }
        if(state_.getValue(FACING).equals(Direction.NORTH)){
            return CommonFunctions.rotateShape(Direction.WEST, Direction.EAST, shape);
        }
        return CommonFunctions.rotateShape(Direction.WEST, state_.getValue(FACING), shape);
    }
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type_) {
        return createTickerHelper(type_, ModBlockEntities.GENERATOR_MACHINE_ENTITY.get(), PressureGeneratorEntity::tick);
    }
}
