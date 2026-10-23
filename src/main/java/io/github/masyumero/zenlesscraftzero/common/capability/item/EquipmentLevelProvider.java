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
        return equipmentLevel.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        equipmentLevel.deserializeNBT(tag);
    }
}
