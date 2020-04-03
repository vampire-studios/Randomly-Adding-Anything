package io.github.vampirestudios.raa.mixins;

import io.github.vampirestudios.raa.generation.dimensions.CustomDimension;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @ModifyVariable(method = "travel", at = @At(value = "FIELD"), ordinal = 0, name = "d")
    private double modifyGravity(double d) {
        if (this.world.dimension instanceof CustomDimension) {
            if (((CustomDimension)this.world.dimension).getDimensionData().hasCustomGravity()) {
                return ((CustomDimension)this.world.dimension).getDimensionData().getGravity() * 0.04d;
            }
        }
        return 0.08d;
    }
}
