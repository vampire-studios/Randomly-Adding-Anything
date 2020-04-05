package io.github.vampirestudios.raa.mixins;

import io.github.vampirestudios.raa.generation.dimensions.CustomDimension;
import io.github.vampirestudios.raa.generation.dimensions.data.DimensionData;
import io.github.vampirestudios.raa.utils.TeleportPlacementHandler;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PortalForcer;
import net.minecraft.world.dimension.Dimension;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PortalForcer.class)
public class MixinPortalForcer
{
    @Shadow
    @Final
    private ServerWorld world;

    @Inject(method = "usePortal", at = @At("HEAD"), cancellable = true)
    private void usePortal(Entity entity, float float_1, CallbackInfoReturnable<Boolean> infoReturnable) {
        // going to a custom dimension
        Dimension destinationDimension = world.getDimension();
        Dimension playerDimension = entity.getEntityWorld().getDimension();

        // going to miner's horizon
        if (destinationDimension instanceof CustomDimension) {
            DimensionData dimensionData = ((CustomDimension) destinationDimension).getDimensionData();
            TeleportPlacementHandler.enterDimension(entity, dimensionData, (ServerWorld) entity.getEntityWorld(), world);
            infoReturnable.setReturnValue(true);
            infoReturnable.cancel();
        }

        // coming from the miner's horizon world
        if (playerDimension instanceof CustomDimension) {
            DimensionData dimensionData = ((CustomDimension) playerDimension).getDimensionData();
            TeleportPlacementHandler.leaveDimension(entity, dimensionData, world);
            infoReturnable.setReturnValue(true);
            infoReturnable.cancel();
        }
    }
}