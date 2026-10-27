package io.github.masyumero.zenlesscraftzero.common.registries;

import io.github.masyumero.zenlesscraftzero.ZCZLang;
import io.github.masyumero.zenlesscraftzero.ZenlessCraftZero;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ZCZAttributes {

    private static final DeferredRegister<Attribute> ATTRIBUTE = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, ZenlessCraftZero.MODID);

    public static final RegistryObject<Attribute> ANOMALY_PROFICIENCY = ATTRIBUTE.register("anomaly_proficiency",
            () -> new RangedAttribute(ZCZLang.ATTRIBUTE_ANOMALY_PROFICIENCY.getTranslationKey(), 70, 0, Double.MAX_VALUE));

    public static void register(IEventBus bus) {
        ATTRIBUTE.register(bus);
    }
}
