package io.github.masyumero.zenlesscraftzero.common.capability.item;

import io.github.masyumero.zenlesscraftzero.common.capability.ZCZCapabilities;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EquipmentLevelProvider implements ICapabilitySerializable<CompoundTag> {

    private final EquipmentLevel equipmentLevel;
    private final LazyOptional<IEquipmentLevel> optional;

    public EquipmentLevelProvider(int maxLevel, int maxXp, CompoundTag tag) {
        this.equipmentLevel = new EquipmentLevel(maxLevel, maxXp);
        this.optional = LazyOptional.of(() -> equipmentLevel);

        if (tag != null) {
            deserializeNBT(tag);
        }
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction) {
        return ZCZCapabilities.EQUIPMENT_LEVEL.orEmpty(capability, optional);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("level", equipmentLevel.getLevel());
        tag.putInt("xp", equipmentLevel.getXp());
        tag.putInt("next_level_up_xp", equipmentLevel.getRequiredXp());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag compoundTag) {
        equipmentLevel.setLevel(compoundTag.getInt("level"));
        equipmentLevel.setXp(compoundTag.getInt("xp"));
        equipmentLevel.setRequiredXp(compoundTag.getInt("next_level_up_xp"));
    }
}
