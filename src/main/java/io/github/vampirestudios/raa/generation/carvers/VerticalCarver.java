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

public class VerticalCarver extends RAACarver<ProbabilityConfig> {
    public VerticalCarver( DimensionData data) {
        super(ProbabilityConfig::deserialize, data);
    }

    @Override
    public boolean carve(Chunk chunk, Function<BlockPos, Biome> posToBiome, Random random, int seaLevel, int chunkX, int chunkZ, int mainChunkX, int mainChunkZ, BitSet carvingMask, ProbabilityConfig carverConfig) {

        double x = (chunkX * 16 + random.nextInt(16));
        double y = random.nextInt(70);
        double z = (chunkZ * 16 + random.nextInt(16));

        for(int p = 0; p < 3; ++p) {
            y-=5;
            float t;
            t = 2.0F + random.nextFloat() * 3.0F;
            this.carveCave(chunk, posToBiome, random.nextLong(), seaLevel, mainChunkX, mainChunkZ, x, y, z, t, 0.35, carvingMask);
        }
        return true;
    }

    protected void carveCave(Chunk chunk, Function<BlockPos, Biome> posToBiome, long seed, int seaLevel, int mainChunkX, int mainChunkZ, double x, double y, double z, float yaw, double yawPitchRatio, BitSet carvingMask) {
        double d = 1.5D + (double)(MathHelper.sin(1.5707964F) * yaw);
        double e = d * yawPitchRatio;
        this.carveRegion(chunk, posToBiome, seed, seaLevel, mainChunkX, mainChunkZ, x + 1.0D, y, z, d, e, carvingMask);
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
