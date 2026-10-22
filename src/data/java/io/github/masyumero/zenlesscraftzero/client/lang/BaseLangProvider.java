package io.github.masyumero.zenlesscraftzero.client.lang;

import io.github.masyumero.zenlesscraftzero.ZenlessCraftZero;
import io.github.masyumero.zenlesscraftzero.api.lang.ILangEntry;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseLangProvider extends LanguageProvider {

    protected static final Map<String, ENJP> ENJP = new HashMap<>();

    public BaseLangProvider(PackOutput output, String locale) {
        super(output, ZenlessCraftZero.MODID, locale);
    }

    public void addENJP(ILangEntry key, String en, String jp) {
        addENJP(key.getTranslationKey(), en, jp);
    }

    public void addENJP(ItemLike key, String en, String jp) {
        addENJP(key.asItem().getDescriptionId(), en, jp);
    }

    public void addENJP(String key, String en, String jp) {
        addENJP(key, new ENJP(en, jp));
    }

    public void addCuriosSlot(String key, String en, String jp) {
        addENJP("curios.identifier." + key, en, jp);
    }

    public void addENJP(String key, ENJP enjp) {
        ENJP.put(key, enjp);
    }

    public record ENJP(String en, String jp) {}
}