package io.github.vampirestudios.raa.world.gen.feature;

import net.minecraft.world.gen.decorator.RangeDecoratorConfig;

public class SimpleRangeDecoratorConfig extends RangeDecoratorConfig {

    // count = veins per chunk
    public SimpleRangeDecoratorConfig(int count, int min, int max) {
        super(count, min, min, max);
    }

}