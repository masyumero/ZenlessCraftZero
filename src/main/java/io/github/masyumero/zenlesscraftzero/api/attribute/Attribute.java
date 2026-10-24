package io.github.masyumero.zenlesscraftzero.api.attribute;

import io.github.masyumero.zenlesscraftzero.api.equip.disc.DiscStat;
import lombok.Getter;

@Getter
public enum Attribute {
    PHYSICAL("physical");

    private final String id;

    Attribute(String id) {
        this.id = id;
    }

    public static Attribute getFromId(String id) {
        for (Attribute stat : Attribute.values()) {
            if (stat.id.equals(id)) return stat;
        }
        return null;
    }
}
