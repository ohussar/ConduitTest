package com.ohussar.conduittest.Core.MachineBase;

import com.ohussar.conduittest.Core.FluidTank;
import com.ohussar.conduittest.Core.Interfaces.IInsertFluid;
import com.ohussar.conduittest.Core.Interfaces.ISyncFluidTank;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractTankHolder extends BlockEntity implements IFluidHandler, ISyncFluidTank, IInsertFluid {

    public List<Fluid> filter = new ArrayList<>();
    public FluidTank tank = new FluidTank(FluidStack.EMPTY, 5000, 0);

    public AbstractTankHolder(BlockEntityType<?> type, BlockPos pos, BlockState state, FluidTank tank) {
        super(type, pos, state);
        this.tank = tank;
    }
    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        tank.loadNbt(nbt);
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        tank.saveNbt(nbt);
        super.saveAdditional(nbt);
    }

    @Override
    public int getTanks() {
        return 1;
    }

    @Override
    public @NotNull FluidStack getFluidInTank(int tank) {
        return this.tank.fluid;
    }

    @Override
    public int getTankCapacity(int tank) {
        return this.tank.tankCapacity;
    }
    @Override
    public boolean canInsertAmountAndFluid(int amount, Fluid fluid){
        return isFluidValid(0, fluid == null ? FluidStack.EMPTY : new FluidStack(fluid, 1)) &&
                ((amount + this.tank.fluidStored) <= this.tank.tankCapacity) &&
                ((amount + this.tank.fluidStored) >= 0);
    }
    @Override
    public boolean canExtractAmount(int amount){
        if(this.tank.fluidStored - amount >= 0 && this.tank.fluid != null && !this.tank.fluid.equals(FluidStack.EMPTY)){
            return true;
        }
        return false;
    }
    public void extractAmount(int amount){
        if(canExtractAmount(amount)){
            this.tank.fluidStored -= amount;
            if(this.tank.fluidStored <= 0 ){
                this.tank.fluidStored = 0;
                this.tank.fluid = FluidStack.EMPTY;
            }
        }
    }
    @Override
    public void insertAmountOfFluid(int amount, Fluid fluid){
        if(canInsertAmountAndFluid(amount, fluid)){
            this.tank.fluidStored += amount;
            if(this.tank.fluid == FluidStack.EMPTY){
                this.tank.fluid = new FluidStack(fluid, 1);
            }
            if(this.tank.fluidStored <= 0 ){
                this.tank.fluidStored = 0;
                this.tank.fluid = FluidStack.EMPTY;
            }
        }
    }

    @Override
    public FluidTank getTank() {
        return this.tank;
    }

    @Override
    public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
        if(filter == null || filter.size() == 0 || filter.contains(stack.getFluid())){
            return (stack.getFluid().equals(this.tank.fluid.getFluid())) || this.tank.fluid == FluidStack.EMPTY;
        }
        return false;
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        int toFill = 0;
        if(resource.isFluidEqual(this.tank.fluid)){
            toFill = tank.tankCapacity - (int)tank.fluidStored;
            if(resource.getAmount() < toFill){
                toFill = resource.getAmount();
            }
            if(action.execute()){
                this.tank.fluidStored += toFill;
            }
        }
        return toFill;
    }

    @Override
    public @NotNull FluidStack drain(FluidStack resource, FluidAction action) {
        int toDrain = 0;
        if(resource.isFluidEqual(this.tank.fluid)){
            if((int)this.tank.fluidStored - resource.getAmount() >= 0){
                toDrain = resource.getAmount();
            }else{
                toDrain = (int)this.tank.fluidStored;
            }
            if(action.execute()){
                this.tank.fluidStored -= toDrain;
            }
        }

        return new FluidStack(resource.getFluid(), toDrain);
    }

    @Override
    public @NotNull FluidStack drain(int maxDrain, FluidAction action) {

        int toDrain = 0;
        if((int)this.tank.fluidStored - maxDrain >= 0){
            toDrain = maxDrain;
        }else{
            toDrain = (int)this.tank.fluidStored;
        }
        if(action.execute()){
            this.tank.fluidStored -= toDrain;
        }

        return new FluidStack(this.tank.fluid.getFluid(), toDrain);
    }

    @Override
    public void receiveSync(FluidTank tank) {
        this.tank = tank;
    }
}
