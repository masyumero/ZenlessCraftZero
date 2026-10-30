package io.github.masyumero.zenlesscraftzero.common.capability.entity;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import io.github.masyumero.zenlesscraftzero.api.anomaly.IAttributeAnomaly;
import io.github.masyumero.zenlesscraftzero.common.anomaly.AttributeAnomaly;
import lombok.Data;
import net.minecraft.nbt.CompoundTag;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
public class AttributeAnomalyCapability implements IAttributeAnomalyCapability {

    private final Map<UUID, IAttributeAnomaly> attribute = new HashMap<>();
    private final Table<UUID, IAttributeAnomaly, Integer> anomalyBuildup = HashBasedTable.create();
    private final Map<IAttributeAnomaly, Integer> cooldowns = new HashMap<>();
    private final Map<IAttributeAnomaly, Integer> anomalyLimit = new HashMap<>();

    public AttributeAnomalyCapability() {}

    @Override
    public void setAttribute(Map<UUID, IAttributeAnomaly> attributes) {
        this.attribute.clear();
        this.attribute.putAll(attributes);
    }

    @Override
    public void addAttribute(UUID uuid, IAttributeAnomaly attributeAnomaly) {
        Map<UUID, IAttributeAnomaly> attributes = new HashMap<>();
        attributes.put(uuid, attributeAnomaly);
        this.attribute.clear();
        this.attribute.putAll(attributes);
    }

    @Override
    public void setAnomalyBuildup(Table<UUID, IAttributeAnomaly, Integer> buildup) {
        this.anomalyBuildup.clear();
        this.anomalyBuildup.putAll(buildup);
    }

    @Override
    public void addAnomalyBuildup(UUID uuid, IAttributeAnomaly attributeAnomaly, int buildup) {
        Integer anomalyBuildup = this.anomalyBuildup.get(uuid, attributeAnomaly);
        if (anomalyBuildup == null) {
            this.anomalyBuildup.put(uuid, attributeAnomaly, Math.min(buildup, anomalyLimit.getOrDefault(attributeAnomaly, -1)));
            return;
        }
        this.anomalyBuildup.put(uuid, attributeAnomaly, Math.min(anomalyBuildup + buildup, anomalyLimit.getOrDefault(attributeAnomaly, -1)));
    }

    @Override
    public void setAnomalyLimit(Map<IAttributeAnomaly, Integer> limit) {
        this.anomalyLimit.clear();
        this.anomalyLimit.putAll(limit);
    }

    @Override
    public void addAnomalyLimit(IAttributeAnomaly attributeAnomaly, int limit) {
        this.anomalyLimit.put(attributeAnomaly, limit);
    }

    @Override
    public void setCooldowns(Map<IAttributeAnomaly, Integer> cooldowns) {
        this.cooldowns.clear();
        this.cooldowns.putAll(cooldowns);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        CompoundTag attributeTag = new CompoundTag();
        CompoundTag buildupTag = new CompoundTag();
        CompoundTag limitTag = new CompoundTag();
        CompoundTag cooldownTag = new CompoundTag();
        this.getAttribute().forEach((uuid, attribute) -> {
            attributeTag.putString(uuid.toString(), attribute.getId());
        });
        tag.put("attribute_anomaly", attributeTag);
        this.getAnomalyBuildup().cellSet().forEach(buildup -> {
            CompoundTag buildupAttributeTag = new CompoundTag();
            buildupAttributeTag.putInt(buildup.getColumnKey().getId(), buildup.getValue());
            buildupTag.put(buildup.getRowKey().toString(), buildupAttributeTag);
        });
        tag.put("anomaly_buildup", buildupTag);
        this.getCooldowns().forEach((anomaly, integer) -> {
            cooldownTag.putInt(anomaly.getId(), integer);
        });
        tag.put("cooldowns", cooldownTag);
        this.getAnomalyLimit().forEach((anomaly, integer) -> {
            limitTag.putInt(anomaly.getId(), integer);
        });
        tag.put("anomaly_limit", limitTag);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        CompoundTag attributeTag = tag.getCompound("attribute_anomaly");
        CompoundTag buildupTag = tag.getCompound("anomaly_buildup");
        CompoundTag cooldownTag = tag.getCompound("cooldowns");
        CompoundTag limitTag = tag.getCompound("anomaly_limit");
        Map<UUID, IAttributeAnomaly> attributes = new HashMap<>();
        Table<UUID, IAttributeAnomaly, Integer> buildup = HashBasedTable.create();
        Map<IAttributeAnomaly, Integer> cooldowns = new HashMap<>();
        Map<IAttributeAnomaly, Integer> anomalyLimit = new HashMap<>();
        if (!attributeTag.isEmpty()) {
            attributeTag.getAllKeys().forEach(uuid -> {
                attributes.put(UUID.fromString(uuid), AttributeAnomaly.getFromId(attributeTag.getString(uuid)));
            });
            this.setAttribute(attributes);
        }
        if (!buildupTag.isEmpty()) {
            buildupTag.getAllKeys().forEach(uuid -> {
                var buildupAttribute = buildupTag.getCompound(uuid);
                buildupAttribute.getAllKeys().forEach(attribute -> {
                    var buildupValue = buildupAttribute.getInt(attribute);
                    buildup.put(UUID.fromString(uuid), AttributeAnomaly.getFromId(attribute), buildupValue);
                });
            });
            this.setAnomalyBuildup(buildup);
        }
        if (!cooldownTag.isEmpty()) {
            cooldownTag.getAllKeys().forEach(attribute -> {
                cooldowns.put(AttributeAnomaly.getFromId(attribute), cooldownTag.getInt(attribute));
            });
            this.setCooldowns(cooldowns);
        }
        if (!limitTag.isEmpty()) {
            limitTag.getAllKeys().forEach(attribute -> {
                anomalyLimit.put(AttributeAnomaly.getFromId(attribute), limitTag.getInt(attribute));
            });
        }
        this.setAnomalyLimit(anomalyLimit);
    }
}
