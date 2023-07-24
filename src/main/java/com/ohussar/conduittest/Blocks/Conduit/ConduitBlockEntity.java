package com.ohussar.conduittest.Blocks.Conduit;

import com.ohussar.conduittest.Core.DirectionHolder;
import com.ohussar.conduittest.Core.Interfaces.ConduitExtractable;
import com.ohussar.conduittest.Core.AbstractSourchMachine;
import com.ohussar.conduittest.Registering.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ConduitBlockEntity extends BlockEntity {
    public List<ConduitExtractable> connected = new ArrayList<>();

    public UUID calledId = UUID.randomUUID();

    public ConduitBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CONDUIT_ENTITY.get(), pos, state);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
    }

    public void propagate(UUID id, AbstractSourchMachine machine){
        if(id.equals(calledId)){
            return;
        }else{
            calledId = id;
        }
        if(connected.size() > 0){
            for(int k = 0; k < connected.size(); k++){
                boolean canAdd = true;
                for(int j = 0; j < machine.DESTINATIONS.size(); j++){
                    if(machine.DESTINATIONS.get(j).getId().equals(connected.get(k).getId())
                            || connected.get(k).getExtractionType() == ConduitExtractable.Type.SOURCE){
                        canAdd = false;
                    }
                }
                if(canAdd){
                    machine.DESTINATIONS.add(connected.get(k));
                }
            }
        }
        BlockPos pos = this.worldPosition;
        BlockGetter getter = this.level;

        BlockState state = getter.getBlockState(pos);
        Conduit block = (Conduit) state.getBlock();
        int[] dir = block.getDirections(pos, getter).toArray();
        BlockPos around[] = {pos.north(), pos.east(), pos.south(), pos.west(), pos.above(), pos.below()};

        for(int k = 0; k < dir.length; k++) {
            if (dir[k] == 1 && getter.getBlockEntity(around[k]) instanceof ConduitBlockEntity entity) {
                if (!entity.calledId.equals(this.calledId)) {
                    entity.propagate(calledId, machine);
                }
            }
        }
    }

    public void getAllNeightbors(){
        BlockPos pos = this.worldPosition;
        BlockGetter getter = this.level;

        BlockState state = getter.getBlockState(pos);
        Conduit block = (Conduit) state.getBlock();
        DirectionHolder holder = block.getDirections(pos, getter);
        connected.clear();
        int[] array = holder.toArray();
        BlockPos[] posa = {pos.north(), pos.east(), pos.south(), pos.west(), pos.above(), pos.below()};
        for(int k = 0; k < array.length; k++){
            if(array[k] == 2){
                ConduitExtractable e = (ConduitExtractable) getter.getBlockEntity(posa[k]);
                connected.add(e);
            }
        }
    }
}
