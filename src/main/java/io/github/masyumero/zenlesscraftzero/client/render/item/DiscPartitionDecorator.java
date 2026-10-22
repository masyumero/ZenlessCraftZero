package io.github.masyumero.zenlesscraftzero.client.render.item;

import io.github.masyumero.zenlesscraftzero.api.equip.disc.DiscPartition;
import io.github.masyumero.zenlesscraftzero.common.utils.ZCZUtil;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.client.event.RegisterItemDecorationsEvent;

public class DiscPartitionDecorator extends AbstractItemDecorator {

    public static void registerDecorators(RegisterItemDecorationsEvent event, DiscPartition partition, ItemLike... items) {
        for (ItemLike item : items) {
            event.register(item, new DiscPartitionDecorator(partition));
        }
    }

    private DiscPartitionDecorator(DiscPartition partition) {
        super(ZCZUtil.rl("gui/icons/" + partition.getId() + ".png"));
    }
}
