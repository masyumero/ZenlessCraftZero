package io.github.masyumero.zenlesscraftzero;

import io.github.masyumero.zenlesscraftzero.client.model.item.ZCZItemModelProvider;
import io.github.masyumero.zenlesscraftzero.common.curios.ZCZCuriosProvider;
import io.github.masyumero.zenlesscraftzero.common.tags.CuriosItemTagsProvider;
import io.github.masyumero.zenlesscraftzero.common.tags.ZCZItemTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = ZenlessCraftZero.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ZenlessCraftZeroDatagen {

    @SubscribeEvent
    public static void gatherEvent(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeClient(), new ZCZItemModelProvider(output, existingFileHelper));

        generator.addProvider(event.includeServer(), new ZCZCuriosProvider(output, existingFileHelper, lookupProvider));
        generator.addProvider(event.includeServer(), new ZCZItemTagsProvider(output, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new CuriosItemTagsProvider(output, lookupProvider, existingFileHelper));
    }
}
