package io.github.masyumero.zenlesscraftzero.client.indicator;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import io.github.masyumero.zenlesscraftzero.api.indicator.AbstractIndicator;
import io.github.masyumero.zenlesscraftzero.client.ZCZClientUtil;
import io.github.masyumero.zenlesscraftzero.common.anomaly.Attribute;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class DamageIndicator extends AbstractIndicator<DamageIndicator.Entry> {

    public static final DamageIndicator INSTANCE = new DamageIndicator();
    private static final List<Entry> ENTRIES = new ArrayList<>();

    public void add(Attribute attribute, double x, double y, double z, float damage, boolean isAnomalyEffect, boolean isCrit) {
        this.add(new Entry(attribute, x, y, z, damage, isAnomalyEffect, isCrit));
    }

    @Override
    public void render(PoseStack poseStack, Camera camera, MultiBufferSource.BufferSource buffer) {
        Minecraft mc = Minecraft.getInstance();
        Vec3 cameraPos = camera.getPosition();
        Font font = mc.font;
        RenderSystem.disableDepthTest();
        for (Entry entry : DamageIndicator.ENTRIES) {
            Vector3f right = new Vector3f(camera.getLeftVector()).mul(-1.0F);
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

            ZCZClientUtil.render(poseStack, () -> {
                poseStack.translate(x, y, z);
                poseStack.mulPose(mc.getEntityRenderDispatcher().cameraOrientation());
                poseStack.scale(-0.03f, -0.03F, 0.03F);
                float lineHeight = 10.0F;
                for (int i = 0; i < components.size(); i++) {
                    FormattedCharSequence text = components.get(i).getVisualOrderText();
                    float textX = -font.width(text) / 2.0F;
                    float textY = i * lineHeight;
                    font.drawInBatch8xOutline(
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
            });
        }
        buffer.endBatch();
        RenderSystem.enableDepthTest();
    }

    protected void add(Entry entry) {
        ENTRIES.add(entry);
    }

    public void tick() {
        ENTRIES.removeIf(entry -> ++entry.age >= 40);
    }

    protected static class Entry implements IEntry {
        public final Attribute attribute;
        public final double x;
        public final double y;
        public final double z;
        public final float damage;
        public final double offsetX;
        public final double offsetY;
        public final boolean isAnomalyEffect;
        public final boolean isCrit;
        public int age;

        public Entry(Attribute attribute, double x, double y, double z, float damage, boolean isAnomalyEffect, boolean isCrit) {
            this.attribute = attribute;
            this.x = x;
            this.y = y;
            this.z = z;
            this.damage = damage;
            this.isAnomalyEffect = isAnomalyEffect;
            this.isCrit = isCrit;
            this.offsetX = (Math.random() * 2.0 - 1.0) * 0.5;
            this.offsetY = Math.random() * 1.1;
            this.age = 0;
        }
    }
}