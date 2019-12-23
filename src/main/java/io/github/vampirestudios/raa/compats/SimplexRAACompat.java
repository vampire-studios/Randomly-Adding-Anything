package io.github.vampirestudios.raa.compats;

import com.google.common.collect.ImmutableList;
import io.github.vampirestudios.raa.registries.CustomOverworldPostProcessors;
import supercoder79.simplexterrain.world.postprocessor.PostProcessors;

import java.util.List;

public class SimplexRAACompat {

    public static void init() {
        List<PostProcessors> list = ImmutableList.of(PostProcessors.SIMPLEX_CAVES, PostProcessors.EROSION, PostProcessors.SOIL,
                PostProcessors.STRATA);
        for (PostProcessors postProcess : list) {
            postProcess.postProcessor.setup();
        }
        CustomOverworldPostProcessors.init(list);
    }
}
