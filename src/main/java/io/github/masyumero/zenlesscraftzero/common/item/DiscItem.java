package io.github.masyumero.zenlesscraftzero.common.item;

import io.github.masyumero.zenlesscraftzero.ZCZLang;
import io.github.masyumero.zenlesscraftzero.api.equip.disc.DiscPartition;
import io.github.masyumero.zenlesscraftzero.api.equip.disc.DiscRank;
import io.github.masyumero.zenlesscraftzero.api.equip.disc.DiscSet;
import io.github.masyumero.zenlesscraftzero.api.equip.disc.DiscSetting;
import io.github.masyumero.zenlesscraftzero.common.capability.ZCZCapabilities;
import io.github.masyumero.zenlesscraftzero.common.capability.item.EquipmentLevelProvider;
import io.github.masyumero.zenlesscraftzero.common.capability.item.IEquipmentLevel;
import io.github.masyumero.zenlesscraftzero.common.registries.ZCZItems;
import lombok.Getter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

@Getter
public class DiscItem extends Item implements ICurioItem {

    private final DiscSet discSet;
    private final DiscPartition discPartition;
    private final DiscRank discRank;
    private final int maxLevel;
    private final int maxXp;
    private final int moldedXp = 100;
    private final int crystallizedXp = 500;
    private final int etherXp = 2000;

    public DiscItem(DiscSetting setting) {
        super(new Properties().stacksTo(1));
        this.discSet = setting.set();
        this.discPartition = setting.partition();
        this.discRank = setting.rank();
        this.maxLevel = discRank.getMaxLevel();
        this.maxXp = discRank.getMaxXp();
    }

    public void bonuses(SlotContext slotContext, ItemStack stack) {
        if (slotContext.identifier().equals(discPartition.getId())) {
            discSet.getDiscBonuses().bonuses(slotContext, stack);
        }
    }

    private void updateXpAndLevel(IEquipmentLevel equipLevel) {
        if (equipLevel.getRequiredXp() == 0) {
            equipLevel.setRequiredXp(discRank.getLevelUpXp(equipLevel.getLevel()));
        }
        if (equipLevel.getRequiredXp() != -1 && equipLevel.getXp() >= equipLevel.getRequiredXp()) {
            equipLevel.incrementLevel();
            equipLevel.setRequiredXp(discRank.getLevelUpXp(equipLevel.getLevel()));
        }
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        discSet.getDiscBonuses().onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        discSet.getDiscBonuses().onUnequip(slotContext, newStack, stack);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (slotContext.identifier().equals(discPartition.getId())) {
            discSet.getDiscBonuses().discTick(slotContext, stack);
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        stack.getCapability(ZCZCapabilities.EQUIPMENT_LEVEL).ifPresent(this::updateXpAndLevel);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        ItemStack stack = context.getItemInHand();
        ItemStack offHandStack = context.getPlayer().getOffhandItem();
        stack.getCapability(ZCZCapabilities.EQUIPMENT_LEVEL).ifPresent(equipLevel -> {
            if (equipLevel.getXp() != equipLevel.getMaxXp()) {
                if (offHandStack.is(ZCZItems.MOLDED_PLATING_AGENT.get())) {
                    equipLevel.addXp(moldedXp, false);
                    offHandStack.setCount(offHandStack.getCount() - 1);
                } else if (offHandStack.is(ZCZItems.CRYSTALLIZED_PLATING_AGENT.get())) {
                    equipLevel.addXp(crystallizedXp, false);
                    offHandStack.setCount(offHandStack.getCount() - 1);
                } else if (offHandStack.is(ZCZItems.ETHER_PLATING_AGENT.get())) {
                    equipLevel.addXp(etherXp, false);
                    offHandStack.setCount(offHandStack.getCount() - 1);
                }
            }
        });
        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag isAdvanced) {
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
        tooltipComponents.add(Component.translatable(ZCZLang.DISC_TWO_PIECE_TOOLTIP.getTranslationKey()));
        tooltipComponents.add(Component.translatable(discSet.getTwoPieceLang().getTranslationKey()));
        tooltipComponents.add(Component.translatable(ZCZLang.DISC_FOUR_PIECE_TOOLTIP.getTranslationKey()));
        tooltipComponents.add(Component.translatable(discSet.getFourPieceLang().getTranslationKey()));
        stack.getCapability(ZCZCapabilities.EQUIPMENT_LEVEL).ifPresent(equipLevel -> {
            tooltipComponents.add(Component.literal("Level: " + equipLevel.getLevel() + "/" + equipLevel.getMaxLevel()));
            tooltipComponents.add(Component.literal("Xp: " + equipLevel.getXp() + "/" + equipLevel.getMaxXp()));
            if (!(equipLevel.getRequiredXp() == -1)) {
                tooltipComponents.add(Component.literal("RequiredXp: " + equipLevel.getRequiredXp()));
            }
        });
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag tag) {
        return new EquipmentLevelProvider(maxLevel, maxXp, tag);
    }
}
