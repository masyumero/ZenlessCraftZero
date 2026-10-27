package io.github.masyumero.zenlesscraftzero.common.damagetype;

import io.github.masyumero.zenlesscraftzero.ZenlessCraftZero;
import io.github.masyumero.zenlesscraftzero.common.registries.ZCZDamageTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.tags.DamageTypeTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ZCZDamageTypeTagsProvider extends DamageTypeTagsProvider {

    public ZCZDamageTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, ZenlessCraftZero.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(DamageTypeTags.BYPASSES_INVULNERABILITY).addOptional(ZCZDamageTypes.ATTRIBUTE_EFFECT.location());
        tag(DamageTypeTags.BYPASSES_COOLDOWN).addOptional(ZCZDamageTypes.ATTRIBUTE_EFFECT.location());
    }
}
