package io.github.masyumero.zenlesscraftzero.common.capability.entity;

import io.github.masyumero.zenlesscraftzero.api.ICapability;
import io.github.masyumero.zenlesscraftzero.common.anomaly.AttributeAnomaly;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public interface IAttribute extends ICapability {

    AttributeAnomaly getAttributeAnomaly();

    void setAttributeAnomaly(AttributeAnomaly attribute);
}
