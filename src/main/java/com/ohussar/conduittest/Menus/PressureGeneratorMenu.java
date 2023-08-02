package com.ohussar.conduittest.Menus;

import com.ohussar.conduittest.Blocks.Machine.CompactingMachineEntity;
import com.ohussar.conduittest.Blocks.SourceMachine.PressureGeneratorEntity;
import com.ohussar.conduittest.Registering.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.Nullable;

public class PressureGeneratorMenu extends AbstractContainerQuickMoveMenu{
    public PressureGeneratorEntity entity;
    public Level level;
    public Player player;
    public PressureGeneratorMenu(int containerId, Inventory inv, FriendlyByteBuf buf) {
        this(containerId, inv, inv.player.level.getBlockEntity(buf.readBlockPos()), inv.player);

    }

    public PressureGeneratorMenu(int containerId, Inventory inv, BlockEntity entity, Player player) {
        super(ModMenuTypes.PRESSURE_GENERATOR_MENU.get(), containerId, 1, inv);
        this.entity = (PressureGeneratorEntity) entity;
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
