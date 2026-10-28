package io.github.masyumero.zenlesscraftzero.api.equip.disc;

import io.github.masyumero.zenlesscraftzero.common.equip.disc.sets.DiscSet;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public record DiscSetting(DiscSet set, DiscPartition partition, DiscRank rank) {
}
