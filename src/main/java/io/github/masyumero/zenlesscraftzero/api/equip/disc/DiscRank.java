package io.github.masyumero.zenlesscraftzero.api.equip.disc;

import io.github.masyumero.zenlesscraftzero.api.equip.Rank;
import lombok.Getter;

@Getter
public enum DiscRank {
    RANK_B("rank_b", Rank.RANK_B, 9, 8000, new int[] {240, 360, 600, 720, 840, 1080, 1200, 1320, 1640}),
    RANK_A("rank_a", Rank.RANK_A, 12, 24000, new int[] {400, 600, 1010, 1210, 1410, 1810, 2020, 2220, 2620, 3020, 3430, 4250}),
    RANK_S("rank_s", Rank.RANK_S, 15, 48000, new int[] {480, 720, 1200, 1440, 1680, 2160, 2400, 2640, 3120, 3600, 4080, 5040, 5760, 6480, 7200});

    private final String id;
    private final Rank rank;
    private final int maxLevel;
    private final int maxXp;
    private final int[] levelUpXp;

    DiscRank(String id, Rank rank, int maxLevel, int maxXp, int[] levelUpXp) {
        this.id = id;
        this.rank = rank;
        this.maxLevel = maxLevel;
        this.maxXp = maxXp;
        this.levelUpXp = levelUpXp;
    }

    public int getLevelUpXp(int level) {
        int xp = 0;
        if (level >= levelUpXp.length) {
            return -1;
        }
        for (int i = 0; i <= level; i++) {
            xp += levelUpXp[i];
        }
        return xp;
    }
}
