package com.ohussar.conduittest.Blocks.Conduit;

import com.ohussar.conduittest.ConduitMain;
import com.ohussar.conduittest.Core.CommonFunctions;
import com.ohussar.conduittest.Core.Constants;
import com.ohussar.conduittest.Core.Interfaces.ISteamCapabilityProvider;
import com.ohussar.conduittest.Core.Interfaces.ISteamGenerationProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ConduitStructureManager {

    private BlockPos original;
    private double systemPressure = 0;
    private List<BlockPos> conduitsConnected = new ArrayList<>();
    private List<BlockPos> blocksConnected = new ArrayList<>();
    public UUID uuid;

    public ConduitStructureManager(BlockPos original){
        this.original = original;
        addBlockConnected(original);
        uuid = UUID.randomUUID();
    }

    public void addBlockConnected(BlockPos pos){
        if(!conduitsConnected.contains(pos)){
            conduitsConnected.add(pos);
        }
    }

    public boolean isRoot(BlockPos pos){
        return pos.equals(original);
    }

    public void removeBlockPos(BlockPos pos, Level level){
        if(conduitsConnected.contains(pos)){
            conduitsConnected.remove(pos);
        }
        updateStructure(level, pos);
    }

    public void reassembleStructure(Level level, BlockPos startPos){
        BlockPos[] aa = CommonFunctions.adjacentBlocks(startPos);
        UUID changeStructureId = UUID.randomUUID();

        int number = 0;
        for(int k = 0; k < aa.length; k++) {
            if(level.getBlockEntity(aa[k]) instanceof ConduitBlockEntity entity){
                number++;
            }
        }

        for(int k = 0; k < aa.length; k++) {
            if(level.getBlockEntity(aa[k]) instanceof ConduitBlockEntity entity){
                entity.propagateManager(new ConduitStructureManager(entity.getBlockPos()), null, changeStructureId);
                entity.getManager().updateMachines(level);
            }
        }
    }

    public void updateStructure(Level level, BlockPos pos) {
        if(conduitsConnected.size() == 0){
            return;
        }
        BlockPos[] aa = CommonFunctions.adjacentBlocks(pos);
        UUID changeStructureId = UUID.randomUUID();

        int number = 0;
        for(int k = 0; k < aa.length; k++) {
            if(level.getBlockEntity(aa[k]) instanceof ConduitBlockEntity entity){
                number++;
            }
        }

        BlockPos first = null;
        for(int k = 0; k < aa.length; k++) {
            if(level.getBlockEntity(aa[k]) instanceof ConduitBlockEntity entity){
                if(first == null){
                    first = aa[k];
                }
                entity.propagateManager(new ConduitStructureManager(entity.getBlockPos()), pos, changeStructureId);
                entity.getManager().updateMachines(level);
            }
        }
        if(pos.equals(original)){
            original = first;
        }
    }

    public void updateMachines(Level level){
        blocksConnected.clear();

        if(conduitsConnected.size() > 0){
            for(int kk = 0; kk < conduitsConnected.size(); kk++){
                if(level.getBlockEntity(conduitsConnected.get(kk)) instanceof ConduitBlockEntity){
                    BlockPos pos = conduitsConnected.get(kk);
                    BlockPos[] aa = CommonFunctions.adjacentBlocks(pos);

                    for(int jj = 0; jj < aa.length; jj++){
                        if(level.getBlockEntity(aa[jj]) instanceof ISteamCapabilityProvider<?> steam){
                            if(steam.canAttachConduit(pos)){
                                addMachines(aa[jj]);
                            }
                        }
                    }
                }
            }
        }

        ConduitMain.LOGGER.info("Machines Connected: " + blocksConnected.toString());

    }

    public int getMachineCount(){
        return blocksConnected.size();
    }
    public int getConduitCount(){
        return conduitsConnected.size();
    }

    public void addMachines(BlockPos pos){
        if(!blocksConnected.contains(pos)){
            blocksConnected.add(pos);
        }
    }


    public void tickSystem(Level level){
        addToSystem(0, level);

        if(blocksConnected.size() > 0){
            for(int kk = 0; kk < blocksConnected.size(); kk++){
                BlockEntity entity = level.getBlockEntity(blocksConnected.get(kk));
                if(entity instanceof ISteamGenerationProvider steam){
                    double pressureProvided = steam.providePressure(this);
                    double pressureDecay = steam.pressureDecay(this.systemPressure);
                    addToSystem(pressureProvided * pressureDecay, level);
                }
            }
        }
    }


    public void addToSystem(double steamValue, Level level){
        if(blocksConnected.size() > 0){
            double totalStorage = 0;
            int number = 0;
            double totalparts = 0;
            double totalVolume = 0;
            for(int kk = 0; kk < blocksConnected.size(); kk++){
                BlockEntity entity = level.getBlockEntity(blocksConnected.get(kk));
                if(entity instanceof ISteamCapabilityProvider<?> steam){
                    if(steam.getMainTank() != null){
                        number++;
                        totalStorage += steam.getMainTank().storage;
                        totalVolume += steam.getMainTank().maxCapacity;
                        totalparts += (double)1 / (double)(steam.getMainTank().storage > 0 ? steam.getMainTank().storage : 1);
                    }
                }
            }

            double stableNumber = totalStorage/number;
            this.systemPressure = Constants.getPressure(totalStorage, totalVolume);
            if(number > 0) {
                for (int kk = 0; kk < blocksConnected.size(); kk++) {
                    BlockEntity entity = level.getBlockEntity(blocksConnected.get(kk));
                    if(entity instanceof ISteamCapabilityProvider<?> steam) {
                        if(steam.getMainTank() != null) {
                            double part = (double)1 / (double)(steam.getMainTank().storage > 0 ? steam.getMainTank().storage : 1);
                            double to = (steamValue/ totalparts) * part;
                            double toChange = (stableNumber - steam.getMainTank().storage) * 0.1d + to;
                            if(Math.abs(toChange) < 0.1){
                                toChange = 0d;
                            }
                            if(toChange == 0 && Math.abs(stableNumber - steam.getMainTank().storage) > 0){
                                toChange = (double)0.5 * (double)sign(stableNumber - steam.getMainTank().storage);
                            }else if(Math.abs(toChange) > 50d){
                                toChange = 50d * sign(toChange);
                            }
                            toChange = Math.round(toChange*(double)100)/(double)100;

                            steam.getMainTank().storage += toChange;
                        }
                    }
                }
            }
        }
    }

    private int sign(double x){
        if(x < 0){
            return -1;
        }
        return 1;
    }

    public void tryToConnectStructures(Level level, BlockPos pos){
        List<UUID> surrounding = new ArrayList<>();
        List<ConduitStructureManager> sur = new ArrayList<>();
        BlockPos[] aa = CommonFunctions.adjacentBlocks(pos);
        for(int k = 0; k < aa.length; k++){
            if(level.getBlockEntity(aa[k]) instanceof ConduitBlockEntity entity) {
                if(entity.getManager() != null && !surrounding.contains(entity.getManager().uuid)){
                    surrounding.add(entity.getManager().uuid);
                    sur.add(entity.getManager());
                }
            }
        }

        if(surrounding.size() > 1){
            ConduitStructureManager newManager = new ConduitStructureManager(pos);
            UUID updateStructureId = UUID.randomUUID();
            for(int k = 0; k < aa.length; k++){
                if(level.getBlockEntity(aa[k]) instanceof ConduitBlockEntity entity) {
                    entity.propagateManager(newManager, pos, updateStructureId);
                }
            }
        }else if(surrounding.size() == 1){
            ConduitBlockEntity be = (ConduitBlockEntity) (level.getBlockEntity(pos));
            be.setManager(sur.get(0));
            be.getManager().addBlockConnected(pos);
            be.getManager().updateMachines(level);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ConduitStructureManager man){
            return man.uuid.equals(this.uuid);
        }
        return false;
    }
}
