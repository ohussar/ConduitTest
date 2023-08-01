package com.ohussar.conduittest.Core.MachineBase;

import com.ohussar.conduittest.Core.Constants;
import com.ohussar.conduittest.Core.Interfaces.ISteamCapabilityProvider;
import com.ohussar.conduittest.Core.Networking.Messages.SyncTank;
import com.ohussar.conduittest.Core.Networking.ModMessages;
import com.ohussar.conduittest.Core.SteamTank;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.UUID;

public abstract class MachineBlockEntity extends BlockEntity implements ISteamCapabilityProvider<MachineBlockEntity> {
    public SteamTank tank;
    private UUID id;

    private int sync = 0;

    public MachineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        tank = new SteamTank(1500, 0, 1.5, 30);
        id = UUID.randomUUID();
    }
    @Override
    public SteamTank getMainTank() {
        return tank;
    }

    @Override
    public int handleSteamReceived(int amount, boolean simulate) {

        tank.storage += amount;

        return amount;
    }

    @Override
    public void syncTank(SteamTank t) {
        this.tank = t;
    }

    @Override
    public MachineBlockEntity getBlockEntity(Level level, BlockPos pos) {
        return (MachineBlockEntity) level.getBlockEntity(pos);
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        tank.saveNbt(nbt);
        nbt.putUUID("machineid",id);
        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        tank.loadNbt(nbt);
        id = nbt.getUUID("machineid");
    }
    public void tickEssential(Level level, BlockPos pos, BlockState state, MachineBlockEntity entity){
        if(!level.isClientSide()){
            entity.sync++;
            if(entity.sync >= 5){
                entity.sync = 0;
                ModMessages.sendToClients(new SyncTank(entity.tank, entity.worldPosition));
            }
        }
        this.tank.storage = Math.round(this.tank.storage*10d)/10d;
        this.tank.pressure = Constants.getPressure(tank.storage, tank.maxCapacity);
    }
    @Override
    public Type getExtractionType(){
        return Type.DESTINATION;
    }
}
