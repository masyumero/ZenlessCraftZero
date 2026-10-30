package io.github.masyumero.zenlesscraftzero.client.render.item;

import io.github.masyumero.zenlesscraftzero.api.equip.Rank;
import io.github.masyumero.zenlesscraftzero.api.item.IHasRank;
import io.github.masyumero.zenlesscraftzero.common.utils.ZCZUtil;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.client.event.RegisterItemDecorationsEvent;

public class RankDecorator extends AbstractItemDecorator {

    public static void registerDecorators(RegisterItemDecorationsEvent event, Rank rank, ItemLike... items) {
        for (ItemLike item : items) {
            event.register(item, new RankDecorator(rank));
        }
    }

    public static void registerDecorators(RegisterItemDecorationsEvent event, ItemLike... items) {
        for (ItemLike item : items) {
            if (item instanceof IHasRank rankItem){
                event.register(item, new RankDecorator(rankItem.getRank()));
            }
        }
    }

    private RankDecorator(Rank rank) {
        super(ZCZUtil.rl("gui/icons/" + rank.getId() + ".png"));
    }
}
