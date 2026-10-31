package io.github.masyumero.zenlesscraftzero.api;

import net.minecraft.nbt.CompoundTag;

public interface ICapability {

    default CompoundTag serializeNBT() {
        return new CompoundTag();
    }

    void deserializeNBT(CompoundTag tag);
}
