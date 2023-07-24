package com.ohussar.conduittest.Blocks;

import com.ohussar.conduittest.Blocks.Conduit.Conduit;
import com.ohussar.conduittest.Blocks.Machine.Machine;
import com.ohussar.conduittest.Blocks.SourceMachine.SourcheMachine;
import com.ohussar.conduittest.ConduitMain;
import com.ohussar.conduittest.Items.ModItems;
import net.minecraft.data.Main;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.Tags;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ConduitMain.MODID);
    public static final RegistryObject<Block> CONDUIT = BLOCKS.register("conduit", () -> new Conduit(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryObject<Block> MACHINE = register("machine",
            () -> new Machine(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> SOURCE_MACHINE = register("source_machine",
            () -> new SourcheMachine(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static <T extends Block> RegistryObject<Block> register(String name, Supplier<T> block){
        RegistryObject<Block> BLOCK = BLOCKS.register(name, block);
        registerBlockItem(name, BLOCK, ConduitMain.tab);
        return BLOCK;
    }

    public static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab){
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }

    public static void register(IEventBus bus){
        BLOCKS.register(bus);
    }
}
