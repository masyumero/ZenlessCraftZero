package io.github.masyumero.zenlesscraftzero.api.equip.disc;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

public class DiscLevelStat {
    private static final Table<DiscRank, DiscStat, double[]> STAT = HashBasedTable.create();
    private static final Table<DiscRank, DiscStat, Double> SUB_STAT = HashBasedTable.create();

    public static void init() {}

    static {
        STAT.put(DiscRank.RANK_S, DiscStat.HP, new double[] {550, 660, 770, 880, 990, 1100, 1210, 1320, 1430, 1540, 1650, 1760, 1870, 1980, 2090, 2200});
        STAT.put(DiscRank.RANK_A, DiscStat.HP, new double[] {367, 458, 550, 642, 734, 825, 917, 1009, 1101, 1192, 1284, 1376, 1468});
        STAT.put(DiscRank.RANK_B, DiscStat.HP, new double[] {183, 243, 305, 366, 426, 488, 549, 609, 671, 732});
        putSubStat(DiscStat.HP, 39, 75, 112);
        putSubStat(DiscStat.ATK, 7, 13, 114);
        putSubStat(DiscStat.DEF, 5, 10, 15);
        putSubStat(DiscStat.HP_RATIO, 0.01, 0.02, 0.03);
        putSubStat(DiscStat.ATK_RATIO, 0.01, 0.02, 0.03);
        putSubStat(DiscStat.DEF_RATIO, 0.016, 0.032, 0.048);
        putSubStat(DiscStat.CRIT_CHANCE, 0.008, 0.016, 0.024);
        putSubStat(DiscStat.CRIT_DAMAGE, 0.016, 0.032, 0.048);
        //putSubStat(DiscStat.ANOMALY_PROFICIENCY, 3, 6, 9);
        putSubStat(DiscStat.PEN_RATIO, 3, 6, 9);
    }

    private static void putSubStat(DiscStat discStat, double... subStat) {
        if (subStat.length > 3) {
            return;
        }
        SUB_STAT.put(DiscRank.RANK_B, discStat, subStat[0]);
        SUB_STAT.put(DiscRank.RANK_A, discStat, subStat[1]);
        SUB_STAT.put(DiscRank.RANK_S, discStat, subStat[2]);
    }

    public static double[] getStat(DiscRank rank, DiscStat stat) {
        if (STAT.contains(rank, stat)) {
            return STAT.get(rank, stat);
        } else {
            return new double[0];
        }
    }

    public static double getSubStatBaseValue(DiscRank rank, DiscStat stat) {
        if (SUB_STAT.contains(rank, stat)) {
            return SUB_STAT.get(rank, stat);
        } else {
            return 0;
        }
    }
}
