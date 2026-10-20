package io.github.masyumero.zenlesscraftzero.client.render.item;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.masyumero.zenlesscraftzero.api.equip.disc.DiscRank;
import io.github.masyumero.zenlesscraftzero.api.equip.disc.DiscSetting;
import io.github.masyumero.zenlesscraftzero.common.utils.ZCZUtil;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.client.IItemDecorator;
import net.minecraftforge.client.event.RegisterItemDecorationsEvent;

public class DiscRankDecorator implements IItemDecorator {
    private final ResourceLocation texture;

    public static void registerDecorators(RegisterItemDecorationsEvent event, DiscRank rank, ItemLike... items) {
        for (ItemLike item : items) {
            event.register(item, new DiscRankDecorator(rank));
        }
    }

    private DiscRankDecorator(DiscRank rank) {
        this.texture = ZCZUtil.rl("gui/icons/" + rank.getId() + ".png");
    }

    @Override
    public boolean render(GuiGraphics guiGraphics, Font font, ItemStack itemStack, int xOffset, int yOffset) {
        if (itemStack.isEmpty()) {
            return false;
        }

        PoseStack pose = guiGraphics.pose();
        pose.pushPose();
        pose.translate(0.0F, 0.0F, 200.0F);
        guiGraphics.blit(this.texture, xOffset, yOffset, 0.0F, 0.0F, 16, 16, 16, 16);
        pose.popPose();
        return true;
    }
}
