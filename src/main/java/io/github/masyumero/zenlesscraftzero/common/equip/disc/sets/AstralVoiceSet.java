package io.github.masyumero.zenlesscraftzero.common.equip.disc.sets;

import com.google.common.collect.Multimap;
import io.github.masyumero.zenlesscraftzero.api.equip.disc.DiscSet;
import io.github.masyumero.zenlesscraftzero.api.equip.disc.sets.DiscBonuses;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.UUID;

public class AstralVoiceSet extends DiscBonuses {

    private static final UUID TWO_PIECE_ATTRIBUTE_UUID = UUID.randomUUID();
    private static final UUID FOUR_PIECE_ATTRIBUTE_UUID = UUID.randomUUID();
    private int astral = 0;
    private int astralDuration = 0;
    private final double damageMultiplier = 0.08;

    @Override
    public DiscSet getSets() {
        return DiscSet.ASTRAL_VOICE;
    }

    @Override
    public void bonuses(SlotContext slotContext, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        if (entity.level().isClientSide()) {
            return;
        }

        if (entity.tickCount % 20 == 0) {
            astralDuration = Math.max(astralDuration - 1, 0);
        }
        if (astral > 0 && astralDuration == 0) {
            astral = 0;
        }

        super.bonuses(slotContext, stack);
    }

    @Override
    public Multimap<AttributeInstance, AttributeModifier> getTwoPieceModifiers(LivingEntity entity, ItemStack stack, String name) {
        Multimap<AttributeInstance, AttributeModifier>  modifier = super.getTwoPieceModifiers(entity, stack, name);
        AttributeInstance attribute = entity.getAttribute(Attributes.ATTACK_DAMAGE);
        modifier.put(attribute, new AttributeModifier(
                TWO_PIECE_ATTRIBUTE_UUID,
                name,
                0.1,
                AttributeModifier.Operation.MULTIPLY_BASE
        ));
        return modifier;
    }

    @Override
    public Multimap<AttributeInstance, AttributeModifier> getFourPieceModifiers(LivingEntity entity, ItemStack stack, String name) {
        Multimap<AttributeInstance, AttributeModifier>  modifier = super.getFourPieceModifiers(entity, stack, name);
        AABB box = entity.getBoundingBox().inflate(10.0);
        List<LivingEntity> entities = entity.level().getEntitiesOfClass(LivingEntity.class, box);
        entities.forEach(livingEntity -> {
                AttributeInstance attribute = livingEntity.getAttribute(Attributes.ATTACK_DAMAGE);
                modifier.put(attribute, new AttributeModifier(
                        FOUR_PIECE_ATTRIBUTE_UUID,
                        name,
                        damageMultiplier * astral,
                        AttributeModifier.Operation.MULTIPLY_BASE
                ));
            });
        return modifier;
    }

    @Override
    public void onFourPieceAttackEntity(AttackEntityEvent event, ItemStack stack, Player player) {
        if (player.level().isClientSide()) {
            return;
        }

        astral = Mth.clamp(astral + 1, 0, 3);
        astralDuration = Mth.clamp(astralDuration + 15, 0, 15);
    }
}
