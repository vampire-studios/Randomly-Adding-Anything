package io.github.vampirestudios.raa.generation.feature.tree;

import com.mojang.datafixers.Dynamic;
import io.github.vampirestudios.raa.utils.Rands;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.feature.BranchedTreeFeature;
import net.minecraft.world.gen.feature.BranchedTreeFeatureConfig;

import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;

public class BentTreeFeature extends BranchedTreeFeature<BranchedTreeFeatureConfig> {
    public BentTreeFeature(Function<Dynamic<?>, ? extends BranchedTreeFeatureConfig> function) {
        super(function);
    }

    @Override
    protected boolean generate(ModifiableTestableWorld world, Random random, BlockPos pos, Set<BlockPos> logPositions, Set<BlockPos> leavesPositions,
                               BlockBox blockBox, BranchedTreeFeatureConfig config) {
        int height = config.baseHeight + random.nextInt(config.heightRandA + 1) + random.nextInt(config.heightRandB + 1);
        int trunkHeight = config.trunkHeight >= 0 ? config.trunkHeight + random.nextInt(config.trunkHeightRandom + 1) : height - (config.foliageHeight +
                random.nextInt(config.foliageHeightRandom + 1));
        int radius = config.foliagePlacer.getRadius(random, trunkHeight, height, config);
        Optional<BlockPos> positionToGenerate = this.findPositionToGenerate(world, height, trunkHeight, radius, pos, config);
        if (!positionToGenerate.isPresent()) {
            return false;
        } else {
            BlockPos generationPos = positionToGenerate.get();
            this.setToDirt(world, generationPos.down());
            int offset = Rands.randIntRange(3, 6);
            int offsetX = Rands.chance(3) ? -1 : Rands.chance(2) ? 0 : 1;
            int offsetZ = Rands.chance(3) ? -1 : Rands.chance(2) ? 0 : 1;
            this.generate(world, random, offset - 2, generationPos, 0, logPositions, blockBox, config);

            this.generate(world, random, offset, generationPos.add(offsetX, offset - 2, offsetZ), 0, logPositions, blockBox,
                    config);
            this.generate(world, random, height, generationPos.add(offsetX * 2, (offset * 2) - 2, offsetZ * 2),
                    config.trunkTopOffsetRandom + random.nextInt(config.trunkTopOffsetRandom + 1), logPositions, blockBox, config);
            config.foliagePlacer.generate(world, random, config, height, trunkHeight, radius, generationPos.add(offsetX * 2, (offset * 2) - 2, offsetZ * 2),
                    leavesPositions);

            return true;
        }
    }
}