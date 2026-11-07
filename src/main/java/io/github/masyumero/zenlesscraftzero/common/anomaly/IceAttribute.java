package io.github.masyumero.zenlesscraftzero.common.anomaly;

import io.github.masyumero.zenlesscraftzero.ZCZLang;
import io.github.masyumero.zenlesscraftzero.api.anomaly.IAttributeAnomaly;
import io.github.masyumero.zenlesscraftzero.api.anomaly.IAttributeEffect;
import io.github.masyumero.zenlesscraftzero.api.lang.ILangEntry;
import io.github.masyumero.zenlesscraftzero.common.capability.entity.IAttributeAnomalyCapability;
import io.github.masyumero.zenlesscraftzero.common.registries.ZCZAttributes;
import io.github.masyumero.zenlesscraftzero.common.registries.ZCZDamageTypes;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class IceAttribute extends AbstractAttributeEffect {

    public static final IceAttribute INSTANCE = new IceAttribute();
    private static final Map<LivingEntity, IAttributeEffect> ATTRIBUTES = new HashMap<>();
    private static final UUID frostbiteUUID = UUID.randomUUID();
    @Getter
    @Setter
    private int duration = 0;
    private int freezeCount = 0;

    @Override
    public float getEffectMultiplier() {
        return 5;
    }

    @Override
    public IAttributeAnomaly getAttributeAnomaly() {
        return AttributeAnomaly.ICE;
    }

    @Override
    public ILangEntry getEffectLang() {
        return ZCZLang.ANOMALY_EFFECT_ICE;
    }

    @Override
    public void addInstance(LivingEntity entity, IAttributeEffect instance) {
        ATTRIBUTES.put(entity, instance);
    }

    @Override
    public IceAttribute getInstance(LivingEntity entity) {
        return (IceAttribute) ATTRIBUTES.get(entity);
    }

    @Override
    public IceAttribute create() {
        return new IceAttribute();
    }

    @Override
    public void anomalyEffectTick(LivingEntity attacker, LivingEntity anomalyTarget, IAttributeAnomalyCapability targetCap) {
        super.anomalyEffectTick(attacker, anomalyTarget, targetCap);
        if (freezeCount != 0) {
            if (anomalyTarget instanceof Mob targetMob) {
                targetMob.setNoAi(true);
            }
            if (anomalyTarget.tickCount % 20 == 0) {
                if (freezeCount == 3) {
                    resetBuildup(attacker, targetCap);
                }
                freezeCount = Math.max(freezeCount - 1, 0);
                if (freezeCount == 0) {
                    doShatter(attacker, anomalyTarget, targetCap);
                }
            }
        }
        if (anomalyTarget.tickCount % 20 == 0) {
            if (duration != 0) {
                var critDamage = attacker.getAttribute(ZCZAttributes.CRIT_DAMAGE.get());
                reduceDuration();
                if (critDamage != null) {
                    if (critDamage.getModifier(frostbiteUUID) == null) {
                        critDamage.addTransientModifier(new AttributeModifier(
                                frostbiteUUID,
                                "frostbite",
                                0.1,
                                AttributeModifier.Operation.ADDITION
                        ));
                    }
                    if (duration == 0) {
                        critDamage.removeModifier(frostbiteUUID);
                    }
                }
            }
        }
    }

    @Override
    public void onCriticalHit(LivingEntity attacker, LivingEntity anomalyTarget, IAttributeAnomalyCapability targetCap) {
        if (freezeCount != 0) {
            doShatter(attacker, anomalyTarget, targetCap);
        }
    }

    @Override
    public void onLivingDeath(LivingEntity attacker, LivingEntity anomalyTarget, IAttributeAnomalyCapability targetCap) {
        var attribute = attacker.getAttribute(ZCZAttributes.CRIT_DAMAGE.get());
        if (attribute != null) {{
            attribute.removeModifier(frostbiteUUID);
        }}
    }

    private void doShatter(LivingEntity attacker, LivingEntity anomalyTarget, IAttributeAnomalyCapability targetCap) {
        if (anomalyTarget instanceof Mob targetMob) {
            targetMob.setNoAi(false);
        }
        var damageSource = new DamageSource(attacker.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ZCZDamageTypes.SHATTER), attacker);
        super.onAnomalyEffect(attacker, anomalyTarget, targetCap, damageSource);
        freezeCount = 0;
    }

    @Override
    public void onAnomalyEffect(LivingEntity attacker, LivingEntity anomalyTarget, IAttributeAnomalyCapability targetCap, DamageSource source) {
        duration = 10;
        freezeCount = 3;
    }
}
