package io.github.masyumero.zenlesscraftzero.common.anomaly;

import io.github.masyumero.zenlesscraftzero.ZCZLang;
import io.github.masyumero.zenlesscraftzero.api.anomaly.IAttributeEffect;
import io.github.masyumero.zenlesscraftzero.api.lang.ILangEntry;
import lombok.Getter;

@Getter
public enum Attribute {
    PHYSICAL("physical", ZCZLang.ANOMALY_PHYSICAL, new PhysicalAttribute(), 0xEDCC2C);

    private final String id;
    private final ILangEntry langEntry;
    private final IAttributeEffect attributeEffect;
    private final int color;

    Attribute(String id, ILangEntry langEntry, IAttributeEffect attributeEffect, int color) {
        this.id = id;
        this.langEntry = langEntry;
        this.attributeEffect = attributeEffect;
        this.color = color;
    }

    public static Attribute getFromId(String id) {
        for (Attribute stat : Attribute.values()) {
            if (stat.id.equals(id)) return stat;
        }
        return null;
    }
}
