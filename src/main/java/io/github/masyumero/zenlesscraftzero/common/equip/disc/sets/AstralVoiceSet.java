package io.github.masyumero.zenlesscraftzero.common.equip.disc.sets;

import io.github.masyumero.zenlesscraftzero.ZenlessCraftZero;
import io.github.masyumero.zenlesscraftzero.api.equip.disc.DiscSet;
import io.github.masyumero.zenlesscraftzero.api.equip.disc.sets.DiscBonuses;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

import java.util.Map;
import java.util.UUID;

public class AstralVoiceSet extends DiscBonuses {

    private static final UUID TWO_PIECE_ATTRIBUTE_UUID = UUID.randomUUID();
    private static final UUID FOUR_PIECE_ATTRIBUTE_UUID = UUID.randomUUID();

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
    public Map<AttributeInstance, AttributeModifier> getTwoPieceModifiers(SlotContext slotContext, ItemStack stack, String name) {
        Map<AttributeInstance, AttributeModifier> modifier = super.getTwoPieceModifiers(slotContext, stack, name);
        LivingEntity entity = slotContext.entity();
        AttributeInstance attribute = entity.getAttribute(Attributes.ATTACK_DAMAGE);
        modifier.put(attribute, new AttributeModifier(
                TWO_PIECE_ATTRIBUTE_UUID,
                name,
                0.1,
                AttributeModifier.Operation.MULTIPLY_BASE
        ));
        return modifier;
    }
}
