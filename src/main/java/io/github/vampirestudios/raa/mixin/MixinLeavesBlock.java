package io.github.vampirestudios.raa.mixin;

import io.github.vampirestudios.raa.generation.dimensions.CustomDimension;
import io.github.vampirestudios.raa.utils.Rands;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.IWorld;
import org.lwjgl.system.CallbackI;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LeavesBlock.class)
public class MixinLeavesBlock {

    @Inject(method = "updateDistanceFromLogs", at = @At("HEAD"), cancellable = true)
    private static void updateDistanceFromLogs(BlockState state, IWorld world, BlockPos pos, CallbackInfoReturnable<BlockState> info) {
        if (world.getDimension() instanceof CustomDimension) {
//            if (!Rands.chance(5)) {
//                info.setReturnValue(state.with(LeavesBlock.DISTANCE, 1));
//            }
            if (Rands.chance(20)) {
                int int_1 = 20;
                BlockPos.PooledMutable blockPos$PooledMutable_1 = BlockPos.PooledMutable.get();
                try {
                    Direction[] var6 = Direction.values();
                    int var7 = var6.length;

                    for (int var8 = 0; var8 < var7; ++var8) {
                        Direction direction_1 = var6[var8];
                        blockPos$PooledMutable_1.method_10114(pos).method_10118(direction_1);
                        int_1 = Math.min(int_1, getDistanceFromLog(world.getBlockState(blockPos$PooledMutable_1)) + 1);
                        if (int_1 == 1) {
                            break;
                        }
                    }
                } catch (Throwable var17) {
                    var17.printStackTrace();
                }
//                System.out.println("t1 " + int_1);
                info.setReturnValue(state.with(LeavesBlock.DISTANCE, (int_1 > 7 && int_1 < 21) ? 6 : Math.min(int_1, 7)));
            } else {
                info.setReturnValue(state.with(LeavesBlock.DISTANCE, 1));
            }
        }
    }

    private static int getDistanceFromLog(BlockState blockState_1) {
        if (BlockTags.LOGS.contains(blockState_1.getBlock())) {
            return 0;
        } else {
            return blockState_1.getBlock() instanceof LeavesBlock ? (Integer)blockState_1.get(LeavesBlock.DISTANCE) : 7;
        }
    }
}
