package io.github.masyumero.zenlesscraftzero.api.equip.disc.sets;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import io.github.masyumero.zenlesscraftzero.api.equip.disc.DiscSet;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

public interface IDiscBonuses {

   DiscSet getSets();

   void discTick(SlotContext slotContext, ItemStack stack);

   void bonuses(SlotContext slotContext, ItemStack stack);

   void twoPieceBonuses(SlotContext slotContext, ItemStack stack);

   void fourPieceBonuses(SlotContext slotContext, ItemStack stack);

   Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack);

   default Multimap<Attribute, AttributeModifier> getTwoPieceAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
      return HashMultimap.create();
   }

   default Multimap<Attribute, AttributeModifier> getFourPieceAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
      return HashMultimap.create();
   }
}
