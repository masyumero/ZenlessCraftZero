package io.github.masyumero.zenlesscraftzero.common.capability.entity;

import com.google.common.collect.Table;
import io.github.masyumero.zenlesscraftzero.api.ICapability;
import io.github.masyumero.zenlesscraftzero.api.anomaly.IAttributeAnomaly;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

import java.util.Map;
import java.util.UUID;

@AutoRegisterCapability
public interface IAttributeAnomalyCapability extends ICapability {

    Map<UUID, IAttributeAnomaly> getAttribute();

    void setAttribute(Map<UUID, IAttributeAnomaly> attributes);

    void addAttribute(UUID uuid, IAttributeAnomaly attributeAnomaly);

    Table<UUID, IAttributeAnomaly, Integer> getAnomalyBuildup();

    void setAnomalyBuildup(Table<UUID, IAttributeAnomaly, Integer> buildup);

    void addAnomalyBuildup(UUID uuid, IAttributeAnomaly attributeAnomaly, int buildup);

    Map<IAttributeAnomaly, Integer> getAnomalyLimit();

    void setAnomalyLimit(Map<IAttributeAnomaly, Integer> limit);

    void addAnomalyLimit(IAttributeAnomaly attributeAnomaly, int limit);

    Map<IAttributeAnomaly, Integer> getCooldowns();

    void setCooldowns(Map<IAttributeAnomaly, Integer> cooldowns);
}
