package io.github.vampirestudios.raa.registries;

import io.github.vampirestudios.raa.generation.feature.*;
import io.github.vampirestudios.raa.generation.feature.config.CorruptedFeatureConfig;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.*;

import static io.github.vampirestudios.raa.RandomlyAddingAnything.MOD_ID;

public class Features {
    public static NetherrackFeature CORRUPTED_NETHRRACK;
    public static CraterFeature CRATER_FEATURE;
    public static TowerFeature TOWER;
    public static CampfireFeature CAMPFIRE;
    public static SmallSkeletalTreeFeature SMALL_SKELETON_TREE;
    public static LargeSkeletalTreeFeature LARGE_SKELETON_TREE;
    public static SpiderLairFeature SPIDER_LAIR;
    public static SmallDeadwoodTreeFeature SMALL_DEADWOOD_TREE;
    public static LargeDeadwoodTreeFeature LARGE_DEADWOOD_TREE;
    public static ArchStructureFeature CANYON_ARCH_STRUCTURE;
    public static StructurePieceType CANYON_ARCH_PIECE;
    public static StructurePieceType VOLCANO_PIECE;

    public static void init() {
        CORRUPTED_NETHRRACK = register("corrupted_netherrack", new NetherrackFeature(DefaultFeatureConfig::deserialize));
        CRATER_FEATURE = register("crater_feature", new CraterFeature(CorruptedFeatureConfig::deserialize));
        TOWER = register("tower", new TowerFeature(DefaultFeatureConfig::deserialize));
        CAMPFIRE = register("campfire", new CampfireFeature(DefaultFeatureConfig::deserialize));
        SMALL_SKELETON_TREE = register("skeleton_tree_small", new SmallSkeletalTreeFeature(TreeFeatureConfig::deserialize));
        LARGE_SKELETON_TREE = register("skeleton_tree_large", new LargeSkeletalTreeFeature(TreeFeatureConfig::deserialize));
        SPIDER_LAIR = register("spider_lair", new SpiderLairFeature(DefaultFeatureConfig::deserialize));
        SMALL_DEADWOOD_TREE = register("small_deadwood_tree", new SmallDeadwoodTreeFeature(TreeFeatureConfig::deserialize));
        LARGE_DEADWOOD_TREE = register("large_deadwood_tree", new LargeDeadwoodTreeFeature(TreeFeatureConfig::deserialize));
        CANYON_ARCH_STRUCTURE = registerStructure("canyon_arch", new ArchStructureFeature(DefaultFeatureConfig::deserialize));
        Feature.STRUCTURES.put("canyon_arch", CANYON_ARCH_STRUCTURE);
        CANYON_ARCH_PIECE = registerStructurePiece("canyon_arch", ArchGenerator::new);
        VOLCANO_PIECE = registerStructurePiece("volcano", VolcanoGenerator::new);
    }

    public static <C extends FeatureConfig, F extends Feature<C>> F register(String name, F feature) {
        if (!Registry.FEATURE.containsId(new Identifier(MOD_ID, name))) {
            return Registry.register(Registry.FEATURE, new Identifier(MOD_ID, name), feature);
        } else {
            return feature;
        }
    }

    public static <F extends StructureFeature<?>> F registerStructure(String name, F structureFeature) {
        if (!Registry.STRUCTURE_FEATURE.containsId(new Identifier(MOD_ID, name))) {
            return Registry.register(Registry.STRUCTURE_FEATURE, new Identifier(MOD_ID, name), structureFeature);
        } else {
            return structureFeature;
        }
    }

    public static <F extends StructurePieceType> F registerStructurePiece(String name, F structurePieceType) {
        if (!Registry.STRUCTURE_PIECE.containsId(new Identifier(MOD_ID, name))) {
            return Registry.register(Registry.STRUCTURE_PIECE, new Identifier(MOD_ID, name), structurePieceType);
        } else {
            return structurePieceType;
        }
    }

}
