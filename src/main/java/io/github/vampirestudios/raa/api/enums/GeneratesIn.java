package io.github.vampirestudios.raa.api.enums;

import io.github.vampirestudios.raa.world.gen.feature.OreFeatureConfig;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public enum GeneratesIn {
    STONE(Blocks.STONE, OreFeatureConfig.Target.STONE),
    GRASS_BLOCK(Blocks.GRASS_BLOCK, OreFeatureConfig.Target.GRASS_BLOCK),
    DIRT_SURFACE(Blocks.DIRT, OreFeatureConfig.Target.DIRT),
    GRAVEL(Blocks.GRAVEL, OreFeatureConfig.Target.GRAVEL),
    SAND_DESERT(Blocks.SAND, OreFeatureConfig.Target.SAND),
    DIORITE(Blocks.DIORITE, OreFeatureConfig.Target.DIORITE),
    ANDESITE(Blocks.ANDESITE, OreFeatureConfig.Target.ANDESITE),
    GRANITE(Blocks.GRANITE, OreFeatureConfig.Target.GRANITE),
    SAND_BEACH(Blocks.SAND, OreFeatureConfig.Target.SAND),
    DIRT_ANY(Blocks.DIRT, OreFeatureConfig.Target.DIRT),
    SAND_ANY(Blocks.SAND, OreFeatureConfig.Target.SAND),
    DIRT_UNDERGROUND(Blocks.DIRT, OreFeatureConfig.Target.DIRT),
    RED_SAND(Blocks.RED_SAND, OreFeatureConfig.Target.RED_SAND),
    END_STONE(Blocks.END_STONE, OreFeatureConfig.Target.END_STONE),
    NETHERRACK(Blocks.NETHERRACK, OreFeatureConfig.Target.NETHERRACK),
    DOES_NOT_APPEAR(null, null),
    BIOME_SPECIFIC(Blocks.STONE, null),
    CLAY(Blocks.CLAY, OreFeatureConfig.Target.CLAY),
    PODZOL(Blocks.PODZOL, OreFeatureConfig.Target.PODZOL),
    COARSE_DIRT(Blocks.COARSE_DIRT, OreFeatureConfig.Target.COARSE_DIRT);

    private Block block;
    private OreFeatureConfig.Target target;

    GeneratesIn(Block block, OreFeatureConfig.Target target) {
        this.block = block;
        this.target = target;
    }

    public Block getBlock() {
        if (block == null) {
            return Blocks.STONE;
        } else {
            return block;
        }
    }

    public OreFeatureConfig.Target getTarget() {
        return target;
    }
}
