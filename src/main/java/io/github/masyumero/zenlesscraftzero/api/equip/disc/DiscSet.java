package io.github.masyumero.zenlesscraftzero.api.equip.disc;

import io.github.masyumero.zenlesscraftzero.api.equip.disc.sets.DiscBonuses;
import io.github.masyumero.zenlesscraftzero.common.equip.disc.sets.AstralVoiceSet;
import lombok.Getter;

@Getter
public enum DiscSet {
    ASTRAL_VOICE("astral_voice", new AstralVoiceSet());

    private final String id;
    private final DiscBonuses discBonuses;

    DiscSet(String id, DiscBonuses discBonuses) {
        this.id = id;
        this.discBonuses = discBonuses;
    }
}
