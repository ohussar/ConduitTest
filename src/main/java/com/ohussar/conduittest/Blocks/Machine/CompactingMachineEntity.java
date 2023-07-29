package com.ohussar.conduittest.Blocks.Machine;

import com.ohussar.conduittest.Core.AllowedDirections;
import com.ohussar.conduittest.Core.MachineBase.MachineBlockEntity;
import com.ohussar.conduittest.Menus.CompactingMachineMenu;
import com.ohussar.conduittest.Registering.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CompactingMachineEntity extends MachineBlockEntity implements MenuProvider {
    public AllowedDirections allowedDirections = new AllowedDirections();
    private ItemStackHandler handler = new ItemStackHandler(4){
        @Override
        protected void onContentsChanged(int slot){
            setChanged();
        }
    };

    private LazyOptional<IItemHandler> inventoryLazyOptional = LazyOptional.empty();;

    public CompactingMachineEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.COMPACTING_MACHINE_ENTITY.get(),pos, state);
        allowedDirections.setValue("right", true).setValue("left", true).setValue("back", true);
    }

    public void onClick(Level level, Player player, CompactingMachineEntity entity, BlockPos pos){
        NetworkHooks.openScreen((ServerPlayer) player, entity, pos);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, CompactingMachineEntity entity) {
        entity.tickEssential(level, pos, state, entity);
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.put("inventory", handler.serializeNBT());
        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        handler.deserializeNBT(nbt.getCompound("inventory"));
    }

    @Override
    public void onLoad() {
        super.onLoad();
        inventoryLazyOptional = LazyOptional.of(() -> handler);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER){
            return inventoryLazyOptional.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        inventoryLazyOptional.invalidate();
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("gui.compactingmachine");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
        return new CompactingMachineMenu(id, inv, this, player);
    }

    @Override
    public boolean canReceivePressure() {
        return true;
    }

    @Override
    public double maxPressure() {
        return 30;
    }

    @Override
    public boolean canAttachConduit(BlockPos pos) {
        BlockState block = this.level.getBlockState(worldPosition);
        Direction dir = block.getValue(DirectionalBlock.FACING);

        return allowedDirections.isAllowed(this.worldPosition, pos, dir);
    }
}
