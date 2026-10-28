package io.github.masyumero.zenlesscraftzero.common.capability.entity;

import com.google.common.collect.Table;
import io.github.masyumero.zenlesscraftzero.common.anomaly.Attribute;
import net.minecraft.nbt.CompoundTag;

import java.util.Map;
import java.util.UUID;

public interface IAttributeAnomaly {

    Map<UUID, Attribute> getAttribute();

    void setAttribute(Map<UUID, Attribute> attributes);

    void addAttribute(UUID uuid, Attribute attribute);

    Table<UUID, Attribute, Integer> getAnomalyBuildup();

    void setAnomalyBuildup(Table<UUID, Attribute, Integer> buildup);

    void addAnomalyBuildup(UUID uuid, Attribute attribute, int buildup);

    int getAnomalyLimit();

    void setAnomalyLimit(int limit);

    CompoundTag serializeNBT();

    void deserializeNBT(CompoundTag tag);
}
