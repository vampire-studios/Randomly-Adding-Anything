package io.github.vampirestudios.raa.registries;

import io.github.vampirestudios.raa.generation.chunkgenerator.CustomOverworldChunkGenerator;
import supercoder79.simplexterrain.world.postprocess.PostProcessors;

import java.util.List;

public class CustomOverworldPostProcessors {

    public static void init(List<PostProcessors> postProcessors) {
        for (PostProcessors postProcess : postProcessors) {
            CustomOverworldChunkGenerator.addTerrainPostProcessor(postProcess.postProcessor);
        }
    }

}
