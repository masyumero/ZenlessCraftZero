package io.github.masyumero.zenlesscraftzero.common.registries;

import dev.shadowsoffire.attributeslib.impl.PercentBasedAttribute;
import io.github.masyumero.zenlesscraftzero.ZCZLang;
import io.github.masyumero.zenlesscraftzero.ZenlessCraftZero;
import io.github.masyumero.zenlesscraftzero.api.anomaly.IAttributeAnomaly;
import io.github.masyumero.zenlesscraftzero.api.lang.ILangEntry;
import io.github.masyumero.zenlesscraftzero.common.anomaly.AttributeAnomaly;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ZCZAttributes {

    private static final DeferredRegister<Attribute> ATTRIBUTE = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, ZenlessCraftZero.MODID);
    public static final Set<RegistryObject<Attribute>> NORMAL_ATTRIBUTES = new HashSet<>();
    public static final Map<IAttributeAnomaly, RegistryObject<Attribute>> ATTRIBUTE_DMG_BONUS = new HashMap<>();

    public static final RegistryObject<Attribute> ANOMALY_PROFICIENCY = ATTRIBUTE.register("anomaly_proficiency",
            () -> new RangedAttribute(ZCZLang.ATTRIBUTE_ANOMALY_PROFICIENCY.getTranslationKey(), 70, 0, 100000));
    public static final RegistryObject<Attribute> ANOMALY_MASTERY = ATTRIBUTE.register("anomaly_mastery",
            () -> new RangedAttribute(ZCZLang.ATTRIBUTE_ANOMALY_MASTERY.getTranslationKey(), 100, 0, 100000));
    public static final RegistryObject<Attribute> ANOMALY_RESISTANCE = ATTRIBUTE.register("anomaly_resistance",
            () -> new PercentBasedAttribute(ZCZLang.ATTRIBUTE_ANOMALY_RESISTANCE.getTranslationKey(), 0, 0, 100));
    public static final RegistryObject<Attribute> CRIT_RATE = ATTRIBUTE.register("crit_rate",
            () -> new PercentBasedAttribute(ZCZLang.ATTRIBUTE_CRIT_RATE.getTranslationKey(), 0.2, 0, Double.MAX_VALUE));
    public static final RegistryObject<Attribute> CRIT_DAMAGE = ATTRIBUTE.register("crit_damage",
            () -> new PercentBasedAttribute(ZCZLang.ATTRIBUTE_CRIT_DAMAGE.getTranslationKey(), 0.5, 0, Double.MAX_VALUE));

    static {
        registerAttributeDMGBonus(AttributeAnomaly.PHYSICAL, ZCZLang.ATTRIBUTE_PHYSICAL_DMG_BONUS);
        registerAttributeDMGBonus(AttributeAnomaly.FIRE, ZCZLang.ATTRIBUTE_FIRE_DMG_BONUS);
        registerAttributeDMGBonus(AttributeAnomaly.ICE, ZCZLang.ATTRIBUTE_ICE_DMG_BONUS);

        NORMAL_ATTRIBUTES.addAll(Set.of(ANOMALY_PROFICIENCY, ANOMALY_MASTERY, ANOMALY_RESISTANCE, CRIT_RATE, CRIT_DAMAGE));
        NORMAL_ATTRIBUTES.addAll(ATTRIBUTE_DMG_BONUS.values());
    }

    private static void registerAttributeDMGBonus(IAttributeAnomaly anomaly, ILangEntry lang) {
        ATTRIBUTE_DMG_BONUS.put(anomaly, ATTRIBUTE.register(anomaly.getId() + "_dmg_bonus", () -> new PercentBasedAttribute(lang.getTranslationKey(), 0, 0, Double.MAX_VALUE)));
    }

    public static void register(IEventBus bus) {
        ATTRIBUTE.register(bus);
    }
}
