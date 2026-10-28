package io.github.masyumero.zenlesscraftzero.common.equip.disc.sets;

import com.google.common.collect.Multimap;
import io.github.masyumero.zenlesscraftzero.api.equip.disc.DiscPartition;
import io.github.masyumero.zenlesscraftzero.api.equip.disc.sets.IDiscBonuses;
import io.github.masyumero.zenlesscraftzero.common.item.DiscItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class DiscBonuses implements IDiscBonuses {

    private ItemStack discStack;

    public int countDisc(LivingEntity entity) {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        CuriosApi.getCuriosInventory(entity).ifPresent(inventory -> {
            for (DiscPartition partition : DiscPartition.values()) {
                inventory.getStacksHandler(partition.getId()).ifPresent(handler -> {
                    IItemHandler stacks = handler.getStacks();
                    for (int i = 0; i < stacks.getSlots(); i++) {
                        ItemStack stack = stacks.getStackInSlot(i);
                        if (stack.isEmpty()) {
                            continue;
                        }

                        if (stack.getItem() instanceof DiscItem) {
                            atomicInteger.getAndIncrement();
                        }
                    }
                });
            }
        });
        return atomicInteger.get();
    }

    public void discTick(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity().level().isClientSide()) {
            return;
        }
        if (!(discStack == null) && !discStack.isEmpty()) {
            if (discStack.getItem() instanceof DiscItem disc) {
                disc.bonuses(slotContext, stack);
            }
        } else {
            discStack = stack;
        }
    }

    @Override
    public void bonuses(SlotContext slotContext, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        if (entity.level().isClientSide()) {
            return;
        }

        var twoPieceModifiers = getTwoPieceModifiers(entity, stack, "two_piece_modifier");
        var fourPieceModifiers = getFourPieceModifiers(entity, stack, "four_piece_modifier");

        if (countDisc(entity) >= 2) {
            twoPieceBonuses(slotContext, stack);
            addAttribute(twoPieceModifiers);
        } else {
            removeAttribute(twoPieceModifiers);
        }
        if (countDisc(entity) >= 4) {
            fourPieceBonuses(slotContext, stack);
            addAttribute(fourPieceModifiers);
        } else {
            removeAttribute(fourPieceModifiers);
        }
    }

    private void addAttribute(Multimap<AttributeInstance, AttributeModifier> modifiers) {
        if (!modifiers.isEmpty()) {
            modifiers.forEach((instance, modifier) -> {
                if (instance == null) {
                    return;
                }

                var current = instance.getModifier(modifier.getId());
                if (current == null || current.getAmount() != modifier.getAmount()) {
                    if (current != null) {
                        instance.removeModifier(modifier);
                    }
                    instance.addTransientModifier(modifier);
                }
            });
        }
    }

    private void removeAttribute(Multimap<AttributeInstance, AttributeModifier> modifiers) {
        if (!modifiers.isEmpty()) {
            modifiers.forEach((instance, modifier) -> {
                if (instance != null && instance.getModifier(modifier.getId()) != null) {
                    instance.removeModifier(modifier);
                }
            });
        }
    }
}
