package io.github.masyumero.zenlesscraftzero.client;

import io.github.masyumero.zenlesscraftzero.ZenlessCraftZero;
import io.github.masyumero.zenlesscraftzero.client.render.item.DiscPartitionDecorator;
import io.github.masyumero.zenlesscraftzero.client.render.item.LevelDecorator;
import io.github.masyumero.zenlesscraftzero.client.render.item.RankDecorator;
import io.github.masyumero.zenlesscraftzero.common.registries.ZCZItems;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterItemDecorationsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = ZenlessCraftZero.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientRegistration {

    @SubscribeEvent
    public static void registerItemDecorations(RegisterItemDecorationsEvent event) {
        ZCZItems.DISCS.forEach((setting, itemRO) -> {
            DiscPartitionDecorator.registerDecorators(event, setting.partition(), itemRO.get());
            LevelDecorator.registerDecorators(event, itemRO.get());
        });

        for (RegistryObject<Item> itemRO : ZCZItems.ITEMS.getEntries()) {
            RankDecorator.registerDecorators(event, itemRO.get());
        }
    }
}
