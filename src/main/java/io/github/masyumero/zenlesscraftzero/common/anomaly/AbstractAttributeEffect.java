package io.github.masyumero.zenlesscraftzero.common.anomaly;

import io.github.masyumero.zenlesscraftzero.api.anomaly.IAttributeAnomaly;
import io.github.masyumero.zenlesscraftzero.api.anomaly.IAttributeEffect;
import io.github.masyumero.zenlesscraftzero.common.capability.entity.IAttributeAnomalyCapability;
import io.github.masyumero.zenlesscraftzero.common.registries.ZCZAttributes;
import io.github.masyumero.zenlesscraftzero.common.registries.ZCZDamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class AbstractAttributeEffect implements IAttributeEffect {

    @Override
    public void anomalyEffectTick(LivingEntity attacker, LivingEntity anomalyTarget, IAttributeAnomalyCapability targetCap) {
        if (anomalyTarget.tickCount % 20 == 0) {
            for (Map.Entry<UUID, IAttributeAnomaly> entry : targetCap.getAttribute().entrySet()) {
                IAttributeAnomaly anomaly = entry.getValue();
                Map<IAttributeAnomaly, Integer> cooldown = new HashMap<>();
                cooldown.put(anomaly, Math.max(targetCap.getCooldowns().getOrDefault(anomaly, 0) -1, 0));
                targetCap.setCooldowns(cooldown);
            }
        }
    }

    @Override
    public void onAnomalyEffect(LivingEntity attacker, LivingEntity anomalyTarget, IAttributeAnomalyCapability targetCap, DamageSource source) {
        anomalyTarget.hurt(source, computeDamage(attacker));
        if (!source.is(ZCZDamageTypes.ATTRIBUTE_EFFECT_DOT)) {
            resetBuildup(attacker, targetCap);
        }
    }

    public void resetBuildup(LivingEntity attacker, IAttributeAnomalyCapability targetCap) {
        targetCap.setAnomalyBuildup(attacker.getUUID(), getAttributeAnomaly(), 0);
    }

    protected float computeDamage(LivingEntity attacker) {
        var anomalyProficiency = attacker.getAttribute(ZCZAttributes.ANOMALY_PROFICIENCY.get());
        var dmgBonus = attacker.getAttribute(ZCZAttributes.ATTRIBUTE_DMG_BONUS.get(getAttributeAnomaly()).get());
        var damage = attacker.getAttribute(Attributes.ATTACK_DAMAGE);
        if (anomalyProficiency != null && dmgBonus != null && damage != null) {
            return (float) (damage.getValue() * (anomalyProficiency.getValue() / 100) * getEffectMultiplier() * (1 + 1) * dmgBonus.getValue());
        }
        return 0.0f;
    }

    protected float computeDisorderDamage(LivingEntity attacker) {
        var baseDamage = computeDamage(attacker);
        var duration = getDuration();

        return 0.0f;
    }

    protected void reduceDuration() {
        setDuration(Math.max(getDuration() -1, 0));
    }
}
