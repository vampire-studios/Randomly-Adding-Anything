package io.github.vampirestudios.raa.registries;

import com.google.common.collect.ImmutableSet;
import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.commands.CommandLocateRAAStructure;
import io.github.vampirestudios.raa.generation.dimensions.DimensionData;
import io.github.vampirestudios.raa.generation.feature.FossilFeature;
import io.github.vampirestudios.raa.generation.feature.*;
import io.github.vampirestudios.raa.generation.feature.cave.WorldCarverBC;
import io.github.vampirestudios.raa.generation.feature.config.CorruptedFeatureConfig;
import io.github.vampirestudios.raa.generation.feature.portalHub.PortalHubFeature;
import io.github.vampirestudios.raa.generation.feature.tree.BentTreeFeature;
import io.github.vampirestudios.raa.generation.feature.tree.DoubleTreeFeature;
import io.github.vampirestudios.raa.generation.feature.tree.FixedTreeFeature;
import io.github.vampirestudios.raa.utils.Utils;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.fluid.Fluids;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.carver.Carver;
import net.minecraft.world.gen.carver.CarverConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.*;

import static io.github.vampirestudios.raa.RandomlyAddingAnything.MOD_ID;

public class Features {
    public static NetherrackFeature CORRUPTED_NETHRRACK;
    public static CraterFeature CRATER_FEATURE;
    public static OutpostFeature OUTPOST;
    public static CampfireFeature CAMPFIRE;
    public static SmallSkeletalTreeFeature SMALL_SKELETON_TREE;
    public static LargeSkeletalTreeFeature LARGE_SKELETON_TREE;
    public static SpiderLairFeature SPIDER_LAIR;
    public static SmallDeadwoodTreeFeature SMALL_DEADWOOD_TREE;
    public static LargeDeadwoodTreeFeature LARGE_DEADWOOD_TREE;
    public static FixedTreeFeature FIXED_TREE;
    public static BentTreeFeature BENT_TREE;
    public static DoubleTreeFeature DOUBLE_TREE;
    public static TowerFeature TOWER;
    public static FossilFeature FOSSIL;
    public static PortalHubFeature PORTAL_HUB;
    public static ShrineFeature SHRINE;

    public static void init() {
        CommandRegistry.INSTANCE.register(false, CommandLocateRAAStructure::register);
        CORRUPTED_NETHRRACK = Registry.register(Registry.FEATURE, new Identifier(MOD_ID, "corrupted_netherrack"), new NetherrackFeature(DefaultFeatureConfig::deserialize));
        CRATER_FEATURE = Registry.register(Registry.FEATURE, new Identifier(MOD_ID, "crater_feature"), new CraterFeature(CorruptedFeatureConfig::deserialize));
        OUTPOST = Registry.register(Registry.FEATURE, new Identifier(MOD_ID, "outpost"), new OutpostFeature(DefaultFeatureConfig::deserialize));
        CAMPFIRE = Registry.register(Registry.FEATURE, new Identifier(MOD_ID, "campfire"), new CampfireFeature(DefaultFeatureConfig::deserialize));
        TOWER = Registry.register(Registry.FEATURE, new Identifier(MOD_ID, "tower"), new TowerFeature(DefaultFeatureConfig::deserialize));
        FOSSIL = Registry.register(Registry.FEATURE, new Identifier(MOD_ID, "fossil"), new FossilFeature(DefaultFeatureConfig::deserialize));
        SHRINE = Registry.register(Registry.FEATURE, new Identifier(MOD_ID, "shrine"), new ShrineFeature(DefaultFeatureConfig::deserialize));

        CORRUPTED_NETHRRACK = register("corrupted_netherrack", new NetherrackFeature(DefaultFeatureConfig::deserialize));
        CRATER_FEATURE = register("crater_feature", new CraterFeature(CorruptedFeatureConfig::deserialize));
        OUTPOST = register("outpost", new OutpostFeature(DefaultFeatureConfig::deserialize));
        CAMPFIRE = register("campfire", new CampfireFeature(DefaultFeatureConfig::deserialize));
        TOWER = register("tower", new TowerFeature(DefaultFeatureConfig::deserialize));
        FOSSIL = register("fossil", new FossilFeature(DefaultFeatureConfig::deserialize));
        SHRINE = register("shrine", new ShrineFeature(DefaultFeatureConfig::deserialize));
        SMALL_SKELETON_TREE = register("skeleton_tree_small", new SmallSkeletalTreeFeature(TreeFeatureConfig::deserialize));
        LARGE_SKELETON_TREE = register("skeleton_tree_large", new LargeSkeletalTreeFeature(TreeFeatureConfig::deserialize));
        SPIDER_LAIR = register("spider_lair", new SpiderLairFeature(DefaultFeatureConfig::deserialize));
        SMALL_DEADWOOD_TREE = register("small_deadwood_tree", new SmallDeadwoodTreeFeature(TreeFeatureConfig::deserialize));
        LARGE_DEADWOOD_TREE = register("large_deadwood_tree", new LargeDeadwoodTreeFeature(TreeFeatureConfig::deserialize));
        FIXED_TREE = register("fixed_tree", new FixedTreeFeature(BranchedTreeFeatureConfig::deserialize2));
        BENT_TREE = register("bent_tree", new BentTreeFeature(BranchedTreeFeatureConfig::deserialize2));
        DOUBLE_TREE = register("double_tree", new DoubleTreeFeature(BranchedTreeFeatureConfig::deserialize2));
        PORTAL_HUB = register("portal_hub", new PortalHubFeature(DefaultFeatureConfig::deserialize));
    }

