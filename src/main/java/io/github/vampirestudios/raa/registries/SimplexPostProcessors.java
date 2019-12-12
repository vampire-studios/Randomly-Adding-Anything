package io.github.vampirestudios.raa.registries;

import io.github.vampirestudios.raa.generation.CavePostProcessor;
import io.github.vampirestudios.raa.generation.RiverPostProcessor;
import io.github.vampirestudios.raa.generation.chunkgenerator.CustomOverworldChunkGenerator;

public class SimplexPostProcessors {
	public static void init() {
		CustomOverworldChunkGenerator.addTerrainPostProcessor(RiverPostProcessor::new);
		CustomOverworldChunkGenerator.addTerrainPostProcessor(CavePostProcessor::new);
	}
}