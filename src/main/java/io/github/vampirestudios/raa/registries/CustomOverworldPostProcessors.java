package io.github.vampirestudios.raa.registries;

import io.github.vampirestudios.raa.generation.chunkgenerator.CustomOverworldChunkGenerator;
import supercoder79.simplexterrain.world.postprocess.CavePostProcessor;
import supercoder79.simplexterrain.world.postprocess.ErosionPostProcessor;
import supercoder79.simplexterrain.world.postprocess.RiverPostProcessor;
import supercoder79.simplexterrain.world.postprocess.StrataPostProcessor;

public class CustomOverworldPostProcessors {

    public static void init() {
        CustomOverworldChunkGenerator.addTerrainPostProcessor(RiverPostProcessor::new);
        CustomOverworldChunkGenerator.addTerrainPostProcessor(CavePostProcessor::new);
        CustomOverworldChunkGenerator.addTerrainPostProcessor(ErosionPostProcessor::new);
        CustomOverworldChunkGenerator.addTerrainPostProcessor(StrataPostProcessor::new);
    }

}
