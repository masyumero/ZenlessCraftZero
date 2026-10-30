package io.github.masyumero.zenlesscraftzero.api.anomaly;

import io.github.masyumero.zenlesscraftzero.api.lang.ILangEntry;

public interface IAttributeAnomaly {

    String getId();

    ILangEntry getLangEntry();

    IAttributeEffect getAttributeEffect();

    int getColor();
}
