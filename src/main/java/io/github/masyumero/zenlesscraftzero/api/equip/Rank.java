package io.github.masyumero.zenlesscraftzero.api.equip;

import lombok.Getter;

@Getter
public enum Rank {
    RANK_C("rank_c"),
    RANK_B("rank_b"),
    RANK_A("rank_a"),
    RANK_S("rank_s");

    private final String id;

    Rank(String id) {
        this.id = id;
    }
}
