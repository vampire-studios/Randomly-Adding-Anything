package io.github.vampirestudios.raa;

import io.github.vampirestudios.raa.api.Heightmap;
import io.github.vampirestudios.raa.api.TerrainPostProcessor;
import io.github.vampirestudios.raa.generation.dimensions.data.DimensionData;
import io.github.vampirestudios.raa.utils.Utils;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IWorld;

import java.util.Random;

public class StrataPostProcessor implements TerrainPostProcessor {
	private static final BlockState[] states = new BlockState[32];
	private DimensionData dimensionData;

	public StrataPostProcessor(DimensionData dimensionData) {
		this.dimensionData = dimensionData;
	}

	@Override
	public void init(long seed) {
	}

	@Override
	public void setup() {
		for (int i = 0; i < 4; i++) {
			states[i*8] = Blocks.STONE.getDefaultState();
			states[i*8 + 1] = Blocks.ANDESITE.getDefaultState();
			states[i*8 + 2] = Blocks.GRANITE.getDefaultState();
			states[i*8 + 3] = Blocks.GRANITE.getDefaultState();
			states[i*8 + 4] = Blocks.STONE.getDefaultState();
			states[i*8 + 5] = Blocks.DIORITE.getDefaultState();
			states[i*8 + 6] = Blocks.ANDESITE.getDefaultState();
			states[i*8 + 7] = Blocks.STONE.getDefaultState();
		}
	}

	@Override
	public void process(IWorld world, Random rand, int chunkX, int chunkZ, Heightmap heightmap) {
		int[] heights = heightmap.getHeightsInChunk(new ChunkPos(chunkX, chunkZ));
		BlockPos.Mutable mutable = new BlockPos.Mutable();
		for (int x = 0; x < 16; x++) {
			mutable.setX(chunkX*16 + x);
			for (int z = 0; z < 16; z++) {
				mutable.setZ(chunkZ*16 + z);
				for (int y = heights[x* 16 + z]; y > 0; y--) {
					mutable.setY(y);
					if (world.getBlockState(mutable) == Registry.BLOCK.get(Utils.addSuffixToPath(dimensionData.getId(), "_stone")).getDefaultState()) world.setBlockState(mutable, states[y/8], 2);
				}
			}
		}
	}
}