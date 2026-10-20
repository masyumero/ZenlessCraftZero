package io.github.masyumero.zenlesscraftzero.common.equip.disc.sets;

import com.google.common.collect.Multimap;
import io.github.masyumero.zenlesscraftzero.ZenlessCraftZero;
import io.github.masyumero.zenlesscraftzero.api.equip.disc.DiscSet;
import io.github.masyumero.zenlesscraftzero.api.equip.disc.sets.DiscBonuses;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

public class AstralVoiceSet extends DiscBonuses {

    @Override
    public DiscSet getSets() {
        return DiscSet.ASTRAL_VOICE;
    }

    @Override
    public void twoPieceBonuses(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity().tickCount % 20 == 0) {
            ZenlessCraftZero.LOGGER.info("TwoPiece: SlotContext: {} Stack: {}", slotContext, stack);
        }
    }

    @Override
    public void fourPieceBonuses(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity().tickCount % 20 == 0) {
            ZenlessCraftZero.LOGGER.info("FourPiece: SlotContext: {} Stack: {}", slotContext, stack);
        }
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getTwoPieceAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        var modifiers = super.getTwoPieceAttributeModifiers(slotContext, uuid, stack);

        modifiers.put(Attributes.ATTACK_DAMAGE,
                new AttributeModifier(
                        uuid,
                        "two_piece_bonuses",
                        10.0,
                        AttributeModifier.Operation.MULTIPLY_BASE
                ));
        return modifiers;
    }
}
