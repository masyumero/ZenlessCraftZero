package io.github.masyumero.zenlesscraftzero.api.equip.disc;

import lombok.Getter;

@Getter
public enum DiscRank {
    RANK_B("rank_b"),
    RANK_A("rank_a"),
    RANK_S("rank_s");

    private final String id;

    DiscRank(String id) {
        this.id = id;
    }
}
