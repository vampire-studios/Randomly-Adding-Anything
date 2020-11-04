package io.github.vampirestudios.raa.registries;

import io.github.vampirestudios.raa.utils.RegistryUtils;
import io.github.vampirestudios.raa.world.gen.feature.OreFeature;
import io.github.vampirestudios.raa.world.gen.feature.OreFeatureConfig;
import net.minecraft.block.Blocks;
import net.minecraft.predicate.block.BlockPredicate;
import net.minecraft.structure.rule.BlockMatchRuleTest;

public class CustomTargets {

    public static OreFeatureConfig.Target STONE;
    public static OreFeatureConfig.Target ANDESITE;
    public static OreFeatureConfig.Target DIORITE;
    public static OreFeatureConfig.Target GRANITE;
    public static OreFeatureConfig.Target GRASS_BLOCK;
    public static OreFeatureConfig.Target GRAVEL;
    public static OreFeatureConfig.Target DIRT;
    public static OreFeatureConfig.Target COARSE_DIRT;
    public static OreFeatureConfig.Target PODZOL;
    public static OreFeatureConfig.Target CLAY;
    public static OreFeatureConfig.Target SAND;
    public static OreFeatureConfig.Target SANDSTONE;
    public static OreFeatureConfig.Target RED_SAND;
    public static OreFeatureConfig.Target RED_SANDSTONE;
    public static OreFeatureConfig.Target NETHERRACK;
    public static OreFeatureConfig.Target END_STONE;
    public static OreFeatureConfig.Target BLACKSTONE;
    public static OreFeatureConfig.Target BASALT;
    public static OreFeatureConfig.Target SOUL_SAND;
    public static OreFeatureConfig.Target SOUL_SOIL;
    public static OreFeatureConfig.Target CRIMSON_NYLIUM;
    public static OreFeatureConfig.Target WARPED_NYLIUM;
    public static OreFeatureConfig.Target DOES_NOT_APPEAR;

    public static void init() {
        STONE = RegistryUtils.registerOreTarget("stone", new BlockPredicate(Blocks.STONE), Blocks.STONE);
        ANDESITE = RegistryUtils.registerOreTarget("andesite", new BlockPredicate(Blocks.ANDESITE), Blocks.ANDESITE);
        DIORITE = RegistryUtils.registerOreTarget("diorite", new BlockPredicate(Blocks.DIORITE), Blocks.DIORITE);
        GRANITE = RegistryUtils.registerOreTarget("granite", new BlockPredicate(Blocks.GRANITE), Blocks.GRANITE);
        GRASS_BLOCK = RegistryUtils.registerOreTarget("grass_block", new BlockPredicate(Blocks.GRASS_BLOCK), Blocks.GRASS_BLOCK);
        GRAVEL = RegistryUtils.registerOreTarget("gravel", new BlockPredicate(Blocks.GRAVEL), Blocks.GRAVEL);
        DIRT = RegistryUtils.registerOreTarget("dirt", new BlockPredicate(Blocks.DIRT), Blocks.DIRT);
        COARSE_DIRT = RegistryUtils.registerOreTarget("coarse_dirt", new BlockPredicate(Blocks.COARSE_DIRT), Blocks.COARSE_DIRT);
        PODZOL = RegistryUtils.registerOreTarget("podzol", new BlockPredicate(Blocks.PODZOL), Blocks.PODZOL);
        CLAY = RegistryUtils.registerOreTarget("clay", new BlockPredicate(Blocks.CLAY), Blocks.CLAY);
        SAND = RegistryUtils.registerOreTarget("sand", new BlockPredicate(Blocks.SAND), Blocks.SAND);
        SANDSTONE = RegistryUtils.registerOreTarget("sandstone", new BlockPredicate(Blocks.SANDSTONE), Blocks.SANDSTONE);
        RED_SAND = RegistryUtils.registerOreTarget("red_sand", new BlockPredicate(Blocks.RED_SAND), Blocks.RED_SAND);
        RED_SANDSTONE = RegistryUtils.registerOreTarget("red_sandstone", new BlockPredicate(Blocks.RED_SANDSTONE), Blocks.RED_SANDSTONE);
        NETHERRACK = RegistryUtils.registerOreTarget("netherrack", new BlockPredicate(Blocks.NETHERRACK), Blocks.NETHERRACK);
        END_STONE = RegistryUtils.registerOreTarget("end_stone", new BlockPredicate(Blocks.END_STONE), Blocks.END_STONE);
        BLACKSTONE = RegistryUtils.registerOreTarget("blackstone", new BlockPredicate(Blocks.BLACKSTONE), Blocks.BLACKSTONE);
        BASALT = RegistryUtils.registerOreTarget("basalt", new BlockPredicate(Blocks.BASALT), Blocks.BASALT);
        SOUL_SAND = RegistryUtils.registerOreTarget("soul_sand", new BlockPredicate(Blocks.SOUL_SAND), Blocks.SOUL_SAND);
        SOUL_SOIL = RegistryUtils.registerOreTarget("soul_soil", new BlockPredicate(Blocks.SOUL_SOIL), Blocks.SOUL_SOIL);
        CRIMSON_NYLIUM = RegistryUtils.registerOreTarget("crimson_nylium", new BlockPredicate(Blocks.CRIMSON_NYLIUM), Blocks.CRIMSON_NYLIUM);
        WARPED_NYLIUM = RegistryUtils.registerOreTarget("warped_nylium", new BlockPredicate(Blocks.WARPED_NYLIUM), Blocks.WARPED_NYLIUM);
        DOES_NOT_APPEAR = RegistryUtils.registerOreTarget("does_not_appear", new BlockPredicate(null), null);
    }

}
