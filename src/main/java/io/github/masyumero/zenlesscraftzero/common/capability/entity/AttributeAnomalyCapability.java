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
    private int anomalyLimit = -1;

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
            this.anomalyBuildup.put(uuid, attributeAnomaly, Math.min(buildup, anomalyLimit));
            return;
        }
        this.anomalyBuildup.put(uuid, attributeAnomaly, Math.min(anomalyBuildup + buildup, anomalyLimit));
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        CompoundTag attributeTag = new CompoundTag();
        CompoundTag buildupTag = new CompoundTag();
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
        tag.putInt("anomaly_limit", this.getAnomalyLimit());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        CompoundTag attributeTag = tag.getCompound("attribute_anomaly");
        CompoundTag buildupTag = tag.getCompound("anomaly_buildup");
        Map<UUID, IAttributeAnomaly> attributes = new HashMap<>();
        Table<UUID, IAttributeAnomaly, Integer> buildup = HashBasedTable.create();
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
        this.setAnomalyLimit(tag.getInt("anomaly_limit"));
    }
}
