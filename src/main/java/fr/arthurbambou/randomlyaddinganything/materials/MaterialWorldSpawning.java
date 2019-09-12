package fr.arthurbambou.randomlyaddinganything.materials;

import fr.arthurbambou.randomlyaddinganything.RandomlyAddingAnything;
import fr.arthurbambou.randomlyaddinganything.api.enums.GeneratesIn;
import fr.arthurbambou.randomlyaddinganything.registries.Materials;
import fr.arthurbambou.randomlyaddinganything.world.gen.feature.OreFeature;
import fr.arthurbambou.randomlyaddinganything.world.gen.feature.OreFeatureConfig;
import fr.arthurbambou.randomlyaddinganything.world.gen.feature.SimpleRangeDecoratorConfig;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;

public class MaterialWorldSpawning {

    public static void init() {
        Registry.BIOME.forEach(biome -> {
            Materials.MATERIALS.forEach(material -> {
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
                    if (biome == Biomes.BEACH) {
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
                if (material.getOreInformation().getGenerateIn() == GeneratesIn.PODZOL) {
                    biome.addFeature(GenerationStep.Feature.UNDERGROUND_ORES, Biome.configureFeature(
                            new OreFeature(OreFeatureConfig::deserialize), new OreFeatureConfig(OreFeatureConfig.Target.PODZOL,
                                    Registry.BLOCK.get(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_ore")).getDefaultState(), 9),
                            Decorator.COUNT_RANGE, new SimpleRangeDecoratorConfig(material.getOreInformation().getOreCount(), 0, 256)));
                }
                if (material.getOreInformation().getGenerateIn() == GeneratesIn.CLAY) {
                    biome.addFeature(GenerationStep.Feature.UNDERGROUND_ORES, Biome.configureFeature(
                            new OreFeature(OreFeatureConfig::deserialize), new OreFeatureConfig(OreFeatureConfig.Target.CLAY,
                                    Registry.BLOCK.get(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_ore")).getDefaultState(), 9),
                            Decorator.COUNT_RANGE, new SimpleRangeDecoratorConfig(material.getOreInformation().getOreCount(), 0, 256)));
                }
                if (material.getOreInformation().getGenerateIn() == GeneratesIn.COARSE_DIRT) {
                    biome.addFeature(GenerationStep.Feature.UNDERGROUND_ORES, Biome.configureFeature(
                            new OreFeature(OreFeatureConfig::deserialize), new OreFeatureConfig(OreFeatureConfig.Target.COARSE_DIRT,
                                    Registry.BLOCK.get(new Identifier(RandomlyAddingAnything.MOD_ID, material.getName().toLowerCase() + "_ore")).getDefaultState(), 9),
                            Decorator.COUNT_RANGE, new SimpleRangeDecoratorConfig(material.getOreInformation().getOreCount(), 0, 256)));
                }
            });
        });
    }

}
