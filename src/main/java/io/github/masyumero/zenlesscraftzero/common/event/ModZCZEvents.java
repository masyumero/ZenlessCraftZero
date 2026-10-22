package io.github.masyumero.zenlesscraftzero.common.event;

import io.github.masyumero.zenlesscraftzero.ZenlessCraftZero;
import io.github.masyumero.zenlesscraftzero.api.equip.disc.DiscPartition;
import io.github.masyumero.zenlesscraftzero.api.equip.disc.DiscSet;
import io.github.masyumero.zenlesscraftzero.api.equip.disc.sets.IDiscBonuses;
import io.github.masyumero.zenlesscraftzero.common.item.DiscItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.IItemHandler;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

@Mod.EventBusSubscriber(modid = ZenlessCraftZero.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModZCZEvents {

    private static void dispatch(LivingEntity entity, BiConsumer<IDiscBonuses, ItemStack> action) {
        if (entity == null) {
            return;
        }

        List<DiscSet> acceptedDiscSets = new ArrayList<>();

        CuriosApi.getCuriosInventory(entity).ifPresent(inventory -> {
            for (DiscPartition partition : DiscPartition.values()) {
                inventory.getStacksHandler(partition.getId()).ifPresent(handler -> {
                    IItemHandler stacks = handler.getStacks();
                    for (int i = 0; i < stacks.getSlots(); i++) {
                        ItemStack stack = stacks.getStackInSlot(i);

                        if (stack.isEmpty()) {
                            continue;
                        }

                        if (stack.getItem() instanceof DiscItem discItem) {
                            DiscSet set = discItem.getDiscSet();
                            if (!acceptedDiscSets.contains(set)) {
                                acceptedDiscSets.add(set);
                                action.accept(set.getDiscBonuses(), stack);
                            }
                        }
                    }
                });
            }
        });
    }

    @SubscribeEvent
    public static void onAttackEntity(AttackEntityEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player player) {
            dispatch(player, (bonuses, stack) -> {
                if (!event.isCanceled()) {
                    bonuses.onAttackEntity(event, stack, player);
                }
            });
        }
    }
}
