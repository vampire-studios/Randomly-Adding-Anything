package io.github.vampirestudios.raa.generation.dimensions;

import io.github.vampirestudios.raa.world.gen.feature.OreFeatureConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.predicate.block.BlockPredicate;
import net.minecraft.util.Identifier;

import java.util.function.Predicate;

public class Csoct extends OreFeatureConfig.Target {

    private Csoct(Identifier name, Predicate<BlockState> predicate, Block block) {
        super(name, predicate, block);
    }

    public Csoct(Identifier name, Block block) {
        this(name, new BlockPredicate(block), block);
    }

}
