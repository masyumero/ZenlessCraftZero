package io.github.masyumero.zenlesscraftzero.common.utils;

import io.github.masyumero.zenlesscraftzero.ZenlessCraftZero;
import io.github.masyumero.zenlesscraftzero.common.capability.ZCZCapabilities;
import io.github.masyumero.zenlesscraftzero.common.capability.entity.IAttributeAnomalyCapability;
import io.github.masyumero.zenlesscraftzero.common.network.s2c.AttributeAnomalySyncPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.function.Consumer;

public class ZCZUtil {

    public static ResourceLocation rl(String id) {
        return ResourceLocation.fromNamespaceAndPath(ZenlessCraftZero.MODID, id);
    }

    public static ResourceLocation curios(String id) {
        return ResourceLocation.fromNamespaceAndPath(CuriosApi.MODID, id);
    }

    public static void getAttributeAnomaly(Entity entity, Consumer<IAttributeAnomalyCapability> consumer, boolean isSync) {
        if (entity instanceof LivingEntity) {
            entity.getCapability(ZCZCapabilities.ATTRIBUTE_ANOMALY).ifPresent(consumer::accept);
            if (isSync) {
                AttributeAnomalySyncPacket.syncAttributeAnomaly(entity);
            }
        }
    }
}
