package io.github.vampirestudios.raa.generation.decorator;

import com.mojang.datafixers.Dynamic;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.decorator.CountExtraChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;

import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class RandomExtraHeightmapDecorator extends Decorator<CountExtraChanceDecoratorConfig> {
    private Random random = null;

    public RandomExtraHeightmapDecorator(Function<Dynamic<?>, ? extends CountExtraChanceDecoratorConfig> function_1) {
        super(function_1);
    }

    @Override
    public Stream<BlockPos> getPositions(IWorld iWorld_1, ChunkGenerator<? extends ChunkGeneratorConfig> chunkGenerator_1, Random random_1, CountExtraChanceDecoratorConfig countExtraChanceDecoratorConfig_1, BlockPos blockPos_1) {
        if (random == null) random = new Random();
        int int_1 = countExtraChanceDecoratorConfig_1.count;
        if (random.nextFloat() < countExtraChanceDecoratorConfig_1.extraChance) {
            int_1 += countExtraChanceDecoratorConfig_1.extraCount;
        }

        return IntStream.range(0, int_1).mapToObj((int_1x) -> {
            int int_2 = random.nextInt(16) + blockPos_1.getX();
            int int_3 = random.nextInt(16) + blockPos_1.getZ();
            int int_4 = iWorld_1.getTopY(Heightmap.Type.MOTION_BLOCKING, int_2, int_3);
            return new BlockPos(int_2, int_4, int_3);
        });
    }
}