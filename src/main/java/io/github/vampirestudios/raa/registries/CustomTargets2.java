/*
package io.github.vampirestudios.raa.registries;

import io.github.vampirestudios.raa.generation.targets.OreTargetData;
import io.github.vampirestudios.raa.utils.RegistryUtils;
import net.minecraft.util.Identifier;

public class CustomTargets2 {

//    public static OreTargetData STONE;
//    public static OreTargetData ANDESITE;
//    public static OreTargetData DIORITE;
//    public static OreTargetData GRANITE;
    public static OreTargetData GRASS_BLOCK;
//    public static OreTargetData GRAVEL;
//    public static OreTargetData DIRT;
//    public static OreTargetData COARSE_DIRT;
//    public static OreTargetData PODZOL;
//    public static OreTargetData CLAY;
//    public static OreTargetData SAND;
//    public static OreTargetData SANDSTONE;
//    public static OreTargetData RED_SAND;
//    public static OreTargetData RED_SANDSTONE;
//    public static OreTargetData NETHERRACK;
//    public static OreTargetData END_STONE;
//    public static OreTargetData DOES_NOT_APPEAR;

    public static void init() {
//        STONE = RegistryUtils.registerOreTarget("stone", new BlockPredicate(Blocks.STONE), Blocks.STONE);
//        ANDESITE = RegistryUtils.registerOreTarget("andesite", new BlockPredicate(Blocks.ANDESITE), Blocks.ANDESITE);
//        DIORITE = RegistryUtils.registerOreTarget("diorite", new BlockPredicate(Blocks.DIORITE), Blocks.DIORITE);
//        GRANITE = RegistryUtils.registerOreTarget("granite", new BlockPredicate(Blocks.GRANITE), Blocks.GRANITE);
        GRASS_BLOCK = register(new Identifier("grass_block"), OreTargetData.Builder.create().name(new Identifier("grass_block")).topOnly(true).build());
//        GRAVEL = RegistryUtils.registerOreTarget("gravel", new BlockPredicate(Blocks.GRAVEL), Blocks.GRAVEL);
//        DIRT = RegistryUtils.registerOreTarget("dirt", new BlockPredicate(Blocks.DIRT), Blocks.DIRT);
//        COARSE_DIRT = RegistryUtils.registerOreTarget("coarse_dirt", new BlockPredicate(Blocks.COARSE_DIRT), Blocks.COARSE_DIRT);
//        PODZOL = RegistryUtils.registerOreTarget("podzol", new BlockPredicate(Blocks.PODZOL), Blocks.PODZOL);
//        CLAY = RegistryUtils.registerOreTarget("clay", new BlockPredicate(Blocks.CLAY), Blocks.CLAY);
//        SAND = RegistryUtils.registerOreTarget("sand", new BlockPredicate(Blocks.SAND), Blocks.SAND);
//        SANDSTONE = RegistryUtils.registerOreTarget("sandstone", new BlockPredicate(Blocks.SANDSTONE), Blocks.SANDSTONE);
//        RED_SAND = RegistryUtils.registerOreTarget("red_sand", new BlockPredicate(Blocks.RED_SAND), Blocks.RED_SAND);
//        RED_SANDSTONE = RegistryUtils.registerOreTarget("red_sandstone", new BlockPredicate(Blocks.RED_SANDSTONE), Blocks.RED_SANDSTONE);
//        NETHERRACK = RegistryUtils.registerOreTarget("netherrack", new BlockPredicate(Blocks.NETHERRACK), Blocks.NETHERRACK);
//        END_STONE = RegistryUtils.registerOreTarget("end_stone", new BlockPredicate(Blocks.END_STONE), Blocks.END_STONE);
//        DOES_NOT_APPEAR = RegistryUtils.registerOreTarget("does_not_appear", new BlockPredicate(null), null);
    }

    public static OreTargetData register(Identifier name, OreTargetData oreTargetData) {
        return RegistryUtils.registerOreTargetData(name, oreTargetData);
    }

}
*/
