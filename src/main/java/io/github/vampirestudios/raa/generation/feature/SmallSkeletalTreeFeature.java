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
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

import java.util.Random;
import java.util.Set;
import java.util.function.Function;

/**
 * @author Indigo Amann
 */
//Code kindly taken from The Hallow, thanks to everyone who is working on it!
public class SmallSkeletalTreeFeature extends AbstractTreeFeature<TreeFeatureConfig> {
    private static final BlockState LOG = Blocks.BONE_BLOCK.getDefaultState();

    public SmallSkeletalTreeFeature(Function<Dynamic<?>, ? extends TreeFeatureConfig> function) {
        super(function);
    }

    @Override
    public boolean generate(ModifiableTestableWorld modifiableTestableWorld, Random random, BlockPos blockPos, Set<BlockPos> set, Set<BlockPos> set1, BlockBox blockBox, TreeFeatureConfig defaultFeatureConfi) {
        if (!isNaturalDirtOrGrass(modifiableTestableWorld, blockPos.down(1))) {
            return false;
        }
        int y;
        blockPos.offset(Direction.DOWN);
        for (y = 0; y < Rands.randInt(6) + 4; y++) {
            addLog(modifiableTestableWorld, blockPos.offset(Direction.UP), Direction.Axis.Y, blockBox);
        }
        if (random.nextBoolean()) {
            Direction direction = Direction.Type.HORIZONTAL.random(random);
            BlockPos top = blockPos.offset(Direction.UP);
            for (int i = 1; i < Rands.randInt(4) + 3; i++) {
                addLog(modifiableTestableWorld, top.offset(direction), direction.getAxis(), blockBox);
            }
        }
        return true;
    }

    private void addLog(ModifiableTestableWorld modifiableTestableWorld, BlockPos blockPos, Direction.Axis axis, BlockBox mutableIntBoundingBox) {
        if (canTreeReplace(modifiableTestableWorld, blockPos)) {
            this.setBlockState(modifiableTestableWorld, blockPos, LOG.with(PillarBlock.AXIS, axis), mutableIntBoundingBox);
        }
    }
}