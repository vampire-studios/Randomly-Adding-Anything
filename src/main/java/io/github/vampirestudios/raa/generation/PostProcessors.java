package io.github.vampirestudios.raa.generation;

import java.util.function.LongFunction;

public enum PostProcessors {
	RIVERS(RiverPostProcessor::new),
	SIMPLEX_CAVES(CavePostProcessor::new);

	public LongFunction<TerrainPostProcessor> factory;

	PostProcessors(LongFunction<TerrainPostProcessor> factory) {

		this.factory = factory;
	}
}