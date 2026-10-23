package io.github.masyumero.zenlesscraftzero.common.capability.item.disc;

import io.github.masyumero.zenlesscraftzero.common.capability.ZCZCapabilities;
import io.github.masyumero.zenlesscraftzero.common.capability.item.EquipmentLevel;
import io.github.masyumero.zenlesscraftzero.common.capability.item.IEquipmentLevel;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class DiscItemProvider implements ICapabilitySerializable<CompoundTag> {

    private final EquipmentLevel equipmentLevel;
    private final Stats stats;

    private final LazyOptional<IEquipmentLevel> equipmentLevelOptional;
    private final LazyOptional<IStats> statsOptional;

    public DiscItemProvider(EquipmentLevel equipmentLevel, Function<IEquipmentLevel, Stats> stats, CompoundTag tag) {
        this.equipmentLevel = equipmentLevel;
        this.stats = stats.apply(equipmentLevel);
        this.equipmentLevelOptional = LazyOptional.of(() -> equipmentLevel);
        this.statsOptional = LazyOptional.of(() -> this.stats);

        if (tag != null) {
            deserializeNBT(tag);
        }
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction) {
        if (capability == ZCZCapabilities.EQUIPMENT_LEVEL) return equipmentLevelOptional.cast();
        if (capability == ZCZCapabilities.STATS) return statsOptional.cast();

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.put("equipment_level", equipmentLevel.serializeNBT());
        tag.put("stats", stats.serializeNBT());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        if (tag.contains("equipment_level")) {
            equipmentLevel.deserializeNBT(tag.getCompound("equipment_level"));
        }

        if (tag.contains("stats")) {
            stats.deserializeNBT(tag.getCompound("stats"));
        }
    }
}
