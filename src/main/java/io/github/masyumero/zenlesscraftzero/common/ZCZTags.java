package io.github.masyumero.zenlesscraftzero.common;

import io.github.masyumero.zenlesscraftzero.api.equip.disc.DiscPartition;
import io.github.masyumero.zenlesscraftzero.common.utils.ZCZUtil;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.Map;

public class ZCZTags {

    public static void init() {
        Items.init();
    }

    public static class Items {

        public static void init() {}

        public static Map<DiscPartition, TagKey<Item>> DISC_PARTITION_TAGS = new HashMap<>();
        public static Map<DiscPartition, TagKey<Item>> DISC_PARTITION_CURIOS_TAGS = new HashMap<>();

        static {
            for (DiscPartition partition : DiscPartition.values()) {
                DISC_PARTITION_TAGS.put(partition, tag(partition.getId()));
                DISC_PARTITION_CURIOS_TAGS.put(partition, curiosTag(partition.getId()));
            }
        }

        private static TagKey<Item> curiosTag(String name) {
            return ItemTags.create(ZCZUtil.curios(name));
        }

        private static TagKey<Item> tag(String name) {
            return ItemTags.create(ZCZUtil.rl(name));
        }
    }
}
