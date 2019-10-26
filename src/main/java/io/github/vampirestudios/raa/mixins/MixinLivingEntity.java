package io.github.vampirestudios.raa.mixins;

import net.minecraft.client.network.packet.ItemPickupAnimationS2CPacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class MixinLivingEntity {

    @Inject(method = "sendPickup", at = @At(value = "HEAD"))
    private void onPlayerPickup(Entity entity_1, int int_1, CallbackInfo ci) {
        if (!entity_1.removed && !((LivingEntity)(Object)this).world.isClient && (entity_1 instanceof ItemEntity || entity_1 instanceof ProjectileEntity || entity_1 instanceof ExperienceOrbEntity)) {

        }
    }
}
