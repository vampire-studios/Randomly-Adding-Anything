package io.github.vampirestudios.raa.blocks;

import io.github.vampirestudios.raa.utils.Rands;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;

import java.util.ArrayList;
import java.util.List;

public class DimensionalBlock extends Block {

    public DimensionalBlock() {
        super(Block.Settings.copy(Blocks.STONE).strength(Rands.randFloatRange(0.25f, 4), Rands.randFloatRange(4, 20)));
    }

    public DimensionalBlock(Block block, float hardness, float resistance) {
        super(Block.Settings.copy(block).strength(hardness, resistance));
    }

    public DimensionalBlock(Block block, float min_hardness, float max_hardness, float min_resistance, float max_resistance) {
        super(Block.Settings.copy(block).strength(Rands.randFloatRange(min_hardness, max_hardness), Rands.randFloatRange(min_resistance, max_resistance)));
    }

    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder) {
        List<ItemStack> list = new ArrayList<>();
        list.add(new ItemStack(state.getBlock().asItem()));
        return list;
    }
}
