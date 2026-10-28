package io.github.masyumero.zenlesscraftzero.client.indicator;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import io.github.masyumero.zenlesscraftzero.api.indicator.AbstractIndicator;
import io.github.masyumero.zenlesscraftzero.client.ZCZClientUtil;
import io.github.masyumero.zenlesscraftzero.common.anomaly.Attribute;
import io.github.masyumero.zenlesscraftzero.common.capability.ZCZCapabilities;
import io.github.masyumero.zenlesscraftzero.common.utils.ZCZMathUtil;
import io.github.masyumero.zenlesscraftzero.common.utils.ZCZUtil;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@OnlyIn(Dist.CLIENT)
public class HealthIndicator extends AbstractIndicator<HealthIndicator.Entry> {

    public static final HealthIndicator INSTANCE = new HealthIndicator();
    private static final List<Entry> ENTRIES = new ArrayList<>();
    private static final List<ResourceLocation> HEALTH_INDICATORS = new ArrayList<>();
    private static final List<ResourceLocation> ANOMALY_INDICATORS = new ArrayList<>();

    static {
        HEALTH_INDICATORS.add(ZCZUtil.rl("textures/health_indicator.png"));
        for (int i = 0; i < 19; i++) {
            ANOMALY_INDICATORS.add(ZCZUtil.rl("textures/anomaly_indicator_"+ i +".png"));
        }
    }

    public void add(Attribute attribute, int entityId) {
        this.add(new Entry(attribute, entityId));
    }

    @Override
    public void render(PoseStack poseStack, Camera camera, MultiBufferSource.BufferSource buffer) {
        Minecraft mc = Minecraft.getInstance();
        Vec3 cameraPosition = camera.getPosition();
        RenderSystem.disableDepthTest();
        for (Entry entry : ENTRIES) {
            Entity entity = mc.level.getEntity(entry.entityId);
            if (entity == null) {
                continue;
            }

            double x = entity.getX() - cameraPosition.x;
            double y = entity.getY() + entity.getBbHeight() + 0.1 - cameraPosition.y;
            double z = entity.getZ() - cameraPosition.z;

            int color = (255 << 24) | entry.attribute.getColor();

            ZCZClientUtil.render(poseStack, () -> {
                poseStack.translate(x, y, z);
                poseStack.mulPose(mc.getEntityRenderDispatcher().cameraOrientation());
                for (ResourceLocation indicator : HEALTH_INDICATORS) {
                    ZCZClientUtil.drawTexture(poseStack, buffer, indicator, -1.0f, 1.0f, color);
                }
                entity.getCapability(ZCZCapabilities.ATTRIBUTE_ANOMALY).ifPresent(anomaly -> {
                    AtomicInteger anomalyBuildup = new AtomicInteger(0);
                    anomaly.getAnomalyBuildup().cellSet().forEach(buildup -> {
                        anomalyBuildup.getAndAdd(buildup.getValue());
                    });
                    var anomalyLimit = anomaly.getAnomalyLimit();
                    ZCZClientUtil.drawTexture(poseStack, buffer, ANOMALY_INDICATORS.get(ZCZMathUtil.normalize(anomalyBuildup.get(), anomalyLimit)), -1.0f, 1.0f, color);
                });
            });
        }
        buffer.endBatch();
        RenderSystem.enableDepthTest();
    }

    @Override
    protected void add(Entry indicator) {
        for (int i = 0; i < ENTRIES.size(); i++) {
            if (ENTRIES.get(i).equals(indicator)) {
                ENTRIES.add(i, indicator);
                return;
            }
        }
        ENTRIES.add(indicator);
    }

    @Override
    public void tick() {
        ENTRIES.removeIf(entry -> ++entry.age >= 100);
    }

    protected static class Entry implements IEntry {
        public final Attribute attribute;
        public final int entityId;
        public int age;

        public Entry(Attribute attribute, int entityId) {
            this.attribute = attribute;
            this.entityId = entityId;
            this.age = 0;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Entry entry) {
                return entry.entityId == this.entityId;
            }
            return false;
        }
    }
}
