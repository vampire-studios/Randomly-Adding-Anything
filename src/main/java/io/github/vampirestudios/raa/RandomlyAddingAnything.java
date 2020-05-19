package io.github.vampirestudios.raa;

import io.github.vampirestudios.raa.api.RAARegisteries;
import io.github.vampirestudios.raa.api.RAAWorldAPI;
import io.github.vampirestudios.raa.config.*;
import io.github.vampirestudios.raa.generation.dimensions.DimensionRecipes;
import io.github.vampirestudios.raa.generation.dimensions.DimensionalBiomeSource;
//import io.github.vampirestudios.raa.generation.dimensions.DimensionalBiomeSourceConfig;
import io.github.vampirestudios.raa.generation.materials.MaterialRecipes;
import io.github.vampirestudios.raa.generation.surface.random.SurfaceBuilderGenerator;
import io.github.vampirestudios.raa.generation.targets.OreTargetGenerator;
import io.github.vampirestudios.raa.registries.*;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSourceType;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.CountExtraChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.DecoratorConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.FeatureConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Function;
import java.util.function.LongFunction;

public class RandomlyAddingAnything implements ModInitializer {

    public static final ItemGroup RAA_ORES = FabricItemGroupBuilder.build(new Identifier("raa", "ores"), () -> new ItemStack(Blocks.IRON_ORE));
    public static final ItemGroup RAA_RESOURCES = FabricItemGroupBuilder.build(new Identifier("raa", "resources"), () -> new ItemStack(Items.IRON_INGOT));
    public static final ItemGroup RAA_TOOLS = FabricItemGroupBuilder.build(new Identifier("raa", "tools"), () -> new ItemStack(Items.IRON_PICKAXE));
    public static final ItemGroup RAA_ARMOR = FabricItemGroupBuilder.build(new Identifier("raa", "armor"), () -> new ItemStack(Items.IRON_HELMET));
    public static final ItemGroup RAA_WEAPONS = FabricItemGroupBuilder.build(new Identifier("raa", "weapons"), () -> new ItemStack(Items.IRON_SWORD));
    public static final ItemGroup RAA_FOOD = FabricItemGroupBuilder.build(new Identifier("raa", "food"), () -> new ItemStack(Items.GOLDEN_APPLE));
    public static final ItemGroup RAA_DIMENSION_BLOCKS = FabricItemGroupBuilder.build(new Identifier("raa", "dimension_blocks"), () ->
            new ItemStack(Items.STONE));
//    public static final ItemGroup RAA = new RAAItemGroup(new Identifier("raa", "raa_tab"));

    public static final String MOD_ID = "raa";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static GeneralConfig CONFIG;
    public static OreTargetConfig ORE_TARGET_CONFIG;
    public static MaterialsConfig MATERIALS_CONFIG;
    public static SurfaceBuilderConfig SURFACE_BUILDER_CONFIG;
    public static DimensionsConfig DIMENSIONS_CONFIG;
    public static DimensionMaterialsConfig DIMENSION_MATERIALS_CONFIG;
    public static EntitiesConfig ENTITIES_CONFIG;

//    public static BiomeSourceType<DimensionalBiomeSourceConfig, DimensionalBiomeSource> DIMENSIONAL_BIOMES;

    public static ModCompat MODCOMPAT;

    public static final Tag<Item> ORES = TagRegistry.item(new Identifier(MOD_ID, "ores"));
    public static final Tag<Item> RESOURCES = TagRegistry.item(new Identifier(MOD_ID, "resources"));
    public static final Tag<Item> TOOLS = TagRegistry.item(new Identifier(MOD_ID, "tools"));
    public static final Tag<Item> ARMOR = TagRegistry.item(new Identifier(MOD_ID, "armor"));
    public static final Tag<Item> WEAPONS = TagRegistry.item(new Identifier(MOD_ID, "weapons"));
    public static final Tag<Item> FOOD = TagRegistry.item(new Identifier(MOD_ID, "food"));
    public static final Tag<Item> DIMENSION_BLOCKS = TagRegistry.item(new Identifier(MOD_ID, "dimension_blocks"));

