package io.github.vampirestudios.raa;

import io.github.vampirestudios.raa.api.Heightmap;
import io.github.vampirestudios.raa.api.TerrainPostProcessor;
import io.github.vampirestudios.raa.api.noise.OctaveNoiseSampler;
import io.github.vampirestudios.raa.api.noise.noises.OpenSimplexNoise;
import io.github.vampirestudios.raa.generation.dimensions.data.DimensionData;
import io.github.vampirestudios.raa.utils.Utils;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkRandom;

import java.util.Random;

public class ErosionPostProcessor implements TerrainPostProcessor {
	private OctaveNoiseSampler sampler;
	private DimensionData dimensionData;

	public ErosionPostProcessor(DimensionData dimensionData) {
		this.dimensionData = dimensionData;
	}

	@Override
	public void init(long seed) {
		sampler = new OctaveNoiseSampler<>(OpenSimplexNoise.class, new ChunkRandom(seed), dimensionData.getDimensionErosion().getOctaves(), dimensionData.getDimensionErosion().getFrequency(), dimensionData.getDimensionErosion().getAmplitudeHigh(), dimensionData.getDimensionErosion().getAmplitudeLow());
	}

	@Override
	public void setup() {

	}

	@Override
	public void process(IWorld world, Random rand, int chunkX, int chunkZ, Heightmap heightmap) {
		int[] heights = heightmap.getHeightsInChunk(new ChunkPos(chunkX, chunkZ));
		BlockPos.Mutable mutable = new BlockPos.Mutable();
		for (int x = 0; x < 16; x++) {
			mutable.setX(chunkX*16 + x);
			for (int z = 0; z < 16; z++) {
				mutable.setZ(chunkZ*16 + z);
				double sample = sampler.sample(chunkX*16 + x, chunkZ*16 + z) + dimensionData.getDimensionErosion().getBaseNoise();
				if (sample < dimensionData.getDimensionErosion().getThreshold() && heights[x*16 + z] > world.getSeaLevel()) {
					for (int y = 0; y < Math.abs(sample); y++) {
						mutable.setY(heights[x*16 + z] - y);
						world.setBlockState(mutable, Blocks.AIR.getDefaultState(), 2);
					}
					if (world.getBlockState(mutable.down()) == Registry.BLOCK.get(Utils.addSuffixToPath(dimensionData.getId(), "_stone")).getDefaultState()) world.setBlockState(mutable.down(), Registry.BLOCK.get(Utils.addSuffixToPath(dimensionData.getId(), "_cobblestone")).getDefaultState(), 2);
				}
			}
		}
	}
}