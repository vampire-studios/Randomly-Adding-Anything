package io.github.vampirestudios.raa.compats;

import com.google.common.collect.ImmutableList;
import io.github.vampirestudios.raa.registries.CustomOverworldGeneratorThings;
import supercoder79.simplexterrain.world.noisemodifier.NoiseModifiers;
import supercoder79.simplexterrain.world.postprocessor.PostProcessors;

import java.util.List;

public class SimplexRAACompat {

    public static void init() {
        List<PostProcessors> postProcessors = ImmutableList.of(PostProcessors.SIMPLEX_CAVES, PostProcessors.EROSION, PostProcessors.SOIL,
                PostProcessors.STRATA);
        for (PostProcessors postProcess : postProcessors) {
            postProcess.postProcessor.setup();
        }

        List<NoiseModifiers> modifiers = ImmutableList.of(NoiseModifiers.DOMES, NoiseModifiers.FJORDS, NoiseModifiers.MESA,
                NoiseModifiers.PEAKS, NoiseModifiers.SANDBARS, NoiseModifiers.VENTS);
        for (NoiseModifiers postProcess : modifiers) {
            postProcess.noiseModifier.setup();
        }
        CustomOverworldGeneratorThings.init(postProcessors, modifiers);
    }
}
