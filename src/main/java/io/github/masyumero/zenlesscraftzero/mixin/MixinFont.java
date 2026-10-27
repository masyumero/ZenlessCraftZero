package io.github.masyumero.zenlesscraftzero.mixin;

import io.github.masyumero.zenlesscraftzero.api.mixin.MixinFontImpl;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.FormattedCharSequence;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Font.class)
public abstract class MixinFont implements MixinFontImpl {

    @Shadow
    public abstract void drawInBatch8xOutline(FormattedCharSequence text, float x, float y, int color, int backgroundColor, Matrix4f matrix, MultiBufferSource bufferSource, int packedLightCoords);

    @Unique
    private boolean zenless_craft_zero$isDamageIndicator = false;

    @Override
    public void zenless_craft_zero$drawInBatch8xOutline(FormattedCharSequence text, float x, float y, int color, int backgroundColor, Matrix4f matrix, MultiBufferSource bufferSource, int packedLightCoords) {
        zenless_craft_zero$isDamageIndicator = true;
        drawInBatch8xOutline(text, x, y, color, backgroundColor, matrix, bufferSource, packedLightCoords);
        zenless_craft_zero$isDamageIndicator = false;
    }

    @ModifyArg(method = "drawInBatch8xOutline", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Font$StringRenderOutput;<init>(Lnet/minecraft/client/gui/Font;Lnet/minecraft/client/renderer/MultiBufferSource;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/gui/Font$DisplayMode;I)V", ordinal = 1), index = 7)
    private Font.DisplayMode drawInBatch8xOutlineDisplayMode(Font.DisplayMode mode) {
        if (zenless_craft_zero$isDamageIndicator) {
            return Font.DisplayMode.SEE_THROUGH;
        }
        return mode;
    }
}
