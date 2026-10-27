package io.github.masyumero.zenlesscraftzero.api.anomaly;

import io.github.masyumero.zenlesscraftzero.api.lang.ILangEntry;
import net.minecraft.world.entity.LivingEntity;

public interface IAttributeEffect {

    void onAnomalyEffect(LivingEntity attacker, LivingEntity anomalyTarget);

    default void anomalyEffectTick(LivingEntity attacker, LivingEntity anomalyTarget) {};

    ILangEntry getEffectLang();
}
