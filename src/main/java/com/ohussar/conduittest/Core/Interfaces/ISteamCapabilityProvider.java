package com.ohussar.conduittest.Core.Interfaces;

import com.ohussar.conduittest.Core.SteamTank;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.concurrent.locks.StampedLock;

public interface ISteamCapabilityProvider<T extends BlockEntity> extends ConduitExtractable<T> {
     /**
      * Get all tanks in machine
     @return An array of SteamTanks
     */
    public SteamTank[] getTanks();

    /**
     *
     * @return The main tank of the machine
     */
    public SteamTank getMainTank();

     /**
     Handles the steam received, if simulate parameter = true, must not add
      the amount to the tank.
     @return Must return the amount FILLED in the tank
     */
    public int handleSteamReceived(int amount, boolean simulate);

    /**
     * Handles the tank sync from server to client
     * @param tank
     */
    public void syncTank(SteamTank tank);

}
