package com.ohussar.conduittest.Core;

import com.ohussar.conduittest.Blocks.Conduit.ConduitBlockEntity;
import com.ohussar.conduittest.Core.Interfaces.ConduitExtractable;
import com.ohussar.conduittest.Core.Interfaces.ISteamCapabilityProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public abstract class AbstractSourchMachine extends BlockEntity implements ISteamCapabilityProvider {
    public UUID id;
    public List<ConduitExtractable> DESTINATIONS = new ArrayList<>();
    private int ticksCounted = 0;
    private int tickGenerated = 0;
    public AbstractSourchMachine(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        id = UUID.randomUUID();
    }

    @Override
    public Type getExtractionType() {
        return Type.SOURCE;
    }

    public void findDestinations(){
        BlockPos pos = worldPosition;
        BlockPos[] posList = {pos.north(), pos.east(), pos.south(), pos.west(), pos.above(), pos.below()};
        UUID called = UUID.randomUUID();
        for(int k = 0; k < posList.length; k++){
            if(level.getBlockEntity(posList[k]) instanceof ConduitBlockEntity){
                ConduitBlockEntity entity = (ConduitBlockEntity) level.getBlockEntity(posList[k]);
                entity.propagate(called, this);
            }
        }
    }

    @Override
    public void load(CompoundTag p_155245_) {
        super.load(p_155245_);
        id = p_155245_.getUUID("machineid");
    }

    @Override
    protected void saveAdditional(CompoundTag p_187471_) {
        p_187471_.putUUID("machineid", id);
        super.saveAdditional(p_187471_);
    }

    public void tickEssential(Level level, BlockPos pos, BlockState state, AbstractSourchMachine entity){
        if(!level.isClientSide()){
            ticksCounted++;
            if(ticksCounted >= tickGenerated){
                ticksCounted = 0;
                Random rand = new Random();
                tickGenerated = 15;
                DESTINATIONS.clear();
                findDestinations();
            }
        }

        // do this both in client side and server, to prevent excessive networking data, but client still must sync variables
        // with server.
        if(DESTINATIONS.size() > 0){
            for(int k = 0; k < DESTINATIONS.size(); k++) {
                ConduitExtractable dest = DESTINATIONS.get(k);
                if(dest instanceof ISteamCapabilityProvider<?> steam){
                    steam.handleSteamReceived(5, false);
                }
            }
        }
    }
}
