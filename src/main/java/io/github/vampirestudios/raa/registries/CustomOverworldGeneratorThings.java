package io.github.vampirestudios.raa.registries;

import supercoder79.simplexterrain.world.gen.SimplexChunkGenerator;
import supercoder79.simplexterrain.world.noisemodifier.NoiseModifiers;
import supercoder79.simplexterrain.world.postprocessor.PostProcessors;

import java.util.List;

public class CustomOverworldGeneratorThings {

    public static void init(List<PostProcessors> postProcessors, List<NoiseModifiers> noiseModifiers) {
        for (PostProcessors postProcess : postProcessors) {
            SimplexChunkGenerator.addTerrainPostProcessor(postProcess.postProcessor);
        }
        for (NoiseModifiers modifier : noiseModifiers) {
            SimplexChunkGenerator.addNoiseModifier(modifier.noiseModifier);
        }
    }

}
