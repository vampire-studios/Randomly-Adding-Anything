package io.github.vampirestudios.raa.generation.feature;

import com.mojang.datafixers.Dynamic;
import io.github.vampirestudios.raa.utils.Rands;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PillarBlock;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

import java.util.Random;
import java.util.Set;
import java.util.function.Function;

/**
 * @author Indigo Amann
 */
//Code kindly taken from The Hallow, thanks to everyone who is working on it!
public class LargeSkeletalTreeFeature extends AbstractTreeFeature<TreeFeatureConfig> {
    private static final BlockState LOG = Blocks.BONE_BLOCK.getDefaultState();

    public LargeSkeletalTreeFeature(Function<Dynamic<?>, ? extends TreeFeatureConfig> function) {
        super(function);
    }

    private static boolean isSurroundedByAir(TestableWorld testableWorld, BlockPos blockPos, Direction ignore) {
        for (Direction direction : Direction.Type.HORIZONTAL) {
            if (direction == ignore) continue;
            if (!isAir(testableWorld, blockPos.offset(direction))) return false;
        }
        return true;
    }

    private void addLog(ModifiableTestableWorld modifiableTestableWorld, BlockPos blockPos, Direction.Axis axis, BlockBox mutableIntBoundingBox) {
        if (canTreeReplace(modifiableTestableWorld, blockPos)) {
            this.setBlockState(modifiableTestableWorld, blockPos, LOG.with(PillarBlock.AXIS, axis), mutableIntBoundingBox);
        }
    }

    @Override
    protected boolean generate(ModifiableTestableWorld modifiableTestableWorld, Random random, BlockPos blockPos, Set<BlockPos> set, Set<BlockPos> set1, BlockBox blockBox, TreeFeatureConfig treeFeatureConfig) {
        if (!isNaturalDirtOrGrass(modifiableTestableWorld, blockPos.down(1))) return false;
        generateBranch(modifiableTestableWorld, random, blockPos.add(0, -1, 0), blockBox, Rands.randInt(4), null);
        return true;
    }

    private void generateBranch(ModifiableTestableWorld world, Random random, BlockPos pos, BlockBox blockBox, int maxBranchouts, Direction lastDir) {
        int baseHeight = Rands.randInt(4) + 2;

        for (int int_4 = 0; int_4 < baseHeight; ++int_4) {
            BlockPos blockPos = pos.up(int_4 + 1);
            if (!isSurroundedByAir(world, blockPos, null)) {
                return;
            }
            addLog(world, blockPos, Direction.Axis.Y, blockBox);
        }

        int int_5 = Rands.randInt(4) + 1;
        int int_1 = 8;

        if (maxBranchouts > 0) {
            for (int int_6 = 0; int_6 < int_5; ++int_6) {
                Direction direction_1 = Direction.Type.HORIZONTAL.random(random);
                if (direction_1 == lastDir) continue;
                BlockPos blockPos_4 = pos.up(baseHeight).offset(direction_1);
                if (Math.abs(blockPos_4.getX() - pos.getX()) < int_1 && Math.abs(blockPos_4.getZ() - pos.getZ()) < int_1 && isAir(world, blockPos_4) && isAir(world, blockPos_4.down(1)) && isSurroundedByAir(world, blockPos_4, direction_1.getOpposite())) {
                    addLog(world, blockPos_4, direction_1.getAxis(), blockBox);
                    generateBranch(world, random, blockPos_4, blockBox, maxBranchouts - 1, direction_1.getOpposite());
                }
            }
        }
    }
}