package com.ohussar.conduittest.Items;

import com.ohussar.conduittest.Blocks.ModBlocks;
import com.ohussar.conduittest.ConduitMain;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ConduitMain.MODID);


    public static final RegistryObject<Item> CONDUIT = ITEMS.register("conduit", () ->
            new BlockItem(ModBlocks.CONDUIT.get(), new Item.Properties().tab(ConduitMain.tab)));
    public static <T extends Item> RegistryObject<Item> register(String name, T t){
        return ITEMS.register(name, () -> t);
    }

    public static RegistryObject<Item> fromBlock(String name, Block block){
        return ITEMS.register(name, () -> new BlockItem(block, new Item.Properties().tab(ConduitMain.tab)));
    }

    public static void register(IEventBus bus){
        ITEMS.register(bus);
    }
}
