package com.ohussar.conduittest.Blocks.Machine;

import com.ohussar.conduittest.ConduitMain;
import com.ohussar.conduittest.Core.MachineBlockEntity;
import com.ohussar.conduittest.Menus.CompactingMachineMenu;
import com.ohussar.conduittest.Registering.ModBlockEntities;
import com.ohussar.conduittest.Registering.ModMenuTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class CompactingMachineEntity extends MachineBlockEntity implements MenuProvider {
    public CompactingMachineEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.COMPACTING_MACHINE_ENTITY.get(),pos, state);
    }

    public void onClick(Level level, Player player, CompactingMachineEntity entity, BlockPos pos){
        NetworkHooks.openScreen((ServerPlayer) player, entity, pos);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, CompactingMachineEntity entity) {
        entity.tickEssential(level, pos, state, entity);
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
}
