package io.github.masyumero.zenlesscraftzero.client.render.item;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.IItemDecorator;

public abstract class AbstractItemDecorator implements IItemDecorator {

    private final ResourceLocation texture;

    protected AbstractItemDecorator(ResourceLocation texture) {
        this.texture = texture;
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
