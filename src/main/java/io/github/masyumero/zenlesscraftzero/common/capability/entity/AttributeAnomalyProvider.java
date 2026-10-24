package io.github.masyumero.zenlesscraftzero.common.capability.entity;

import io.github.masyumero.zenlesscraftzero.api.attribute.Attribute;
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

    public AttributeAnomalyProvider(CompoundTag tag) {
        this.attributeAnomaly = new AttributeAnomaly();
        this.optional = LazyOptional.of(() -> attributeAnomaly);

        if (tag != null) {
            deserializeNBT(tag);
        }
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction) {
        return ZCZCapabilities.ATTRIBUTE_ANOMALY.orEmpty(capability, optional);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putString("attribute", attributeAnomaly.getAttribute().getId());
        tag.putInt("anomaly_buildup", attributeAnomaly.getAnomalyBuildup());
        tag.putInt("anomaly_limit", attributeAnomaly.getAnomalyLimit());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        attributeAnomaly.setAttribute(Attribute.getFromId(tag.getString("attribute")));
        attributeAnomaly.setAnomalyBuildup(tag.getInt("anomaly_buildup"));
        attributeAnomaly.setAnomalyLimit(tag.getInt("anomaly_limit"));
    }
}
