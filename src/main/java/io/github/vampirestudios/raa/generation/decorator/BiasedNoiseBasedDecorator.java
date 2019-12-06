package io.github.vampirestudios.raa.generation.decorator;

import com.mojang.datafixers.Dynamic;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.noise.OctaveSimplexNoiseSampler;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.decorator.Decorator;

import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class BiasedNoiseBasedDecorator extends Decorator<BiasedNoiseBasedDecoratorConfig> {
    public static final OctaveSimplexNoiseSampler NOISE = new OctaveSimplexNoiseSampler(new ChunkRandom(79L), 2, 0);

    public BiasedNoiseBasedDecorator(Function<Dynamic<?>, ? extends BiasedNoiseBasedDecoratorConfig> function_1) {
        super(function_1);
    }

    @Override
    public Stream<BlockPos> getPositions(IWorld world, ChunkGenerator<? extends ChunkGeneratorConfig> var2, Random random, BiasedNoiseBasedDecoratorConfig config, BlockPos pos) {
        double noise = NOISE.sample((double) pos.getX() / config.noiseFactor, (double) pos.getZ() / config.noiseFactor, false);
        int int_1 = (int) Math.ceil((noise + config.noiseOffset) * (double) config.noiseToCountRatio);
        //System.out.println(int_1 + " : " + noise);
        return IntStream.range(0, int_1).mapToObj((int_1x) -> {
            int int_2 = random.nextInt(16);
            int int_3 = random.nextInt(16);
            int int_4 = world.getTopPosition(config.heightmap, new BlockPos(pos.getX() + int_2, 0, pos.getZ() + int_3)).getY();
            return new BlockPos(pos.getX() + int_2, int_4, pos.getZ() + int_3);
        });
    }
}
