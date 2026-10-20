package io.github.masyumero.zenlesscraftzero.common.utils;

import io.github.masyumero.zenlesscraftzero.ZenlessCraftZero;
import net.minecraft.resources.ResourceLocation;
import top.theillusivec4.curios.api.CuriosApi;

public class ZCZUtil {

    public static ResourceLocation rl(String id) {
        return ResourceLocation.fromNamespaceAndPath(ZenlessCraftZero.MODID, id);
    }

    public static ResourceLocation curios(String id) {
        return ResourceLocation.fromNamespaceAndPath(CuriosApi.MODID, id);
    }
}
