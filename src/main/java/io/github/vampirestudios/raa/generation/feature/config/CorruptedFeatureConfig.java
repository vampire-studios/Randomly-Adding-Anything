package io.github.vampirestudios.raa.generation.feature.config;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import net.minecraft.world.gen.feature.FeatureConfig;

public class CorruptedFeatureConfig implements FeatureConfig {
    public boolean corrupted = false;

    public CorruptedFeatureConfig(boolean corrupted) {
        this.corrupted = corrupted;
    }

    @Override
    public <T> Dynamic<T> serialize(DynamicOps<T> dynamicOps_1) {
        return new Dynamic(dynamicOps_1, dynamicOps_1.createMap(ImmutableMap.of(dynamicOps_1.createString("corrupted"), dynamicOps_1.createBoolean(this.corrupted))));
    }

    public static <T> CorruptedFeatureConfig deserialize(Dynamic<T> dynamic_1) {
        return new CorruptedFeatureConfig(dynamic_1.get("corrupted").asBoolean(false));
    }
}
