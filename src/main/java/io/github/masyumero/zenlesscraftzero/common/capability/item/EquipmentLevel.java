package io.github.masyumero.zenlesscraftzero.common.capability.item;

import lombok.Data;
import net.minecraft.nbt.CompoundTag;

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

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("level", this.getLevel());
        tag.putInt("xp", this.getXp());
        tag.putInt("next_level_up_xp", this.getRequiredXp());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag compoundTag) {
        this.setLevel(compoundTag.getInt("level"));
        this.setXp(compoundTag.getInt("xp"));
        this.setRequiredXp(compoundTag.getInt("next_level_up_xp"));
    }
}
