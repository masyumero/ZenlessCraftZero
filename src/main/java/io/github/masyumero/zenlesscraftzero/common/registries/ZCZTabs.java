package io.github.masyumero.zenlesscraftzero.common.registries;

import io.github.masyumero.zenlesscraftzero.ZenlessCraftZero;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ZCZTabs {

    public static final DeferredRegister<CreativeModeTab> TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ZenlessCraftZero.MODID);
    public static final RegistryObject<CreativeModeTab> ZCZ_TAB =TAB.register("zenless_craft_zero", () -> CreativeModeTab.builder()
            .displayItems((itemDisplayParameters, output) ->
                            ZCZItems.ITEMS.getEntries().forEach(itemRO -> output.accept(itemRO.get()))
                    )
            .icon(() -> new ItemStack(ZCZItems.ICON.get()))
            .build());

    public static void register(IEventBus bus) {
        TAB.register(bus);
    }
}
