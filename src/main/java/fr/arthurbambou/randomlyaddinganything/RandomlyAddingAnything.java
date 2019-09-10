package fr.arthurbambou.randomlyaddinganything;

import com.swordglowsblue.artifice.api.Artifice;
import fr.arthurbambou.randomlyaddinganything.api.enums.GeneratesIn;
import fr.arthurbambou.randomlyaddinganything.api.enums.OreTypes;
import fr.arthurbambou.randomlyaddinganything.config.Config;
import fr.arthurbambou.randomlyaddinganything.config.SavingSystem;
import fr.arthurbambou.randomlyaddinganything.materials.Material;
import fr.arthurbambou.randomlyaddinganything.registries.Materials;
import fr.arthurbambou.randomlyaddinganything.registries.Textures;
import fr.arthurbambou.randomlyaddinganything.world.gen.feature.OreFeature;
import fr.arthurbambou.randomlyaddinganything.world.gen.feature.OreFeatureConfig;
import fr.arthurbambou.randomlyaddinganything.world.gen.feature.SimpleRangeDecoratorConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;

public class RandomlyAddingAnything implements ModInitializer {

	public static final ItemGroup RAA_ORES = FabricItemGroupBuilder.build(new Identifier("raa", "ores"), () -> new ItemStack(Blocks.IRON_ORE));
	public static final ItemGroup RAA_RESOURCES = FabricItemGroupBuilder.build(new Identifier("raa", "resources"), () -> new ItemStack(Items.IRON_INGOT));
	public static final ItemGroup RAA_TOOLS = FabricItemGroupBuilder.build(new Identifier("raa", "tools"), () -> new ItemStack(Items.IRON_PICKAXE));
	public static final ItemGroup RAA_ARMOR = FabricItemGroupBuilder.build(new Identifier("raa", "armor"), () -> new ItemStack(Items.IRON_HELMET));
	public static final ItemGroup RAA_WEAPONS = FabricItemGroupBuilder.build(new Identifier("raa", "weapons"), () -> new ItemStack(Items.IRON_SWORD));
	public static final String MOD_ID = "raa";
	public static final Config CONFIG = new Config();

