package io.github.masyumero.zenlesscraftzero.client;

import io.github.masyumero.zenlesscraftzero.ZenlessCraftZero;
import io.github.masyumero.zenlesscraftzero.api.equip.Rank;
import io.github.masyumero.zenlesscraftzero.client.render.item.DiscPartitionDecorator;
import io.github.masyumero.zenlesscraftzero.client.render.item.LevelDecorator;
import io.github.masyumero.zenlesscraftzero.client.render.item.RankDecorator;
import io.github.masyumero.zenlesscraftzero.common.registries.ZCZItems;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterItemDecorationsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ZenlessCraftZero.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientRegistration {

    @SubscribeEvent
    public static void registerItemDecorations(RegisterItemDecorationsEvent event) {
        ZCZItems.DISCS.forEach((setting, itemRO) -> {
            RankDecorator.registerDecorators(event, setting.rank().getRank(), itemRO.get());
            DiscPartitionDecorator.registerDecorators(event, setting.partition(), itemRO.get());
            LevelDecorator.registerDecorators(event, itemRO.get());
        });

        RankDecorator.registerDecorators(event, Rank.RANK_C, ZCZItems.MOLDED_PLATING_AGENT.get());
        RankDecorator.registerDecorators(event, Rank.RANK_B, ZCZItems.CRYSTALLIZED_PLATING_AGENT.get());
        RankDecorator.registerDecorators(event, Rank.RANK_A, ZCZItems.ETHER_PLATING_AGENT.get());
    }
}
