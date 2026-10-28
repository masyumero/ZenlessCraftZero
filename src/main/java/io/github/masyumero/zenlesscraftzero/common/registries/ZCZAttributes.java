package io.github.masyumero.zenlesscraftzero.common.registries;

import dev.shadowsoffire.attributeslib.impl.PercentBasedAttribute;
import io.github.masyumero.zenlesscraftzero.ZCZLang;
import io.github.masyumero.zenlesscraftzero.ZenlessCraftZero;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashSet;
import java.util.Set;

public class ZCZAttributes {

    private static final DeferredRegister<Attribute> ATTRIBUTE = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, ZenlessCraftZero.MODID);
    public static final Set<RegistryObject<Attribute>> NORMAL_ATTRIBUTES = new HashSet<>();

    public static final RegistryObject<Attribute> ANOMALY_PROFICIENCY = ATTRIBUTE.register("anomaly_proficiency",
            () -> new RangedAttribute(ZCZLang.ATTRIBUTE_ANOMALY_PROFICIENCY.getTranslationKey(), 70, 0, Double.MAX_VALUE));
    public static final RegistryObject<Attribute> PHYSICAL_DMG_BONUS = ATTRIBUTE.register("physical_dmg_bonus",
            () -> new PercentBasedAttribute(ZCZLang.ATTRIBUTE_PHYSICAL_DMG_BONUS.getTranslationKey(), 0, 0, Double.MAX_VALUE));
    public static final RegistryObject<Attribute> CRIT_RATE = ATTRIBUTE.register("crit_rate",
            () -> new PercentBasedAttribute(ZCZLang.ATTRIBUTE_CRIT_RATE.getTranslationKey(), 0.2, 0, Double.MAX_VALUE));
    public static final RegistryObject<Attribute> CRIT_DAMAGE = ATTRIBUTE.register("crit_damage",
            () -> new PercentBasedAttribute(ZCZLang.ATTRIBUTE_CRIT_DAMAGE.getTranslationKey(), 0.5, 0, Double.MAX_VALUE));

    static {
        NORMAL_ATTRIBUTES.addAll(Set.of(ANOMALY_PROFICIENCY, PHYSICAL_DMG_BONUS, CRIT_RATE, CRIT_DAMAGE));
    }

    public static void register(IEventBus bus) {
        ATTRIBUTE.register(bus);
    }
}
