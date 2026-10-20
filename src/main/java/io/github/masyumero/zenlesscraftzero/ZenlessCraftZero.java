package io.github.masyumero.zenlesscraftzero;

import com.mojang.logging.LogUtils;
import io.github.masyumero.zenlesscraftzero.common.ZCZTags;
import io.github.masyumero.zenlesscraftzero.common.registries.ZCZItems;
import io.github.masyumero.zenlesscraftzero.common.registries.ZCZTabs;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(ZenlessCraftZero.MODID)
public class ZenlessCraftZero {

    public static final String MODID = "zenless_craft_zero";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ZenlessCraftZero(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        modEventBus.addListener(this::commonSetup);
        ZCZItems.register(modEventBus);
        ZCZTabs.register(modEventBus);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(ZCZTags::init);
    }
}
