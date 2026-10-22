package io.github.masyumero.zenlesscraftzero;

import io.github.masyumero.zenlesscraftzero.api.lang.ILangEntry;
import io.github.masyumero.zenlesscraftzero.common.utils.ZCZUtil;
import net.minecraft.Util;
import org.jetbrains.annotations.NotNull;

public enum ZCZLang implements ILangEntry {
    DISC_TWO_PIECE_TOOLTIP("disc", "two_piece_tooltip"),
    DISC_FOUR_PIECE_TOOLTIP("disc", "four_piece_tooltip"),

    ASTRAL_VOICE_TWO_PIECE_TOOLTIP("disc", "astral_voice_two_piece_tooltip"),
    ASTRAL_VOICE_FOUR_PIECE_TOOLTIP("disc", "astral_voice_four_piece_tooltip");

    private final String key;

    ZCZLang(String type, String path) {
        this(Util.makeDescriptionId(type, ZCZUtil.rl(path)));
    }

    ZCZLang(String key) {
        this.key = key;
    }

    @NotNull
    @Override
    public String getTranslationKey() {
        return key;
    }
}
