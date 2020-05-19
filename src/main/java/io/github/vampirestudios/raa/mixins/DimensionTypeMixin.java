package io.github.vampirestudios.raa.mixins;

import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.api.accessor.DimensionTypeAccessor;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DimensionType.class)
public class DimensionTypeMixin implements DimensionTypeAccessor {

    @Shadow @Final private int id;

    private boolean raa$hellish;
    private boolean raa$doesWaterVaporize;

    @Inject(method = "method_27998", at = @At("HEAD"), cancellable = true)
    public void raa$isHellish(CallbackInfoReturnable<Boolean> cir) {
        if (Registry.DIMENSION_TYPE.getId(Registry.DIMENSION_TYPE.get(this.id)).getNamespace().equals(RandomlyAddingAnything.MOD_ID)) {
            cir.setReturnValue(this.raa$hellish);
        }
    }

    @Inject(method = "method_27999", at = @At("HEAD"), cancellable = true)
    public void raa$doesWaterVaporize(CallbackInfoReturnable<Boolean> cir) {
        if (Registry.DIMENSION_TYPE.getId(Registry.DIMENSION_TYPE.get(this.id)).getNamespace().equals(RandomlyAddingAnything.MOD_ID)) {
            cir.setReturnValue(this.raa$doesWaterVaporize);
        }
    }

    @Override
    public DimensionType setHellish(boolean hellish) {
        this.raa$hellish = hellish;
        return (DimensionType) (Object)this;
    }

    @Override
    public DimensionType setDoesWaterVaporize(boolean doesWaterVaporize) {
        this.raa$doesWaterVaporize = doesWaterVaporize;
        return (DimensionType) (Object)this;
    }
}
