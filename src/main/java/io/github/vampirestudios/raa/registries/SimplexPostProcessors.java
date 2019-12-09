package io.github.vampirestudios.raa.registries;

import io.github.vampirestudios.raa.generation.CavePostProcessor;
import io.github.vampirestudios.raa.generation.RiverPostProcessor;
import io.github.vampirestudios.raa.generation.chunkgenerator.OverworldChunkGenerator;

public class SimplexPostProcessors {
	public static void init() {
		OverworldChunkGenerator.addTerrainPostProcessor(RiverPostProcessor::new);
		OverworldChunkGenerator.addTerrainPostProcessor(CavePostProcessor::new);
	}
}