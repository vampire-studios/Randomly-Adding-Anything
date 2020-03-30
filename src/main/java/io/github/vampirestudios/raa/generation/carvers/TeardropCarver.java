package io.github.vampirestudios.raa.generation.carvers;

import com.mojang.datafixers.Dynamic;
import io.github.vampirestudios.raa.generation.dimensions.data.DimensionData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.carver.Carver;

import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;

public class TeardropCarver extends RAACarver<ProbabilityConfig> {
    public TeardropCarver( DimensionData data) {
        super(ProbabilityConfig::deserialize, data);
    }

    @Override
    public boolean carve(Chunk chunk, Function<BlockPos, Biome> posToBiome, Random random, int seaLevel, int chunkX, int chunkZ, int mainChunkX, int mainChunkZ, BitSet carvingMask, ProbabilityConfig carverConfig) {

        //initialize variables
        int x = (chunkX* 16) + random.nextInt(16);
        int z = (chunkZ* 16) + random.nextInt(16);
        int y = random.nextInt(256);
        double yaw = 1;
        double pitch = 3;
        double xzs = 0.5;
        for (int i = 0; i < 8; i++) {
            y -= 1; //lower the carving region
            xzs += 0.0157; //increase the horizontal stretch
            this.carveRegion(chunk,
                    posToBiome,
                    random.nextInt(),
                    seaLevel,
                    mainChunkX,
                    mainChunkZ,
                    x,
                    y,
                    z,
                    yaw + (i * xzs),
                    pitch / (i / xzs),
                    carvingMask);
        }

        //ease out the bubble
        this.carveRegion(chunk,
                posToBiome,
                random.nextInt(),
                seaLevel,
                mainChunkX,
                mainChunkZ,
                x,
                y - 1,
                z,
                yaw + (5 * xzs),
                pitch / (5 / xzs),
                carvingMask);
        return true;
    }

    @Override
    public boolean shouldCarve(Random random, int chunkX, int chunkZ, ProbabilityConfig config) {
        return random.nextFloat() <= config.probability;
    }

    @Override
    protected boolean isPositionExcluded(double scaledRelativeX, double scaledRelativeY, double scaledRelativeZ, int y) {
        return false;
    }
}
