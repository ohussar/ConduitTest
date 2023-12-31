package com.ohussar.conduittest.Core.Interfaces;

import com.ohussar.conduittest.Core.SteamTank;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.concurrent.locks.StampedLock;

public interface ISteamCapabilityProvider<T extends BlockEntity> extends ConduitExtractable<T> {

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

    /**
     * Make this the faces that can connect a cable based on the cable pos
     * @param pos
     * @return True or false
     */

    public boolean canAttachConduit(BlockPos pos);

    /**
     * must return true if a machine / cable can insert pressure into the steam tank if it has one.
     * if returns false, is expected that this machine can only provide pressure or it does not provide anything
     * to the cable system.
     * @return
     */
    public boolean canReceivePressure();

    /**
     * must return a double value that is considered the maximum pressure. The pressure is calculated with the following
     * formula: tankStorage / tankCapacity (maybe considered volume) * 0.5;
     * @return double
     */
    public double maxPressure();


}
