package io.github.masyumero.zenlesscraftzero.client.event;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import io.github.masyumero.zenlesscraftzero.ZenlessCraftZero;
import io.github.masyumero.zenlesscraftzero.common.anomaly.Attribute;
import io.github.masyumero.zenlesscraftzero.api.mixin.MixinFontImpl;
import io.github.masyumero.zenlesscraftzero.client.indicator.DamageIndicators;
import io.github.masyumero.zenlesscraftzero.client.indicator.HealthIndicators;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = ZenlessCraftZero.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ZCZClientEvents {

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        DamageIndicators.tick();
        HealthIndicators.tick();
    }

    @SubscribeEvent
    public static void onRenderLevelStage(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_ENTITIES) {
            return;
        }

        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || mc.player == null) {
            return;
        }

        PoseStack poseStack = event.getPoseStack();
        Vec3 cameraPos = event.getCamera().getPosition();
        MultiBufferSource.BufferSource buffer = mc.renderBuffers().bufferSource();
        Font font = mc.font;
        RenderSystem.disableDepthTest();
        for (DamageIndicators.Entry entry : DamageIndicators.entries()) {
            Vector3f right = new Vector3f(event.getCamera().getLeftVector()).mul(-1.0F);
            double horizontalOffset = entry.offsetX;
            double verticalOffset = entry.offsetY;
            double x = entry.x + right.x * horizontalOffset - cameraPos.x;
            double y = entry.y + 0.4 + verticalOffset - cameraPos.y;
            double z = entry.z + right.z * horizontalOffset - cameraPos.z;
            Attribute attribute = entry.attribute;
            List<Component> components = new ArrayList<>();
            if (entry.isAnomalyEffect) {
                components.add(Component.translatable(attribute.getAttributeEffect().getEffectLang().getTranslationKey()));
            }
            components.add(Component.literal(String.format("%.0f" + (entry.isCrit ? "!!" : ""), entry.damage)));

            int color = (255 << 24) | attribute.getColor();

            poseStack.pushPose();
            poseStack.translate(x, y, z);
            poseStack.mulPose(mc.getEntityRenderDispatcher().cameraOrientation());
            poseStack.scale(-0.03f, -0.03F, 0.03F);
            float lineHeight = 10.0F;
            for (int i = 0; i < components.size(); i++) {
                FormattedCharSequence text = components.get(i).getVisualOrderText();
                float textX = -font.width(text) / 2.0F;
                float textY = i * lineHeight;
                ((MixinFontImpl)font).zenless_craft_zero$drawInBatch8xOutline(
                        text,
                        textX,
                        textY,
                        color,
                        0xFF000000,
                        poseStack.last().pose(),
                        buffer,
                        LightTexture.FULL_BRIGHT
                );
            }
            poseStack.popPose();
        }
        buffer.endBatch();
        RenderSystem.enableDepthTest();
    }
}
