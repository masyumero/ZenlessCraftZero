package io.github.masyumero.zenlesscraftzero.common.anomaly;

import io.github.masyumero.zenlesscraftzero.ZCZLang;
import io.github.masyumero.zenlesscraftzero.api.anomaly.IAttributeAnomaly;
import io.github.masyumero.zenlesscraftzero.api.anomaly.IAttributeEffect;
import io.github.masyumero.zenlesscraftzero.api.lang.ILangEntry;
import io.github.masyumero.zenlesscraftzero.common.capability.entity.IAttributeAnomalyCapability;
import io.github.masyumero.zenlesscraftzero.common.network.s2c.DamageIndicatorPacket;
import io.github.masyumero.zenlesscraftzero.common.network.s2c.ZCZS2CNetwork;
import io.github.masyumero.zenlesscraftzero.common.registries.ZCZDamageTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

import java.util.HashMap;
import java.util.Map;

public class FireAttribute extends AbstractAttributeEffect {

    public static final FireAttribute INSTANCE = new FireAttribute();
    private static final Map<LivingEntity, IAttributeEffect> ATTRIBUTES = new HashMap<>();
    private int durationTick = 0;

    @Override
    public float getEffectMultiplier() {
        return 0.5f;
    }

    @Override
    public IAttributeAnomaly getAttributeAnomaly() {
        return AttributeAnomaly.FIRE;
    }

    @Override
    public ILangEntry getEffectLang() {
        return ZCZLang.ANOMALY_EFFECT_FIRE;
    }

    @Override
    public void anomalyEffectTick(LivingEntity attacker, LivingEntity anomalyTarget, IAttributeAnomalyCapability targetCap) {
        super.anomalyEffectTick(attacker, anomalyTarget, targetCap);
        if (anomalyTarget.tickCount % 10 == 0) {
            if (durationTick != 0) {
                var damageSource = new DamageSource(attacker.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ZCZDamageTypes.ATTRIBUTE_EFFECT_DOT), attacker);
                super.onAnomalyEffect(attacker, anomalyTarget, targetCap, damageSource);
                if (durationTick == 200) {
                    resetBuildup(attacker, targetCap);
                    if (anomalyTarget.level() instanceof ServerLevel level) {
                        ZCZS2CNetwork.sendIndicator(level, anomalyTarget.getX(), anomalyTarget.getY() + anomalyTarget.getBbHeight(), anomalyTarget.getZ(), 64.0, new DamageIndicatorPacket((AttributeAnomaly) getAttributeAnomaly(), anomalyTarget.getX(), anomalyTarget.getY() + anomalyTarget.getBbHeight(), anomalyTarget.getZ(), 0, true, false, Component.translatable(getEffectLang().getTranslationKey())));
                    }
                }
                durationTick = Math.max(durationTick - 10, 0);
            }
        }
    }

    @Override
    public void onAnomalyEffect(LivingEntity attacker, LivingEntity anomalyTarget, IAttributeAnomalyCapability targetCap, DamageSource source) {
        durationTick = 200;
    }

    @Override
    public void addInstance(LivingEntity entity, IAttributeEffect instance) {
        ATTRIBUTES.put(entity, instance);
    }

    @Override
    public FireAttribute getInstance(LivingEntity entity) {
        return (FireAttribute) ATTRIBUTES.get(entity);
    }

    @Override
    public FireAttribute create() {
        return new FireAttribute();
    }
}
