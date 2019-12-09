package io.github.vampirestudios.raa.generation;

import io.github.vampirestudios.raa.api.Heightmap;
import net.minecraft.world.IWorld;

import java.util.Random;

public interface TerrainPostProcessor {
	void postProcess(IWorld world, Random rand, int chunkX, int chunkZ, Heightmap heightmap);
}