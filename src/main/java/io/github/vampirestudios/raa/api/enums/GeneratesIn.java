package io.github.vampirestudios.raa.api.enums;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public enum GeneratesIn {
    STONE(Blocks.STONE),
    GRASS_BLOCK(Blocks.GRASS_BLOCK),
    DIRT_SURFACE(Blocks.DIRT),
    GRAVEL(Blocks.GRAVEL),
    SAND_DESERT(Blocks.SAND),
    DIORITE(Blocks.DIORITE),
    ANDESITE(Blocks.ANDESITE),
    GRANITE(Blocks.GRANITE),
    SAND_BEACH(Blocks.SAND),
    DIRT_ANY(Blocks.DIRT),
    SAND_ANY(Blocks.SAND),
    DIRT_UNDERGROUND(Blocks.DIRT),
    SAND_ANY2(Blocks.SAND),
    RED_SAND(Blocks.RED_SAND),
    END_STONE(Blocks.END_STONE),
    NETHERRACK(Blocks.NETHERRACK),
    DOES_NOT_APPEAR(null),
    BIOME_SPECIFIC(Blocks.STONE),
    CLAY(Blocks.CLAY),
    PODZOL(Blocks.PODZOL),
    COARSE_DIRT(Blocks.COARSE_DIRT);

    private Block block;

    GeneratesIn(Block block) {
        this.block = block;
    }

    public Block getBlock() {
        if (block == null) {
            return Blocks.STONE;
        } else {
            return block;
        }
    }
}
