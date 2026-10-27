package io.github.masyumero.zenlesscraftzero.common.event;

import io.github.masyumero.zenlesscraftzero.ZenlessCraftZero;
import io.github.masyumero.zenlesscraftzero.api.anomaly.Attribute;
import io.github.masyumero.zenlesscraftzero.api.equip.disc.DiscPartition;
import io.github.masyumero.zenlesscraftzero.api.equip.disc.DiscSet;
import io.github.masyumero.zenlesscraftzero.api.equip.disc.sets.IDiscBonuses;
import io.github.masyumero.zenlesscraftzero.common.capability.ZCZCapabilities;
import io.github.masyumero.zenlesscraftzero.common.capability.entity.AttributeAnomalyProvider;
import io.github.masyumero.zenlesscraftzero.common.item.DiscItem;
import io.github.masyumero.zenlesscraftzero.common.network.s2c.DamageIndicatorNetwork;
import io.github.masyumero.zenlesscraftzero.common.registries.ZCZDamageTypes;
import io.github.masyumero.zenlesscraftzero.common.utils.ZCZUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.IItemHandler;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

@Mod.EventBusSubscriber(modid = ZenlessCraftZero.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ZCZEvents {

    private static void dispatchBonuses(LivingEntity entity, BiConsumer<IDiscBonuses, ItemStack> action) {
        if (entity == null) {
            return;
        }

        List<DiscSet> acceptedDiscSets = new ArrayList<>();

        CuriosApi.getCuriosInventory(entity).ifPresent(inventory -> {
            for (DiscPartition partition : DiscPartition.values()) {
                inventory.getStacksHandler(partition.getId()).ifPresent(handler -> {
                    IItemHandler stacks = handler.getStacks();
                    for (int i = 0; i < stacks.getSlots(); i++) {
                        ItemStack stack = stacks.getStackInSlot(i);

                        if (stack.isEmpty()) {
                            continue;
                        }

                        if (stack.getItem() instanceof DiscItem discItem) {
                            DiscSet set = discItem.getDiscSet();
                            if (!acceptedDiscSets.contains(set)) {
                                acceptedDiscSets.add(set);
                                action.accept(set.getDiscBonuses(), stack);
                            }
                        }
                    }
                });
            }
        });
    }

    @SubscribeEvent
    public static void onAttackEntity(AttackEntityEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player player) {
            dispatchBonuses(player, (bonuses, stack) -> {
                if (!event.isCanceled()) {
                    bonuses.onAttackEntity(event, stack, player);
                }
            });
        }
    }

    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        Level level = entity.level();
        if (!(level instanceof ServerLevel serverLevel)) {
            return;
        }

        entity.getCapability(ZCZCapabilities.ATTRIBUTE_ANOMALY).ifPresent(attributeAnomaly -> {
            var anomalyLimit = attributeAnomaly.getAnomalyLimit();
            if (anomalyLimit == -1) {
                attributeAnomaly.setAnomalyLimit(20);
            }
            var attributes = attributeAnomaly.getAttribute();
            if (!attributes.isEmpty()) {
                attributes.forEach((uuid, attribute) -> {
                    if (serverLevel.getEntity(uuid) instanceof LivingEntity livingEntity) {
                        var anomalyBuildup = attributeAnomaly.getAnomalyBuildup().get(uuid, attribute);
                        if (anomalyBuildup != null) {
                            if (anomalyBuildup.equals(anomalyLimit)) {
                                attribute.getAttributeEffect().onAnomalyEffect(livingEntity, entity);
                            }
                        }
                        attribute.getAttributeEffect().anomalyEffectTick(livingEntity, entity);
                    }
                });
            }
        });
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        LivingEntity entity = event.getEntity();
        Level level = entity.level();
        var attacker = event.getSource().getDirectEntity();
        if (!(level instanceof ServerLevel serverLevel)) {
            return;
        }

        entity.getCapability(ZCZCapabilities.ATTRIBUTE_ANOMALY).ifPresent(attributeAnomaly -> {
            if (attacker != null) {
                attributeAnomaly.addAttribute(attacker.getUUID(), Attribute.PHYSICAL);
                attributeAnomaly.addAnomalyBuildup(attacker.getUUID(), Attribute.PHYSICAL, 100);
            }
        });

        float damage = event.getAmount();
        if (damage <= 0.0F) {
            return;
        }
        DamageIndicatorNetwork.send(serverLevel, Attribute.PHYSICAL, entity.getX(), entity.getY() + entity.getBbHeight(), entity.getZ(), damage, event.getSource().is(ZCZDamageTypes.ATTRIBUTE_EFFECT));
    }

    @SubscribeEvent
    public static void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (!(event.getObject() instanceof LivingEntity)) {
            return;
        }

        var provider = new AttributeAnomalyProvider();

        event.addCapability(ZCZUtil.rl("attribute_anomaly"), provider);
        event.addListener(provider::invalidate);
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (!event.isWasDeath()) {
            return;
        }

        Player original = event.getOriginal();
        Player clone = event.getEntity();

        original.reviveCaps();

        try {
            original.getCapability(ZCZCapabilities.ATTRIBUTE_ANOMALY).ifPresent(oldAttributeAnomaly -> {
                clone.getCapability(ZCZCapabilities.ATTRIBUTE_ANOMALY).ifPresent(newAttributeAnomaly -> {
                    newAttributeAnomaly.deserializeNBT(oldAttributeAnomaly.serializeNBT());
                });
            });
        } finally {
            original.invalidateCaps();
        }
    }
}
