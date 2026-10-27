package io.github.masyumero.zenlesscraftzero.common.anomaly;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import io.github.masyumero.zenlesscraftzero.ZCZLang;
import io.github.masyumero.zenlesscraftzero.ZenlessCraftZero;
import io.github.masyumero.zenlesscraftzero.api.anomaly.Attribute;
import io.github.masyumero.zenlesscraftzero.api.anomaly.AttributeEffect;
import io.github.masyumero.zenlesscraftzero.api.lang.ILangEntry;
import io.github.masyumero.zenlesscraftzero.common.capability.ZCZCapabilities;
import io.github.masyumero.zenlesscraftzero.common.registries.ZCZAttributes;
import io.github.masyumero.zenlesscraftzero.common.registries.ZCZDamageTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.UUID;

public class PhysicalAttribute extends AttributeEffect {

    @Override
    public void onAnomalyEffect(LivingEntity attacker, LivingEntity anomalyTarget) {
        var anomalyProficiency = attacker.getAttribute(ZCZAttributes.ANOMALY_PROFICIENCY.get());
        if (anomalyProficiency != null) {
            var proficiencyValue = anomalyProficiency.getValue();
            var attackDamage = attacker.getAttribute(Attributes.ATTACK_DAMAGE);
            if (attackDamage != null) {
                var attackerATK = attackDamage.getValue();
                float effectDamage = (float) (attackerATK * (proficiencyValue / 100) * 7.13);
                var damageSource = new DamageSource(attacker.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ZCZDamageTypes.ATTRIBUTE_EFFECT));
                anomalyTarget.hurt(damageSource, effectDamage);
                anomalyTarget.getCapability(ZCZCapabilities.ATTRIBUTE_ANOMALY).ifPresent(attributeAnomaly -> {
                    Table<UUID, Attribute, Integer> buildup = HashBasedTable.create();
                    buildup.put(anomalyTarget.getUUID(), Attribute.PHYSICAL, 0);
                    attributeAnomaly.setAnomalyBuildup(buildup);
                });
                ZenlessCraftZero.LOGGER.info("AnomalyEffected!! Damage:{} BaseDamage:{} AnomalyProficiency:{}", effectDamage, attackerATK, proficiencyValue);
            }
        }
    }

    @Override
    public ILangEntry getEffectLang() {
        return ZCZLang.ANOMALY_EFFECT_PHYSICAL;
    }
}
