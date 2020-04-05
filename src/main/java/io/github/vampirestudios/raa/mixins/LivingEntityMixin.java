package io.github.vampirestudios.raa.mixins;

import io.github.vampirestudios.raa.utils.Testing;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//Thanks to JoeZwet and GalactiCraft Rewoken
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @ModifyVariable(method = "travel", at = @At(value = "FIELD"), ordinal = 0, name = "d")
    private double modifyGravity(double d) {
        return Testing.changeGravity(this.world);
    }

    @Shadow public abstract StatusEffectInstance getStatusEffect(StatusEffect effect);

    @Inject(method = "computeFallDamage", at = @At("RETURN"), cancellable = true)
    private void modifyFallDamage(float fallDistance, float damageMultiplier, CallbackInfoReturnable<Integer> info) {
        Testing.changeGravityDamage(this.getStatusEffect(StatusEffects.JUMP_BOOST), this.world, fallDistance, damageMultiplier, info);
    }

}