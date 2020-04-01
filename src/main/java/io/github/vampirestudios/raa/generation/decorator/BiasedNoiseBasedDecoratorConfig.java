package io.github.vampirestudios.raa.generation.decorator;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.decorator.DecoratorConfig;

import java.util.Random;

public class BiasedNoiseBasedDecoratorConfig implements DecoratorConfig {
    public final int noiseToCountRatio;
    public final double noiseFactor;
    public final double noiseOffset;
    public final Heightmap.Type heightmap;

    public BiasedNoiseBasedDecoratorConfig(int noiseToCountRatio, double noiseFactor, double noiseOffset, Heightmap.Type heightmap) {
        this.noiseToCountRatio = noiseToCountRatio;
        this.noiseFactor = noiseFactor;
        this.noiseOffset = noiseOffset;
        this.heightmap = heightmap;
    }

    public static BiasedNoiseBasedDecoratorConfig deserialize(Dynamic<?> dynamic_1) {
        int int_1 = dynamic_1.get("noise_to_count_ratio").asInt(10);
        double double_1 = dynamic_1.get("noise_factor").asDouble(80.0D);
        double double_2 = dynamic_1.get("noise_offset").asDouble(0.0D);
        Heightmap.Type heightmap$Type_1 = Heightmap.Type.byName(dynamic_1.get("heightmap").asString("OCEAN_FLOOR_WG"));
        return new BiasedNoiseBasedDecoratorConfig(int_1, double_1, double_2, heightmap$Type_1);
    }

    public <T> Dynamic<T> serialize(DynamicOps<T> dynamicOps_1) {
        return new Dynamic<>(dynamicOps_1, dynamicOps_1.createMap(ImmutableMap.of(dynamicOps_1.createString("noise_to_count_ratio"), dynamicOps_1.createInt(this.noiseToCountRatio), dynamicOps_1.createString("noise_factor"), dynamicOps_1.createDouble(this.noiseFactor), dynamicOps_1.createString("noise_offset"), dynamicOps_1.createDouble(this.noiseOffset), dynamicOps_1.createString("heightmap"), dynamicOps_1.createString(this.heightmap.getName()))));
    }


    public static BiasedNoiseBasedDecoratorConfig method_26675(Random random) {
        return new BiasedNoiseBasedDecoratorConfig(10, 80.0D, 0.0D, Heightmap.Type.OCEAN_FLOOR_WG);
    }
}
