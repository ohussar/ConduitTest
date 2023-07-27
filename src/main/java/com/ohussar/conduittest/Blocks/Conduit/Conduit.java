package com.ohussar.conduittest.Blocks.Conduit;

import com.ohussar.conduittest.Blocks.SourceMachine.SourceMachineEntity;
import com.ohussar.conduittest.ConduitMain;
import com.ohussar.conduittest.Core.DirectionHolder;
import com.ohussar.conduittest.Core.Interfaces.ConduitExtractable;
import com.ohussar.conduittest.Registering.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Conduit extends BaseEntityBlock {
    public static final IntegerProperty NORTH = IntegerProperty.create("north",0, 2);
    public static final IntegerProperty EAST = IntegerProperty.create("east",0, 2);
    public static final IntegerProperty SOUTH = IntegerProperty.create("south",0, 2);
    public static final IntegerProperty WEST = IntegerProperty.create("west",0, 2);
    public static final IntegerProperty UP = IntegerProperty.create("up",0, 2);
    public static final IntegerProperty DOWN = IntegerProperty.create("down",0, 2);

    private VoxelShape shape = Block.box(6, 6, 6, 10, 10, 10);

    public Conduit(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(NORTH, Integer.valueOf(0)).setValue(EAST, Integer.valueOf(0)).setValue(SOUTH, Integer.valueOf(0)).setValue(WEST, Integer.valueOf(0)).setValue(UP, Integer.valueOf(0)).setValue(DOWN, Integer.valueOf(0)));
    }


    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockGetter blockgetter = context.getLevel();
        BlockPos blockpos = context.getClickedPos();

        return getState(blockgetter, super.getStateForPlacement(context), blockpos);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction p_60542_, BlockState p_60543_, LevelAccessor level, BlockPos pos, BlockPos p_60546_) {
        return getState(level, state, pos);
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter getter, BlockPos pos, CollisionContext p_60558_) {
        DirectionHolder dir = getDirections(pos, getter);
        int xstart = 6, ystart = 6, zstart = 6;
        int xend = 10, yend = 10, zend = 10;
        if(dir.north != 0){
            zstart = 0;
        }
        if(dir.south != 0){
            zend += 6;
        }
        if(dir.west != 0){
            xstart = 0;
        }
        if(dir.east != 0){
            xend += 6;
        }

        if(dir.up != 0){
            yend += 6;
        }
        if(dir.down != 0){
            ystart = 0;
        }

        VoxelShape xaxis = Block.box(xstart, 6, 6, xend, 10, 10);
        VoxelShape yaxis = Block.box(6, ystart, 6, 10, yend, 10);
        VoxelShape zaxis = Block.box(6, 6, zstart, 10, 10, zend);

        return Shapes.or(Shapes.or(xaxis, yaxis), zaxis);
    }

    @Override
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    public int getConnectionInt(BlockEntity block){
        if(block instanceof ConduitExtractable<?> ){
            return 2;
        }else if(block instanceof ConduitBlockEntity){
            return 1;
        }
        return 0;
    }

    public DirectionHolder getDirections(BlockPos pos, BlockGetter getter){
        BlockPos blockpos1 = pos.north();
        BlockPos blockpos2 = pos.east();
        BlockPos blockpos3 = pos.south();
        BlockPos blockpos4 = pos.west();
        BlockPos blockpos5 = pos.above();
        BlockPos blockpos6 = pos.below();
        BlockEntity blockstate1 = getter.getBlockEntity(blockpos1);
        BlockEntity blockstate2 = getter.getBlockEntity(blockpos2);
        BlockEntity blockstate3 = getter.getBlockEntity(blockpos3);
        BlockEntity blockstate4 = getter.getBlockEntity(blockpos4);
        BlockEntity blockstate5 = getter.getBlockEntity(blockpos5);
        BlockEntity blockstate6 = getter.getBlockEntity(blockpos6);
        int north = getConnectionInt(blockstate1);
        int east = getConnectionInt(blockstate2);
        int south = getConnectionInt(blockstate3);
        int west = getConnectionInt(blockstate4);
        int up = getConnectionInt(blockstate5);
        int down = getConnectionInt(blockstate6);
        return new DirectionHolder(north, east, south, west, up, down);
    }

    public BlockState getState(BlockGetter level, BlockState state, BlockPos blockpos){
        DirectionHolder dir = getDirections(blockpos, level);

        BlockState newState =  state.setValue(NORTH, Integer.valueOf(dir.north))
                .setValue(EAST , Integer.valueOf(dir.east))
                .setValue(SOUTH, Integer.valueOf(dir.south))
                .setValue(WEST , Integer.valueOf(dir.west))
                .setValue(UP   , Integer.valueOf(dir.up))
                .setValue(DOWN , Integer.valueOf(dir.down));


        return newState;
    }

    @Override
    public void neighborChanged(BlockState p_60509_, Level level, BlockPos blockPos, Block p_60512_, BlockPos pos, boolean p_60514_) {
        BlockState blockstate = level.getBlockState(blockPos);
        Block block = blockstate.getBlock();
        if(block instanceof Conduit conduit){
            BlockState newstate = conduit.getState(level, blockstate, blockPos);
            ((ConduitBlockEntity) (level.getBlockEntity(blockPos))).getAllNeightbors();
            if(!level.isClientSide()) {
                level.sendBlockUpdated(blockPos, blockstate, newstate, 2);
            }
        }
        if(level.getBlockState(pos).getBlock() instanceof Conduit conduit){
            ConduitBlockEntity myEntity = (ConduitBlockEntity) level.getBlockEntity(blockPos);

            ConduitBlockEntity adjacentManager = (ConduitBlockEntity) level.getBlockEntity(pos);

            if(myEntity.getManager() == null){
                myEntity.createManager();
            }

            myEntity.getManager().tryToConnectStructures(level, pos);

        }
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean p_60519_) {
        if(!state.is(newState.getBlock())){
            ((ConduitBlockEntity) level.getBlockEntity(pos)).onRemove();
        }
        super.onRemove(state, level, pos, newState, p_60519_);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> blockState) {
        blockState.add(NORTH, EAST, SOUTH, WEST, UP, DOWN);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        ConduitBlockEntity block = new ConduitBlockEntity(pos, state);

        return block;
    }
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type_) {
        return createTickerHelper(type_, ModBlockEntities.CONDUIT_ENTITY.get(), ConduitBlockEntity::tick);
    }
}
