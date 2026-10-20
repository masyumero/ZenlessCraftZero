package io.github.masyumero.zenlesscraftzero.common.tags;

import io.github.masyumero.zenlesscraftzero.common.ZCZTags;
import io.github.masyumero.zenlesscraftzero.common.registries.ZCZItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.concurrent.CompletableFuture;

public class CuriosItemTagsProvider extends TagsProvider<Item> {

    public CuriosItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, Registries.ITEM, lookupProvider, CuriosApi.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        ZCZItems.DISCS.forEach((setting, discItemRO) -> {
            tag(ZCZTags.Items.DISC_PARTITION_CURIOS_TAGS.get(setting.partition())).add(discItemRO.getKey());
        });
    }
}
