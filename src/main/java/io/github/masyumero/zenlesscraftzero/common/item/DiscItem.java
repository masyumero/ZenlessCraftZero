package io.github.masyumero.zenlesscraftzero.common.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import io.github.masyumero.zenlesscraftzero.ZCZLang;
import io.github.masyumero.zenlesscraftzero.api.equip.disc.*;
import io.github.masyumero.zenlesscraftzero.common.capability.ZCZCapabilities;
import io.github.masyumero.zenlesscraftzero.common.capability.item.EquipmentLevel;
import io.github.masyumero.zenlesscraftzero.common.capability.item.IEquipmentLevel;
import io.github.masyumero.zenlesscraftzero.common.capability.item.disc.DiscItemProvider;
import io.github.masyumero.zenlesscraftzero.common.capability.item.disc.IStats;
import io.github.masyumero.zenlesscraftzero.common.capability.item.disc.Stats;
import io.github.masyumero.zenlesscraftzero.common.registries.ZCZItems;
import lombok.Getter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.*;
import java.util.function.Predicate;

@Getter
public class DiscItem extends Item implements ICurioItem {

    private static final Random RANDOM = new Random();

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

    private void updateStatsAttribute(IStats stats, ItemStack itemStack, LivingEntity entity, Predicate<ItemStack> itemStackPredicate) {
        if (entity.level().isClientSide()) {
            return;
        }
        Multimap<AttributeInstance, AttributeModifier> modifiers = HashMultimap.create();
        if (stats.getMainStat() != null) {
            AttributeInstance instance = entity.getAttribute(stats.getMainStat().getAttribute());
            double[] statValues = DiscLevelStat.getStat(discRank, stats.getMainStat());
            if (instance != null && statValues.length != 0) {
                AttributeModifier modifier = new AttributeModifier(
                        stats.getMainStat().getPartitionUUID(discPartition),
                        "main_stat",
                        statValues[stats.getEquipLevel().getLevel()],
                        stats.getMainStat().getOperation());
                modifiers.put(instance, modifier);
            }
        }
        if (!stats.getSubStats().isEmpty()) {
            Map<DiscStat, Integer> subStats = stats.getSubStats();
            subStats.forEach((discStat, integer) -> {
                AttributeInstance instance = entity.getAttribute(discStat.getAttribute());
                double statValues = DiscLevelStat.getSubStatBaseValue(discRank, discStat) * (1 + integer);
                if (instance != null) {
                    AttributeModifier modifier = new AttributeModifier(
                            discStat.getPartitionSubUUID(discPartition),
                            "sub_stat",
                            statValues,
                            discStat.getOperation());
                    modifiers.put(instance, modifier);
                }
            });
        }

        modifiers.forEach((instance, modifier) -> {
            var current = instance.getModifier(modifier.getId());
            if (!itemStackPredicate.test(itemStack)) {
                instance.removeModifier(modifier);
                return;
            }
            if (current == null || current.getAmount() != modifier.getAmount()) {
                if (current != null) {
                    instance.removeModifier(modifier);
                }
                instance.addTransientModifier(modifier);
            }
        });
    }

    private void updateStats(IStats stats) {
        if (stats.getMainStat() == null) {
            DiscStat[] partitionStats = DiscStat.getPartitionStats(discPartition);
            stats.setMainStat(partitionStats[RANDOM.nextInt(partitionStats.length)]);
        }
        if (stats.getSubStats().isEmpty()) {
            DiscStat[] subStats = DiscStat.getSubStats();
            var statCount = getStatCount();
            for (int i = 0; i < statCount;) {
                if (stats.addSubStats(subStats[RANDOM.nextInt(subStats.length)])) {
                    i++;
                }
            }
        } else {
            Map<DiscStat, Integer> currentSubStats = stats.getSubStats();
            DiscStat[] currentSubStatsKeys = currentSubStats.keySet().toArray(new DiscStat[0]);
            DiscStat[] subStats = DiscStat.getSubStats();
            int maxEnhance = (stats.getEquipLevel().getLevel() / 3) - stats.getUpgradedCount();
            if (stats.getUpgradedCount() != getMaxLevel() / 3) {
                for (int i = 0; i < maxEnhance;) {
                    if (stats.enhanceSubStats(currentSubStatsKeys[RANDOM.nextInt(currentSubStatsKeys.length)])) {
                        i++;
                        stats.addUpgradedCount(1);
                    } else if (stats.addSubStats(subStats[RANDOM.nextInt(subStats.length)])) {
                        i++;
                        stats.addUpgradedCount(1);
                    }
                }
            }
        }
    }

