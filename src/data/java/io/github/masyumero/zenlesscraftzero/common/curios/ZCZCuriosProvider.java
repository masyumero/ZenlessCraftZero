package io.github.masyumero.zenlesscraftzero.common.curios;

import io.github.masyumero.zenlesscraftzero.ZenlessCraftZero;
import io.github.masyumero.zenlesscraftzero.api.equip.disc.DiscPartition;
import io.github.masyumero.zenlesscraftzero.common.utils.ZCZUtil;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import top.theillusivec4.curios.api.CuriosDataProvider;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.concurrent.CompletableFuture;

public class ZCZCuriosProvider extends CuriosDataProvider {

    public ZCZCuriosProvider(PackOutput output, ExistingFileHelper fileHelper, CompletableFuture<HolderLookup.Provider> registries) {
        super(ZenlessCraftZero.MODID, output, fileHelper, registries);
    }

    @Override
    public void generate(HolderLookup.Provider provider, ExistingFileHelper existingFileHelper) {
        for (DiscPartition slot : DiscPartition.values()) {
            createSlot(slot.getId())
                    .size(1)
                    .addCosmetic(false)
                    .dropRule(ICurio.DropRule.ALWAYS_DROP)
                    //.icon(ZCZUtil.rl("slot/" + slot.getId()))
                    .addValidator(ZCZUtil.curios("tag"));

            createEntities("players")
                    .addPlayer()
                    .addSlots(slot.getId());
        }
    }
}
