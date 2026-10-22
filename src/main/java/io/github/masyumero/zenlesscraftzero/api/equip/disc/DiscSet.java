package io.github.masyumero.zenlesscraftzero.api.equip.disc;

import io.github.masyumero.zenlesscraftzero.ZCZLang;
import io.github.masyumero.zenlesscraftzero.api.equip.disc.sets.DiscBonuses;
import io.github.masyumero.zenlesscraftzero.api.lang.ILangEntry;
import io.github.masyumero.zenlesscraftzero.common.equip.disc.sets.AstralVoiceSet;
import lombok.Getter;

@Getter
public enum DiscSet {
    ASTRAL_VOICE("astral_voice", new AstralVoiceSet(), ZCZLang.ASTRAL_VOICE_TWO_PIECE_TOOLTIP, ZCZLang.ASTRAL_VOICE_FOUR_PIECE_TOOLTIP);

    private final String id;
    private final DiscBonuses discBonuses;
    private final ILangEntry twoPieceLang;
    private final ILangEntry fourPieceLang;

    DiscSet(String id, DiscBonuses discBonuses, ILangEntry twoPieceLang, ILangEntry fourPieceLang) {
        this.id = id;
        this.discBonuses = discBonuses;
        this.twoPieceLang = twoPieceLang;
        this.fourPieceLang = fourPieceLang;
    }
}
