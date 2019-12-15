package io.github.vampirestudios.raa.generation.dimensions;

import io.github.vampirestudios.raa.world.gen.feature.OreFeatureConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.predicate.block.BlockPredicate;

import java.util.function.Predicate;

public class Csoct extends OreFeatureConfig.Target {

    private Csoct(String name, Predicate<BlockState> predicate, Block block) {
        super(name, predicate, block);
    }

    public Csoct(String name, Block block) {
        this(name, new BlockPredicate(block), block);
    }

}
