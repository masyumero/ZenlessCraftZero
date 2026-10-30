package io.github.masyumero.zenlesscraftzero.common.event;

import io.github.masyumero.zenlesscraftzero.ZenlessCraftZero;
import io.github.masyumero.zenlesscraftzero.api.ZenlessCraftZeroApi;
import io.github.masyumero.zenlesscraftzero.api.anomaly.IAttributeAnomaly;
import io.github.masyumero.zenlesscraftzero.api.equip.disc.DiscPartition;
import io.github.masyumero.zenlesscraftzero.api.equip.disc.sets.IDiscBonuses;
import io.github.masyumero.zenlesscraftzero.common.anomaly.AttributeAnomaly;
import io.github.masyumero.zenlesscraftzero.common.capability.entity.AttributeAnomalyProvider;
import io.github.masyumero.zenlesscraftzero.common.equip.disc.sets.DiscSet;
import io.github.masyumero.zenlesscraftzero.common.item.DiscItem;
import io.github.masyumero.zenlesscraftzero.common.network.s2c.AttributeAnomalySyncPacket;
import io.github.masyumero.zenlesscraftzero.common.network.s2c.DamageIndicatorPacket;
import io.github.masyumero.zenlesscraftzero.common.network.s2c.HealthIndicatorPacket;
import io.github.masyumero.zenlesscraftzero.common.network.s2c.ZCZS2CNetwork;
import io.github.masyumero.zenlesscraftzero.common.registries.ZCZAttributes;
import io.github.masyumero.zenlesscraftzero.common.registries.ZCZDamageTypes;
import io.github.masyumero.zenlesscraftzero.common.utils.ZCZUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
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
import java.util.Map;
import java.util.UUID;
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
        ZenlessCraftZeroApi.getAttributeAnomaly(entity, attributeAnomaly -> {
            var anomalyLimit = attributeAnomaly.getAnomalyLimit();
            if (anomalyLimit.isEmpty()) {
                for (AttributeAnomaly anomaly : AttributeAnomaly.values()) {
                    var limit = (int) (30 * entity.getMaxHealth());
                    if (anomaly.equals(AttributeAnomaly.PHYSICAL)) {
                        limit = (int) (limit * 1.2);
                    }
                    attributeAnomaly.addAnomalyLimit(anomaly, limit);
                }
            }
            var attributes = attributeAnomaly.getAttribute();
            if (!attributes.isEmpty()) {
                for (Map.Entry<UUID, IAttributeAnomaly> entry : attributes.entrySet()) {
                    UUID uuid = entry.getKey();
                    IAttributeAnomaly attribute = entry.getValue();
                    if (serverLevel.getEntity(uuid) instanceof LivingEntity livingEntity) {
                        attribute.getAttributeEffect().anomalyEffectTick(livingEntity, entity, attributeAnomaly);
                    }
                }
            }
        }, true);
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        LivingEntity entity = event.getEntity();
        Level level = entity.level();
        var attacker = event.getSource().getDirectEntity();
        boolean isAttributeEffect = event.getSource().is(ZCZDamageTypes.ATTRIBUTE_EFFECT);
        boolean isCrit = false;
        if (!(level instanceof ServerLevel serverLevel)) {
            return;
        }

        if (!isAttributeEffect) {
            ZenlessCraftZeroApi.getAttributeAnomaly(entity, attributeAnomaly -> {
                if (attacker instanceof LivingEntity livingEntity) {
                    UUID attackerUUID = attacker.getUUID();
                    attributeAnomaly.addAttribute(attackerUUID, AttributeAnomaly.PHYSICAL);

                    AttributeInstance masterlyAttribute = livingEntity.getAttribute(ZCZAttributes.ANOMALY_MASTERY.get());
                    double masterly = masterlyAttribute != null ? masterlyAttribute.getValue() : 0;
                    int buildupEfficiency = 0;
                    var buildupValue = 0;
                    var targetAnomalyResistance = entity.getAttribute(ZCZAttributes.ANOMALY_RESISTANCE.get()).getValue();
                    int buildup = (int) (100 * masterly / 100 * (100 + buildupEfficiency + buildupValue) / 100 * (1 - targetAnomalyResistance));
                    attributeAnomaly.addAnomalyBuildup(attackerUUID, AttributeAnomaly.PHYSICAL, buildup);

                    var attributes = attributeAnomaly.getAttribute();
                    var attribute = attributes.get(attackerUUID);
                    var anomalyLimit = attributeAnomaly.getAnomalyLimit().getOrDefault(attribute, 0);
                    var anomalyBuildup = attributeAnomaly.getAnomalyBuildup().get(attackerUUID, attribute);
                    if (anomalyBuildup != null) {
                        if (anomalyBuildup.equals(anomalyLimit) && attributeAnomaly.getCooldowns().get(attribute) == 0) {
                            attribute.getAttributeEffect().onAnomalyEffect(livingEntity, entity, attributeAnomaly, event.getAmount());
                            attributeAnomaly.setCooldowns(Map.of(attribute, 3));
                        }
                    }
                }
            }, true);

            if (attacker instanceof LivingEntity livingAttacker) {
                var critRate = livingAttacker.getAttribute(ZCZAttributes.CRIT_RATE.get());
                var critDamage = livingAttacker.getAttribute(ZCZAttributes.CRIT_DAMAGE.get());
                if (critRate != null && critDamage != null) {
                    if (Math.random() < critRate.getValue()) {
                        event.setAmount((float) (event.getAmount() * (1 + critDamage.getValue())));
                        isCrit = true;
                    }
                }
            }
        }

        ZCZS2CNetwork.sendIndicator(serverLevel, entity.getX(), entity.getY() + entity.getBbHeight(), entity.getZ(), 64.0, new HealthIndicatorPacket(AttributeAnomaly.PHYSICAL, entity.getId()));
        ZCZS2CNetwork.sendIndicator(serverLevel, entity.getX(), entity.getY() + entity.getBbHeight(), entity.getZ(), 64.0, new DamageIndicatorPacket(AttributeAnomaly.PHYSICAL, entity.getX(), entity.getY() + entity.getBbHeight(), entity.getZ(), event.getAmount(), isAttributeEffect, isCrit));
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
            ZenlessCraftZeroApi.getAttributeAnomaly(original, oldAttributeAnomaly -> {
                ZenlessCraftZeroApi.getAttributeAnomaly(clone, newAttributeAnomaly -> {
                    newAttributeAnomaly.deserializeNBT(oldAttributeAnomaly.serializeNBT());
                }, true);
            }, false);
        } finally {
            original.invalidateCaps();
        }
    }

    @SubscribeEvent
    public static void onStartTracking(PlayerEvent.StartTracking event) {
        Entity target = event.getTarget();

        if (!(event.getEntity() instanceof ServerPlayer)) {
            return;
        }

        AttributeAnomalySyncPacket.syncAttributeAnomaly(target);
    }
}
