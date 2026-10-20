package io.github.masyumero.zenlesscraftzero.api.equip.disc;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public record DiscSetting(DiscSet set, DiscPartition partition, DiscRank rank) {
}
