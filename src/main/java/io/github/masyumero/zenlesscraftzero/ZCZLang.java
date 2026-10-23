package io.github.masyumero.zenlesscraftzero;

import io.github.masyumero.zenlesscraftzero.api.lang.ILangEntry;
import io.github.masyumero.zenlesscraftzero.common.utils.ZCZUtil;
import net.minecraft.Util;
import org.jetbrains.annotations.NotNull;

public enum ZCZLang implements ILangEntry {
    DISC_TWO_PIECE_TOOLTIP("disc", "two_piece_tooltip"),
    DISC_FOUR_PIECE_TOOLTIP("disc", "four_piece_tooltip"),

    DISC_STAT_MAIN("disc", "stat_main"),
    DISC_STAT_SUB("disc", "stat_sub"),
    DISC_STAT_EMPTY("disc", "stat_empty"),
    DISC_STAT_HP("disc", "stat_hp"),
    DISC_STAT_ATK("disc", "stat_atk"),
    DISC_STAT_DEF("disc", "stat_def"),
    DISC_STAT_HP_RATIO("disc", "stat_hp_ratio"),
    DISC_STAT_ATK_RATIO("disc", "stat_atk_ratio"),
    DISC_STAT_DEF_RATIO("disc", "stat_def_ratio"),
    DISC_STAT_CRIT_CHANCE("disc", "stat_crit_chance"),
    DISC_STAT_CRIT_DAMAGE("disc", "stat_crit_damage"),
    DISC_STAT_ANOMALY_PROFICIENCY("disc", "stat_anomaly_proficiency"),
    DISC_STAT_PEN_RATIO("disc", "stat_pen_ratio"),

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
