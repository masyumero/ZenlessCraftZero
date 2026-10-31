package io.github.masyumero.zenlesscraftzero.common.capability.entity;

import io.github.masyumero.zenlesscraftzero.common.capability.ZCZCapabilities;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AttributeProvider implements ICapabilitySerializable<CompoundTag> {

    private final AttributeCapability attribute;
    private final LazyOptional<IAttribute> optional;

    public AttributeProvider() {
        this.attribute = new AttributeCapability();
        this.optional = LazyOptional.of(() -> this.attribute);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction) {
        return ZCZCapabilities.ATTRIBUTE.orEmpty(capability, this.optional);
    }

    @Override
    public CompoundTag serializeNBT() {
        return attribute.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        attribute.deserializeNBT(tag);
    }

    public void invalidate() {
        optional.invalidate();
    }
}
