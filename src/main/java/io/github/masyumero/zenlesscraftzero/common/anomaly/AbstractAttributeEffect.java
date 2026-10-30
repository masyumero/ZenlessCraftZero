package io.github.masyumero.zenlesscraftzero.common.anomaly;

import io.github.masyumero.zenlesscraftzero.api.anomaly.IAttributeAnomaly;
import io.github.masyumero.zenlesscraftzero.api.anomaly.IAttributeEffect;
import io.github.masyumero.zenlesscraftzero.common.capability.entity.IAttributeAnomalyCapability;
import net.minecraft.world.entity.LivingEntity;

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
}