    public static void addDefaultCarvers(Biome biome, DimensionData dimensionData) {
        if (Utils.checkBitFlag(dimensionData.getFlags(), Utils.TECTONIC)) {
            WorldCarverBC caveCarver = registerCarver("cave_carver", new WorldCarverBC(dimensionData));
            caveCarver.initialize(-6409096104954950338L);
            biome.addCarver(GenerationStep.Carver.AIR, Biome.configureCarver(caveCarver, new ProbabilityConfig(1)));
//            RavineCarver ravineCarver = registerCarver("ravine_carver", new RavineCarver(dimensionData));
//            biome.addCarver(GenerationStep.Carver.AIR, Biome.configureCarver(ravineCarver, new ProbabilityConfig(1)));
        } else {
            WorldCarverBC caveCarver = registerCarver("cave_carver", new WorldCarverBC(dimensionData));
            caveCarver.initialize(-6409096104954950338L);
            biome.addCarver(GenerationStep.Carver.AIR, Biome.configureCarver(caveCarver, new ProbabilityConfig(0.14285715F)));
//            RavineCarver ravineCarver = registerCarver("ravine_carver", new RavineCarver(dimensionData));
//            biome.addCarver(GenerationStep.Carver.AIR, Biome.configureCarver(ravineCarver, new ProbabilityConfig(0.02F)));
        }
    }

    public static void addDefaultSprings(Biome biome, DimensionData data) {
        biome.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.SPRING_FEATURE.configure(new SpringFeatureConfig(Fluids.WATER.getDefaultState(), true, 4, 1, ImmutableSet.of(Registry.BLOCK.get(new Identifier(RandomlyAddingAnything.MOD_ID, data.getName().toLowerCase() + "_stone"))))).createDecoratedFeature(Decorator.COUNT_BIASED_RANGE.configure(new RangeDecoratorConfig(50, 8, 8, 256))));
        biome.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.SPRING_FEATURE.configure(new SpringFeatureConfig(Fluids.LAVA.getDefaultState(), true, 4, 1, ImmutableSet.of(Registry.BLOCK.get(new Identifier(RandomlyAddingAnything.MOD_ID, data.getName().toLowerCase() + "_stone"))))).createDecoratedFeature(Decorator.COUNT_VERY_BIASED_RANGE.configure(new RangeDecoratorConfig(20, 8, 16, 256))));
    }

    public static <C extends FeatureConfig, F extends Feature<C>> F register(String name, F feature) {
        if (Registry.FEATURE.get(new Identifier(MOD_ID, name)) == null) {
            return Registry.register(Registry.FEATURE, new Identifier(MOD_ID, name), feature);
        } else {
            return feature;
        }
    }

    public static <F extends StructureFeature<?>> F registerStructure(String name, F structureFeature) {
        if (Registry.STRUCTURE_FEATURE.get(new Identifier(MOD_ID, name)) == null) {
            return Registry.register(Registry.STRUCTURE_FEATURE, new Identifier(MOD_ID, name), structureFeature);
        } else {
            return structureFeature;
        }
    }

    public static <F extends StructurePieceType> F registerStructurePiece(String name, F structurePieceType) {
        if (Registry.STRUCTURE_PIECE.get(new Identifier(MOD_ID, name)) == null) {
            return Registry.register(Registry.STRUCTURE_PIECE, new Identifier(MOD_ID, name), structurePieceType);
        } else {
            return structurePieceType;
        }
    }

    public static <F extends CarverConfig, C extends Carver<F>> C registerCarver(String name, C carver) {
        if (Registry.CARVER.get(new Identifier(MOD_ID, name)) == null) {
            return Registry.register(Registry.CARVER, new Identifier(MOD_ID, name), carver);
        } else {
            return carver;
        }
    }

}
