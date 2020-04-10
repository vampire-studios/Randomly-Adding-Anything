package io.github.vampirestudios.raa.generation.dimensions.data;

import io.github.vampirestudios.vampirelib.utils.registry.WoodType;
import net.minecraft.block.Blocks;

public enum DimensionWoodType {
    OAK(WoodType.OAK),
    BIRCH(WoodType.BIRCH),
    SPRUCE(WoodType.SPRUCE),
    JUNGLE(WoodType.JUNGLE),
    ACACIA(WoodType.ACACIA),
    DARK_OAK(WoodType.DARK_OAK),
    CRIMSON(new WoodType("crimson", Blocks.NETHER_WART_BLOCK, Blocks.CRIMSON_STEM)),
    WARPED(new WoodType("warped", Blocks.WARPED_WART_BLOCK, Blocks.WARPED_STEM)),
    STRIPPED_CRIMSON(new WoodType("stripped_crimson", Blocks.NETHER_WART, Blocks.STRIPPED_CRIMSON_STEM)),
    STRIPPED_WARPED(new WoodType("stripped_warped", Blocks.WARPED_WART_BLOCK, Blocks.STRIPPED_WARPED_STEM)),
    TESTING(new WoodType("raa:testing", Blocks.PURPLE_STAINED_GLASS, Blocks.SPRUCE_LOG));

    public WoodType woodType;

    DimensionWoodType(WoodType woodType) {
        this.woodType = woodType;
    }
}
