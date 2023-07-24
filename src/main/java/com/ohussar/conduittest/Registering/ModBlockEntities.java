package com.ohussar.conduittest.Registering;

import com.ohussar.conduittest.Blocks.Conduit.ConduitBlockEntity;
import com.ohussar.conduittest.Blocks.Machine.MachineBlockEntity;
import com.ohussar.conduittest.Blocks.ModBlocks;
import com.ohussar.conduittest.Blocks.SourceMachine.SourceMachineEntity;
import com.ohussar.conduittest.ConduitMain;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ConduitMain.MODID);

   public static final RegistryObject<BlockEntityType<ConduitBlockEntity>> CONDUIT_ENTITY = BLOCK_ENTITY_TYPES.register("conduit_entity",
           () -> BlockEntityType.Builder.of(ConduitBlockEntity::new, ModBlocks.CONDUIT.get()).build(null));

   public static final RegistryObject<BlockEntityType<MachineBlockEntity>> MACHINE_ENTITY = BLOCK_ENTITY_TYPES.register("machine_entity",
           () -> BlockEntityType.Builder.of(MachineBlockEntity::new, ModBlocks.MACHINE.get()).build(null));

   public static final RegistryObject<BlockEntityType<SourceMachineEntity>> SOURCE_MACHINE_ENTITY = BLOCK_ENTITY_TYPES.register("source_machine_entity",
           () -> BlockEntityType.Builder.of(SourceMachineEntity::new, ModBlocks.SOURCE_MACHINE.get()).build(null));

    public static void register(IEventBus bus){
        BLOCK_ENTITY_TYPES.register(bus);
    }

}
