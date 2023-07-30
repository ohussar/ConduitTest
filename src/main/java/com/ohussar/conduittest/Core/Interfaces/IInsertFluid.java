package com.ohussar.conduittest.Core.Interfaces;

import com.ohussar.conduittest.Core.FluidTank;
import net.minecraft.world.level.material.Fluid;

public interface IInsertFluid {
    public boolean canInsertAmountAndFluid(int amount, Fluid fluid);
    public void insertAmountOfFluid(int amount, Fluid fluid);

    public FluidTank getTank();
}
