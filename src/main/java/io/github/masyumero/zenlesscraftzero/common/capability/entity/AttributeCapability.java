package io.github.masyumero.zenlesscraftzero.common.capability.entity;

import io.github.masyumero.zenlesscraftzero.api.anomaly.IAttributeAnomaly;
import io.github.masyumero.zenlesscraftzero.common.anomaly.AttributeAnomaly;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.nbt.CompoundTag;

@Getter
@Setter
public class AttributeCapability implements IAttribute {

    private IAttributeAnomaly attributeAnomaly;

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = IAttribute.super.serializeNBT();
        tag.putString("attribute_anomaly", this.getAttributeAnomaly().getId());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        setAttributeAnomaly(AttributeAnomaly.getFromId(tag.getString("attribute_anomaly")));
    }
}
