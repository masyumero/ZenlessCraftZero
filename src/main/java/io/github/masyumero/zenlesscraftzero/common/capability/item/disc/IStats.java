package io.github.masyumero.zenlesscraftzero.common.capability.item.disc;

import io.github.masyumero.zenlesscraftzero.common.capability.item.IEquipmentLevel;
import io.github.masyumero.zenlesscraftzero.common.equip.disc.DiscStat;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

import java.util.Map;

@AutoRegisterCapability
public interface IStats {

    IEquipmentLevel getEquipLevel();

    DiscStat getMainStat();

    void setMainStat(DiscStat attribute);

    Map<DiscStat, Integer> getSubStats();

    void setSubStats(Map<DiscStat, Integer> discStats);

    boolean addSubStats(DiscStat discStat);

    boolean enhanceSubStats(DiscStat discStat);

    int getUpgradedCount();

    void setUpgradedCount(int count);

    void addUpgradedCount(int count);

    CompoundTag serializeNBT();

    void deserializeNBT(CompoundTag tag);
}
