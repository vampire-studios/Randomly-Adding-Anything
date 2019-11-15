package io.github.vampirestudios.raa.blocks;

import io.github.vampirestudios.raa.utils.Rands;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public class DimensionalBlock extends Block {
    public DimensionalBlock() {
        super(Block.Settings.copy(Blocks.STONE).strength(Rands.randFloatRange(0.25f, 4), Rands.randFloatRange(4, 20)));
    }
}