	@Override
	public void onInitialize() {
		Textures.init();
		if (SavingSystem.init()) {
			Materials.init();
			SavingSystem.createFile();
		} else {
			SavingSystem.readFile();
			Materials.isReady = true;
		}
		Materials.createMaterialResources();

		Artifice.registerData(new Identifier(RandomlyAddingAnything.MOD_ID, "recipe_pack"), serverResourcePackBuilder -> {
			for (Material material : Materials.MATERIAL_LIST) {
				Item repairItem;
				if (material.getOreInformation().getOreType() == OreTypes.METAL) {
					repairItem = Registry.ITEM.get(new Identifier(MOD_ID, material.getName().toLowerCase() + "_ingot"));
				} else if (material.getOreInformation().getOreType() == OreTypes.CRYSTAL) {
					repairItem = Registry.ITEM.get(new Identifier(MOD_ID, material.getName().toLowerCase() + "_crystal"));
				} else {
					repairItem = Registry.ITEM.get(new Identifier(MOD_ID, material.getName().toLowerCase() + "_gem"));
				}
				if (material.hasArmor()) {
					serverResourcePackBuilder.addShapedRecipe(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_helmet"), shapedRecipeBuilder -> {
						shapedRecipeBuilder.group(new Identifier("raa:helmets"));
						shapedRecipeBuilder.pattern(
								"###",
								"# #"
						);
						shapedRecipeBuilder.ingredientItem('#', Registry.ITEM.getId(repairItem));
						shapedRecipeBuilder.result(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_helmet"), 1);
					});
					serverResourcePackBuilder.addShapedRecipe(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_chestplate"), shapedRecipeBuilder -> {
						shapedRecipeBuilder.group(new Identifier("raa:chestplates"));
						shapedRecipeBuilder.pattern(
								"# #",
								"###",
								"###"
						);
						shapedRecipeBuilder.ingredientItem('#', Registry.ITEM.getId(repairItem));
						shapedRecipeBuilder.result(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_chestplate"), 1);
					});
					serverResourcePackBuilder.addShapedRecipe(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_leggings"), shapedRecipeBuilder -> {
						shapedRecipeBuilder.group(new Identifier("raa:leggings"));
						shapedRecipeBuilder.pattern(
								"###",
								"# #",
								"# #"
						);
						shapedRecipeBuilder.ingredientItem('#', Registry.ITEM.getId(repairItem));
						shapedRecipeBuilder.result(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_leggings"), 1);
					});
					serverResourcePackBuilder.addShapedRecipe(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_boots"), shapedRecipeBuilder -> {
						shapedRecipeBuilder.group(new Identifier("raa:boots"));
						shapedRecipeBuilder.pattern(
								"# #",
								"# #"
						);
						shapedRecipeBuilder.ingredientItem('#', Registry.ITEM.getId(repairItem));
						shapedRecipeBuilder.result(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_boots"), 1);
					});
				}
				if (material.hasTools()) {
					serverResourcePackBuilder.addShapedRecipe(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_hoe"), shapedRecipeBuilder -> {
						shapedRecipeBuilder.group(new Identifier("raa:hoes"));
						shapedRecipeBuilder.pattern(
								"## ",
								" % ",
								" % "
						);
						shapedRecipeBuilder.ingredientItem('#', Registry.ITEM.getId(repairItem));
						shapedRecipeBuilder.ingredientItem('%', Registry.ITEM.getId(Items.STICK));
						shapedRecipeBuilder.result(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_hoe"), 1);
					});
					serverResourcePackBuilder.addShapedRecipe(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_shovel"), shapedRecipeBuilder -> {
						shapedRecipeBuilder.group(new Identifier("raa:shovels"));
						shapedRecipeBuilder.pattern(
								" # ",
								" % ",
								" % "
						);
						shapedRecipeBuilder.ingredientItem('#', Registry.ITEM.getId(repairItem));
						shapedRecipeBuilder.ingredientItem('%', Registry.ITEM.getId(Items.STICK));
						shapedRecipeBuilder.result(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_shovel"), 1);
					});
					serverResourcePackBuilder.addShapedRecipe(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_axe"), shapedRecipeBuilder -> {
						shapedRecipeBuilder.group(new Identifier("raa:axes"));
						shapedRecipeBuilder.pattern(
								"## ",
								"#% ",
								" % "
						);
						shapedRecipeBuilder.ingredientItem('#', Registry.ITEM.getId(repairItem));
						shapedRecipeBuilder.ingredientItem('%', Registry.ITEM.getId(Items.STICK));
						shapedRecipeBuilder.result(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_axe"), 1);
					});
					serverResourcePackBuilder.addShapedRecipe(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_pickaxe"), shapedRecipeBuilder -> {
						shapedRecipeBuilder.group(new Identifier("raa:pickaxes"));
						shapedRecipeBuilder.pattern(
								"###",
								" % ",
								" % "
						);
						shapedRecipeBuilder.ingredientItem('#', Registry.ITEM.getId(repairItem));
						shapedRecipeBuilder.ingredientItem('%', Registry.ITEM.getId(Items.STICK));
						shapedRecipeBuilder.result(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_pickaxe"), 1);
					});
				}
				if (material.hasWeapons()) {
					serverResourcePackBuilder.addShapedRecipe(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_sword"), shapedRecipeBuilder -> {
						shapedRecipeBuilder.group(new Identifier("raa:swords"));
						shapedRecipeBuilder.pattern(
								" # ",
								" # ",
								" % "
						);
						shapedRecipeBuilder.ingredientItem('#', Registry.ITEM.getId(repairItem));
						shapedRecipeBuilder.ingredientItem('%', Registry.ITEM.getId(Items.STICK));
						shapedRecipeBuilder.result(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_sword"), 1);
					});
				}
				if (material.getOreInformation().getOreType() == OreTypes.METAL) {
					serverResourcePackBuilder.addSmeltingRecipe(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_ingot"), cookingRecipeBuilder -> {
						cookingRecipeBuilder.cookingTime(200);
						cookingRecipeBuilder.ingredientItem(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_ore"));
						cookingRecipeBuilder.experience(0.7);
						cookingRecipeBuilder.result(Registry.ITEM.getId(repairItem));
					});
					serverResourcePackBuilder.addBlastingRecipe(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_ingot_from_blasting"), cookingRecipeBuilder -> {
						cookingRecipeBuilder.cookingTime(100);
						cookingRecipeBuilder.ingredientItem(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_ore"));
						cookingRecipeBuilder.experience(0.7);
						cookingRecipeBuilder.result(Registry.ITEM.getId(repairItem));
					});
					serverResourcePackBuilder.addShapedRecipe(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_ingot_from_nuggets"), shapedRecipeBuilder -> {
						shapedRecipeBuilder.group(new Identifier("raa:ingots"));
						shapedRecipeBuilder.pattern(
								"###",
								"###",
								"###"
						);
						shapedRecipeBuilder.ingredientItem('#', new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_nugget"));
						shapedRecipeBuilder.result(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_ingot"), 1);
					});
					serverResourcePackBuilder.addShapedRecipe(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_block"), shapedRecipeBuilder -> {
						shapedRecipeBuilder.group(new Identifier("raa:storage_blocks"));
						shapedRecipeBuilder.pattern(
								"###",
								"###",
								"###"
						);
						shapedRecipeBuilder.ingredientItem('#', new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_ingot"));
						shapedRecipeBuilder.result(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_block"), 1);
					});
					serverResourcePackBuilder.addShapelessRecipe(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_ingot_from_" + material.getName().toLowerCase() + "_block"), shapelessRecipeBuilder -> {
						shapelessRecipeBuilder.group(new Identifier("raa:ingots"));
						shapelessRecipeBuilder.ingredientItem(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_block"));
						shapelessRecipeBuilder.result(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_ingot"), 9);
					});
				} else if (material.getOreInformation().getOreType() == OreTypes.GEM) {
					serverResourcePackBuilder.addShapedRecipe(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_block"), shapedRecipeBuilder -> {
						shapedRecipeBuilder.group(new Identifier("raa:storage_blocks"));
						shapedRecipeBuilder.pattern(
								"###",
								"###",
								"###"
						);
						shapedRecipeBuilder.ingredientItem('#', new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_gem"));
						shapedRecipeBuilder.result(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_block"), 1);
					});
				} else {
					serverResourcePackBuilder.addShapedRecipe(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_block"), shapedRecipeBuilder -> {
						shapedRecipeBuilder.group(new Identifier("raa:storage_blocks"));
						shapedRecipeBuilder.pattern(
								"###",
								"###",
								"###"
						);
						shapedRecipeBuilder.ingredientItem('#', new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_crystal"));
						shapedRecipeBuilder.result(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_block"), 1);
					});
				}
			}
		});
		Registry.BIOME.forEach(biome -> {
			for (Material material : Materials.MATERIAL_LIST) {
				if (material.getOreInformation().getGenerateIn() == GeneratesIn.SAND_DESERT) {
					if (biome == Biomes.DESERT || biome == Biomes.DESERT_HILLS || biome == Biomes.DESERT_LAKES) {
						biome.addFeature(GenerationStep.Feature.UNDERGROUND_ORES, Biome.configureFeature(
						        new OreFeature(OreFeatureConfig::deserialize), new OreFeatureConfig(OreFeatureConfig.Target.SAND,
                                        Registry.BLOCK.get(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_ore")).getDefaultState(), 9),
                                        Decorator.COUNT_RANGE, new SimpleRangeDecoratorConfig(material.getOreInformation().getOreCount(), 0, 256)));
					}
				}
				if (material.getOreInformation().getGenerateIn() == GeneratesIn.STONE) {
					biome.addFeature(GenerationStep.Feature.UNDERGROUND_ORES, Biome.configureFeature(
							new OreFeature(OreFeatureConfig::deserialize), new OreFeatureConfig(OreFeatureConfig.Target.STONE,
									Registry.BLOCK.get(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_ore")).getDefaultState(), 9),
							Decorator.COUNT_RANGE, new SimpleRangeDecoratorConfig(material.getOreInformation().getOreCount(), 0, 256)));
				}
				if (material.getOreInformation().getGenerateIn() == GeneratesIn.GRASS_BLOCK) {
					biome.addFeature(GenerationStep.Feature.UNDERGROUND_ORES, Biome.configureFeature(
							new OreFeature(OreFeatureConfig::deserialize), new OreFeatureConfig(OreFeatureConfig.Target.GRASS_BLOCK,
									Registry.BLOCK.get(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_ore")).getDefaultState(), 9),
							Decorator.COUNT_RANGE, new SimpleRangeDecoratorConfig(material.getOreInformation().getOreCount(), 0, 256)));
				}
				if (material.getOreInformation().getGenerateIn() == GeneratesIn.GRAVEL) {
					biome.addFeature(GenerationStep.Feature.UNDERGROUND_ORES, Biome.configureFeature(
							new OreFeature(OreFeatureConfig::deserialize), new OreFeatureConfig(OreFeatureConfig.Target.GRAVEL,
									Registry.BLOCK.get(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_ore")).getDefaultState(), 9),
							Decorator.COUNT_RANGE, new SimpleRangeDecoratorConfig(material.getOreInformation().getOreCount(), 0, 256)));
				}
				if (material.getOreInformation().getGenerateIn() == GeneratesIn.DIORITE) {
					biome.addFeature(GenerationStep.Feature.UNDERGROUND_ORES, Biome.configureFeature(
							new OreFeature(OreFeatureConfig::deserialize), new OreFeatureConfig(OreFeatureConfig.Target.DIORITE,
									Registry.BLOCK.get(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_ore")).getDefaultState(), 9),
							Decorator.COUNT_RANGE, new SimpleRangeDecoratorConfig(material.getOreInformation().getOreCount(), 0, 256)));
				}
				if (material.getOreInformation().getGenerateIn() == GeneratesIn.GRANITE) {
					biome.addFeature(GenerationStep.Feature.UNDERGROUND_ORES, Biome.configureFeature(
							new OreFeature(OreFeatureConfig::deserialize), new OreFeatureConfig(OreFeatureConfig.Target.GRANITE,
									Registry.BLOCK.get(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_ore")).getDefaultState(), 9),
							Decorator.COUNT_RANGE, new SimpleRangeDecoratorConfig(material.getOreInformation().getOreCount(), 0, 256)));
				}
				if (material.getOreInformation().getGenerateIn() == GeneratesIn.ANDESITE) {
					biome.addFeature(GenerationStep.Feature.UNDERGROUND_ORES, Biome.configureFeature(
							new OreFeature(OreFeatureConfig::deserialize), new OreFeatureConfig(OreFeatureConfig.Target.ANDESITE,
									Registry.BLOCK.get(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_ore")).getDefaultState(), 9),
							Decorator.COUNT_RANGE, new SimpleRangeDecoratorConfig(material.getOreInformation().getOreCount(), 0, 256)));
				}
				if (material.getOreInformation().getGenerateIn() == GeneratesIn.NETHERRACK) {
					if (biome == Biomes.NETHER) {
						biome.addFeature(GenerationStep.Feature.UNDERGROUND_ORES, Biome.configureFeature(
								new OreFeature(OreFeatureConfig::deserialize), new OreFeatureConfig(OreFeatureConfig.Target.NETHERRACK,
										Registry.BLOCK.get(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_ore")).getDefaultState(), 9),
								Decorator.COUNT_RANGE, new SimpleRangeDecoratorConfig(material.getOreInformation().getOreCount(), 0, 256)));
					}
				}
				if (material.getOreInformation().getGenerateIn() == GeneratesIn.END_STONE) {
					if (biome == Biomes.END_BARRENS || biome == Biomes.END_HIGHLANDS || biome == Biomes.END_MIDLANDS || biome == Biomes.THE_END || biome == Biomes.SMALL_END_ISLANDS) {
						biome.addFeature(GenerationStep.Feature.UNDERGROUND_ORES, Biome.configureFeature(
								new OreFeature(OreFeatureConfig::deserialize), new OreFeatureConfig(OreFeatureConfig.Target.END_STONE,
										Registry.BLOCK.get(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_ore")).getDefaultState(), 9),
								Decorator.COUNT_RANGE, new SimpleRangeDecoratorConfig(material.getOreInformation().getOreCount(), 0, 256)));
					}
				}
				if (material.getOreInformation().getGenerateIn() == GeneratesIn.SAND_BEACH) {
					if (biome == Biomes.BEACH ) {
                        biome.addFeature(GenerationStep.Feature.UNDERGROUND_ORES, Biome.configureFeature(
                                new OreFeature(OreFeatureConfig::deserialize), new OreFeatureConfig(OreFeatureConfig.Target.SAND,
                                        Registry.BLOCK.get(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_ore")).getDefaultState(), 9),
                                Decorator.COUNT_RANGE, new SimpleRangeDecoratorConfig(material.getOreInformation().getOreCount(), 0, 256)));
					}
				}
				if (material.getOreInformation().getGenerateIn() == GeneratesIn.SAND_ANY) {
					biome.addFeature(GenerationStep.Feature.UNDERGROUND_ORES, Biome.configureFeature(
							new OreFeature(OreFeatureConfig::deserialize), new OreFeatureConfig(OreFeatureConfig.Target.SAND,
									Registry.BLOCK.get(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_ore")).getDefaultState(), 9),
							Decorator.COUNT_RANGE, new SimpleRangeDecoratorConfig(material.getOreInformation().getOreCount(), 0, 256)));
				}
				if (material.getOreInformation().getGenerateIn() == GeneratesIn.SAND_ANY2) {
					biome.addFeature(GenerationStep.Feature.UNDERGROUND_ORES, Biome.configureFeature(
							new OreFeature(OreFeatureConfig::deserialize), new OreFeatureConfig(OreFeatureConfig.Target.SAND,
									Registry.BLOCK.get(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_ore")).getDefaultState(), 9),
							Decorator.COUNT_RANGE, new SimpleRangeDecoratorConfig(material.getOreInformation().getOreCount(), 0, 256)));
				}
				if (material.getOreInformation().getGenerateIn() == GeneratesIn.RED_SAND) {
					biome.addFeature(GenerationStep.Feature.UNDERGROUND_ORES, Biome.configureFeature(
							new OreFeature(OreFeatureConfig::deserialize), new OreFeatureConfig(OreFeatureConfig.Target.RED_SAND,
									Registry.BLOCK.get(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_ore")).getDefaultState(), 9),
							Decorator.COUNT_RANGE, new SimpleRangeDecoratorConfig(material.getOreInformation().getOreCount(), 0, 256)));
				}
			}
		});
	}
}