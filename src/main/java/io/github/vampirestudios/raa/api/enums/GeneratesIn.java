package io.github.vampirestudios.raa.api.enums;

import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.world.gen.feature.OreFeatureConfig;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GeneratesIn {
    public static final GeneratesIn STONE = new GeneratesIn("stone", Blocks.STONE, OreFeatureConfig.Target.STONE);
    public static final GeneratesIn GRASS_BLOCK = new GeneratesIn("grass_block", Blocks.GRASS_BLOCK, OreFeatureConfig.Target.GRASS_BLOCK);
    public static final GeneratesIn DIRT_SURFACE = new GeneratesIn("dirt_surface", Blocks.DIRT, OreFeatureConfig.Target.DIRT);
    public static final GeneratesIn GRAVEL = new GeneratesIn("gravel", Blocks.GRAVEL, OreFeatureConfig.Target.GRAVEL);
    public static final GeneratesIn SAND_DESERT = new GeneratesIn("sand_desert", Blocks.SAND, OreFeatureConfig.Target.SAND);
    public static final GeneratesIn DIORITE = new GeneratesIn("diorite", Blocks.DIORITE, OreFeatureConfig.Target.DIORITE);
    public static final GeneratesIn ANDESITE = new GeneratesIn("andesite", Blocks.ANDESITE, OreFeatureConfig.Target.ANDESITE);
    public static final GeneratesIn GRANITE = new GeneratesIn("granite", Blocks.GRANITE, OreFeatureConfig.Target.GRANITE);
    public static final GeneratesIn SAND_BEACH = new GeneratesIn("sand_beach", Blocks.SAND, OreFeatureConfig.Target.SAND);
    public static final GeneratesIn DIRT_ANY = new GeneratesIn("dirt_any", Blocks.DIRT, OreFeatureConfig.Target.DIRT);
    public static final GeneratesIn SAND_ANY = new GeneratesIn("sand_any", Blocks.SAND, OreFeatureConfig.Target.SAND);
    public static final GeneratesIn DIRT_UNDERGROUND = new GeneratesIn("dirt_underground", Blocks.DIRT, OreFeatureConfig.Target.DIRT);
    public static final GeneratesIn RED_SAND = new GeneratesIn("red_sand", Blocks.RED_SAND, OreFeatureConfig.Target.RED_SAND);
    public static final GeneratesIn END_STONE = new GeneratesIn("end_stone", Blocks.END_STONE, OreFeatureConfig.Target.END_STONE);
    public static final GeneratesIn NETHERRACK = new GeneratesIn("netherrack", Blocks.NETHERRACK, OreFeatureConfig.Target.NETHERRACK);
    public static final GeneratesIn DOES_NOT_APPEAR = new GeneratesIn("does_not_appear", null, null);
    public static final GeneratesIn BIOME_SPECIFIC = new GeneratesIn("biome_specific", Blocks.STONE, null);
    public static final GeneratesIn CLAY = new GeneratesIn("clay", Blocks.CLAY, OreFeatureConfig.Target.CLAY);
    public static final GeneratesIn PODZOL = new GeneratesIn("podzol", Blocks.PODZOL, OreFeatureConfig.Target.PODZOL);
    public static final GeneratesIn COARSE_DIRT = new GeneratesIn("coarse_dirt", Blocks.COARSE_DIRT, OreFeatureConfig.Target.COARSE_DIRT);

    private static final List<GeneratesIn> VALUES = new ArrayList<>();

    private Identifier identifier;
    private Block block;
    private OreFeatureConfig.Target target;

    // This is private because this is only for adding easily
    private GeneratesIn(String identifier, Block block, OreFeatureConfig.Target target) {
        this(new Identifier(RandomlyAddingAnything.MOD_ID, Objects.requireNonNull(identifier)), block, target);
    }

    public GeneratesIn(Identifier identifier, Block block, OreFeatureConfig.Target target) {
        this.identifier = Objects.requireNonNull(identifier);
        this.block = block;
        this.target = target;

        VALUES.add(this);
    }

    public static List<GeneratesIn> getValues() {
        return VALUES;
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
