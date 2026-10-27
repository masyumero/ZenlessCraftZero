package io.github.masyumero.zenlesscraftzero.api.anomaly;

import io.github.masyumero.zenlesscraftzero.common.anomaly.PhysicalAttribute;
import lombok.Getter;

@Getter
public enum Attribute {
    PHYSICAL("physical", new PhysicalAttribute(), 0xEDCC2C);

    private final String id;
    private final IAttributeEffect attributeEffect;
    private final int color;

    Attribute(String id, IAttributeEffect attributeEffect, int color) {
        this.id = id;
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
