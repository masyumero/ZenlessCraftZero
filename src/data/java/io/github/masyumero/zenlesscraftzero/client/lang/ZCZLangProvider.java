package io.github.masyumero.zenlesscraftzero.client.lang;

import io.github.masyumero.zenlesscraftzero.ZCZLang;
import io.github.masyumero.zenlesscraftzero.api.equip.disc.DiscPartition;
import io.github.masyumero.zenlesscraftzero.api.equip.disc.DiscRank;
import io.github.masyumero.zenlesscraftzero.api.equip.disc.DiscSet;
import io.github.masyumero.zenlesscraftzero.api.equip.disc.DiscSetting;
import io.github.masyumero.zenlesscraftzero.common.registries.ZCZItems;
import net.minecraft.data.PackOutput;

public class ZCZLangProvider extends BaseLangProvider {
    public ZCZLangProvider(PackOutput output) {
        super(output, "en_us");
    }

    public static final String[] EN_NUMBER = new String[] {
            "One", "Two", "Three", "Four", "Five", "Six"
    };

    @Override
    protected void addTranslations() {
        for (int i = 1; i < DiscPartition.values().length+1; i++) {
            addCuriosSlot("partition_" + i, "Partition " + EN_NUMBER[i - 1], "パーティション" + i);
        }

        addENJP(ZCZLang.DISC_TWO_PIECE_TOOLTIP, "2-Piece Sets: ", "2セット: ");
        addENJP(ZCZLang.DISC_FOUR_PIECE_TOOLTIP, "4-Piece Sets: ", "4セット: ");

        addENJP(ZCZLang.ASTRAL_VOICE_TWO_PIECE_TOOLTIP, "ATK +10%", "攻撃力+10%。");
        addENJP(ZCZLang.ASTRAL_VOICE_FOUR_PIECE_TOOLTIP, "Whenever the character attacks an enemy, all nearby players gain 1 stack of Astral, up to a maximum of 3 stacks, and lasting 15s. Repeated triggers reset the duration. Each stack of Astral increases the DMG dealt by nearby players by 8%. Passive effects of the same name do not stack.", "敵を攻撃したとき、付近のプレイヤー全員が「天籟」を1重獲得する。最大3重まで重ね掛け可能、継続時間15秒、重複して発動すると継続時間が更新される。「天籟」1重につき、付近のプレイヤーの与ダメージ+8%、同じパッシブ効果は重ね掛け不可。");

        addENJP(ZCZLang.DISC_STAT_MAIN, "Main Stat: ", "メインステータス: ");
        addENJP(ZCZLang.DISC_STAT_SUB, "Sub-Stats: ", "サブステータス: ");
        addENJP(ZCZLang.DISC_STAT_EMPTY, "Empty", "[]");
        addENJP(ZCZLang.DISC_STAT_HP, "HP %s %s", "HP %s %s");
        addENJP(ZCZLang.DISC_STAT_ATK, "ATK %s %s", "攻撃力 %s %s");
        addENJP(ZCZLang.DISC_STAT_DEF, "DEF %s %s", "防御力 %s %s");
        addENJP(ZCZLang.DISC_STAT_HP_RATIO, "HP %s %s%%", "HP %s %s%%");
        addENJP(ZCZLang.DISC_STAT_ATK_RATIO, "ATK %s %s%%", "攻撃力 %s %s%%");
        addENJP(ZCZLang.DISC_STAT_DEF_RATIO, "DEF %s %s%%", "防御力 %s %s%%");
        addENJP(ZCZLang.DISC_STAT_CRIT_CHANCE, "CRIT Rate %s %s%%", "会心率 %s %s%%");
        addENJP(ZCZLang.DISC_STAT_CRIT_DAMAGE, "CRIT DMG %s %s%%", "会心ダメージ %s %s%%");
        addENJP(ZCZLang.DISC_STAT_ANOMALY_PROFICIENCY, "Anomaly Proficiency %s %s%%", "異常掌握 %s %s%%");
        addENJP(ZCZLang.DISC_STAT_PEN_RATIO, "PEN Ratio %s %s%%", "貫通率 %s %s%%");

        addENJP(ZCZItems.MOLDED_PLATING_AGENT.get(), "Molded Plating Agent", "可塑化助剤");
        addENJP(ZCZItems.CRYSTALLIZED_PLATING_AGENT.get(), "Crystallized Plating Agent", "結晶化助剤");
        addENJP(ZCZItems.ETHER_PLATING_AGENT.get(), "Ether Plating Agent", "エーテル助剤");

        addDisc(DiscSet.ASTRAL_VOICE, "Astral Voice", "静寂のアストラ");

        ENJP.forEach((key, enjp) -> this.add(key, enjp.en()));
    }

    private void addDisc(DiscSet set, String en, String jp) {
        for (DiscPartition partition : DiscPartition.values()) {
            for (DiscRank rank : DiscRank.values()) {
                addENJP(ZCZItems.getDisc(new DiscSetting(set, partition, rank)).get(), en, jp);
            }
        }
    }
}
