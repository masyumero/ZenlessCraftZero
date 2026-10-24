package io.github.masyumero.zenlesscraftzero.api.equip.disc;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

public class DiscLevelStat {
    private static final Table<DiscRank, DiscStat, double[]> STAT = HashBasedTable.create();
    private static final Table<DiscRank, DiscStat, Double> SUB_STAT = HashBasedTable.create();

    public static void init() {
    }

    static {
        STAT.put(DiscRank.RANK_S, DiscStat.HP, new double[]{550, 660, 770, 880, 990, 1100, 1210, 1320, 1430, 1540, 1650, 1760, 1870, 1980, 2090, 2200});
        STAT.put(DiscRank.RANK_A, DiscStat.HP, new double[]{367, 458, 550, 642, 734, 825, 917, 1009, 1101, 1192, 1284, 1376, 1468});
        STAT.put(DiscRank.RANK_B, DiscStat.HP, new double[]{183, 243, 305, 366, 426, 488, 549, 609, 671, 732});
        STAT.put(DiscRank.RANK_S, DiscStat.ATK, new double[]{79, 94, 110, 126, 142, 158, 173, 189, 205, 221, 237, 252, 268, 284, 300, 316});
        STAT.put(DiscRank.RANK_A, DiscStat.ATK, new double[]{53, 66, 79, 92, 106, 119, 132, 145, 159, 172, 185, 198, 212});
        STAT.put(DiscRank.RANK_B, DiscStat.ATK, new double[]{26, 34, 43, 52, 60, 69, 78, 86, 95, 104});
        STAT.put(DiscRank.RANK_S, DiscStat.DEF, new double[]{46, 55, 64, 73, 82, 92, 101, 110, 119, 128, 138, 147, 156, 165, 174, 184});
        STAT.put(DiscRank.RANK_A, DiscStat.DEF, new double[]{31, 38, 46, 54, 62, 69, 77, 85, 93, 100, 108, 116, 124});
        STAT.put(DiscRank.RANK_B, DiscStat.DEF, new double[]{15, 19, 25, 30, 34, 40, 45, 49, 55, 60});
        STAT.put(DiscRank.RANK_S, DiscStat.HP_RATIO, new double[]{0.075, 0.09, 0.105, 0.12, 0.135, 0.15, 0.165, 0.18, 0.195, 0.21, 0.225, 0.24, 0.255, 0.27, 0.285, 0.30});
        STAT.put(DiscRank.RANK_A, DiscStat.HP_RATIO, new double[]{0.05, 0.0625, 0.075, 0.0875, 0.10, 0.1125, 0.125, 0.1375, 0.15, 0.1625, 0.175, 0.1875, 0.20});
        STAT.put(DiscRank.RANK_B, DiscStat.HP_RATIO, new double[]{0.025, 0.033, 0.042, 0.05, 0.058, 0.067, 0.075, 0.083, 0.092, 0.10});
        STAT.put(DiscRank.RANK_S, DiscStat.DEF_RATIO, new double[]{0.075, 0.09, 0.105, 0.12, 0.135, 0.15, 0.165, 0.18, 0.195, 0.21, 0.225, 0.24, 0.255, 0.27, 0.285, 0.30});
        STAT.put(DiscRank.RANK_A, DiscStat.DEF_RATIO, new double[]{0.05, 0.0625, 0.075, 0.0875, 0.10, 0.1125, 0.125, 0.1375, 0.15, 0.1625, 0.175, 0.1875, 0.20});
        STAT.put(DiscRank.RANK_B, DiscStat.DEF_RATIO, new double[]{0.025, 0.033, 0.042, 0.05, 0.058, 0.067, 0.075, 0.083, 0.092, 0.10});
        STAT.put(DiscRank.RANK_S, DiscStat.DEF_RATIO, new double[]{0.12, 0.144, 0.168, 0.192, 0.216, 0.24, 0.264, 0.288, 0.312, 0.336, 0.36, 0.384, 0.408, 0.432, 0.456, 0.48});
        STAT.put(DiscRank.RANK_A, DiscStat.DEF_RATIO, new double[]{0.08, 0.10, 0.12, 0.14, 0.16, 0.18, 0.20, 0.22, 0.24, 0.26, 0.28, 0.30, 0.32});
        STAT.put(DiscRank.RANK_B, DiscStat.DEF_RATIO, new double[]{0.04, 0.053, 0.067, 0.08, 0.093, 0.107, 0.12, 0.133, 0.147, 0.16});
        STAT.put(DiscRank.RANK_S, DiscStat.CRIT_CHANCE, new double[]{0.06, 0.072, 0.084, 0.096, 0.108, 0.12, 0.132, 0.144, 0.156, 0.168, 0.18, 0.192, 0.204, 0.216, 0.228, 0.24});
        STAT.put(DiscRank.RANK_A, DiscStat.CRIT_CHANCE, new double[]{0.04, 0.05, 0.06, 0.07, 0.08, 0.09, 0.10, 0.11, 0.12, 0.13, 0.14, 0.15, 0.16});
        STAT.put(DiscRank.RANK_B, DiscStat.CRIT_CHANCE, new double[]{0.02, 0.027, 0.033, 0.04, 0.047, 0.053, 0.06, 0.067, 0.073, 0.08});
        STAT.put(DiscRank.RANK_S, DiscStat.CRIT_DAMAGE, new double[]{0.12, 0.144, 0.168, 0.192, 0.216, 0.24, 0.264, 0.288, 0.312, 0.336, 0.36, 0.384, 0.408, 0.432, 0.456, 0.48});
        STAT.put(DiscRank.RANK_A, DiscStat.CRIT_DAMAGE, new double[]{0.08, 0.10, 0.12, 0.14, 0.16, 0.18, 0.20, 0.22, 0.24, 0.26, 0.28, 0.30, 0.32});
        STAT.put(DiscRank.RANK_B, DiscStat.CRIT_DAMAGE, new double[]{0.04, 0.053, 0.067, 0.08, 0.093, 0.107, 0.12, 0.133, 0.147, 0.16});
//        STAT.put(DiscRank.RANK_S, DiscStat.ANOMALY_PROFICIENCY, new double[]{0.075, 0.09, 0.105, 0.12, 0.135, 0.15, 0.165, 0.18, 0.195, 0.21, 0.225, 0.24, 0.255, 0.27, 0.285, 0.30});
//        STAT.put(DiscRank.RANK_A, DiscStat.ANOMALY_PROFICIENCY, new double[]{0.05, 0.0625, 0.075, 0.0875, 0.10, 0.1125, 0.125, 0.1375, 0.15, 0.1625, 0.175, 0.1875, 0.20});
//        STAT.put(DiscRank.RANK_B, DiscStat.ANOMALY_PROFICIENCY, new double[]{0.025, 0.033, 0.042, 0.05, 0.058, 0.067, 0.075, 0.083, 0.092, 0.10});
        STAT.put(DiscRank.RANK_S, DiscStat.PEN_RATIO, new double[]{0.06, 0.072, 0.084, 0.096, 0.108, 0.12, 0.132, 0.144, 0.156, 0.168, 0.18, 0.192, 0.204, 0.216, 0.228, 0.24});
        STAT.put(DiscRank.RANK_A, DiscStat.PEN_RATIO, new double[]{0.04, 0.05, 0.06, 0.07, 0.08, 0.09, 0.10, 0.11, 0.12, 0.13, 0.14, 0.15, 0.16});
        STAT.put(DiscRank.RANK_B, DiscStat.PEN_RATIO, new double[]{0.02, 0.027, 0.033, 0.04, 0.047, 0.053, 0.06, 0.067, 0.073, 0.08});
//        STAT.put(DiscRank.RANK_S, DiscStat.ATTRIBUTE_DMG_BONUS, new double[]{0.075, 0.09, 0.105, 0.12, 0.135, 0.15, 0.165, 0.18, 0.195, 0.21, 0.225, 0.24, 0.255, 0.27, 0.285, 0.30});
//        STAT.put(DiscRank.RANK_A, DiscStat.ATTRIBUTE_DMG_BONUS, new double[]{0.05, 0.0625, 0.075, 0.0875, 0.10, 0.1125, 0.125, 0.1375, 0.15, 0.1625, 0.175, 0.1875, 0.20});
//        STAT.put(DiscRank.RANK_B, DiscStat.ATTRIBUTE_DMG_BONUS, new double[]{0.025, 0.033, 0.042, 0.05, 0.058, 0.067, 0.075, 0.083, 0.092, 0.10});
//        STAT.put(DiscRank.RANK_S, DiscStat.ANOMALY_MASTERY_RATIO, new double[]{23, 27, 32, 36, 41, 46, 50, 55, 59, 64, 69, 73, 78, 82, 87, 92});
//        STAT.put(DiscRank.RANK_A, DiscStat.ANOMALY_MASTERY_RATIO, new double[]{15, 18, 22, 26, 30, 33, 37, 41, 45, 48, 52, 56, 60});
//        STAT.put(DiscRank.RANK_B, DiscStat.ANOMALY_MASTERY_RATIO, new double[]{8, 10, 13, 16, 18, 21, 24, 26, 29, 32});
//        STAT.put(DiscRank.RANK_S, DiscStat.IMPACT, new double[]{0.045, 0.054, 0.063, 0.072, 0.081, 0.09, 0.099, 0.108, 0.117, 0.126, 0.135, 0.144, 0.153, 0.162, 0.171, 0.18});
//        STAT.put(DiscRank.RANK_A, DiscStat.IMPACT, new double[]{0.03, 0.0375, 0.045, 0.0525, 0.06, 0.0675, 0.075, 0.0825, 0.09, 0.0975, 0.105, 0.1125, 0.12});
//        STAT.put(DiscRank.RANK_B, DiscStat.IMPACT, new double[]{0.015, 0.02, 0.025, 0.03, 0.035, 0.04, 0.045, 0.05, 0.055, 0.06});
//        STAT.put(DiscRank.RANK_S, DiscStat.ENERGY_REGEN, new double[]{0.15, 0.18, 0.21, 0.24, 0.27, 0.30, 0.33, 0.36, 0.39, 0.42, 0.45, 0.48, 0.51, 0.54, 0.57, 0.60});
//        STAT.put(DiscRank.RANK_A, DiscStat.ENERGY_REGEN, new double[]{0.10, 0.125, 0.15, 0.175, 0.20, 0.225, 0.25, 0.275, 0.30, 0.325, 0.35, 0.375, 0.40});
//        STAT.put(DiscRank.RANK_B, DiscStat.ENERGY_REGEN, new double[]{0.05, 0.067, 0.083, 0.10, 0.117, 0.133, 0.15, 0.167, 0.183, 0.20});
        putSubStat(DiscStat.HP, 39, 75, 112);
        putSubStat(DiscStat.ATK, 7, 13, 114);
        putSubStat(DiscStat.DEF, 5, 10, 15);
        putSubStat(DiscStat.HP_RATIO, 0.01, 0.02, 0.03);
        putSubStat(DiscStat.ATK_RATIO, 0.01, 0.02, 0.03);
        putSubStat(DiscStat.DEF_RATIO, 0.016, 0.032, 0.048);
        putSubStat(DiscStat.CRIT_CHANCE, 0.008, 0.016, 0.024);
        putSubStat(DiscStat.CRIT_DAMAGE, 0.016, 0.032, 0.048);
        //putSubStat(DiscStat.ANOMALY_PROFICIENCY, 3, 6, 9);
        putSubStat(DiscStat.PEN, 3, 6, 9);
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
