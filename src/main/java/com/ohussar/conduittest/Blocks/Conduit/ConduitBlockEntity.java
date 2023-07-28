package com.ohussar.conduittest.Blocks.Conduit;

import com.ohussar.conduittest.ConduitMain;
import com.ohussar.conduittest.Core.Interfaces.ConduitExtractable;
import com.ohussar.conduittest.Registering.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;



public class ConduitBlockEntity extends BlockEntity {
    public List<ConduitExtractable> connected = new ArrayList<>();

    public UUID calledId = UUID.randomUUID();
    private ConduitStructureManager manager;
    private UUID changeId;
    private boolean isRoot = false;
    private boolean start = false;
    public ConduitBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CONDUIT_ENTITY.get(), pos, state);
    }


    public void onRemove(){
        if(manager != null){
            manager.removeBlockPos(worldPosition, this.level);
        }
    }
    public ConduitStructureManager getManager(){
        return this.manager;
    }

    public void createManager(){
        this.manager = new ConduitStructureManager(worldPosition);
        this.manager.updateMachines(this.level);
    }

    public void setManager(ConduitStructureManager manager){
        this.manager = manager;
    }

    public void propagateManager(ConduitStructureManager manager, BlockPos removedPos, UUID Id){
        if(this.changeId != null && this.changeId.equals(Id)){
            return;
        }
        this.changeId = Id;
        if(this.manager != null && manager.uuid.equals(this.manager.uuid)){
            return;
        }
        if(this.worldPosition.equals(removedPos)){
            return;
        }
        this.manager = manager;
        this.manager.addBlockConnected(worldPosition);
        BlockPos pos = this.worldPosition;
        BlockGetter getter = this.level;

        BlockState state = getter.getBlockState(pos);
        if(!(state.getBlock() instanceof Conduit)){
            return;
        }
        Conduit block = (Conduit) state.getBlock();
        int[] dir = block.getDirections(pos, getter).toArray();
        BlockPos around[] = {pos.north(), pos.east(), pos.south(), pos.west(), pos.above(), pos.below()};

        for(int k = 0; k < dir.length; k++) {
            if (dir[k] == 1 && getter.getBlockEntity(around[k]) instanceof ConduitBlockEntity entity) {
                entity.propagateManager(manager, removedPos, Id);
            }
        }
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.isRoot = nbt.getBoolean("isRoot");
        if(this.isRoot){
            if(this.manager == null){
                this.manager = new ConduitStructureManager(this.worldPosition);
            }
            start = true;
            this.manager.addBlockConnected(this.worldPosition);
            ConduitMain.LOGGER.info("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        }
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.putBoolean("isRoot", this.isRoot);
        super.saveAdditional(nbt);
    }

    public void getAllNeightbors(){
        if(this.manager != null){
            this.manager.updateMachines(level);
        }
    }

    public static void tick(Level level, BlockPos pos, BlockState state, ConduitBlockEntity entity){
        if(entity.manager != null && !level.isClientSide()){
            if(entity.manager.isRoot(pos)){
                //stabilizes the system
                entity.isRoot = true;
                entity.manager.tickSystem(level);
                if(entity.start){
                    entity.start = false;
                    entity.manager.reassembleStructure(level, pos);
                    entity.manager.updateMachines(level);
                }
            }else{
                entity.isRoot = false;
            }
        }
        if(entity.manager == null && !level.isClientSide()){
            entity.createManager();
        }
    }
}
