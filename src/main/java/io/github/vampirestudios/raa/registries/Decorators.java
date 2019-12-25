package io.github.vampirestudios.raa.registries;

import io.github.vampirestudios.raa.generation.decorator.BiasedNoiseBasedDecorator;
import io.github.vampirestudios.raa.generation.decorator.BiasedNoiseBasedDecoratorConfig;
import io.github.vampirestudios.raa.generation.decorator.RandomExtraHeightmapDecorator;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.decorator.CountExtraChanceDecoratorConfig;

import static io.github.vampirestudios.raa.RandomlyAddingAnything.MOD_ID;

public class Decorators {
    public static BiasedNoiseBasedDecorator BIASED_NOISE_DECORATOR;
    public static RandomExtraHeightmapDecorator RANDOM_EXTRA_HEIGHTMAP_DECORATOR;

    public static void init() {
        BIASED_NOISE_DECORATOR = Registry.register(Registry.DECORATOR, new Identifier(MOD_ID, "biased_noise"), new BiasedNoiseBasedDecorator(BiasedNoiseBasedDecoratorConfig::deserialize));
        RANDOM_EXTRA_HEIGHTMAP_DECORATOR = Registry.register(Registry.DECORATOR, new Identifier(MOD_ID, "random_extra_heightmap"), new RandomExtraHeightmapDecorator(CountExtraChanceDecoratorConfig::deserialize));
    }
}
