package io.github.masyumero.zenlesscraftzero.api.equip.disc.sets;

import io.github.masyumero.zenlesscraftzero.api.equip.disc.DiscSet;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

import java.util.*;

public interface IDiscBonuses {

   DiscSet getSets();

   void discTick(SlotContext slotContext, ItemStack stack);

   default void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {}

   default void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {}

   void bonuses(SlotContext slotContext, ItemStack stack);

   void twoPieceBonuses(SlotContext slotContext, ItemStack stack);

   void fourPieceBonuses(SlotContext slotContext, ItemStack stack);

   default Map<AttributeInstance, AttributeModifier> getTwoPieceModifiers(SlotContext slotContext, ItemStack stack, String name) {
      return new HashMap<>();
   }

   default Map<AttributeInstance, AttributeModifier> getFourPieceModifiers(SlotContext slotContext, ItemStack stack, String name) {
      return new HashMap<>();
   }
}
