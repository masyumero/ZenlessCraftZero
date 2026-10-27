package io.github.masyumero.zenlesscraftzero.common.capability.entity;

import io.github.masyumero.zenlesscraftzero.common.capability.ZCZCapabilities;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AttributeAnomalyProvider implements ICapabilitySerializable<CompoundTag> {

    private final AttributeAnomaly attributeAnomaly;
    private final LazyOptional<IAttributeAnomaly> optional;

    public AttributeAnomalyProvider() {
        this.attributeAnomaly = new AttributeAnomaly();
        this.optional = LazyOptional.of(() -> attributeAnomaly);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction) {
        return ZCZCapabilities.ATTRIBUTE_ANOMALY.orEmpty(capability, optional);
    }

    @Override
    public CompoundTag serializeNBT() {
        return attributeAnomaly.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        attributeAnomaly.deserializeNBT(tag);
    }

    public void invalidate() {
        optional.invalidate();
    }
}
