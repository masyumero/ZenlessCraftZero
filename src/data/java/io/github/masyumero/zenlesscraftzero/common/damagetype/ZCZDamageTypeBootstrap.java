package io.github.masyumero.zenlesscraftzero.common.damagetype;

import io.github.masyumero.zenlesscraftzero.common.registries.ZCZDamageTypes;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;

public class ZCZDamageTypeBootstrap {

    public static void bootstrap(BootstapContext<DamageType> context) {
        context.register(ZCZDamageTypes.ATTRIBUTE_EFFECT, new DamageType(
                "attribute_effect",
                DamageScaling.NEVER,
                0
        ));
    }
}
