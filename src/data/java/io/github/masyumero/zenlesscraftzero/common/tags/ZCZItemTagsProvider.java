package io.github.masyumero.zenlesscraftzero.common.tags;

import io.github.masyumero.zenlesscraftzero.ZenlessCraftZero;
import io.github.masyumero.zenlesscraftzero.common.ZCZTags;
import io.github.masyumero.zenlesscraftzero.common.registries.ZCZItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ZCZItemTagsProvider extends TagsProvider<Item> {

    public ZCZItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, Registries.ITEM, lookupProvider, ZenlessCraftZero.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        ZCZItems.DISCS.forEach((setting, discItemRO) -> {
            tag(ZCZTags.Items.DISC_PARTITION_TAGS.get(setting.partition())).add(discItemRO.getKey());
        });
    }
}
