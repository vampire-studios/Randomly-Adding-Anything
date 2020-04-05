package io.github.vampirestudios.raa.generation.feature;

import com.mojang.datafixers.Dynamic;
import io.github.vampirestudios.raa.utils.Rands;
import net.minecraft.block.Blocks;
import net.minecraft.class_5138;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;
import java.util.function.Function;

public class QuarryFeature extends Feature<DefaultFeatureConfig> {
    public QuarryFeature(Function<Dynamic<?>, ? extends DefaultFeatureConfig> configDeserializer) {
        super(configDeserializer);
    }

    @Override
    public boolean generate(IWorld world, class_5138 arg, ChunkGenerator<? extends ChunkGeneratorConfig> generator, Random random, BlockPos pos, DefaultFeatureConfig config) {
        pos = pos.down();

        if (!world.getBlockState(pos).isOpaque()) return false;

        int size = Rands.randIntRange(8, 12);

        pos = pos.down(size);
        for (int y = 1; y < size + 1; y++) {
            for (int x = -y; x <= y; x++) {
                for (int z = -y; z <= y; z++) {
                    world.setBlockState(pos.add(x, y, z), Blocks.AIR.getDefaultState(), 2);
                }
            }
        }


        return false;
    }
}
