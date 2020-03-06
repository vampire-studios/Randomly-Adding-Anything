package io.github.vampirestudios.raa.mixins;

import net.minecraft.block.LeavesBlock;
import net.minecraft.state.property.IntProperty;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(LeavesBlock.class)
public class MixinLeavesBlock {

    @Shadow @Final
    public static final IntProperty DISTANCE = IntProperty.of("distance", 1, 20);

    @ModifyConstant(method = {
            "<init>",
            "hasRandomTicks(Lnet/minecraft/block/BlockState;)Z",
            "randomTick(Lnet/minecraft/block/BlockState;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;Ljava/util/Random;)V",
            "updateDistanceFromLogs(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/IWorld;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;",
            "getDistanceFromLog(Lnet/minecraft/block/BlockState;)I"
    }, constant = @Constant(intValue = 7))
    public int onInit(int old) {
        return 20;
    }

}