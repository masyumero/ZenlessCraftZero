package io.github.masyumero.zenlesscraftzero.common.capability.item.disc;

import io.github.masyumero.zenlesscraftzero.common.capability.entity.IAttribute;
import io.github.masyumero.zenlesscraftzero.common.capability.item.IEquipmentLevel;
import io.github.masyumero.zenlesscraftzero.common.equip.disc.DiscStat;
import lombok.Data;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;

import java.util.HashMap;
import java.util.Map;

@Data
public class Stats implements IStats {

    private final IEquipmentLevel equipLevel;
    private DiscStat mainStat;
    private int upgradedCount;
    private Map<DiscStat, Integer> subStats = new HashMap<>();

    public Stats(IEquipmentLevel equipLevel) {
        this.equipLevel = equipLevel;
    }

    @Override
    public void setSubStats(Map<DiscStat, Integer> discStats) {
        if (discStats.size() > 4) {
            return;
        }
        subStats = new HashMap<>(discStats);
    }

    @Override
    public boolean addSubStats(DiscStat discStat) {
        if (subStats.size() > 4) {
            return false;
        }
        if (subStats.containsKey(discStat)) {
            return false;
        }
        if (mainStat == discStat) {
            return false;
        }
        subStats.put(discStat, 0);
        return true;
    }

    @Override
    public boolean enhanceSubStats(DiscStat discStat) {
        if (!subStats.containsKey(discStat)) {
            return false;
        }
        if (subStats.size() != 4) {
            return false;
        }
        subStats.put(discStat, subStats.get(discStat) + 1);
        return true;
    }

    @Override
    public void addUpgradedCount(int count) {
        upgradedCount = Mth.clamp(upgradedCount + count, 0, 5);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = IStats.super.serializeNBT();
        if (this.getMainStat() != null) {
            tag.putString("main_stat", this.getMainStat().getId());
        }
        tag.putInt("upgraded_count", upgradedCount);
        if (!this.getSubStats().isEmpty()) {
            CompoundTag subStatsTag = new CompoundTag();
            this.getSubStats().forEach((discStat, value) -> {
                subStatsTag.putInt(discStat.getId(), value);
            });
            tag.put("sub_stats", subStatsTag);
        }
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        this.setMainStat(DiscStat.getFromId(tag.getString("main_stat")));
        this.setUpgradedCount(tag.getInt("upgraded_count"));
        CompoundTag subStatsTag = tag.getCompound("sub_stats");
        Map<DiscStat, Integer> subStats = new HashMap<>();
        subStatsTag.getAllKeys().forEach(key -> {
            DiscStat stat = DiscStat.getFromId(key);
            if (stat != null) {
                subStats.put(stat, subStatsTag.getInt(key));
            }
        });

        this.setSubStats(subStats);
    }
}
