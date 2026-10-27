package io.github.masyumero.zenlesscraftzero.common.capability.entity;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import io.github.masyumero.zenlesscraftzero.api.anomaly.Attribute;
import lombok.Data;
import net.minecraft.nbt.CompoundTag;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
public class AttributeAnomaly implements IAttributeAnomaly {

    private final Map<UUID, Attribute> attribute = new HashMap<>();
    private final Table<UUID, Attribute, Integer> anomalyBuildup = HashBasedTable.create();
    private int anomalyLimit = -1;

    public AttributeAnomaly() {}

    @Override
    public void setAttribute(Map<UUID, Attribute> attributes) {
        this.attribute.clear();
        this.attribute.putAll(attributes);
    }

    @Override
    public void addAttribute(UUID uuid, Attribute attribute) {
        Map<UUID, Attribute> attributes = new HashMap<>();
        attributes.put(uuid, attribute);
        this.attribute.clear();
        this.attribute.putAll(attributes);
    }

    @Override
    public void setAnomalyBuildup(Table<UUID, Attribute, Integer> buildup) {
        this.anomalyBuildup.clear();
        this.anomalyBuildup.putAll(buildup);
    }

    @Override
    public void addAnomalyBuildup(UUID uuid, Attribute attribute, int buildup) {
        Integer anomalyBuildup = this.anomalyBuildup.get(uuid, attribute);
        if (anomalyBuildup == null) {
            this.anomalyBuildup.put(uuid, attribute, Math.min(buildup, anomalyLimit));
            return;
        }
        this.anomalyBuildup.put(uuid, attribute, Math.min(anomalyBuildup + buildup, anomalyLimit));
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        CompoundTag attributeTag = new CompoundTag();
        CompoundTag buildupTag = new CompoundTag();
        this.getAttribute().forEach((uuid, attribute) -> {
            attributeTag.putString(uuid.toString(), attribute.getId());
        });
        tag.put("attribute", attributeTag);
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
        CompoundTag attributeTag = tag.getCompound("attribute");
        CompoundTag buildupTag = tag.getCompound("anomaly_buildup");
        Map<UUID, Attribute> attributes = new HashMap<>();
        Table<UUID, Attribute, Integer> buildup = HashBasedTable.create();
        if (!attributeTag.isEmpty()) {
            attributeTag.getAllKeys().forEach(uuid -> {
                attributes.put(UUID.fromString(uuid), Attribute.getFromId(attributeTag.getString(uuid)));
            });
            this.setAttribute(attributes);
        }
        if (!buildupTag.isEmpty()) {
            buildupTag.getAllKeys().forEach(uuid -> {
                var buildupAttribute = buildupTag.getCompound(uuid);
                buildupAttribute.getAllKeys().forEach(attribute -> {
                    var buildupValue = buildupAttribute.getInt(attribute);
                    buildup.put(UUID.fromString(uuid), Attribute.getFromId(attribute), buildupValue);
                });
            });
            this.setAnomalyBuildup(buildup);
        }
        this.setAnomalyLimit(tag.getInt("anomaly_limit"));
    }
}
