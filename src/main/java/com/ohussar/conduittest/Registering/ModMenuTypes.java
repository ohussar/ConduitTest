package com.ohussar.conduittest.Registering;

import com.ohussar.conduittest.ConduitMain;
import com.ohussar.conduittest.Menus.CompactingMachineMenu;
import com.ohussar.conduittest.Menus.PressureGeneratorMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, ConduitMain.MODID);

    public static RegistryObject<MenuType<CompactingMachineMenu>> COMPACTING_MACHINE_MENU = register(CompactingMachineMenu::new, "compacting_machine_menu");
    public static RegistryObject<MenuType<PressureGeneratorMenu>> PRESSURE_GENERATOR_MENU = register(PressureGeneratorMenu::new, "pressure_generator_menu");
    public static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> register(IContainerFactory<T> factory, String name){
        return MENU_TYPES.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus bus){
        MENU_TYPES.register(bus);
    }
}
