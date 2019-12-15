package io.github.vampirestudios.raa.api.enums;

import io.github.vampirestudios.raa.api.RAARegistery;
import io.github.vampirestudios.raa.utils.RegistryUtils;
import io.github.vampirestudios.raa.world.gen.feature.OreFeatureConfig;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class GeneratesIn {

    public static final GeneratesIn STONE = RegistryUtils.registerGeneratesIn("stone", Blocks.STONE, OreFeatureConfig.Target.STONE);
    public static final GeneratesIn GRASS_BLOCK = RegistryUtils.registerGeneratesIn("grass_block", Blocks.GRASS_BLOCK, OreFeatureConfig.Target.GRASS_BLOCK);
    public static final GeneratesIn DIRT_SURFACE = RegistryUtils.registerGeneratesIn("dirt_surface", Blocks.DIRT, OreFeatureConfig.Target.DIRT);
    public static final GeneratesIn GRAVEL = RegistryUtils.registerGeneratesIn("gravel", Blocks.GRAVEL, OreFeatureConfig.Target.GRAVEL);
    public static final GeneratesIn SAND_DESERT = RegistryUtils.registerGeneratesIn("sand_desert", Blocks.SAND, OreFeatureConfig.Target.SAND);
    public static final GeneratesIn DIORITE = RegistryUtils.registerGeneratesIn("diorite", Blocks.DIORITE, OreFeatureConfig.Target.DIORITE);
    public static final GeneratesIn ANDESITE = RegistryUtils.registerGeneratesIn("andesite", Blocks.ANDESITE, OreFeatureConfig.Target.ANDESITE);
    public static final GeneratesIn GRANITE = RegistryUtils.registerGeneratesIn("granite", Blocks.GRANITE, OreFeatureConfig.Target.GRANITE);
    public static final GeneratesIn SAND_BEACH = RegistryUtils.registerGeneratesIn("sand_beach", Blocks.SAND, OreFeatureConfig.Target.SAND);
    public static final GeneratesIn DIRT_ANY = RegistryUtils.registerGeneratesIn("dirt_any", Blocks.DIRT, OreFeatureConfig.Target.DIRT);
    public static final GeneratesIn SAND_ANY = RegistryUtils.registerGeneratesIn("sand_any", Blocks.SAND, OreFeatureConfig.Target.SAND);
    public static final GeneratesIn DIRT_UNDERGROUND = RegistryUtils.registerGeneratesIn("dirt_underground", Blocks.DIRT, OreFeatureConfig.Target.DIRT);
    public static final GeneratesIn RED_SAND = RegistryUtils.registerGeneratesIn("red_sand", Blocks.RED_SAND, OreFeatureConfig.Target.RED_SAND);
    public static final GeneratesIn END_STONE = RegistryUtils.registerGeneratesIn("end_stone", Blocks.END_STONE, OreFeatureConfig.Target.END_STONE);
    public static final GeneratesIn NETHERRACK = RegistryUtils.registerGeneratesIn("netherrack", Blocks.NETHERRACK, OreFeatureConfig.Target.NETHERRACK);
    public static final GeneratesIn DOES_NOT_APPEAR = RegistryUtils.registerGeneratesIn("does_not_appear", null, null);
    public static final GeneratesIn BIOME_SPECIFIC = RegistryUtils.registerGeneratesIn("biome_specific", Blocks.STONE, null);
    public static final GeneratesIn CLAY = RegistryUtils.registerGeneratesIn("clay", Blocks.CLAY, OreFeatureConfig.Target.CLAY);
    public static final GeneratesIn PODZOL = RegistryUtils.registerGeneratesIn("podzol", Blocks.PODZOL, OreFeatureConfig.Target.PODZOL);
    public static final GeneratesIn COARSE_DIRT = RegistryUtils.registerGeneratesIn("coarse_dirt", Blocks.COARSE_DIRT, OreFeatureConfig.Target.COARSE_DIRT);
    public static final GeneratesIn DIMENSION_STONE = RegistryUtils.registerGeneratesIn("dimension_stone", null, null);

    private Identifier identifier;
    private Block block;
    private OreFeatureConfig.Target target;

    public GeneratesIn(Identifier identifier, Block block, OreFeatureConfig.Target target) {
        this.identifier = Objects.requireNonNull(identifier);
        this.block = block;
        this.target = target;
    }

    public static List<GeneratesIn> getValues() {
        return RAARegistery.GENERATES_IN_REGISTRY.stream().collect(Collectors.toList());
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public Block getBlock() {
        if (block == null) {
            return Blocks.STONE;
        } else {
            return block;
        }
    }

    public OreFeatureConfig.Target getTarget() {
        return target;
    }

}
