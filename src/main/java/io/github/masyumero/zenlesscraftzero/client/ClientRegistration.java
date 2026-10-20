package io.github.masyumero.zenlesscraftzero.client;

import io.github.masyumero.zenlesscraftzero.ZenlessCraftZero;
import io.github.masyumero.zenlesscraftzero.client.render.item.DiscRankDecorator;
import io.github.masyumero.zenlesscraftzero.common.registries.ZCZItems;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterItemDecorationsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ZenlessCraftZero.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientRegistration {

    @SubscribeEvent
    public static void registerItemDecorations(RegisterItemDecorationsEvent event) {
        ZCZItems.DISCS.forEach((setting, itemRO) -> DiscRankDecorator.registerDecorators(event, setting.rank(), itemRO.get()));
    }
}
