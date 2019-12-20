package io.github.vampirestudios.raa.generation.materials;

import io.github.vampirestudios.raa.api.RAARegisteries;
import io.github.vampirestudios.raa.api.RAAWorldAPI;
import net.minecraft.util.registry.Registry;

public class MaterialOreGenerator {

    public static void init() {
        Registry.BIOME.forEach(biome -> RAARegisteries.TARGET_REGISTRY.forEach(target -> RAAWorldAPI.generateOresForTarget(biome, target)));
    }

}
