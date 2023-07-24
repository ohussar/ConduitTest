package com.ohussar.conduittest.Blocks.Machine;

import com.ohussar.conduittest.ConduitMain;
import com.ohussar.conduittest.Core.Interfaces.ISteamCapabilityProvider;
import com.ohussar.conduittest.Core.Interfaces.ISyncVariables;
import com.ohussar.conduittest.Core.Networking.Messages.SyncTank;
import com.ohussar.conduittest.Core.Networking.ModMessages;
import com.ohussar.conduittest.Core.SteamTank;
import com.ohussar.conduittest.Registering.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.UUID;

public class MachineBlockEntity extends BlockEntity implements ISteamCapabilityProvider<MachineBlockEntity> {
    public SteamTank tank;
    private UUID id;

    private int sync = 0;

    public MachineBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MACHINE_ENTITY.get(), pos, state);
        tank = new SteamTank(1500, 0, 1.5);
        id = UUID.randomUUID();
    }

    @Override
    public SteamTank[] getTanks() {
        return new SteamTank[]{tank};
    }

    @Override
    public SteamTank getMainTank() {
        return tank;
    }

    @Override
    public int handleSteamReceived(int amount, boolean simulate) {

        int toFill = tank.maxCapacity - tank.storage;
        if(amount < toFill){
            toFill = amount;
        }
        if(!simulate){
            tank.storage += toFill;
        }

        return toFill;
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
    public boolean syncTimer(){
        sync++;
        if(sync > 5){
            sync = 0;
            return true;
        }
        return false;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, MachineBlockEntity entity){
        if(!level.isClientSide()){
            entity.sync++;
            if(entity.sync >= 10){
                entity.sync = 0;
                ModMessages.sendToClients(new SyncTank(entity.tank, entity.worldPosition));
            }
        }
    }
    @Override
    public Type getExtractionType(){
        return Type.DESTINATION;
    }
}
