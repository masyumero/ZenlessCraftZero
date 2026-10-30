package io.github.masyumero.zenlesscraftzero;

import com.mojang.logging.LogUtils;
import io.github.masyumero.zenlesscraftzero.common.ZCZTags;
import io.github.masyumero.zenlesscraftzero.common.equip.disc.DiscLevelStat;
import io.github.masyumero.zenlesscraftzero.common.network.s2c.ZCZS2CNetwork;
import io.github.masyumero.zenlesscraftzero.common.registries.ZCZAttributes;
import io.github.masyumero.zenlesscraftzero.common.registries.ZCZItems;
import io.github.masyumero.zenlesscraftzero.common.registries.ZCZTabs;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
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
        modEventBus.addListener(this::onEntityAttributeModification);
        ZCZItems.register(modEventBus);
        ZCZAttributes.register(modEventBus);
        ZCZTabs.register(modEventBus);
        ZCZS2CNetwork.register();
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(ZCZTags::init);
        event.enqueueWork(DiscLevelStat::init);
    }

    public void onEntityAttributeModification(EntityAttributeModificationEvent event) {
        for (EntityType<? extends LivingEntity> type : event.getTypes()) {
            ZCZAttributes.NORMAL_ATTRIBUTES.forEach(attribute -> {
                if (!event.has(type, attribute.get())) {
                    event.add(type, attribute.get());
                }
            });
        }
    }
}
