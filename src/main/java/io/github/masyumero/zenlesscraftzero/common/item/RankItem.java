package io.github.masyumero.zenlesscraftzero.common.item;

import io.github.masyumero.zenlesscraftzero.api.equip.Rank;
import io.github.masyumero.zenlesscraftzero.api.item.IHasRank;
import lombok.Getter;
import net.minecraft.world.item.Item;

@Getter
public class RankItem extends Item implements IHasRank {

    private final Rank rank;

    public RankItem(Rank rank) {
        super(new Properties());
        this.rank = rank;
    }

}
