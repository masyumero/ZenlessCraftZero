package io.github.masyumero.zenlesscraftzero.common.capability.item;

import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public interface IEquipmentLevel {

    int getLevel();

    int getMaxLevel();

    void setLevel(int level);

    void incrementLevel();

    int getXp();

    int getMaxXp();

    int getNextLevelUpXp();

    void setNextLevelUpXp(int xp);

    void setXp(int xp);

    int addXp(int amount, boolean simulate);
}
