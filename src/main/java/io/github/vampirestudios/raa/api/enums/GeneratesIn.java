package io.github.vampirestudios.raa.api.enums;

import io.github.vampirestudios.raa.world.gen.feature.OreFeatureConfig;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public class GeneratesIn {
    public static final GeneratesIn STONE = new GeneratesIn(Blocks.STONE, OreFeatureConfig.Target.STONE);
    public static final GeneratesIn GRASS_BLOCK = new GeneratesIn(Blocks.GRASS_BLOCK, OreFeatureConfig.Target.GRASS_BLOCK);
    public static final GeneratesIn DIRT_SURFACE = new GeneratesIn(Blocks.DIRT, OreFeatureConfig.Target.DIRT);
    public static final GeneratesIn GRAVEL = new GeneratesIn(Blocks.GRAVEL, OreFeatureConfig.Target.GRAVEL);
    public static final GeneratesIn SAND_DESERT = new GeneratesIn(Blocks.SAND, OreFeatureConfig.Target.SAND);
    public static final GeneratesIn DIORITE = new GeneratesIn(Blocks.DIORITE, OreFeatureConfig.Target.DIORITE);
    public static final GeneratesIn ANDESITE = new GeneratesIn(Blocks.ANDESITE, OreFeatureConfig.Target.ANDESITE);
    public static final GeneratesIn GRANITE = new GeneratesIn(Blocks.GRANITE, OreFeatureConfig.Target.GRANITE);
    public static final GeneratesIn SAND_BEACH = new GeneratesIn(Blocks.SAND, OreFeatureConfig.Target.SAND);
    public static final GeneratesIn DIRT_ANY = new GeneratesIn(Blocks.DIRT, OreFeatureConfig.Target.DIRT);
    public static final GeneratesIn SAND_ANY = new GeneratesIn(Blocks.SAND, OreFeatureConfig.Target.SAND);
    public static final GeneratesIn DIRT_UNDERGROUND = new GeneratesIn(Blocks.DIRT, OreFeatureConfig.Target.DIRT);
    public static final GeneratesIn RED_SAND = new GeneratesIn(Blocks.RED_SAND, OreFeatureConfig.Target.RED_SAND);
    public static final GeneratesIn END_STONE = new GeneratesIn(Blocks.END_STONE, OreFeatureConfig.Target.END_STONE);
    public static final GeneratesIn NETHERRACK = new GeneratesIn(Blocks.NETHERRACK, OreFeatureConfig.Target.NETHERRACK);
    public static final GeneratesIn DOES_NOT_APPEAR = new GeneratesIn(null, null);
    public static final GeneratesIn BIOME_SPECIFIC = new GeneratesIn(Blocks.STONE, null);
    public static final GeneratesIn CLAY = new GeneratesIn(Blocks.CLAY, OreFeatureConfig.Target.CLAY);
    public static final GeneratesIn PODZOL = new GeneratesIn(Blocks.PODZOL, OreFeatureConfig.Target.PODZOL);
    public static final GeneratesIn COARSE_DIRT = new GeneratesIn(Blocks.COARSE_DIRT, OreFeatureConfig.Target.COARSE_DIRT);

    private Block block;
    private OreFeatureConfig.Target target;

    public GeneratesIn(Block block, OreFeatureConfig.Target target) {
        this.block = block;
        this.target = target;
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
