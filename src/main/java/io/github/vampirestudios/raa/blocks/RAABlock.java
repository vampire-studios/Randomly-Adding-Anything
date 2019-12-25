package io.github.vampirestudios.raa.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;

import java.util.ArrayList;
import java.util.List;

public class RAABlock extends Block {
    public RAABlock() {
        super(Settings.copy(Blocks.STONE));
    }

    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder) {
        List<ItemStack> list = new ArrayList<>();
        list.add(new ItemStack(state.getBlock().asItem()));
        return list;
    }

}
