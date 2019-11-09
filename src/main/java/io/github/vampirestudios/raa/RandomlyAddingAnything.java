package io.github.vampirestudios.raa;

import io.github.vampirestudios.raa.config.DimensionSavingSystem;
import io.github.vampirestudios.raa.config.GeneralConfig;
import io.github.vampirestudios.raa.config.SavingSystem;
import io.github.vampirestudios.raa.generation.materials.MaterialRecipes;
import io.github.vampirestudios.raa.generation.materials.MaterialWorldSpawning;
import io.github.vampirestudios.raa.generation.surface.CustomDimensionSurfaceBuilder;
import io.github.vampirestudios.raa.registries.*;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

public class RandomlyAddingAnything implements ModInitializer {

	public static final ItemGroup RAA_ORES = FabricItemGroupBuilder.build(new Identifier("raa", "ores"), () -> new ItemStack(Blocks.IRON_ORE));
	public static final ItemGroup RAA_RESOURCES = FabricItemGroupBuilder.build(new Identifier("raa", "resources"), () -> new ItemStack(Items.IRON_INGOT));
	public static final ItemGroup RAA_TOOLS = FabricItemGroupBuilder.build(new Identifier("raa", "tools"), () -> new ItemStack(Items.IRON_PICKAXE));
	public static final ItemGroup RAA_ARMOR = FabricItemGroupBuilder.build(new Identifier("raa", "armor"), () -> new ItemStack(Items.IRON_HELMET));
	public static final ItemGroup RAA_WEAPONS = FabricItemGroupBuilder.build(new Identifier("raa", "weapons"), () -> new ItemStack(Items.IRON_SWORD));
	public static final ItemGroup RAA_FOOD = FabricItemGroupBuilder.build(new Identifier("raa", "food"), () -> new ItemStack(Items.GOLDEN_APPLE));
	public static final ItemGroup RAA_DIMENSION_BLOCKS = FabricItemGroupBuilder.build(new Identifier("raa", "dimension_blocks"), () -> new ItemStack(Items.STONE));
	public static final String MOD_ID = "raa";
	public static GeneralConfig CONFIG;
	public static CustomDimensionSurfaceBuilder SURFACE_BUILDER;

	@Override
	public void onInitialize() {
		AutoConfig.register(GeneralConfig.class, JanksonConfigSerializer::new);
		CONFIG = AutoConfig.getConfigHolder(GeneralConfig.class).getConfig();
		Textures.init();
		Features.init();
		if (SavingSystem.init() || CONFIG.regen) {
			Materials.init();
			Dimensions.init();
			SavingSystem.createFile();
			CONFIG.regen = false;
		} else {
			SavingSystem.readFile();
			Materials.ready = true;
		}
		if (DimensionSavingSystem.init() || CONFIG.regen) {
			Dimensions.init();
			DimensionSavingSystem.createFile();
			CONFIG.regen = false;
		} else {
			DimensionSavingSystem.readFile();
			Dimensions.isReady = true;
		}
		SURFACE_BUILDER = Registry.register(Registry.SURFACE_BUILDER, new Identifier(MOD_ID, "custom_surface_builder"),
				new CustomDimensionSurfaceBuilder(TernarySurfaceConfig::deserialize));
		Decorators.init();
		Dimensions.createDimensions();
		Materials.createMaterialResources();
		MaterialRecipes.init();
		MaterialWorldSpawning.init();
	}
}