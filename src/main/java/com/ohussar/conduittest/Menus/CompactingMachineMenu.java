package com.ohussar.conduittest.Menus;

import com.ohussar.conduittest.Blocks.Machine.CompactingMachineEntity;
import com.ohussar.conduittest.Registering.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;

public class CompactingMachineMenu extends AbstractContainerQuickMoveMenu{

    public CompactingMachineEntity entity;
    public Level level;
    public Player player;
    public CompactingMachineMenu(int containerId, Inventory inv, FriendlyByteBuf buf) {
        this(containerId, inv, inv.player.level.getBlockEntity(buf.readBlockPos()), inv.player);

    }
    public CompactingMachineMenu(int id, Inventory inv, BlockEntity entity, Player player){
        super(ModMenuTypes.COMPACTING_MACHINE_MENU.get(), id, 0, inv);

        this.entity = (CompactingMachineEntity) entity;
        this.level = player.level;
        this.player = player;
    }

    @Override
    public boolean stillValid(Player player) {
        double dist = Math.sqrt(player.blockPosition().distSqr(entity.getBlockPos()));
        if(dist<7){
            return true;
        }
        return false;
    }
}
