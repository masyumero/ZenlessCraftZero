package io.github.masyumero.zenlesscraftzero.api.equip.disc.sets;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import io.github.masyumero.zenlesscraftzero.common.item.DiscItem;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

public abstract class DiscBonuses implements IDiscBonuses {

    private int discCount;
    private ItemStack discStack;

    public void incrementDiscCount(ItemStack stack) {
        discCount = Mth.clamp(discCount + 1, 0, 6);
        discStack = stack;
    }

    public void decrementDiscCount(ItemStack stack) {
        discCount = Mth.clamp(discCount - 1, 0, 6);
        discStack = stack;
    }

    public void discTick(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity().level().isClientSide()) {
            return;
        }
        if (!(discStack == null) && !discStack.isEmpty()) {
            if (discStack.getItem() instanceof DiscItem disc) {
                disc.bonuses(slotContext, stack);
            }
        }
    }

    @Override
    public void bonuses(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity().level().isClientSide()) {
            return;
        }
        if (discCount >= 2) {
            twoPieceBonuses(slotContext, stack);
        }
        if (discCount >= 4) {
            fourPieceBonuses(slotContext, stack);
        }
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        return HashMultimap.create();
    }
}
