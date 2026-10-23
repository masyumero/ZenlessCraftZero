package io.github.masyumero.zenlesscraftzero.common.capability;

import io.github.masyumero.zenlesscraftzero.common.capability.item.IEquipmentLevel;
import io.github.masyumero.zenlesscraftzero.common.capability.item.disc.IStats;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class ZCZCapabilities {

    public static final Capability<IEquipmentLevel> EQUIPMENT_LEVEL = CapabilityManager.get(new CapabilityToken<>() {});
    public static final Capability<IStats> STATS = CapabilityManager.get(new CapabilityToken<>() {});

}
