package io.github.masyumero.zenlesscraftzero.api.indicator;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashSet;
import java.util.Set;

public interface IIndicator<I extends IIndicator.IEntry> {

    Set<IIndicator<?>> INDICATORS = new HashSet<>();

    @OnlyIn(Dist.CLIENT)
    default void registerIndicator() {
        synchronized (INDICATORS) {
            INDICATORS.add(this);
        }
    }

    void render(PoseStack poseStack, Camera camera, MultiBufferSource.BufferSource buffer);

    void tick();

    interface IEntry {}
}
