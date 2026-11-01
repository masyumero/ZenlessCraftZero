package io.github.masyumero.zenlesscraftzero.common.item;

import io.github.masyumero.zenlesscraftzero.common.anomaly.AttributeAnomaly;
import io.github.masyumero.zenlesscraftzero.common.utils.ZCZUtil;
import lombok.Getter;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

@Getter
public class AttributeItem extends Item implements ICurioItem {

    private final AttributeAnomaly attributeAnomaly;

    public AttributeItem(AttributeAnomaly attributeAnomaly) {
        super(new Properties().stacksTo(1));
        this.attributeAnomaly = attributeAnomaly;
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        LivingEntity entity = slotContext.entity();

        ZCZUtil.getAttribute(entity, attribute -> attribute.setAttributeAnomaly(attributeAnomaly));
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        LivingEntity entity = slotContext.entity();

        ZCZUtil.getAttribute(entity, attribute -> attribute.setAttributeAnomaly(AttributeAnomaly.PHYSICAL));
    }
}
