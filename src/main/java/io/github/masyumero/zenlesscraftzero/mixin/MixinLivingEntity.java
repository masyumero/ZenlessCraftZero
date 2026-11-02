package io.github.masyumero.zenlesscraftzero.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.masyumero.zenlesscraftzero.common.event.ZCZEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity {

    @WrapOperation(method = "actuallyHurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;setHealth(F)V"))
    private void setHealthWarpOperation(LivingEntity entity, float health, Operation<Void> original, DamageSource damageSource) {
        var originalHealth = entity.getHealth();
        var damage = originalHealth - health;
        ZCZEvents.onLivingDamage(entity, damageSource, damage);
        original.call(entity, health);
    }
}
