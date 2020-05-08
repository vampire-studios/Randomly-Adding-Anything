package io.github.vampirestudios.raa.blocks;

import io.github.vampirestudios.raa.generation.dimensions.data.DimensionData;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public class DimensionalStone extends Block {
    private final DimensionData dimensionData;
    private Identifier name;

    public DimensionalStone(Identifier name, DimensionData dimensionData) {
        super(Settings.copy(Blocks.STONE).strength(dimensionData.getStoneHardness(), dimensionData.getStoneResistance()));
        this.dimensionData = dimensionData;
        this.name = name;
    }

    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder) {
        List<ItemStack> list = new ArrayList<>();
        list.add(new ItemStack(Registry.BLOCK.get(new Identifier(name.getNamespace(), name.getPath() + "_cobblestone")).asItem()));
        return list;
    }
}
