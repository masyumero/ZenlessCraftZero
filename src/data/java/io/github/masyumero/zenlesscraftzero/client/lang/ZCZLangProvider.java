package io.github.masyumero.zenlesscraftzero.client.lang;

import io.github.masyumero.zenlesscraftzero.ZCZLang;
import io.github.masyumero.zenlesscraftzero.api.equip.disc.DiscPartition;
import io.github.masyumero.zenlesscraftzero.api.equip.disc.DiscRank;
import io.github.masyumero.zenlesscraftzero.common.equip.disc.sets.DiscSet;
import io.github.masyumero.zenlesscraftzero.api.equip.disc.DiscSetting;
import io.github.masyumero.zenlesscraftzero.common.registries.ZCZAttributes;
import io.github.masyumero.zenlesscraftzero.common.registries.ZCZItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraftforge.registries.RegistryObject;

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
        addENJP(ZCZLang.FREEDOM_BLUES_TWO_PIECE_TOOLTIP, "Anomaly Proficiency +30", "異常マスタリー+30Pt。");
        addENJP(ZCZLang.FREEDOM_BLUES_FOUR_PIECE_TOOLTIP, "When an EX Special Attack hits an enemy, reduce the target's Anomaly Buildup RES to the equipper's Attribute by 20% for 8s. This effect does not stack with others of the same attribute.", "『強化特殊スキル』が敵に命中すると、装備者の属性に応じてターゲットの対応する状態異常蓄積耐性-20%、継続時間8秒。同属性での重ね掛けは不可。");

        addAttribute(ZCZAttributes.ANOMALY_PROFICIENCY, "Anomaly Proficiency", "異常マスタリー", "Anomaly Proficiency enhances the damage inflicted by Attribute Anomalies. Each point in Anomaly Proficiency is equivalent to 1% of Attribute Anomaly DMG.", "異常マスタリーは状態異常によるダメージに影響する。異常マスタリー1Ptは、状態異常ダメージ1%に相当する。");
        addAttribute(ZCZAttributes.ANOMALY_MASTERY, "Anomaly Mastery", "異常掌握", "Anomaly Mastery affects the efficiency of Anomaly Buildup. Each point in Anomaly Mastery is equivalent to 1% of Anomaly Buildup Rate.", "異常掌握は、状態異常蓄積値の蓄積効率に影響する。異常掌握1Ptは、状態異常蓄積効率1％に相当する。");
        addAttribute(ZCZAttributes.ANOMALY_RESISTANCE, "Anomaly Resistance", "状態異常蓄積耐性", "", "");
        addAttribute(ZCZAttributes.PHYSICAL_DMG_BONUS, "Physical DMG Bonus", "物理属性ダメージボーナス", "", "");
        addAttribute(ZCZAttributes.CRIT_RATE, "CRIT Rate", "会心率", "", "");
        addAttribute(ZCZAttributes.CRIT_DAMAGE, "CRIT DMG", "会心ダメージ", "", "");

        addENJP(ZCZLang.DISC_STAT_MAIN, "Main Stat: ", "メインステータス: ");
        addENJP(ZCZLang.DISC_STAT_SUB, "Sub-Stats: ", "サブステータス: ");
        addENJP(ZCZLang.DISC_STAT_EMPTY, "Empty", "[]");
        addENJP(ZCZLang.DISC_STAT_HP, "HP %s %s", "HP %s %s");
        addENJP(ZCZLang.DISC_STAT_ATK, "ATK %s %s", "攻撃力 %s %s");
        addENJP(ZCZLang.DISC_STAT_DEF, "DEF %s %s", "防御力 %s %s");
        addENJP(ZCZLang.DISC_STAT_HP_RATIO, "HP %s %s%%", "HP %s %s%%");
        addENJP(ZCZLang.DISC_STAT_ATK_RATIO, "ATK %s %s%%", "攻撃力 %s %s%%");
        addENJP(ZCZLang.DISC_STAT_DEF_RATIO, "DEF %s %s%%", "防御力 %s %s%%");
        addENJP(ZCZLang.DISC_STAT_CRIT_RATE, "CRIT Rate %s %s%%", "会心率 %s %s%%");
        addENJP(ZCZLang.DISC_STAT_CRIT_DAMAGE, "CRIT DMG %s %s%%", "会心ダメージ %s %s%%");
        addENJP(ZCZLang.DISC_STAT_ANOMALY_PROFICIENCY, "Anomaly Proficiency %s %s", "異常マスタリー %s %s");
        addENJP(ZCZLang.DISC_STAT_ANOMALY_MASTERY, "Anomaly Mastery %s %s%%", "異常掌握  %s %s%%");
        addENJP(ZCZLang.DISC_STAT_PEN_RATIO, "PEN Ratio %s %s%%", "貫通率 %s %s%%");
        addENJP(ZCZLang.DISC_STAT_PEN, "PEN %s %s%%", "貫通値 %s %s%%");
        addENJP(ZCZLang.DISC_STAT_PHYSICAL_DMG, "Physical DMG Bonus %s %s%%", "物理属性ダメージボーナス %s %s%%");

        addENJP(ZCZItems.MOLDED_PLATING_AGENT.get(), "Molded Plating Agent", "可塑化助剤");
        addENJP(ZCZItems.CRYSTALLIZED_PLATING_AGENT.get(), "Crystallized Plating Agent", "結晶化助剤");
        addENJP(ZCZItems.ETHER_PLATING_AGENT.get(), "Ether Plating Agent", "エーテル助剤");

        addENJP(ZCZLang.ANOMALY_EFFECT_PHYSICAL, "Assault!!", "強撃!!");
        addENJP(ZCZLang.ANOMALY_PHYSICAL, "Physical", "物理");

        addDisc(DiscSet.ASTRAL_VOICE, "Astral Voice", "静寂のアストラ");
        addDisc(DiscSet.FREEDOM_BLUES, "Freedom Blues", "フリーダム・ブルース");

        ENJP.forEach((key, enjp) -> this.add(key, enjp.en()));
    }

    private void addDisc(DiscSet set, String en, String jp) {
        for (DiscPartition partition : DiscPartition.values()) {
            for (DiscRank rank : DiscRank.values()) {
                addENJP(ZCZItems.getDisc(new DiscSetting(set, partition, rank)).get(), en, jp);
            }
        }
    }

    private void addAttribute(RegistryObject<Attribute> attribute, String en, String jp, String descEn, String descJp) {
        addENJP(attribute.get().getDescriptionId(), en, jp);
        addENJP(attribute.get().getDescriptionId() + "desc", descEn, descJp);
    }
}
