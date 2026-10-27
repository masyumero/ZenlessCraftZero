package io.github.masyumero.zenlesscraftzero.common.registries;

import io.github.masyumero.zenlesscraftzero.common.utils.ZCZUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

public class ZCZDamageTypes {

    public static final ResourceKey<DamageType> ATTRIBUTE_EFFECT = ResourceKey.create(Registries.DAMAGE_TYPE, ZCZUtil.rl("attribute_effect"));
}
