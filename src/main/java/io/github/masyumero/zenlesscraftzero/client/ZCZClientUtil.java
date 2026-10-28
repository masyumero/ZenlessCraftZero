package io.github.masyumero.zenlesscraftzero.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

public class ZCZClientUtil {

    public static MultiBufferSource.BufferSource bufferSource() {
        return Minecraft.getInstance().renderBuffers().bufferSource();
    }

    public static VertexConsumer getBuffer(RenderType type) {
        return bufferSource().getBuffer(type);
    }

    public static void render(PoseStack poseStack, Runnable... runnables) {
        poseStack.pushPose();
        for (Runnable runnable : runnables) {
            runnable.run();
        }
        poseStack.popPose();
    }

    public static void drawTexture(PoseStack poseStack, MultiBufferSource.BufferSource buffer, ResourceLocation texture, float width, float height, int color) {
        Matrix4f matrix = poseStack.last().pose();

        VertexConsumer consumer = getBuffer(RenderType.entityTranslucent(texture));

        float r = ((color >> 16) & 0xFF) / 255.0F;
        float g = ((color >> 8) & 0xFF) / 255.0F;
        float b = (color & 0xFF) / 255.0F;
        float a = ((color >>> 24) & 0xFF) / 255.0F;

        consumer.vertex(matrix, -width / 2, -height / 2, 0)
                .color(r, g, b, a).uv(0, 1)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(LightTexture.FULL_BRIGHT)
                .normal(0, 0, 1).endVertex();

        consumer.vertex(matrix, width / 2, -height / 2, 0)
                .color(r, g, b, a).uv(1, 1)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(LightTexture.FULL_BRIGHT)
                .normal(0, 0, 1).endVertex();

        consumer.vertex(matrix, width / 2, height / 2, 0)
                .color(r, g, b, a).uv(1, 0)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(LightTexture.FULL_BRIGHT)
                .normal(0, 0, 1).endVertex();

        consumer.vertex(matrix, -width / 2, height / 2, 0)
                .color(r, g, b, a).uv(0, 0)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(LightTexture.FULL_BRIGHT)
                .normal(0, 0, 1).endVertex();
    }
}
