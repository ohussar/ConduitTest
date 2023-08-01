package com.ohussar.conduittest.Core.Interfaces;

import com.ohussar.conduittest.Blocks.Conduit.ConduitStructureManager;

public interface ISteamGenerationProvider {
    public double providePressure(ConduitStructureManager manager);
    public double maximumPressurePush();
    public double pressureDecay(double pressure);
}
