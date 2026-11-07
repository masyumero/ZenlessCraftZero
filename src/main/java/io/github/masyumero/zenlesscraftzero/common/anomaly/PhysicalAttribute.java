package io.github.masyumero.zenlesscraftzero.common.anomaly;

import io.github.masyumero.zenlesscraftzero.ZCZLang;
import io.github.masyumero.zenlesscraftzero.api.anomaly.IAttributeAnomaly;
import io.github.masyumero.zenlesscraftzero.api.anomaly.IAttributeEffect;
import io.github.masyumero.zenlesscraftzero.api.lang.ILangEntry;
import io.github.masyumero.zenlesscraftzero.common.capability.entity.IAttributeAnomalyCapability;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

import java.util.HashMap;
import java.util.Map;

public class PhysicalAttribute extends AbstractAttributeEffect {

    public static final PhysicalAttribute INSTANCE = new PhysicalAttribute();
    private static final Map<LivingEntity, IAttributeEffect> ATTRIBUTES = new HashMap<>();
    @Getter
    @Setter
    private int duration = 0;

    @Override
    public float getEffectMultiplier() {
        return 7.13f;
    }

    @Override
    public IAttributeAnomaly getAttributeAnomaly() {
        return AttributeAnomaly.PHYSICAL;
    }

    @Override
    public ILangEntry getEffectLang() {
        return ZCZLang.ANOMALY_EFFECT_PHYSICAL;
    }

    @Override
    public void addInstance(LivingEntity entity, IAttributeEffect instance) {
        ATTRIBUTES.put(entity, instance);
    }

    @Override
    public PhysicalAttribute getInstance(LivingEntity entity) {
        return (PhysicalAttribute) ATTRIBUTES.get(entity);
    }

    @Override
    public PhysicalAttribute create() {
        return new PhysicalAttribute();
    }

    @Override
    public void anomalyEffectTick(LivingEntity attacker, LivingEntity anomalyTarget, IAttributeAnomalyCapability targetCap) {
        super.anomalyEffectTick(attacker, anomalyTarget, targetCap);
        reduceDuration();
    }

    @Override
    public void onAnomalyEffect(LivingEntity attacker, LivingEntity anomalyTarget, IAttributeAnomalyCapability targetCap, DamageSource source) {
        super.onAnomalyEffect(attacker, anomalyTarget, targetCap, source);
        duration = 10;
    }
}
