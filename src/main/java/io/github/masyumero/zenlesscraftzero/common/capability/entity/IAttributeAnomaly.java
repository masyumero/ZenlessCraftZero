package io.github.masyumero.zenlesscraftzero.common.capability.entity;

import io.github.masyumero.zenlesscraftzero.api.attribute.Attribute;

public interface IAttributeAnomaly {

    Attribute getAttribute();

    void setAttribute(Attribute attribute);

    int getAnomalyBuildup();

    void setAnomalyBuildup(int value);

    void addAnomalyBuildup(int value);

    int getAnomalyLimit();

    void setAnomalyLimit(int value);
}
