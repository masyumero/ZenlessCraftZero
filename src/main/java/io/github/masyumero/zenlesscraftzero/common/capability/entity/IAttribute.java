package io.github.masyumero.zenlesscraftzero.common.capability.entity;

import io.github.masyumero.zenlesscraftzero.api.ICapability;
import io.github.masyumero.zenlesscraftzero.api.anomaly.IAttributeAnomaly;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public interface IAttribute extends ICapability {

    IAttributeAnomaly getAttributeAnomaly();

    void setAttributeAnomaly(IAttributeAnomaly attribute);
}
