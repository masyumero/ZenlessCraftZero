package io.github.masyumero.zenlesscraftzero.client.event;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.masyumero.zenlesscraftzero.ZenlessCraftZero;
import io.github.masyumero.zenlesscraftzero.api.indicator.IIndicator;
import io.github.masyumero.zenlesscraftzero.client.ZCZClientUtil;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ZenlessCraftZero.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ZCZClientEvents {

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        IIndicator.INDICATORS.forEach(IIndicator::tick);
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
        Camera camera = event.getCamera();
        IIndicator.INDICATORS.forEach(indicator -> indicator.render(poseStack, camera, ZCZClientUtil.bufferSource()));
    }
}
