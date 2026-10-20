package io.github.masyumero.zenlesscraftzero.common.item;

import io.github.masyumero.zenlesscraftzero.api.equip.disc.DiscPartition;
import io.github.masyumero.zenlesscraftzero.api.equip.disc.DiscRank;
import io.github.masyumero.zenlesscraftzero.api.equip.disc.DiscSet;
import io.github.masyumero.zenlesscraftzero.api.equip.disc.DiscSetting;
import lombok.Getter;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

@Getter
public class DiscItem extends Item implements ICurioItem {

    private final DiscSet discSet;
    private final DiscPartition discPartition;
    private final DiscRank discRank;

    public DiscItem(DiscSetting setting) {
        super(new Properties());
        this.discSet = setting.set();
        this.discPartition = setting.partition();
        this.discRank = setting.rank();
    }

    public void bonuses(SlotContext slotContext, ItemStack stack) {
        if (slotContext.identifier().equals(discPartition.getId())) {
            discSet.getDiscBonuses().bonuses(slotContext, stack);
        }
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        discSet.getDiscBonuses().incrementDiscCount();
        discSet.getDiscBonuses().onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        discSet.getDiscBonuses().decrementDiscCount();
        discSet.getDiscBonuses().onUnequip(slotContext, newStack, stack);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (slotContext.identifier().equals(discPartition.getId())) {
            discSet.getDiscBonuses().discTick(slotContext, stack);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
    }

}
