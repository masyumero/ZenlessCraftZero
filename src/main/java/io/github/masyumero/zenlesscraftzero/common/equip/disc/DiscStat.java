package io.github.masyumero.zenlesscraftzero.common.equip.disc;

import dev.shadowsoffire.attributeslib.api.ALObjects;
import io.github.masyumero.zenlesscraftzero.ZCZLang;
import io.github.masyumero.zenlesscraftzero.api.equip.disc.DiscPartition;
import io.github.masyumero.zenlesscraftzero.api.lang.ILangEntry;
import io.github.masyumero.zenlesscraftzero.common.registries.ZCZAttributes;
import lombok.Getter;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.registries.RegistryObject;

import java.util.*;

@Getter
public enum DiscStat {
    HP("hp", ZCZLang.DISC_STAT_HP, Attributes.MAX_HEALTH, AttributeModifier.Operation.ADDITION, true, DiscPartition.PARTITION_1),
    ATK("atk", ZCZLang.DISC_STAT_ATK, Attributes.ATTACK_DAMAGE, AttributeModifier.Operation.ADDITION, true, DiscPartition.PARTITION_2),
    DEF("def", ZCZLang.DISC_STAT_DEF, Attributes.ARMOR, AttributeModifier.Operation.ADDITION, true, DiscPartition.PARTITION_3),
    HP_RATIO("hp_ratio", ZCZLang.DISC_STAT_HP_RATIO, Attributes.MAX_HEALTH, AttributeModifier.Operation.MULTIPLY_BASE, true, DiscPartition.PARTITION_4, DiscPartition.PARTITION_5, DiscPartition.PARTITION_6),
    ATK_RATIO("atk_ratio", ZCZLang.DISC_STAT_ATK_RATIO, Attributes.ATTACK_DAMAGE, AttributeModifier.Operation.MULTIPLY_BASE, true, DiscPartition.PARTITION_4, DiscPartition.PARTITION_5, DiscPartition.PARTITION_6),
    DEF_RATIO("def_ratio", ZCZLang.DISC_STAT_DEF_RATIO, Attributes.ARMOR, AttributeModifier.Operation.MULTIPLY_BASE, true, DiscPartition.PARTITION_4, DiscPartition.PARTITION_5, DiscPartition.PARTITION_6),
    CRIT_CHANCE("crit_rate", ZCZLang.DISC_STAT_CRIT_RATE, ZCZAttributes.CRIT_RATE, AttributeModifier.Operation.ADDITION, true, DiscPartition.PARTITION_4),
    CRIT_DAMAGE("crit_damage", ZCZLang.DISC_STAT_CRIT_DAMAGE, ZCZAttributes.CRIT_DAMAGE, AttributeModifier.Operation.ADDITION, true, DiscPartition.PARTITION_4),
    ANOMALY_PROFICIENCY("anomaly_proficiency", ZCZLang.DISC_STAT_ANOMALY_PROFICIENCY, ZCZAttributes.ANOMALY_PROFICIENCY, AttributeModifier.Operation.ADDITION, true, DiscPartition.PARTITION_4),
    PEN_RATIO("pen_ratio", ZCZLang.DISC_STAT_PEN_RATIO, ALObjects.Attributes.ARMOR_PIERCE, AttributeModifier.Operation.MULTIPLY_BASE, false, DiscPartition.PARTITION_5),
    PEN("pen", ZCZLang.DISC_STAT_PEN, ALObjects.Attributes.ARMOR_PIERCE, AttributeModifier.Operation.ADDITION, true),
    PHYSICAL_DMG_BONUS("physical_dmg_bonus", ZCZLang.DISC_STAT_PHYSICAL_DMG, ZCZAttributes.PHYSICAL_DMG_BONUS, AttributeModifier.Operation.ADDITION, false, DiscPartition.PARTITION_5),
    ANOMALY_MASTERY("anomaly_mastery", ZCZLang.DISC_STAT_ANOMALY_MASTERY, ZCZAttributes.ANOMALY_MASTERY, AttributeModifier.Operation.MULTIPLY_BASE, false, DiscPartition.PARTITION_6),
    //IMPACT("impact", ZCZLang.DISC_STAT_IMPACT, , AttributeModifier.Operation.MULTIPLY_BASE, false, DiscPartition.PARTITION_6)
    //ENERGY_REGEN("energy_regen", ZCZLang.DISC_STAT_ENERGY_REGEN, , AttributeModifier.Operation.MULTIPLY_BASE, false, DiscPartition.PARTITION_6)
    ;

    private final String id;
    private final ILangEntry translate;
    private final Attribute attribute;
    private final AttributeModifier.Operation operation;
    private final boolean isSubStat;
    private final DiscPartition[] partitions;
    private final Map<DiscPartition, UUID> uuid = new HashMap<>();
    private final Map<DiscPartition, UUID> subUuid = new HashMap<>();

    DiscStat(String id, ILangEntry translate, Attribute attribute, AttributeModifier.Operation operation, boolean isSubStat, DiscPartition... partitions) {
        this.id = id;
        this.translate = translate;
        this.attribute = attribute;
        this.operation = operation;
        this.isSubStat = isSubStat;
        this.partitions = partitions;
        if (partitions != null) {
            for (DiscPartition partition : partitions) {
                uuid.put(partition, UUID.randomUUID());
            }
        }
        if (isSubStat) {
            for (DiscPartition partition : DiscPartition.values()) {
                subUuid.put(partition, UUID.randomUUID());
            }
        }
    }

    DiscStat(String id, ILangEntry translate, RegistryObject<Attribute> attributeRO, AttributeModifier.Operation operation, boolean isSubStat, DiscPartition... partitions) {
        this(id, translate, attributeRO.get(), operation, isSubStat, partitions);
    }

    public UUID getPartitionUUID(DiscPartition partition) {
        if (!uuid.containsKey(partition)) {
            return null;
        }
        return uuid.get(partition);
    }

    public UUID getPartitionSubUUID(DiscPartition partition) {
        if (!subUuid.containsKey(partition)) {
            return null;
        }
        return subUuid.get(partition);
    }

    public static DiscStat getFromId(String id) {
        for (DiscStat stat : DiscStat.values()) {
            if (stat.id.equals(id)) return stat;
        }
        return null;
    }

    public static DiscStat[] getPartitionStats(DiscPartition partition) {
        List<DiscStat> stats = new ArrayList<>();
        for (DiscStat discStat : DiscStat.values()) {
            if (Arrays.asList(discStat.getPartitions()).contains(partition)) {
                stats.add(discStat);
            }
        }
        return stats.toArray(new DiscStat[0]);
    }

    public static DiscStat[] getSubStats() {
        List<DiscStat> stats = new ArrayList<>();
        for (DiscStat discStat : DiscStat.values()) {
            if (discStat.isSubStat) {
                stats.add(discStat);
            }
        }
        return stats.toArray(new DiscStat[0]);
    }
}
