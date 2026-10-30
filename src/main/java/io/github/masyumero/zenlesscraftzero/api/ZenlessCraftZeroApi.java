package io.github.masyumero.zenlesscraftzero.api;

import io.github.masyumero.zenlesscraftzero.common.capability.ZCZCapabilities;
import io.github.masyumero.zenlesscraftzero.common.capability.entity.IAttributeAnomalyCapability;
import io.github.masyumero.zenlesscraftzero.common.network.s2c.AttributeAnomalySyncPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import java.util.function.Consumer;

public final class ZenlessCraftZeroApi {

    public ZenlessCraftZeroApi() {}

    public static void getAttributeAnomaly(Entity entity, Consumer<IAttributeAnomalyCapability> consumer, boolean isSync) {
        if (entity instanceof LivingEntity) {
            entity.getCapability(ZCZCapabilities.ATTRIBUTE_ANOMALY).ifPresent(consumer::accept);
            if (isSync) {
                AttributeAnomalySyncPacket.syncAttributeAnomaly(entity);
            }
        }
    }
}
