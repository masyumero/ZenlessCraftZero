package io.github.masyumero.zenlesscraftzero.common.anomaly;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import io.github.masyumero.zenlesscraftzero.ZCZLang;
import io.github.masyumero.zenlesscraftzero.ZenlessCraftZero;
import io.github.masyumero.zenlesscraftzero.api.anomaly.IAttributeEffect;
import io.github.masyumero.zenlesscraftzero.api.lang.ILangEntry;
import io.github.masyumero.zenlesscraftzero.common.capability.ZCZCapabilities;
import io.github.masyumero.zenlesscraftzero.common.registries.ZCZAttributes;
import io.github.masyumero.zenlesscraftzero.common.registries.ZCZDamageTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

import java.util.UUID;

public class PhysicalAttribute implements IAttributeEffect {

    @Override
    public void onAnomalyEffect(LivingEntity attacker, LivingEntity anomalyTarget, float damage) {
        var anomalyProficiency = attacker.getAttribute(ZCZAttributes.ANOMALY_PROFICIENCY.get());
        var dmgBonus = attacker.getAttribute(ZCZAttributes.PHYSICAL_DMG_BONUS.get());
        if (anomalyProficiency != null && dmgBonus != null) {
            float effectDamage = (float) (damage * (anomalyProficiency.getValue() / 100) * 7.13 * (1 + dmgBonus.getValue()));
            var damageSource = new DamageSource(attacker.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ZCZDamageTypes.ATTRIBUTE_EFFECT));
            anomalyTarget.hurt(damageSource, effectDamage);
            anomalyTarget.getCapability(ZCZCapabilities.ATTRIBUTE_ANOMALY).ifPresent(attributeAnomaly -> {
                Table<UUID, Attribute, Integer> buildup = HashBasedTable.create();
                buildup.put(anomalyTarget.getUUID(), Attribute.PHYSICAL, 0);
                attributeAnomaly.setAnomalyBuildup(buildup);
            });
        }
    }

    @Override
    public ILangEntry getEffectLang() {
        return ZCZLang.ANOMALY_EFFECT_PHYSICAL;
    }
}
