package io.github.vampirestudios.raa.itemGroup;

import io.github.vampirestudios.raa.RandomlyAddingAnything;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.util.List;

public class RAAItemGroup extends TabbedItemGroup {

	public RAAItemGroup(Identifier id)
	{
		super(id);
	}

	@Override
	public void initTabs(List<ItemTab> tabs) {
		tabs.add(new ItemTab(new ItemStack(Blocks.IRON_ORE), "ores", RandomlyAddingAnything.ORES));
		tabs.add(new ItemTab(new ItemStack(Items.IRON_INGOT), "resources", RandomlyAddingAnything.RESOURCES));
		tabs.add(new ItemTab(new ItemStack(Items.IRON_PICKAXE),"tools", RandomlyAddingAnything.TOOLS));
		tabs.add(new ItemTab(new ItemStack(Items.IRON_HELMET),"armor", RandomlyAddingAnything.ARMOR));
		tabs.add(new ItemTab(new ItemStack(Items.IRON_SWORD),"weapons", RandomlyAddingAnything.WEAPONS));
		tabs.add(new ItemTab(new ItemStack(Items.GOLDEN_APPLE),"food", RandomlyAddingAnything.FOOD));
		tabs.add(new ItemTab(new ItemStack(Blocks.STONE),"dimension_blocks", RandomlyAddingAnything.DIMENSION_BLOCKS));
	}

	@Override
	public ItemStack createIcon()
	{
		return new ItemStack(Items.GOLD_BLOCK);
	}

}