package io.github.vampirestudios.raa.generation.chunkgenerator;

import io.github.vampirestudios.raa.utils.OctaveOpenSimplexNoise;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.noise.OctavePerlinNoiseSampler;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.Heightmap;
import net.minecraft.world.Heightmap.Type;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class SuperCustomChunkGenerator extends ChunkGenerator<SuperCustomChunkGeneratorConfig> {

	private final ChunkRandom random;
	private final long seed;

	private final OctavePerlinNoiseSampler surfaceBuilderBlockNoise;
	private OctaveOpenSimplexNoise sampler0; // can be null
	private final OctaveOpenSimplexNoise sampler1;
	private final OctaveOpenSimplexNoise sampler2;

	private final int seaLevel;
	private final boolean useSampler0;

	private final BlockState defaultBlock, defaultFluid;

	public SuperCustomChunkGenerator(IWorld world, BiomeSource biomeSource, SuperCustomChunkGeneratorConfig config) {
		super(world, biomeSource, config);

		this.defaultBlock = config.getDefaultBlock();
		this.defaultFluid = config.getDefaultFluid();

		this.seed = world.getSeed();
		this.random = new ChunkRandom(this.seed);
		this.seaLevel = config.seaLevel;

		this.useSampler0 = config.enableLargeSampler;

		if (useSampler0) {
			sampler0 = new OctaveOpenSimplexNoise(this.random, 3, 1000.0, config.largeSamplerAmplitude, config.largeSamplerAmplitude);
		}

		sampler1 = new OctaveOpenSimplexNoise(this.random, 6, config.noiseStretch, 1D, 1D);
		sampler2 = new OctaveOpenSimplexNoise(this.random, 1, 7.5, 5.0, 5.0);

		this.surfaceBuilderBlockNoise = new OctavePerlinNoiseSampler(this.random, 3, 0);
	}

	@Override
	public void buildSurface(ChunkRegion chunkRegion, Chunk chunk) {
		int startX = chunk.getPos().getStartX();
		int startZ = chunk.getPos().getStartZ();

		BlockPos.Mutable pos = new BlockPos.Mutable();

		for (int localX = 0; localX < 16; ++localX) {
			for (int localZ = 0; localZ < 16; ++localZ) {
				int x = localX + startX;
				int z = localZ + startZ;
				int height = chunk.sampleHeightmap(Heightmap.Type.WORLD_SURFACE_WG, localX, localZ) + 1;

				double noise = this.surfaceBuilderBlockNoise.sample((double)x * 0.0625D, (double)z * 0.0625D, 0.0625D, (double)localX * 0.0625D) * 15.0D;
				chunkRegion.method_23753(pos.set(x, height, z)).buildSurface(random, chunk, x, z, height, noise, this.getConfig().getDefaultBlock(), this.getConfig().getDefaultFluid(), this.getSeaLevel(), this.world.getSeed());
			}
		}
	}

	@Override
	public int getSpawnHeight() {
		return this.getSeaLevel() + 1;
	}

	@Override
	public int getSeaLevel() {
		return seaLevel;
	}

	@Override
	public void populateNoise(IWorld world, Chunk chunk) {
		int chunkX = chunk.getPos().x;
		int chunkZ = chunk.getPos().z;

		BlockPos.Mutable pos = new BlockPos.Mutable();

		for (int subChunkX = 0; subChunkX < 4; ++subChunkX)  {
			for (int subChunkZ = 0; subChunkZ < 4; ++subChunkZ)  {
				double[] low = sampleNoise(world, subChunkX, subChunkX, chunkX, chunkZ, 0);
				double[] high = sampleNoise(world, subChunkX, subChunkX, chunkX, chunkZ, 1);

				for (int subChunkY = 0; subChunkY < 32; ++subChunkY) {
					// TODO generate
					for (int localX = 0; localX < 4; ++localX) {
						pos.setX((subChunkX << 2) + localX);
						double deltaX = (double) localX / 4.0;

						for (int localZ = 0; localZ < 4; ++localZ) {
							pos.setZ((subChunkZ << 2) + localZ);
							double deltaZ = (double) localZ / 4.0;

							// start interpolation
							// nw = 0, sw = 1, ne = 2, se = 3
							double lowVal = lerp(
									deltaZ,
									lerp(deltaX, low[0], low[2]),
									lerp(deltaX, low[1], low[3]));

							double highVal = lerp(
									deltaZ,
									lerp(deltaX, high[0], high[2]),
									lerp(deltaX, high[1], high[3]));

							for (int localY = 0; localY < 8; ++localY) {
								int y = (subChunkY << 3) + localY;
								pos.setY(y);

								double deltaY = (double) localY / 8.0;
								double height = lerp(deltaY, lowVal, highVal);

								BlockState toSet = AIR;
								if (y < height) {
									toSet = this.defaultBlock;
								} else if (y < seaLevel) {
									toSet = this.defaultFluid;
								}

								chunk.setBlockState(pos, toSet, false);
							}
						}
					}
					low = high;
					high = sampleNoise(world, subChunkX, subChunkX, chunkX, chunkZ, subChunkY + 2);
				}
			}
		}
	}

	private static final BlockState AIR = Blocks.AIR.getDefaultState();

	private static double lerp(double delta, double low, double high) {
		return low + delta * (high - low);
	}

	private double[] sampleNoise(IWorld source, int subChunkX, int subChunkZ, int chunkX, int chunkZ, int subChunkY) {
		int x1 = (chunkX << 2) + subChunkX;
		int z1 = (chunkZ << 2) + subChunkZ;

		int x2 = (chunkX << 2) + 1 + subChunkX;
		int z2 = (chunkZ << 2) + 1 + subChunkZ;

		double[] result = new double[4];

		int biomeSampleY = (subChunkY << 1);

		// getStoredBiome is actually getBiomeForNoiseGen
		result[0] = sample(x1, subChunkY, z1, source.getStoredBiome(x1, biomeSampleY, z1));
		result[1] = sample(x1, subChunkY, z2, source.getStoredBiome(x1, biomeSampleY, z2));
		result[2] = sample(x2, subChunkY, z1, source.getStoredBiome(x2, biomeSampleY, z1));
		result[3] = sample(x2, subChunkY, z2, source.getStoredBiome(x2, biomeSampleY, z2));

		return result;
	}

	private double sample(double x, double y, double z, Biome biom) {
		SuperCustomBiome biome;

		if (biom instanceof SuperCustomBiome) {
			biome = (SuperCustomBiome) biom;
		} else {
			throw new RuntimeException("Biome for generating with SuperCustomChunkGenerator is not a SuperCustomBiome!");
		}

		double result = 0.0;

		if (useSampler0) {
			result += sampler0.sample(x, z);
		}

		result += sampler1.sampleCustom(x, y * 0.0625D, z, biome.stretch, biome.variationAbove, biome.variationBelow, biome.octaves);
		result += sampler2.sample(x, z);
		return result;
	}

	@Override
	public int getHeightOnGround(int x, int z, Type heightmapType) {
		int chunkX = (x >> 4);
		int chunkZ = (z >> 4);

		int subChunkX = (x >> 2) & 3;
		int subChunkZ = (z >> 2) & 3;

		int resultHeight = 0;

		BlockPos.Mutable pos = new BlockPos.Mutable();

		double[] low = sampleNoise(world, subChunkX, subChunkX, chunkX, chunkZ, 0);
		double[] high = sampleNoise(world, subChunkX, subChunkX, chunkX, chunkZ, 1);

		for (int subChunkY = 0; subChunkY < 32; ++subChunkY) {
			// TODO generate
			for (int localX = 0; localX < 4; ++localX) {
				pos.setX((subChunkX << 2) + localX);
				double deltaX = (double) localX / 4.0;

				for (int localZ = 0; localZ < 4; ++localZ) {
					pos.setZ((subChunkZ << 2) + localZ);
					double deltaZ = (double) localZ / 4.0;

					// start interpolation
					// nw = 0, sw = 1, ne = 2, se = 3
					double lowVal = lerp(
							deltaZ,
							lerp(deltaX, low[0], low[2]),
							lerp(deltaX, low[1], low[3]));

					double highVal = lerp(
							deltaZ,
							lerp(deltaX, high[0], high[2]),
							lerp(deltaX, high[1], high[3]));

					for (int localY = 0; localY < 8; ++localY) {
						int y = (subChunkY << 3) + localY;
						pos.setY(y);

						double deltaY = (double) localY / 8.0;
						double height = lerp(deltaY, lowVal, highVal);

						BlockState toSet = AIR;
						if (y < height) {
							toSet = this.defaultBlock;
						} else if (y < seaLevel) {
							toSet = this.defaultFluid;
						}

						if (heightmapType.getBlockPredicate().test(toSet)) {
							resultHeight = (int) height;
						}
					}
				}
			}
			low = high;
			high = sampleNoise(world, subChunkX, subChunkX, chunkX, chunkZ, subChunkY + 2);
		}
		
		return resultHeight;
	}

}
