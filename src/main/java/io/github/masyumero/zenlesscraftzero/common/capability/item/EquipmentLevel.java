package io.github.masyumero.zenlesscraftzero.common.capability.item;

import lombok.Data;

@Data
public class EquipmentLevel implements IEquipmentLevel {

    private final int maxLevel;
    private final int maxXp;
    private int level;
    private int xp;
    private int requiredXp;

    public EquipmentLevel(int maxLevel, int maxXp) {
        this.maxLevel = maxLevel;
        this.maxXp = maxXp;
    }

    @Override
    public void incrementLevel() {
        level = Math.min(level + 1, maxLevel);
    }

    @Override
    public int addXp(int amount, boolean simulate) {
        if (amount <= 0) {
            return 0;
        }

        int accepted = Math.min(amount, maxXp - xp);

        if (!simulate) {
            xp += accepted;
        }

        return accepted;
    }
}
