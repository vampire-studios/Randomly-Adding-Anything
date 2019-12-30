package io.github.vampirestudios.raa.registries;

import io.github.vampirestudios.raa.RandomlyAddingAnything;
import net.minecraft.block.Block;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class RAATags {

    public static final Tag<Block> UNDERGROUND_BLOCKS = new Tag<>(new Identifier(RandomlyAddingAnything.MOD_ID, "underground_blocks"));

}
