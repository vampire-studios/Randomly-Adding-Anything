package io.github.vampirestudios.raa;

import io.github.vampirestudios.raa.api.RAARegisteries;
import io.github.vampirestudios.raa.api.RAAWorldAPI;
import io.github.vampirestudios.raa.compats.SimplexRAACompat;
import io.github.vampirestudios.raa.config.DimensionMaterialsConfig;
import io.github.vampirestudios.raa.config.DimensionsConfig;
import io.github.vampirestudios.raa.config.GeneralConfig;
import io.github.vampirestudios.raa.config.MaterialsConfig;
import io.github.vampirestudios.raa.generation.dimensions.DimensionRecipes;
import io.github.vampirestudios.raa.generation.dimensions.DimensionalBiomeSource;
import io.github.vampirestudios.raa.generation.dimensions.DimensionalBiomeSourceConfig;
import io.github.vampirestudios.raa.generation.materials.MaterialRecipes;
import io.github.vampirestudios.raa.registries.*;
import io.github.vampirestudios.raa.utils.Rands;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSourceType;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.CountExtraChanceDecoratorConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
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
    public static final String MOD_ID = "raa";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static GeneralConfig CONFIG;
    public static MaterialsConfig MATERIALS_CONFIG;
    public static DimensionsConfig DIMENSIONS_CONFIG;
    public static DimensionMaterialsConfig DIMENSION_MATERIALS_CONFIG;

    public static BiomeSourceType<DimensionalBiomeSourceConfig, DimensionalBiomeSource> DIMENSIONAL_BIOMES;

    public static ModCompat MODCOMPAT;

    @Override
    public void onInitialize() {
        MODCOMPAT = new ModCompat();
        AutoConfig.register(GeneralConfig.class, GsonConfigSerializer::new);
        CONFIG = AutoConfig.getConfigHolder(GeneralConfig.class).getConfig();
        Textures.init();
        FoliagePlacers.init();
        Features.init();
        Decorators.init();
        SurfaceBuilders.init();
        ChunkGenerators.init();
        if (FabricLoader.getInstance().isModLoaded("simplexterrain")) {
            SimplexRAACompat.init();
        }
        new CustomTargets();

        //Reflection hacks
        Constructor<BiomeSourceType> constructor;
        try {
            constructor = BiomeSourceType.class.getDeclaredConstructor(Function.class, LongFunction.class);
            constructor.setAccessible(true);
            DIMENSIONAL_BIOMES = constructor.newInstance((Function) DimensionalBiomeSource::new, (LongFunction) DimensionalBiomeSourceConfig::new);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }

        MATERIALS_CONFIG = new MaterialsConfig("materials/material_config");
        if (CONFIG.materialNumber > 0) {
            if (CONFIG.regen || !MATERIALS_CONFIG.fileExist()) {
                MATERIALS_CONFIG.generate();
                MATERIALS_CONFIG.save();
            } else {
                MATERIALS_CONFIG.load();
            }
        }
        Materials.createMaterialResources();

        DIMENSIONS_CONFIG = new DimensionsConfig("dimensions/dimension_config");
        if (CONFIG.dimensionNumber > 0) {
            if (CONFIG.regen || !DIMENSIONS_CONFIG.fileExist()) {
                DIMENSIONS_CONFIG.generate();
                DIMENSIONS_CONFIG.save();
            } else {
                DIMENSIONS_CONFIG.load();
            }
        }
        Dimensions.createDimensions();

        DIMENSION_MATERIALS_CONFIG = new DimensionMaterialsConfig("dimensions/dimensional_material_config");
        if (CONFIG.dimensionMaterials > 0) {
            if (CONFIG.regen || !DIMENSION_MATERIALS_CONFIG.fileExist()) {
                DIMENSION_MATERIALS_CONFIG.generate();
                DIMENSION_MATERIALS_CONFIG.save();
            } else {
                DIMENSION_MATERIALS_CONFIG.load();
            }
        }
        Materials.createDimensionMaterialResources();

        DimensionRecipes.init();
        MaterialRecipes.init();

        Criterions.init();

        List<Biome> biomes = new ArrayList<>();
        Registry.BIOME.forEach(biomes::add);
        RegistryEntryAddedCallback.event(Registry.BIOME).register((i, identifier, biome) -> biomes.add(biome));

        biomes.forEach(biome -> {
            RAARegisteries.TARGET_REGISTRY.forEach(target -> RAAWorldAPI.generateOresForTarget(biome, target));
            if (biome.getCategory() != Biome.Category.OCEAN && CONFIG.shouldSpawnPortalHub) {
                biome.addFeature(GenerationStep.Feature.SURFACE_STRUCTURES, Features.PORTAL_HUB.configure(new DefaultFeatureConfig()).
                        createDecoratedFeature(Decorators.RANDOM_EXTRA_HEIGHTMAP_DECORATOR.
                                configure(new CountExtraChanceDecoratorConfig(0, 0.001F, 1))));
            }
        });
    }
}