package io.github.vampirestudios.raa.predicate.block;

import net.minecraft.block.Block;

import java.util.function.Predicate;

public class BlockPredicate implements Predicate<Block> {

    private final Block block;

    public BlockPredicate(Block block_1) {
        this.block = block_1;
    }

    public static BlockPredicate make(Block block_1) {
        return new BlockPredicate(block_1);
    }

    public boolean test(Block block) {
        return block != null && block == this.block;
    }

}