    @Override
    public void onInitialize() {
        AutoConfig.register(GeneralConfig.class, GsonConfigSerializer::new);
        CONFIG = AutoConfig.getConfigHolder(GeneralConfig.class).getConfig();
        MODCOMPAT = new ModCompat();
        Textures.init();
        FoliagePlacers.init();
        Features.init();
        Decorators.init();
//        SurfaceBuilders.init();
//        ChunkGenerators.init();
        CustomTargets.init();
        RAAPlacements.init();

        //Reflection hacks
//        Constructor<BiomeSourceType> constructor;
//        try {
//            constructor = BiomeSourceType.class.getDeclaredConstructor(Function.class, LongFunction.class);
//            constructor.setAccessible(true);
//            DIMENSIONAL_BIOMES = constructor.newInstance((Function) DimensionalBiomeSource::new, (LongFunction) DimensionalBiomeSourceConfig::new);
//        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
//            e.printStackTrace();
//        }

//        OreTargetGenerator.registerElements();
//        ORE_TARGET_CONFIG = new OreTargetConfig("targets/ore_target_config");
//        if (!ORE_TARGET_CONFIG.fileExist()) {
//            ORE_TARGET_CONFIG.generate();
//            ORE_TARGET_CONFIG.save();
//        } else {
//            ORE_TARGET_CONFIG.load();
//        }

        MATERIALS_CONFIG = new MaterialsConfig("materials/material_config");
        if (CONFIG.materialGenAmount > 0) {
            if (!MATERIALS_CONFIG.fileExist()) {
                MATERIALS_CONFIG.generate();
                MATERIALS_CONFIG.save();
            } else {
                MATERIALS_CONFIG.load();
            }
        }
        Materials.createMaterialResources();

        ENTITIES_CONFIG = new EntitiesConfig("entities/entities_config");
        if (CONFIG.entityGenAmount > 0) {
            if (!ENTITIES_CONFIG.fileExist()) {
                ENTITIES_CONFIG.generate();
                ENTITIES_CONFIG.save();
            } else {
                ENTITIES_CONFIG.load();
            }
        }

//        SurfaceBuilderGenerator.registerElements();
//        SURFACE_BUILDER_CONFIG = new SurfaceBuilderConfig("surface_builders/surface_builder_config");
//        if (!SURFACE_BUILDER_CONFIG.fileExist()) {
//            SURFACE_BUILDER_CONFIG.generate();
//            SURFACE_BUILDER_CONFIG.save();
//        } else {
//            SURFACE_BUILDER_CONFIG.load();
//        }
//
//        DIMENSIONS_CONFIG = new DimensionsConfig("dimensions/dimension_config");
//        if (CONFIG.dimensionGenAmount > 0) {
//            if (!DIMENSIONS_CONFIG.fileExist()) {
//                DIMENSIONS_CONFIG.generate();
//                DIMENSIONS_CONFIG.save();
//            } else {
//                DIMENSIONS_CONFIG.load();
//            }
//        }
//        Dimensions.createDimensions();
//
//        DIMENSION_MATERIALS_CONFIG = new DimensionMaterialsConfig("dimensions/dimensional_material_config");
//        if (CONFIG.dimensionMaterialGenAmount > 0) {
//            if (!DIMENSION_MATERIALS_CONFIG.fileExist()) {
//                DIMENSION_MATERIALS_CONFIG.generate();
//                DIMENSION_MATERIALS_CONFIG.save();
//            } else {
//                DIMENSION_MATERIALS_CONFIG.load();
//            }
//        }
//        Materials.createDimensionMaterialResources();
//        MODCOMPAT.generateCompatItems();
//
//        DimensionRecipes.init();
        MaterialRecipes.init();

        //entity stuff
        Entities.init();

        Registry.BIOME.forEach(biome -> {
            RAARegisteries.TARGET_REGISTRY.forEach(target -> RAAWorldAPI.generateOresForTarget(biome, target));

            if (biome.getCategory() != Biome.Category.OCEAN && CONFIG.shouldSpawnPortalHub) {
                biome.addFeature(GenerationStep.Feature.SURFACE_STRUCTURES, Features.PORTAL_HUB.configure(new DefaultFeatureConfig()).
                        createDecoratedFeature(Decorators.RANDOM_EXTRA_HEIGHTMAP_DECORATOR.
                                configure(new CountExtraChanceDecoratorConfig(0, 0.001F, 1))));
            }

            /*if (biome.getCategory() != Biome.Category.OCEAN) {
                biome.addFeature(GenerationStep.Feature.RAW_GENERATION, Features.DUNGEON_FEATURE.configure(new DefaultFeatureConfig())
                        .createDecoratedFeature(Decorator.NOPE.configure(DecoratorConfig.DEFAULT)));
                biome.addStructureFeature(Features.DUNGEON_FEATURE.configure(FeatureConfig.DEFAULT));
            }*/

            /*if (biome.getCategory() != Biome.Category.OCEAN) {
                biome.addFeature(GenerationStep.Feature.RAW_GENERATION, Features.LABYRINT_FEATURE.configure(new DefaultFeatureConfig())
                        .createDecoratedFeature(Decorator.NOPE.configure(DecoratorConfig.DEFAULT)));
                biome.addStructureFeature(Features.LABYRINT_FEATURE.configure(FeatureConfig.DEFAULT));
            }*/
        });
    }
}