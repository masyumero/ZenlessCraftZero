package io.github.masyumero.zenlesscraftzero.common.utils;

import net.minecraft.util.Mth;

public class ZCZMathUtil {

    public static int normalize(double value, double max) {
        if (max <= 0) return 0;
        return (int) Math.round(Mth.clamp(value / max, 0.0, 1.0) * 18);
    }
}
