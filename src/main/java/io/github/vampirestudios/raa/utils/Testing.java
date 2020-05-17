package io.github.vampirestudios.raa.utils;

import io.github.vampirestudios.raa.generation.dimensions.CustomDimension;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class Testing {

    public static double changeGravity(World world) {
        if (world.getDimension() instanceof CustomDimension) {
            return ((CustomDimension)world.getDimension()).getDimensionData().getGravity() * 0.08d;
        }
        return 0.08d;
    }

    public static void changeGravityDamage(StatusEffectInstance statusEffectInstance, World world, float fallDistance, float damageMultiplier, CallbackInfoReturnable<Integer> info) {
        float f = statusEffectInstance == null ? 0.0F : (float)(statusEffectInstance.getAmplifier() + 1);
        if (world.getDimension() instanceof CustomDimension) {
            info.setReturnValue((int) (MathHelper.ceil((fallDistance - 3.0F - f) * damageMultiplier) * ((CustomDimension)world.getDimension()).getDimensionData().getGravity()));
        }
    }

}