    private int getStatCount() {
        var num = RANDOM.nextDouble();
        var statCount = 0;
        if (discRank == DiscRank.RANK_B) {
            statCount = 1;
            if (num <= 0.2) {
                statCount = 2;
            }
        } else if (discRank == DiscRank.RANK_A) {
            statCount = 2;
            if (num <= 0.2) {
                statCount = 3;
            }
        } else if (discRank == DiscRank.RANK_S) {
            statCount = 3;
            if (num <= 0.2) {
                statCount = 4;
            }
        }
        return statCount;
    }

    @Override
    public @Nullable CompoundTag getShareTag(ItemStack stack) {
        CompoundTag tag = super.getShareTag(stack) == null ? new CompoundTag() : super.getShareTag(stack);
        stack.getCapability(ZCZCapabilities.EQUIPMENT_LEVEL).ifPresent(equipLevel -> {
            tag.put("equipment_level", equipLevel.serializeNBT());
        });
        stack.getCapability(ZCZCapabilities.STATS).ifPresent(stats -> {
            tag.put("stats", stats.serializeNBT());
        });
        return tag;
    }

    @Override
    public void readShareTag(ItemStack stack, @Nullable CompoundTag tag) {
        super.readShareTag(stack, tag);
        if (tag == null) {
            return;
        }

        if (tag.contains("equipment_level")) {
            stack.getCapability(ZCZCapabilities.EQUIPMENT_LEVEL).ifPresent(equipLevel -> {
                equipLevel.deserializeNBT(tag.getCompound("equipment_level"));
            });
        }
        if (tag.contains("stats")) {
            stack.getCapability(ZCZCapabilities.STATS).ifPresent(stats -> {
                stats.deserializeNBT(tag.getCompound("stats"));
            });
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
        if (level.isClientSide() || !(entity instanceof LivingEntity livingEntity)) {
            return;
        }
        stack.getCapability(ZCZCapabilities.EQUIPMENT_LEVEL).ifPresent(this::updateXpAndLevel);
        stack.getCapability(ZCZCapabilities.STATS).ifPresent(stats -> {
            this.updateStats(stats);
            CuriosApi.getCuriosInventory(livingEntity).ifPresent(inventory -> {
                inventory.getStacksHandler(discPartition.getId()).ifPresent(handler -> {
                    IDynamicStackHandler stacks = handler.getStacks();
                    for (int i = 0; i < stacks.getSlots(); i++) {
                        ItemStack curioStack = stacks.getStackInSlot(i);
                        this.updateStatsAttribute(stats, stack, livingEntity, curioStack::equals);
                    }
                });
            });
        });
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
        stack.getCapability(ZCZCapabilities.STATS).ifPresent(stats -> {
            tooltipComponents.add(Component.translatable(ZCZLang.DISC_STAT_MAIN.getTranslationKey()));
            var mainStat = stats.getMainStat();
            double[] statValues = DiscLevelStat.getStat(discRank, mainStat);
            if (mainStat != null) {
                tooltipComponents.add(Component.translatable(mainStat.getTranslate().getTranslationKey(), "", statValues.length != 0 ? statValues[stats.getEquipLevel().getLevel()] : 0));
            } else {
                tooltipComponents.add(Component.translatable(ZCZLang.DISC_STAT_EMPTY.getTranslationKey()));
            }
            tooltipComponents.add(Component.translatable(ZCZLang.DISC_STAT_SUB.getTranslationKey()));
            var subStats = stats.getSubStats();
            if (!subStats.isEmpty()) {
                subStats.forEach((discStat, integer) -> {
                    double subStatValue = DiscLevelStat.getSubStatBaseValue(discRank, discStat) * (1 + integer);
                    if (subStatValue < 1.0) {
                        tooltipComponents.add(Component.translatable(discStat.getTranslate().getTranslationKey(), integer == 0 ? " " : "+" + integer, subStatValue * 100));
                    } else {
                        tooltipComponents.add(Component.translatable(discStat.getTranslate().getTranslationKey(), integer == 0 ? " " : "+" + integer, subStatValue));
                    }
                });
                for (int i = 0; i < 4 - subStats.size(); i++) {
                    tooltipComponents.add(Component.translatable(ZCZLang.DISC_STAT_EMPTY.getTranslationKey()));
                }
            } else {
                for (int i = 0; i < 4; i++) {
                    tooltipComponents.add(Component.translatable(ZCZLang.DISC_STAT_EMPTY.getTranslationKey()));
                }
            }
        });
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag tag) {
        return new DiscItemProvider(new EquipmentLevel(maxLevel, maxXp), Stats::new, tag);
    }
}
