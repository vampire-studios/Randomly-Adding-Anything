package io.github.vampirestudios.raa.api;

import io.github.vampirestudios.raa.predicate.block.BlockPredicate;
import io.github.vampirestudios.raa.world.gen.feature.OreFeatureConfig;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionType;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class OreGenerationSupport {

    private Biome generationBiome;
    private OreFeatureConfig.Target target;
    private DimensionType dimensionType;

    public OreGenerationSupport(Biome generationBiome, OreFeatureConfig.Target target, DimensionType dimensionType) {
        this.generationBiome = generationBiome;
        this.target = target;
        this.dimensionType = dimensionType;
    }

    public Biome getGenerationBiome() {
        return generationBiome;
    }

    public OreFeatureConfig.Target getTarget() {
        return target;
    }

    public DimensionType getDimensionType() {
        return dimensionType;
    }

    public static class Target {

        public static final Target STONE = new Target("stone", new BlockPredicate(Blocks.STONE));
        public static final Target ANDESITE = new Target("andesite", new BlockPredicate(Blocks.ANDESITE));
        public static final Target DIORITE = new Target("diorite", new BlockPredicate(Blocks.DIORITE));
        public static final Target GRANITE = new Target("granite", new BlockPredicate(Blocks.GRANITE));
        public static final Target GRASS_BLOCK = new Target("grass_block", new BlockPredicate(Blocks.GRASS_BLOCK));
        public static final Target GRAVEL = new Target("gravel", new BlockPredicate(Blocks.GRAVEL));
        public static final Target DIRT = new Target("dirt", new BlockPredicate(Blocks.DIRT));
        public static final Target COARSE_DIRT = new Target("coarse_dirt", new BlockPredicate(Blocks.COARSE_DIRT));
        public static final Target PODZOL = new Target("podzol", new BlockPredicate(Blocks.PODZOL));
        public static final Target CLAY = new Target("clay", new BlockPredicate(Blocks.CLAY));
        public static final Target SAND = new Target("sand", (block) -> {
            if (block == null) {
                return false;
            } else {
                return block == Blocks.SAND || block == Blocks.SANDSTONE;
            }
        });
        public static final Target RED_SAND = new Target("red_sand", (block) -> {
            if (block == null) {
                return false;
            } else {
                return block == Blocks.RED_SAND || block == Blocks.RED_SANDSTONE;
            }
        });
        public static final Target NETHERRACK = new Target("netherrack", new BlockPredicate(Blocks.NETHERRACK));
        public static final Target END_STONE = new Target("end_stone", new BlockPredicate(Blocks.END_STONE));
        private static Map<String, OreGenerationSupport.Target> TARGETS = new HashMap<>();

        static {
            TARGETS.put("stone", STONE);
            TARGETS.put("andesite", ANDESITE);
            TARGETS.put("diorite", DIORITE);
            TARGETS.put("granite", GRANITE);
            TARGETS.put("grass_block", GRASS_BLOCK);
            TARGETS.put("gravel", GRAVEL);
            TARGETS.put("dirt", DIRT);
            TARGETS.put("coarse_dirt", COARSE_DIRT);
            TARGETS.put("podzol", PODZOL);
            TARGETS.put("clay", CLAY);
            TARGETS.put("sand", SAND);
            TARGETS.put("red_sand", RED_SAND);
            TARGETS.put("netherrack", NETHERRACK);
            TARGETS.put("end_stone", END_STONE);
        }

        private String name;
        private Predicate<Block> blockStatePredicate;

        public Target(String name, Predicate<Block> blockStatePredicate) {
            this.name = name;
            this.blockStatePredicate = blockStatePredicate;
        }

        public static void add(String name, Target target) {
            TARGETS.put(name, target);
        }

        public String getName() {
            return name;
        }

        public Predicate<Block> getBlockStatePredicate() {
            return blockStatePredicate;
        }

    }

}
