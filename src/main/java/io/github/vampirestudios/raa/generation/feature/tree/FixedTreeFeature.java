package io.github.vampirestudios.raa.generation.feature.tree;

import com.mojang.datafixers.Dynamic;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.feature.BranchedTreeFeature;
import net.minecraft.world.gen.feature.BranchedTreeFeatureConfig;

import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;

public class FixedTreeFeature extends BranchedTreeFeature<BranchedTreeFeatureConfig> {
    public FixedTreeFeature(Function<Dynamic<?>, ? extends BranchedTreeFeatureConfig> function) {
        super(function);
    }

    @Override
    protected boolean generate(ModifiableTestableWorld modifiableTestableWorld_1, Random random_1, BlockPos blockPos_1, Set<BlockPos> set_1, Set<BlockPos> set_2, BlockBox blockBox_1, BranchedTreeFeatureConfig branchedTreeFeatureConfig_1) {
        int int_1 = branchedTreeFeatureConfig_1.baseHeight + random_1.nextInt(branchedTreeFeatureConfig_1.heightRandA + 1) + random_1.nextInt(branchedTreeFeatureConfig_1.heightRandB + 1);
        int int_2 = branchedTreeFeatureConfig_1.trunkHeight >= 0 ? branchedTreeFeatureConfig_1.trunkHeight + random_1.nextInt(branchedTreeFeatureConfig_1.trunkHeightRandom + 1) : int_1 - (branchedTreeFeatureConfig_1.foliageHeight + random_1.nextInt(branchedTreeFeatureConfig_1.foliageHeightRandom + 1));
        int int_3 = branchedTreeFeatureConfig_1.foliagePlacer.getRadius(random_1, int_2, int_1, branchedTreeFeatureConfig_1);
        Optional<BlockPos> optional_1 = this.findPositionToGenerate(modifiableTestableWorld_1, int_1, int_2, int_3, blockPos_1, branchedTreeFeatureConfig_1);
        if (!optional_1.isPresent()) {
            return false;
        } else {
            BlockPos blockPos_2 = optional_1.get();
            this.setToDirt(modifiableTestableWorld_1, blockPos_2.down());
            this.generate(modifiableTestableWorld_1, random_1, int_1, blockPos_2, branchedTreeFeatureConfig_1.trunkTopOffsetRandom + random_1.nextInt(branchedTreeFeatureConfig_1.foliageHeight + 1), set_1, blockBox_1, branchedTreeFeatureConfig_1);
            branchedTreeFeatureConfig_1.foliagePlacer.generate(modifiableTestableWorld_1, random_1, branchedTreeFeatureConfig_1, int_1, int_2, int_3, blockPos_2, set_2);
            return true;
        }
    }
}
