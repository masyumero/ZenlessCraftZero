package io.github.masyumero.zenlesscraftzero.api.equip.disc.sets;

import io.github.masyumero.zenlesscraftzero.common.item.DiscItem;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

import java.util.Map;

public abstract class DiscBonuses implements IDiscBonuses {

    private int discCount;
    private ItemStack discStack;

    public void incrementDiscCount() {
        discCount = Mth.clamp(discCount + 1, 0, 6);
    }

    public void decrementDiscCount() {
        discCount = Mth.clamp(discCount - 1, 0, 6);
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

        var twoPieceModifiers = getTwoPieceModifiers(slotContext, stack, "two_piece_modifier");
        var fourPieceModifiers = getFourPieceModifiers(slotContext, stack, "four_piece_modifier");

        if (discCount >= 2) {
            twoPieceBonuses(slotContext, stack);
            addAttribute(twoPieceModifiers);
        } else {
            removeAttribute(twoPieceModifiers);
        }
        if (discCount >= 4) {
            fourPieceBonuses(slotContext, stack);
            addAttribute(fourPieceModifiers);
        } else {
            removeAttribute(fourPieceModifiers);
        }
    }

    private void addAttribute(Map<AttributeInstance, AttributeModifier> modifiers) {
        if (!modifiers.isEmpty()) {
            modifiers.forEach((instance, modifier) -> {
                if (instance.getModifier(modifier.getId()) == null) {
                    instance.addTransientModifier(modifier);
                }
            });
        }
    }

    private void removeAttribute(Map<AttributeInstance, AttributeModifier> modifiers) {
        if (!modifiers.isEmpty()) {
            modifiers.forEach((instance, modifier) -> {
                if (instance.getModifier(modifier.getId()) != null) {
                    instance.removeModifier(modifier);
                }
            });
        }
    }
}
