package io.github.masyumero.zenlesscraftzero.client.model.item;

import io.github.masyumero.zenlesscraftzero.ZenlessCraftZero;
import io.github.masyumero.zenlesscraftzero.common.registries.ZCZItems;
import io.github.masyumero.zenlesscraftzero.common.utils.ZCZUtil;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ZCZItemModelProvider extends ItemModelProvider {

    public ZCZItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ZenlessCraftZero.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        ZCZItems.DISCS.forEach((setting, itemRO) -> {
            getBuilder(itemRO.getId().getPath()).parent(new ModelFile.UncheckedModelFile("item/generated")).texture("layer0", ZCZUtil.rl("item/" + setting.set().getId()));
        });

        basicItem(ZCZItems.ICON.get());
    }
}
