package io.github.masyumero.zenlesscraftzero.client.indicator;

import io.github.masyumero.zenlesscraftzero.api.anomaly.Attribute;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class DamageIndicators {

    private static final List<Entry> ENTRIES = new ArrayList<>();

    public static void add(Attribute attribute, double x, double y, double z, float damage, boolean isAnomalyEffect) {
        ENTRIES.add(new Entry(attribute, x, y, z, damage, isAnomalyEffect));
    }

    public static void tick() {
        ENTRIES.removeIf(entry -> ++entry.age >= 40);
    }

    public static List<Entry> entries() {
        return ENTRIES;
    }

    public static class Entry {
        public final Attribute attribute;
        public final double x;
        public final double y;
        public final double z;
        public final float damage;
        public final double offsetX;
        public final double offsetY;
        public final boolean isAnomalyEffect;
        public int age;

        public Entry(Attribute attribute, double x, double y, double z, float damage, boolean isAnomalyEffect) {
            this.attribute = attribute;
            this.x = x;
            this.y = y;
            this.z = z;
            this.damage = damage;
            this.isAnomalyEffect = isAnomalyEffect;
            this.offsetX = (Math.random() * 2.0 - 1.0) * 0.5;
            this.offsetY = Math.random() * 1.1;
            this.age = 0;
        }
    }
}