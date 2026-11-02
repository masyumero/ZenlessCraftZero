package io.github.masyumero.zenlesscraftzero.common.anomaly;

import io.github.masyumero.zenlesscraftzero.ZCZLang;
import io.github.masyumero.zenlesscraftzero.api.anomaly.IAttributeAnomaly;
import io.github.masyumero.zenlesscraftzero.api.anomaly.IAttributeEffect;
import io.github.masyumero.zenlesscraftzero.api.lang.ILangEntry;
import lombok.Getter;

import java.util.function.Supplier;

@Getter
public enum AttributeAnomaly implements IAttributeAnomaly {
    PHYSICAL("physical", ZCZLang.ANOMALY_PHYSICAL, PhysicalAttribute.INSTANCE, 0xEDCC2C),
    FIRE("fire", ZCZLang.ANOMALY_FIRE, FireAttribute.INSTANCE, 0xFF5623),;

    private final String id;
    private final ILangEntry langEntry;
    private final IAttributeEffect attributeEffect;
    private final int color;

    AttributeAnomaly(String id, ILangEntry langEntry, IAttributeEffect attributeEffect, int color) {
        this.id = id;
        this.langEntry = langEntry;
        this.attributeEffect = attributeEffect;
        this.color = color;
    }

    public static AttributeAnomaly getFromId(String id) {
        for (AttributeAnomaly stat : AttributeAnomaly.values()) {
            if (stat.id.equals(id)) return stat;
        }
        return null;
    }
}
