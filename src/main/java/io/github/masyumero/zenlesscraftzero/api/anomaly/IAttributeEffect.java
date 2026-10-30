package io.github.masyumero.zenlesscraftzero.api.anomaly;

import io.github.masyumero.zenlesscraftzero.api.lang.ILangEntry;
import io.github.masyumero.zenlesscraftzero.common.capability.entity.IAttributeAnomalyCapability;
import net.minecraft.world.entity.LivingEntity;

public interface IAttributeEffect {

    void onAnomalyEffect(LivingEntity attacker, LivingEntity anomalyTarget, IAttributeAnomalyCapability targetCap, float damage);

    void anomalyEffectTick(LivingEntity attacker, LivingEntity anomalyTarget, IAttributeAnomalyCapability targetCap);

    ILangEntry getEffectLang();
}
