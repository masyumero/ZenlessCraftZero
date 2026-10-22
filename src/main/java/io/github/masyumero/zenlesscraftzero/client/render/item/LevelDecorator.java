package io.github.masyumero.zenlesscraftzero.client.render.item;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.masyumero.zenlesscraftzero.common.capability.ZCZCapabilities;
import io.github.masyumero.zenlesscraftzero.common.utils.ZCZUtil;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.client.event.RegisterItemDecorationsEvent;

import java.util.concurrent.atomic.AtomicInteger;

public class LevelDecorator extends AbstractItemDecorator {

    public static void registerDecorators(RegisterItemDecorationsEvent event, ItemLike... items) {
        for (ItemLike item : items) {
            event.register(item, new LevelDecorator());
        }
    }

    protected LevelDecorator() {
        super(null);
    }

    private ResourceLocation getTexture(ItemStack itemStack) {
        AtomicInteger level = new AtomicInteger();
        itemStack.getCapability(ZCZCapabilities.EQUIPMENT_LEVEL).ifPresent(equipment -> {
            level.set(equipment.getLevel());
        });
        return ZCZUtil.rl("gui/icons/level_" + level + ".png");
    }

    @Override
    public boolean render(GuiGraphics guiGraphics, Font font, ItemStack itemStack, int xOffset, int yOffset) {
        if (itemStack.isEmpty()) {
            return false;
        }

        PoseStack pose = guiGraphics.pose();
        pose.pushPose();
        pose.translate(0.0F, 0.0F, 200.0F);
        guiGraphics.blit(getTexture(itemStack), xOffset, yOffset, 0.0F, 0.0F, 16, 16, 16, 16);
        pose.popPose();
        return true;
    }
}
