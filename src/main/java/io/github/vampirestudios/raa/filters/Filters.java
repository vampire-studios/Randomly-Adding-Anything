package io.github.vampirestudios.raa.filters;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class Filters implements ModInitializer {

    private static final Filters instance = new Filters();

    private final Map<ItemGroup, Set<FilterEntry>> filterMap = new HashMap<>();

    @Override
    public void onInitialize() {
        this.register(ItemGroup.BUILDING_BLOCKS, new Identifier("building_blocks/natural"), new ItemStack(Blocks.GRASS_BLOCK));
        this.register(ItemGroup.BUILDING_BLOCKS, new Identifier("building_blocks/stones"), new ItemStack(Blocks.STONE));
        this.register(ItemGroup.BUILDING_BLOCKS, new Identifier("building_blocks/woods"), new ItemStack(Blocks.OAK_LOG));
        this.register(ItemGroup.BUILDING_BLOCKS, new Identifier("building_blocks/minerals"), new ItemStack(Blocks.EMERALD_BLOCK));
        this.register(ItemGroup.BUILDING_BLOCKS, new Identifier("stairs"), new ItemStack(Blocks.OAK_STAIRS));
        this.register(ItemGroup.BUILDING_BLOCKS, new Identifier("slabs"), new ItemStack(Blocks.OAK_SLAB));
        this.register(ItemGroup.BUILDING_BLOCKS, new Identifier("forge", "glass"), new ItemStack(Blocks.GLASS));
        this.register(ItemGroup.BUILDING_BLOCKS, new Identifier("building_blocks/colored"), new ItemStack(Blocks.RED_WOOL));
        this.register(ItemGroup.DECORATIONS, new Identifier("decoration_blocks/vegetation"), new ItemStack(Blocks.GRASS));
        this.register(ItemGroup.DECORATIONS, new Identifier("decoration_blocks/functional"), new ItemStack(Blocks.CRAFTING_TABLE));
        this.register(ItemGroup.DECORATIONS, new Identifier("decoration_blocks/fences_and_walls"), new ItemStack(Blocks.OAK_FENCE));
        this.register(ItemGroup.DECORATIONS, new Identifier("decoration_blocks/interior"), new ItemStack(Blocks.RED_BED));
        this.register(ItemGroup.DECORATIONS, new Identifier("decoration_blocks/glass"), new ItemStack(Blocks.GLASS_PANE));
        this.register(ItemGroup.DECORATIONS, new Identifier("decoration_blocks/colored"), new ItemStack(Blocks.GREEN_GLAZED_TERRACOTTA));
        this.register(ItemGroup.DECORATIONS, new Identifier("decoration_blocks/special"), new ItemStack(Blocks.DRAGON_HEAD));
        this.register(ItemGroup.REDSTONE, new Identifier("redstone/core"), new ItemStack(Items.REDSTONE));
        this.register(ItemGroup.REDSTONE, new Identifier("redstone/components"), new ItemStack(Items.STICKY_PISTON));
        this.register(ItemGroup.REDSTONE, new Identifier("redstone/inputs"), new ItemStack(Items.TRIPWIRE_HOOK));
        this.register(ItemGroup.REDSTONE, new Identifier("redstone/doors"), new ItemStack(Items.OAK_DOOR));
        this.register(ItemGroup.TRANSPORTATION, new Identifier("transportation/vehicles"), new ItemStack(Items.MINECART));
        this.register(ItemGroup.MISC, new Identifier("miscellaneous/materials"), new ItemStack(Items.GOLD_INGOT));
        this.register(ItemGroup.MISC, new Identifier("miscellaneous/eggs"), new ItemStack(Items.TURTLE_EGG));
        this.register(ItemGroup.MISC, new Identifier("miscellaneous/plants_and_seeds"), new ItemStack(Items.SUGAR_CANE));
        this.register(ItemGroup.MISC, new Identifier("miscellaneous/dyes"), new ItemStack(Items.RED_DYE));
        this.register(ItemGroup.MISC, new Identifier("miscellaneous/discs"), new ItemStack(Items.MUSIC_DISC_MALL));
        this.register(ItemGroup.FOOD, new Identifier("foodstuffs/raw"), new ItemStack(Items.BEEF));
        this.register(ItemGroup.FOOD, new Identifier("foodstuffs/cooked"), new ItemStack(Items.COOKED_PORKCHOP));
        this.register(ItemGroup.FOOD, new Identifier("foodstuffs/special"), new ItemStack(Items.GOLDEN_APPLE));
        this.register(ItemGroup.COMBAT, new Identifier("combat/armor"), new ItemStack(Items.IRON_CHESTPLATE));
        this.register(ItemGroup.COMBAT, new Identifier("combat/weapons"), new ItemStack(Items.IRON_SWORD));
        this.register(ItemGroup.COMBAT, new Identifier("combat/arrows"), new ItemStack(Items.ARROW));
        this.register(ItemGroup.COMBAT, new Identifier("combat/enchanting_books"), new ItemStack(Items.ENCHANTED_BOOK));
        this.register(ItemGroup.TOOLS, new Identifier("tools/tools"), new ItemStack(Items.IRON_SHOVEL));
        this.register(ItemGroup.TOOLS, new Identifier("tools/equipment"), new ItemStack(Items.COMPASS));
        this.register(ItemGroup.TOOLS, new Identifier("tools/enchanting_books"), new ItemStack(Items.ENCHANTED_BOOK));
        this.register(ItemGroup.BREWING, new Identifier("brewing/potions"), new ItemStack(Items.DRAGON_BREATH));
        this.register(ItemGroup.BREWING, new Identifier("brewing/ingredients"), new ItemStack(Items.BLAZE_POWDER));
        this.register(ItemGroup.BREWING, new Identifier("brewing/equipment"), new ItemStack(Items.BREWING_STAND));
    }

    public static Filters get()
    {
        return instance;
    }

    public void register(ItemGroup group, Identifier tag, ItemStack icon) {
        Set<FilterEntry> entries = this.filterMap.computeIfAbsent(group, itemGroup -> new LinkedHashSet<>());
        entries.add(new FilterEntry(tag, icon));
    }

    public Set<ItemGroup> getGroups()
    {
        return ImmutableSet.copyOf(this.filterMap.keySet());
    }

    public ImmutableList<FilterEntry> getFilters(ItemGroup group) {
        return ImmutableList.copyOf(this.filterMap.get(group));
    }

    public boolean hasFilters(ItemGroup group)
    {
        return this.filterMap.containsKey(group);
    }
}