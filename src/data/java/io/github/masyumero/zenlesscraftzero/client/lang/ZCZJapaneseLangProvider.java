package io.github.masyumero.zenlesscraftzero.client.lang;

import net.minecraft.data.PackOutput;

public class ZCZJapaneseLangProvider extends BaseLangProvider {
    public ZCZJapaneseLangProvider(PackOutput output) {
        super(output, "ja_jp");
    }

    @Override
    protected void addTranslations() {
        ENJP.forEach((key, enjp) -> this.add(key, enjp.jp()));
    }
}
