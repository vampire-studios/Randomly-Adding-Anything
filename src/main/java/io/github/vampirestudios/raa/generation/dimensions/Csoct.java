package io.github.vampirestudios.raa.generation.dimensions;

import io.github.vampirestudios.raa.world.gen.feature.OreFeatureConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;

import java.util.function.Predicate;

public class Csoct extends OreFeatureConfig.Target {

    public Csoct(String name, Predicate<BlockState> predicate, Block block) {
        super(name, predicate, block);
    }

}
