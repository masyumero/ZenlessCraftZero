package io.github.masyumero.zenlesscraftzero.api.anomaly;

import io.github.masyumero.zenlesscraftzero.api.lang.ILangEntry;
import io.github.masyumero.zenlesscraftzero.common.capability.entity.IAttributeAnomalyCapability;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public interface IAttributeEffect {

    void anomalyEffectTick(LivingEntity attacker, LivingEntity anomalyTarget, IAttributeAnomalyCapability targetCap);

    float getEffectMultiplier();

    IAttributeAnomaly getAttributeAnomaly();

    ILangEntry getEffectLang();

    void onAnomalyEffect(LivingEntity attacker, LivingEntity anomalyTarget, IAttributeAnomalyCapability targetCap, DamageSource source);

    void addInstance(LivingEntity entity, IAttributeEffect instance);

    IAttributeEffect getInstance(LivingEntity entity);

    IAttributeEffect create();
}
