package io.github.vampirestudios.raa.registries;

import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.client.DimensionalLeafBlock;
import io.github.vampirestudios.vampirelib.utils.registry.RegistryUtils;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;

public class RAAMiscBlocks {

    public static Block ACACIA_LEAVES;
    public static Block BIRCH_LEAVES;
    public static Block DARK_OAK_LEAVES;
    public static Block JUNGLE_LEAVES;
    public static Block OAK_LEAVES;
    public static Block SPRUCE_LEAVES;

    public static void init() {
        ACACIA_LEAVES = RegistryUtils.register(new DimensionalLeafBlock(), new Identifier(RandomlyAddingAnything.MOD_ID, "acacia_leaves"));
        BIRCH_LEAVES = RegistryUtils.register(new DimensionalLeafBlock(), new Identifier(RandomlyAddingAnything.MOD_ID, "birch_leaves"));
        DARK_OAK_LEAVES = RegistryUtils.register(new DimensionalLeafBlock(), new Identifier(RandomlyAddingAnything.MOD_ID, "dark_oak_leaves"));
        JUNGLE_LEAVES = RegistryUtils.register(new DimensionalLeafBlock(), new Identifier(RandomlyAddingAnything.MOD_ID, "jungle_leaves"));
        OAK_LEAVES = RegistryUtils.register(new DimensionalLeafBlock(), new Identifier(RandomlyAddingAnything.MOD_ID, "oak_leaves"));
        SPRUCE_LEAVES = RegistryUtils.register(new DimensionalLeafBlock(), new Identifier(RandomlyAddingAnything.MOD_ID, "spruce_leaves"));
    }

}
