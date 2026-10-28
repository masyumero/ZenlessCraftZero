package io.github.masyumero.zenlesscraftzero.api.equip.disc.sets;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import io.github.masyumero.zenlesscraftzero.common.equip.disc.sets.DiscSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import top.theillusivec4.curios.api.SlotContext;

public interface IDiscBonuses {

   DiscSet getSets();

   int countDisc(LivingEntity entity);

   default void discTick(SlotContext slotContext, ItemStack stack) {};

   default void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {}

   default void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {}

   default void bonuses(SlotContext slotContext, ItemStack stack) {};

   default void twoPieceBonuses(SlotContext slotContext, ItemStack stack) {};

   default void fourPieceBonuses(SlotContext slotContext, ItemStack stack) {};

   default Multimap<AttributeInstance, AttributeModifier> getTwoPieceModifiers(LivingEntity entity, ItemStack stack, String name) {
      return HashMultimap.create();
   }

   default Multimap<AttributeInstance, AttributeModifier> getFourPieceModifiers(LivingEntity entity, ItemStack stack, String name) {
      return HashMultimap.create();
   }

   default void onTwoPieceAttackEntity(AttackEntityEvent event, ItemStack stack, Player player) {}

   default void onFourPieceAttackEntity(AttackEntityEvent event, ItemStack stack, Player player) {}

   default void onAttackEntity(AttackEntityEvent event, ItemStack stack, Player player) {
      if (countDisc(player) >= 2) {
         onTwoPieceAttackEntity(event, stack, player);
      }
      if (countDisc(player) >= 4) {
         onFourPieceAttackEntity(event, stack, player);
      }
   }
}
