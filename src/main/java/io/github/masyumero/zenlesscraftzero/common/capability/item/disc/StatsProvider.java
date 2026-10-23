package io.github.masyumero.zenlesscraftzero.common.capability.item.disc;

import io.github.masyumero.zenlesscraftzero.common.capability.ZCZCapabilities;
import io.github.masyumero.zenlesscraftzero.common.capability.item.IEquipmentLevel;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StatsProvider implements ICapabilitySerializable<CompoundTag> {

    private final Stats stats;
    private final LazyOptional<IStats> optional;

    public StatsProvider(IEquipmentLevel equipLevel, CompoundTag tag) {
        this.stats = new Stats(equipLevel);
        this.optional = LazyOptional.of(() -> stats);

        if (tag != null) {
            deserializeNBT(tag);
        }
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction) {
        return ZCZCapabilities.STATS.orEmpty(capability, optional);
    }

    @Override
    public CompoundTag serializeNBT() {
        return stats.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        stats.deserializeNBT(tag);
    }
}
