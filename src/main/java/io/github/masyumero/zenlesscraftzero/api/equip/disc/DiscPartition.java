package io.github.masyumero.zenlesscraftzero.api.equip.disc;

import lombok.Getter;

@Getter
public enum DiscPartition {
    PARTITION_1("partition_1"),
    PARTITION_2("partition_2"),
    PARTITION_3("partition_3"),
    PARTITION_4("partition_4"),
    PARTITION_5("partition_5"),
    PARTITION_6("partition_6");

    private final String id;

    DiscPartition(String id) {
        this.id = id;
    }

    public int getSlotNumber() {
        return this.ordinal() + 1;
    }
}
