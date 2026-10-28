package io.github.masyumero.zenlesscraftzero.client.indicator;

import io.github.masyumero.zenlesscraftzero.common.anomaly.Attribute;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class HealthIndicators {

    private static final List<Entry> ENTRIES = new ArrayList<>();

    public static void add(Attribute attribute, Entity entity) {
        ENTRIES.add(new Entry(attribute, entity));
    }

    public static void tick() {
        ENTRIES.removeIf(entry -> ++entry.age >= 40);
    }

    public static List<Entry> entries() {
        return ENTRIES;
    }

    public static class Entry {
        public final Attribute attribute;
        public final Entity entity;
        public int age;

        public Entry(Attribute attribute, Entity entity) {
            this.attribute = attribute;
            this.entity = entity;
            this.age = 0;
        }
    }
}
