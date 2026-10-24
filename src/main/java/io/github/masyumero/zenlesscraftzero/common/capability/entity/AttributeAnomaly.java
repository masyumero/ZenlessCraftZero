package io.github.masyumero.zenlesscraftzero.common.capability.entity;

import io.github.masyumero.zenlesscraftzero.api.attribute.Attribute;
import lombok.Data;

@Data
public class AttributeAnomaly implements IAttributeAnomaly {

    private Attribute attribute;
    private int anomalyBuildup;
    private int anomalyLimit;

    public AttributeAnomaly() {}

    @Override
    public void addAnomalyBuildup(int value) {

    }
}
