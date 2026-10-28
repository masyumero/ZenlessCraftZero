package io.github.masyumero.zenlesscraftzero.common.registries;

import io.github.masyumero.zenlesscraftzero.ZenlessCraftZero;
import io.github.masyumero.zenlesscraftzero.api.equip.disc.DiscPartition;
import io.github.masyumero.zenlesscraftzero.api.equip.disc.DiscRank;
import io.github.masyumero.zenlesscraftzero.common.equip.disc.sets.DiscSet;
import io.github.masyumero.zenlesscraftzero.api.equip.disc.DiscSetting;
import io.github.masyumero.zenlesscraftzero.common.item.DiscItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ZCZItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ZenlessCraftZero.MODID);
    public static final Map<DiscSetting, RegistryObject<Item>> DISCS = new HashMap<>();

    static {
        for (DiscSet set : DiscSet.values()) {
            for (DiscRank rank : DiscRank.values()) {
                for (DiscPartition partition : DiscPartition.values()) {
                    DiscSetting setting = new DiscSetting(set, partition, rank);
                    DISCS.put(setting, registerDisc(setting));
                }
            }
        }
    }

    public static final RegistryObject<Item> ICON = registerItem("icon", Item::new);
    public static final RegistryObject<Item> MOLDED_PLATING_AGENT = registerItem("molded_plating_agent", Item::new);
    public static final RegistryObject<Item> CRYSTALLIZED_PLATING_AGENT = registerItem("crystallized_plating_agent", Item::new);
    public static final RegistryObject<Item> ETHER_PLATING_AGENT = registerItem("ether_plating_agent", Item::new);

    private static RegistryObject<Item> registerDisc(DiscSetting setting) {
        return ITEMS.register(setting.set().getId() + "_" + setting.partition().getSlotNumber() + "_" + setting.rank().getId(), () -> new DiscItem(setting));
    }

    private static RegistryObject<Item> registerItem(String name, Function<Item.Properties, Item> item) {
        return ITEMS.register(name, () -> item.apply(new Item.Properties()));
    }

    public static RegistryObject<Item> getDisc(DiscSetting setting) {
        return DISCS.get(setting);
    }

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
