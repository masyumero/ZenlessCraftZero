package io.github.masyumero.zenlesscraftzero.common.equip.disc.sets;

import com.google.common.collect.Multimap;
import io.github.masyumero.zenlesscraftzero.api.equip.disc.DiscSet;
import io.github.masyumero.zenlesscraftzero.api.equip.disc.sets.DiscBonuses;
import io.github.masyumero.zenlesscraftzero.common.registries.ZCZAttributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

public class FreedomBluesSet extends DiscBonuses {

    private static final UUID TWO_PIECE_ATTRIBUTE_UUID = UUID.randomUUID();
    private static final UUID FOUR_PIECE_ATTRIBUTE_UUID = UUID.randomUUID();

    @Override
    public DiscSet getSets() {
        return DiscSet.FREEDOM_BLUES;
    }

    @Override
    public Multimap<AttributeInstance, AttributeModifier> getTwoPieceModifiers(LivingEntity entity, ItemStack stack, String name) {
        Multimap<AttributeInstance, AttributeModifier> modifier = super.getTwoPieceModifiers(entity, stack, name);
        AttributeInstance attribute = entity.getAttribute(ZCZAttributes.ANOMALY_PROFICIENCY.get());
        modifier.put(attribute, new AttributeModifier(
                TWO_PIECE_ATTRIBUTE_UUID,
                name,
                30,
                AttributeModifier.Operation.ADDITION
        ));
        return modifier;
    }
}
