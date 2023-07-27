package com.ohussar.conduittest;

import com.mojang.logging.LogUtils;
import com.ohussar.conduittest.Blocks.Machine.CompactingMachineScreen;
import com.ohussar.conduittest.Blocks.ModBlocks;
import com.ohussar.conduittest.Core.Networking.ModMessages;
import com.ohussar.conduittest.Items.ModItems;
import com.ohussar.conduittest.Registering.ModBlockEntities;
import com.ohussar.conduittest.Registering.ModMenuTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ConduitMain.MODID)
public class ConduitMain
{
    public static final String MODID = "conduit_test";
    public static final Logger LOGGER = LogUtils.getLogger();


    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    public static CreativeModeTab tab  = new CreativeModeTab("Conduit Test") {
        @Override
        public ItemStack makeIcon() {
            return ItemStack.EMPTY;
        }
    };
    public ConduitMain()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(() -> {
            ModMessages.register();
        });
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }


    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        @SuppressWarnings("deprecated")
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            MenuScreens.register(ModMenuTypes.COMPACTING_MACHINE_MENU.get(), CompactingMachineScreen::new);
        }
    }
}
