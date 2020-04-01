package io.github.vampirestudios.raa.registries;

import com.google.common.collect.ImmutableSet;
import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.commands.CommandLocateRAAStructure;
import io.github.vampirestudios.raa.generation.carvers.*;
import io.github.vampirestudios.raa.generation.dimensions.data.DimensionData;
import io.github.vampirestudios.raa.generation.feature.FossilFeature;
import io.github.vampirestudios.raa.generation.feature.*;
import io.github.vampirestudios.raa.generation.feature.config.ColumnBlocksConfig;
import io.github.vampirestudios.raa.generation.feature.config.CorruptedFeatureConfig;
import io.github.vampirestudios.raa.generation.feature.portalHub.PortalHubFeature;
import io.github.vampirestudios.raa.generation.feature.tree.BentTreeFeature;
import io.github.vampirestudios.raa.generation.feature.tree.DoubleTreeFeature;
import io.github.vampirestudios.raa.generation.feature.tree.FixedTreeFeature;
import io.github.vampirestudios.raa.utils.Rands;
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
    public static FixedTreeFeature FIXED_TREE;
    public static BentTreeFeature BENT_TREE;
    public static DoubleTreeFeature DOUBLE_TREE;
    public static TowerFeature TOWER;
    public static FossilFeature FOSSIL;
    public static PortalHubFeature PORTAL_HUB;
    public static ShrineFeature SHRINE;

    public static BeeNestFeature BEE_NEST;
    public static CaveCampfireFeature CAVE_CAMPFIRE;
    public static MushRuinFeature MUSHROOM_RUIN;
    public static UndegroundBeeHiveFeature UNDERGROUND_BEE_HIVE;
    public static StonehengeFeature STONE_HENGE;
    public static ColumnRampFeature COLUMN_RAMP;
    public static ColumnVerticalFeature COLUMN_VERTICAL;
    public static HangingRuinsFeature HANGING_RUINS;

    public static void init() {
        CommandRegistry.INSTANCE.register(false, CommandLocateRAAStructure::register);

        CORRUPTED_NETHRRACK = register("corrupted_netherrack", new NetherrackFeature(DefaultFeatureConfig::deserialize, DefaultFeatureConfig::method_26619));
        CRATER_FEATURE = register("crater_feature", new CraterFeature(CorruptedFeatureConfig::deserialize, null));
        OUTPOST = register("outpost", new OutpostFeature(DefaultFeatureConfig::deserialize, DefaultFeatureConfig::method_26619));
        CAMPFIRE = register("campfire", new CampfireFeature(DefaultFeatureConfig::deserialize, DefaultFeatureConfig::method_26619));
        TOWER = register("tower", new TowerFeature(DefaultFeatureConfig::deserialize, DefaultFeatureConfig::method_26619));
        FOSSIL = register("fossil", new FossilFeature(DefaultFeatureConfig::deserialize, DefaultFeatureConfig::method_26619));
        SHRINE = register("shrine", new ShrineFeature(DefaultFeatureConfig::deserialize, DefaultFeatureConfig::method_26619));
        SMALL_SKELETON_TREE = register("skeleton_tree_small", new SmallSkeletalTreeFeature(TreeFeatureConfig::deserialize, TreeFeatureConfig::method_26650));
        LARGE_SKELETON_TREE = register("skeleton_tree_large", new LargeSkeletalTreeFeature(TreeFeatureConfig::deserialize, TreeFeatureConfig::method_26650));
        SPIDER_LAIR = register("spider_lair", new SpiderLairFeature(DefaultFeatureConfig::deserialize, DefaultFeatureConfig::method_26619));
        FIXED_TREE = register("fixed_tree", new FixedTreeFeature(BranchedTreeFeatureConfig::deserialize, BranchedTreeFeatureConfig::method_26646));
        BENT_TREE = register("bent_tree", new BentTreeFeature(BranchedTreeFeatureConfig::deserialize, BranchedTreeFeatureConfig::method_26646));
        DOUBLE_TREE = register("double_tree", new DoubleTreeFeature(BranchedTreeFeatureConfig::deserialize, BranchedTreeFeatureConfig::method_26646));
        PORTAL_HUB = register("portal_hub", new PortalHubFeature(DefaultFeatureConfig::deserialize, DefaultFeatureConfig::method_26619));

        BEE_NEST = register("bee_nest", new BeeNestFeature(DefaultFeatureConfig::deserialize, DefaultFeatureConfig::method_26619));
        CAVE_CAMPFIRE = register("cave_campfire", new CaveCampfireFeature(DefaultFeatureConfig::deserialize, DefaultFeatureConfig::method_26619));
        MUSHROOM_RUIN = register("mushroom_ruin", new MushRuinFeature(DefaultFeatureConfig::deserialize, DefaultFeatureConfig::method_26619));
        UNDERGROUND_BEE_HIVE = register("underground_bee_hive", new UndegroundBeeHiveFeature(DefaultFeatureConfig::deserialize, DefaultFeatureConfig::method_26619));

        STONE_HENGE = register("stone_henge", new StonehengeFeature(DefaultFeatureConfig::deserialize, DefaultFeatureConfig::method_26619));
        COLUMN_RAMP = register("columnn_ramp", new ColumnRampFeature(ColumnBlocksConfig::deserialize, null));
        COLUMN_VERTICAL = register("columnn_vertical", new ColumnVerticalFeature(ColumnBlocksConfig::deserialize, null));
        HANGING_RUINS = register("hanging_ruins", new HangingRuinsFeature(DefaultFeatureConfig::deserialize, DefaultFeatureConfig::method_26619));
    }

    // we use this cursed code to make a new carver per dimension
    public static void addDefaultCarvers(Biome biome, DimensionData dimensionData) {
        if (Utils.checkBitFlag(dimensionData.getFlags(), Utils.TECTONIC)) {
            CaveCarver caveCarver = registerCarver("cave_carver_" + dimensionData.getName(), new CaveCarver(dimensionData));
            biome.addCarver(GenerationStep.Carver.AIR, Biome.configureCarver(caveCarver, new ProbabilityConfig(1)));

            RavineCarver ravineCarver = registerCarver("ravine_carver_" + dimensionData.getName(), new RavineCarver(dimensionData));
            biome.addCarver(GenerationStep.Carver.AIR, Biome.configureCarver(ravineCarver, new ProbabilityConfig(1)));

//            CaveCavityCarver caveCavityCarver = registerCarver("cave_cavity_carver", new CaveCavityCarver(dimensionData));
//            biome.addCarver(GenerationStep.Carver.AIR, Biome.configureCarver(caveCavityCarver, new ProbabilityConfig(1)));
//
//            StackedBubbleRoomsCarver bubbleRoomsCarver = registerCarver("stacked_bubble_rooms_carver", new StackedBubbleRoomsCarver(dimensionData));
//            biome.addCarver(GenerationStep.Carver.AIR, Biome.configureCarver(bubbleRoomsCarver, new ProbabilityConfig(1)));
        } else {
            //TODO: bring this to a dedicated class
            if (!Rands.chance(5)) { //80% chance of normal caves
                CaveCarver caveCarver = registerCarver("cave_carver_" + dimensionData.getName(), new CaveCarver(dimensionData));
                biome.addCarver(GenerationStep.Carver.AIR, Biome.configureCarver(caveCarver, new ProbabilityConfig(0.14285715F)));
            }

            if (!Rands.chance(4)) { //75% chance of normal ravines
                RavineCarver ravineCarver = registerCarver("ravine_carver_" + dimensionData.getName(), new RavineCarver(dimensionData));
                biome.addCarver(GenerationStep.Carver.AIR, Biome.configureCarver(ravineCarver, new ProbabilityConfig(0.02F)));
            }

            if (Rands.chance(10)) { //10% chance of cave cavity
                CaveCavityCarver caveCavityCarver = registerCarver("cave_cavity_carver_" + dimensionData.getName(), new CaveCavityCarver(dimensionData));
                biome.addCarver(GenerationStep.Carver.AIR, Biome.configureCarver(caveCavityCarver, new ProbabilityConfig(0.03F)));
            }

            if (Rands.chance(6)) { //16.6% chance of bubble rooms
                StackedBubbleRoomsCarver bubbleRoomsCarver = registerCarver("stacked_bubble_rooms_carver_" + dimensionData.getName(), new StackedBubbleRoomsCarver(dimensionData));
                biome.addCarver(GenerationStep.Carver.AIR, Biome.configureCarver(bubbleRoomsCarver, new ProbabilityConfig(0.035F)));
            }

            if (Rands.chance(3)) { //33% chance of teardrops
                TeardropCarver teardropCarver = registerCarver("teardrop_carver_" + dimensionData.getName(), new TeardropCarver(dimensionData));
                biome.addCarver(GenerationStep.Carver.AIR, Biome.configureCarver(teardropCarver, new ProbabilityConfig(0.06F)));
            }

            if (Rands.chance(4)) { //25% chance of vertical caves
                VerticalCarver verticalCarver = registerCarver("vertical_carver_" + dimensionData.getName(), new VerticalCarver(dimensionData));
                biome.addCarver(GenerationStep.Carver.AIR, Biome.configureCarver(verticalCarver, new ProbabilityConfig(0.04F)));
            }

           if (Rands.chance(10)) { //10% chance of big rooms
                BigRoomCarver bigRoomCarver = registerCarver("big_room_carver_" + dimensionData.getName(), new BigRoomCarver(dimensionData));
                biome.addCarver(GenerationStep.Carver.AIR, Biome.configureCarver(bigRoomCarver, new ProbabilityConfig(0.04F)));
           }
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
        name = name.toLowerCase();
        if (Registry.CARVER.get(new Identifier(MOD_ID, name)) == null) {
            return Registry.register(Registry.CARVER, new Identifier(MOD_ID, name), carver);
        } else {
            return carver;
        }
    }

}
