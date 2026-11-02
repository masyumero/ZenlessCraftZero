package io.github.masyumero.zenlesscraftzero.mixin;

import io.github.masyumero.zenlesscraftzero.api.mixin.IMixinDamageSource;
import net.minecraft.world.damagesource.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(DamageSource.class)
public class MixinDamageSource implements IMixinDamageSource {

    @Unique
    boolean zenless_craft_zero$isCrit = false;

    @Unique
    public void zenless_craft_zero$setCrit(boolean crit) {
        zenless_craft_zero$isCrit = crit;
    }

    @Unique
    public boolean zenless_craft_zero$getCrit() {
        return zenless_craft_zero$isCrit;
    }
}
