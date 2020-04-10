package io.github.vampirestudios.raa.generation.dimensions;

import com.google.common.collect.ImmutableList;
import io.github.vampirestudios.raa.api.dimension.DimensionChunkGenerators;
import io.github.vampirestudios.raa.generation.decorator.BiasedNoiseBasedDecoratorConfig;
import io.github.vampirestudios.raa.generation.dimensions.data.DimensionBiomeData;
import io.github.vampirestudios.raa.generation.dimensions.data.DimensionData;
import io.github.vampirestudios.raa.generation.dimensions.data.DimensionTreeData;
import io.github.vampirestudios.raa.generation.dimensions.data.DimensionTreeTypes;
import io.github.vampirestudios.raa.generation.feature.StoneCircleFeature;
import io.github.vampirestudios.raa.generation.feature.TombFeature;
import io.github.vampirestudios.raa.generation.feature.config.ColumnBlocksConfig;
import io.github.vampirestudios.raa.generation.feature.config.CorruptedFeatureConfig;
import io.github.vampirestudios.raa.generation.feature.tree.foliage.*;
import io.github.vampirestudios.raa.registries.Decorators;
import io.github.vampirestudios.raa.registries.Features;
import io.github.vampirestudios.raa.registries.SurfaceBuilders;
import io.github.vampirestudios.raa.utils.Rands;
import io.github.vampirestudios.raa.utils.Utils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityType;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.PineFoliagePlacer;
import net.minecraft.world.gen.foliage.SpruceFoliagePlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacer;

import java.util.ArrayList;
import java.util.List;

public class CustomDimensionalBiome extends Biome {

    private DimensionData dimensionData;

    public CustomDimensionalBiome(DimensionData dimensionData, DimensionBiomeData biomeData) {
        super((new Settings()
                .configureSurfaceBuilder((SurfaceBuilder<TernarySurfaceConfig>) Registry.SURFACE_BUILDER.get(biomeData.getSurfaceBuilder()),
                        biomeData.getSurfaceConfig())
                .precipitation(Utils.checkBitFlag(dimensionData.getFlags(), Utils.FROZEN) ? Precipitation.SNOW : Rands.chance(10) ? Precipitation.RAIN : Precipitation.NONE)
                .category(Category.PLAINS)
                .depth(biomeData.getDepth())
                .scale(biomeData.getScale())
                .temperature(biomeData.getTemperature())
                .downfall(biomeData.getDownfall())
                .effects(
                        new BiomeEffects.Builder()
                                .fogColor(dimensionData.getDimensionColorPalette().getFogColor())
                                .waterColor(biomeData.getWaterColor())
                                .waterFogColor(biomeData.getWaterColor())
                                .build()
                )
                .parent(null)
        ));
        this.dimensionData = dimensionData;

        if (!(dimensionData.getDimensionChunkGenerator() == DimensionChunkGenerators.FLOATING))
            if (Utils.checkBitFlag(dimensionData.getFlags(), Utils.ABANDONED) || Utils.checkBitFlag(dimensionData.getFlags(), Utils.CIVILIZED))
                this.addStructureFeature(Feature.MINESHAFT.configure(new MineshaftFeatureConfig((Utils.checkBitFlag(dimensionData.getFlags(), Utils.CIVILIZED)) ? 0.016D : 0.004D,
                        MineshaftFeature.Type.NORMAL)));

        Features.addDefaultCarvers(this, dimensionData, biomeData);
        Features.addDefaultSprings(this, dimensionData);

        DefaultBiomeFeatures.addDefaultLakes(this);
        DefaultBiomeFeatures.addDungeons(this);
        if (Registry.SURFACE_BUILDER.get(biomeData.getSurfaceBuilder()) == SurfaceBuilders.HYPER_FLAT) {
            DefaultBiomeFeatures.addMoreSeagrass(this);
            DefaultBiomeFeatures.addKelp(this);
        }

        if (Rands.chance(4) && !Utils.checkBitFlag(dimensionData.getFlags(), Utils.DEAD) && !Utils.checkBitFlag(dimensionData.getFlags(), Utils.DRY) && !Utils.checkBitFlag(dimensionData.getFlags(), Utils.MOLTEN)) {
            DefaultBiomeFeatures.addPlainsTallGrass(this);
        }
        DefaultBiomeFeatures.addMineables(this);
        DefaultBiomeFeatures.addDefaultOres(this);
        DefaultBiomeFeatures.addDefaultDisks(this);

        if (!Utils.checkBitFlag(dimensionData.getFlags(), Utils.DEAD) && !Utils.checkBitFlag(dimensionData.getFlags(), Utils.CORRUPTED)) {
            switch (biomeData.getTreeType()) {
                case MULTIPLE_TREE_FOREST:
                    for (DimensionTreeData treeData : biomeData.getTreeData()) {
                        if (treeData.getTreeType() == DimensionTreeTypes.MEGA_JUNGLE || treeData.getTreeType() == DimensionTreeTypes.MEGA_SPRUCE || treeData.getTreeType() == DimensionTreeTypes.DARK_OAK) {
                            MegaTreeFeatureConfig config = (new MegaTreeFeatureConfig.Builder(new SimpleBlockStateProvider(treeData.getWoodType().woodType.getLog().getDefaultState()), new SimpleBlockStateProvider(treeData.getWoodType().woodType.getLeaves().getDefaultState())))
                                    .baseHeight(treeData.getBaseHeight()).heightInterval(treeData.getFoliageHeightRandom()).build();

                            this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                                    getMegaTree(treeData.getTreeType())
                                            .configure(
                                                    config
                                            ).createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig((int) Math.floor(treeData.getChance()), treeData.getChance(), 1))));
                        } else {
                            BranchedTreeFeatureConfig config1 = getTreeConfig(treeData);
                            this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                                    Feature.NORMAL_TREE
                                            .configure(
                                                    config1
                                            ).createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig((int) Math.floor(treeData.getChance()), treeData.getChance(), 1))));
                        }
                    }
                break;

                case PLAINS_TREES:
                    this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.configure(new RandomFeatureConfig(ImmutableList.of(Feature.FANCY_TREE.configure(DefaultBiomeFeatures.FANCY_TREE_WITH_MORE_BEEHIVES_CONFIG).withChance(0.33333334F)), Feature.NORMAL_TREE.configure(DefaultBiomeFeatures.OAK_TREE_WITH_MORE_BEEHIVES_CONFIG))).createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(0, 0.05F, 1))));
                    break;

                case SINGLE_TREE_FOREST:
                    DimensionTreeData treeData = biomeData.getTreeData().get(0); //single tree forests only have 1 entry

                    if (treeData.getTreeType() == DimensionTreeTypes.MEGA_JUNGLE || treeData.getTreeType() == DimensionTreeTypes.MEGA_SPRUCE || treeData.getTreeType() == DimensionTreeTypes.DARK_OAK) {
                        MegaTreeFeatureConfig config = getMegaTreeConfig(treeData);

                        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                                getMegaTree(treeData.getTreeType())
                                        .configure(
                                                config
                                        ).createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig((int) (treeData.getChance()*25), 0.5f, 1))));
                    } else {
                        BranchedTreeFeatureConfig config1 = getTreeConfig(treeData);
                        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                                Feature.NORMAL_TREE
                                        .configure(
                                                config1
                                        ).createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig((int) (treeData.getChance()*25), 0.5f, 1))));
                    }
                    break;
            }
        }

        if (Utils.checkBitFlag(dimensionData.getFlags(), Utils.DEAD) || Utils.checkBitFlag(dimensionData.getFlags(), Utils.DRY)) {
            this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Features.LARGE_SKELETON_TREE.configure(DefaultBiomeFeatures.OAK_TREE_CONFIG).createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(0, biomeData.getLargeSkeletonTreeChance(), 1))));
            this.addFeature(GenerationStep.Feature.SURFACE_STRUCTURES, Feature.FOSSIL.configure(FeatureConfig.DEFAULT).createDecoratedFeature(Decorator.CHANCE_PASSTHROUGH.configure(new ChanceDecoratorConfig(64))));
        }

        if (!Utils.checkBitFlag(dimensionData.getFlags(), Utils.DEAD) && !Utils.checkBitFlag(dimensionData.getFlags(), Utils.CORRUPTED)) {
            if (Utils.checkBitFlag(dimensionData.getFlags(), Utils.LUSH)) {
                this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.RANDOM_PATCH.configure(DefaultBiomeFeatures.SUNFLOWER_CONFIG)
                        .createDecoratedFeature(Decorator.COUNT_HEIGHTMAP_DOUBLE.configure(new CountDecoratorConfig(50))));
                this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.RANDOM_PATCH.configure(DefaultBiomeFeatures.ROSE_BUSH_CONFIG)
                        .createDecoratedFeature(Decorator.COUNT_HEIGHTMAP_DOUBLE.configure(new CountDecoratorConfig(20))));
                this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.RANDOM_PATCH.configure(DefaultBiomeFeatures.LILAC_CONFIG)
                        .createDecoratedFeature(Decorator.COUNT_HEIGHTMAP_DOUBLE.configure(new CountDecoratorConfig(20))));
            }
            this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.RANDOM_PATCH.configure(DefaultBiomeFeatures.SUGAR_CANE_CONFIG)
                    .createDecoratedFeature(Decorator.COUNT_HEIGHTMAP_DOUBLE.configure(new CountDecoratorConfig(Utils.checkBitFlag(dimensionData.getFlags(), Utils.LUSH) ? 20 : 5))));

            this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.RANDOM_PATCH.configure(DefaultBiomeFeatures.PUMPKIN_PATCH_CONFIG)
                    .createDecoratedFeature(Decorator.CHANCE_HEIGHTMAP_DOUBLE.configure(new ChanceDecoratorConfig(Utils.checkBitFlag(dimensionData.getFlags(), Utils.LUSH) ? 50 : 20))));
        }

        if (Utils.checkBitFlag(dimensionData.getFlags(), Utils.CORRUPTED)) {
            this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Features.CRATER_FEATURE.configure(new CorruptedFeatureConfig(true)).createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(0, Rands.randFloatRange(0, 1F), 1))));
            this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Features.CORRUPTED_NETHRRACK.configure(new DefaultFeatureConfig()).createDecoratedFeature(Decorator.COUNT_HEIGHTMAP_DOUBLE.configure(new CountDecoratorConfig(2))));
        } else {
            if (biomeData.spawnsCratersInNonCorrupted()) {
                this.addFeature(GenerationStep.Feature.SURFACE_STRUCTURES, Features.CRATER_FEATURE.configure(new CorruptedFeatureConfig(false)).createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(0, Rands.randFloatRange(0, 1F), 1))));
            }
        }

        float campfireChance = 0;
        float outpostChance = 0;
        float towerChance = 0;
        float fossilChance = 0;
        float storageChance = 0;
        float shrineChance = 0.002F;

        if (Utils.checkBitFlag(dimensionData.getFlags(), Utils.ABANDONED)) {
            outpostChance = Rands.randFloatRange(0.002F, 0.003F);
            towerChance = Rands.randFloatRange(0.002F, 0.00225F);
            storageChance = Rands.randFloatRange(0.0025F, 0.003F);
        }
        if (Utils.checkBitFlag(dimensionData.getFlags(), Utils.DEAD)) {
            campfireChance = 0;
            fossilChance = Rands.randFloatRange(0.007F, 0.0075F);
            storageChance = Rands.randFloatRange(0.001F, 0.002F);
        }
        if (Utils.checkBitFlag(dimensionData.getFlags(), Utils.CIVILIZED)) {
            campfireChance = Rands.randFloatRange(0.005F, 0.007F);
            outpostChance = Rands.randFloatRange(0.002F, 0.008F);
            towerChance = Rands.randFloatRange(0.002F, 0.003F);
            storageChance = Rands.randFloatRange(0.0025F, 0.003F);
        }

        if (dimensionData.getDimensionChunkGenerator().equals(DimensionChunkGenerators.CAVES)) {
            this.addFeature(net.minecraft.world.gen.GenerationStep.Feature.UNDERGROUND_DECORATION, Feature.BASALT_PILLAR.configure(FeatureConfig.DEFAULT)
                    .createDecoratedFeature(Decorator.COUNT_RANGE.configure(new RangeDecoratorConfig(10, 0, 0, 256))));
        }

        // TODO fix this
        this.addFeature(GenerationStep.Feature.SURFACE_STRUCTURES, Features.OUTPOST.configure(new DefaultFeatureConfig()).createDecoratedFeature(Decorators.RANDOM_EXTRA_HEIGHTMAP_DECORATOR.configure(new CountExtraChanceDecoratorConfig(0, outpostChance, 1))));
        this.addFeature(GenerationStep.Feature.SURFACE_STRUCTURES, Features.CAMPFIRE.configure(new DefaultFeatureConfig()).createDecoratedFeature(Decorators.RANDOM_EXTRA_HEIGHTMAP_DECORATOR.configure(new CountExtraChanceDecoratorConfig(0, campfireChance, 1))));
        this.addFeature(GenerationStep.Feature.SURFACE_STRUCTURES, Features.TOWER.configure(new DefaultFeatureConfig()).createDecoratedFeature(Decorators.RANDOM_EXTRA_HEIGHTMAP_DECORATOR.configure(new CountExtraChanceDecoratorConfig(0, towerChance, 1))));
        this.addFeature(GenerationStep.Feature.SURFACE_STRUCTURES, Features.FOSSIL.configure(new DefaultFeatureConfig()).createDecoratedFeature(Decorators.RANDOM_EXTRA_HEIGHTMAP_DECORATOR.configure(new CountExtraChanceDecoratorConfig(0, fossilChance, 1))));
        this.addFeature(GenerationStep.Feature.SURFACE_STRUCTURES, Features.SHRINE.configure(new DefaultFeatureConfig()).createDecoratedFeature(Decorators.RANDOM_EXTRA_HEIGHTMAP_DECORATOR.configure(new CountExtraChanceDecoratorConfig(0, shrineChance, 1))));
        this.addFeature(GenerationStep.Feature.SURFACE_STRUCTURES, Features.ABOVE_GROUND_STORAGE.configure(new DefaultFeatureConfig()).createDecoratedFeature(Decorators.RANDOM_EXTRA_HEIGHTMAP_DECORATOR.configure(new CountExtraChanceDecoratorConfig(0, storageChance, 1))));
//        this.addFeature(GenerationStep.Feature.SURFACE_STRUCTURES, Features.QUARRY.configure(new DefaultFeatureConfig()).createDecoratedFeature(Decorators.RANDOM_EXTRA_HEIGHTMAP_DECORATOR.configure(new CountExtraChanceDecoratorConfig(0, 0.01f, 1))));

        this.addFeature(GenerationStep.Feature.SURFACE_STRUCTURES,
                Features.STONE_HENGE.configure(new DefaultFeatureConfig()).createDecoratedFeature(
                        Decorators.RANDOM_EXTRA_HEIGHTMAP_DECORATOR.configure(
                                new CountExtraChanceDecoratorConfig(0, 0.001F, 1)
                        )
                )
        );
        /*this.addFeature(GenerationStep.Feature.SURFACE_STRUCTURES,
                Features.HANGING_RUINS.configure(FeatureConfig.DEFAULT).createDecoratedFeature(
                        RAAPlacements.LEDGE_UNDERSIDE_MINI_FEATURE.configure(
                                new ChanceAndTypeConfig(0.1F, ChanceAndTypeConfig.Type.HANGING_RUINS)
                        )
                )
        );*/
        /*this.addFeature(GenerationStep.Feature.SURFACE_STRUCTURES,
                Features.HANGING_RUINS.configure(new DefaultFeatureConfig()).createDecoratedFeature(
                        Decorators.RANDOM_EXTRA_HEIGHTMAP_DECORATOR.configure(
                                new CountExtraChanceDecoratorConfig(0, 0.001F, 1)
                        )
                )
        );*/

//        this.addFeature(GenerationStep.Feature.SURFACE_STRUCTURES, Features.BEE_NEST.configure(new DefaultFeatureConfig()).createDecoratedFeature(Decorators.RANDOM_EXTRA_HEIGHTMAP_DECORATOR.configure(new CountExtraChanceDecoratorConfig(0, 1.0f, 1))));
//        this.addFeature(GenerationStep.Feature.UNDERGROUND_STRUCTURES, Features.CAVE_CAMPFIRE.configure(new DefaultFeatureConfig()).createDecoratedFeature(Decorators.RANDOM_EXTRA_HEIGHTMAP_DECORATOR.configure(new CountExtraChanceDecoratorConfig(0, 1.0f, 1))));
//        this.addFeature(GenerationStep.Feature.SURFACE_STRUCTURES, Features.MUSHROOM_RUIN.configure(new DefaultFeatureConfig()).createDecoratedFeature(Decorators.RANDOM_EXTRA_HEIGHTMAP_DECORATOR.configure(new CountExtraChanceDecoratorConfig(0, 1.0f, 1))));
//        this.addFeature(GenerationStep.Feature.UNDERGROUND_STRUCTURES, Features.UNDERGROUND_BEE_HIVE.configure(new DefaultFeatureConfig()).createDecoratedFeature(Decorators.RANDOM_EXTRA_HEIGHTMAP_DECORATOR.configure(new CountExtraChanceDecoratorConfig(0, 1.0f, 1))));

        if (biomeData.hasMushrooms()) {
            this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.configure(new RandomFeatureConfig(
                    ImmutableList.of(
                            Feature.HUGE_BROWN_MUSHROOM.configure(DefaultBiomeFeatures.HUGE_BROWN_MUSHROOM_CONFIG).withChance(1)),
                    Feature.NORMAL_TREE.configure(DefaultBiomeFeatures.OAK_TREE_CONFIG)
            )).createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(0, Rands.randFloatRange(0.01F, 1F), 1))));
            this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.configure(new RandomFeatureConfig(
                    ImmutableList.of(
                            Feature.HUGE_RED_MUSHROOM.configure(DefaultBiomeFeatures.HUGE_RED_MUSHROOM_CONFIG).withChance(1)),
                    Feature.NORMAL_TREE.configure(DefaultBiomeFeatures.OAK_TREE_CONFIG)
            )).createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(0, Rands.randFloatRange(0.01F, 1F), 1))));
        }
        if (biomeData.hasMossyRocks())
            DefaultBiomeFeatures.addMossyRocks(this);

        DefaultBiomeFeatures.addDefaultMushrooms(this);
        DefaultBiomeFeatures.addDefaultVegetation(this);
        DefaultBiomeFeatures.addSprings(this);
        DefaultBiomeFeatures.addFrozenTopLayer(this);

        if ((Utils.checkBitFlag(dimensionData.getFlags(), Utils.DEAD) && Utils.checkBitFlag(dimensionData.getFlags(), Utils.CIVILIZED)) || (Utils.checkBitFlag(dimensionData.getFlags(), Utils.DEAD) && Utils.checkBitFlag(dimensionData.getFlags(), Utils.ABANDONED))) {
            StoneCircleFeature STONE_CIRCLE = Features.register(String.format("%s_stone_circle", dimensionData.getId().getPath().toLowerCase()), new StoneCircleFeature(dimensionData));
            this.addFeature(GenerationStep.Feature.SURFACE_STRUCTURES, STONE_CIRCLE.configure(FeatureConfig.DEFAULT).createDecoratedFeature(Decorator.CHANCE_HEIGHTMAP.configure(new ChanceDecoratorConfig(120))));

            this.addFeature(GenerationStep.Feature.SURFACE_STRUCTURES, Features.SPIDER_LAIR.configure(FeatureConfig.DEFAULT).createDecoratedFeature(Decorator.CHANCE_HEIGHTMAP.configure(new ChanceDecoratorConfig(230))));

            TombFeature tomb = Features.register(String.format("%s_tomb", dimensionData.getId().getPath().toLowerCase()), new TombFeature(dimensionData));
            this.addFeature(GenerationStep.Feature.SURFACE_STRUCTURES, tomb.configure(FeatureConfig.DEFAULT).createDecoratedFeature(Decorators.RANDOM_EXTRA_HEIGHTMAP_DECORATOR.configure(new CountExtraChanceDecoratorConfig(0, 0.015f, 1))));
        }

        if (dimensionData.getMobs().containsKey("sheep"))
            this.addSpawn(EntityCategory.CREATURE, new SpawnEntry(EntityType.SHEEP, dimensionData.getMobs().get("sheep")[0], dimensionData.getMobs().get("sheep")[1], dimensionData.getMobs().get("sheep")[2]));
        if (dimensionData.getMobs().containsKey("pig"))
            this.addSpawn(EntityCategory.CREATURE, new SpawnEntry(EntityType.PIG, dimensionData.getMobs().get("pig")[0], dimensionData.getMobs().get("pig")[1], dimensionData.getMobs().get("pig")[2]));
        if (dimensionData.getMobs().containsKey("chicken"))
            this.addSpawn(EntityCategory.CREATURE, new SpawnEntry(EntityType.CHICKEN, dimensionData.getMobs().get("chicken")[0], dimensionData.getMobs().get("chicken")[1], dimensionData.getMobs().get("chicken")[2]));
        if (dimensionData.getMobs().containsKey("cow"))
            this.addSpawn(EntityCategory.CREATURE, new SpawnEntry(EntityType.COW, dimensionData.getMobs().get("cow")[0], dimensionData.getMobs().get("cow")[1], dimensionData.getMobs().get("cow")[2]));
        if (dimensionData.getMobs().containsKey("horse"))
            this.addSpawn(EntityCategory.CREATURE, new SpawnEntry(EntityType.HORSE, dimensionData.getMobs().get("horse")[0], dimensionData.getMobs().get("horse")[1], dimensionData.getMobs().get("horse")[2]));
        if (dimensionData.getMobs().containsKey("donkey"))
            this.addSpawn(EntityCategory.CREATURE, new SpawnEntry(EntityType.DONKEY, dimensionData.getMobs().get("donkey")[0], dimensionData.getMobs().get("donkey")[1], dimensionData.getMobs().get("donkey")[2]));

        if (dimensionData.getMobs().containsKey("bat"))
            this.addSpawn(EntityCategory.AMBIENT, new SpawnEntry(EntityType.BAT, dimensionData.getMobs().get("bat")[0], dimensionData.getMobs().get("bat")[1], dimensionData.getMobs().get("bat")[2]));

        if (dimensionData.getMobs().containsKey("spider"))
            this.addSpawn(EntityCategory.MONSTER, new SpawnEntry(EntityType.SPIDER, dimensionData.getMobs().get("spider")[0], dimensionData.getMobs().get("spider")[1], dimensionData.getMobs().get("spider")[2]));
        if (dimensionData.getMobs().containsKey("zombie"))
            this.addSpawn(EntityCategory.MONSTER, new SpawnEntry(EntityType.ZOMBIE, dimensionData.getMobs().get("zombie")[0], dimensionData.getMobs().get("zombie")[1], dimensionData.getMobs().get("zombie")[2]));
        if (dimensionData.getMobs().containsKey("zombie_villager"))
            this.addSpawn(EntityCategory.MONSTER, new SpawnEntry(EntityType.ZOMBIE_VILLAGER, dimensionData.getMobs().get("zombie_villager")[0], dimensionData.getMobs().get("zombie_villager")[1], dimensionData.getMobs().get("zombie_villager")[2]));
        if (dimensionData.getMobs().containsKey("skeleton"))
            this.addSpawn(EntityCategory.MONSTER, new SpawnEntry(EntityType.SKELETON, dimensionData.getMobs().get("skeleton")[0], dimensionData.getMobs().get("skeleton")[1], dimensionData.getMobs().get("skeleton")[2]));
        if (dimensionData.getMobs().containsKey("creeper"))
            this.addSpawn(EntityCategory.MONSTER, new SpawnEntry(EntityType.CREEPER, dimensionData.getMobs().get("creeper")[0], dimensionData.getMobs().get("creeper")[1], dimensionData.getMobs().get("creeper")[2]));
        if (dimensionData.getMobs().containsKey("slime"))
            this.addSpawn(EntityCategory.MONSTER, new SpawnEntry(EntityType.SLIME, dimensionData.getMobs().get("slime")[0], dimensionData.getMobs().get("slime")[1], dimensionData.getMobs().get("slime")[2]));
        if (dimensionData.getMobs().containsKey("enderman"))
            this.addSpawn(EntityCategory.MONSTER, new SpawnEntry(EntityType.ENDERMAN, dimensionData.getMobs().get("enderman")[0], dimensionData.getMobs().get("enderman")[1], dimensionData.getMobs().get("enderman")[2]));
        if (dimensionData.getMobs().containsKey("witch"))
            this.addSpawn(EntityCategory.MONSTER, new SpawnEntry(EntityType.WITCH, dimensionData.getMobs().get("witch")[0], dimensionData.getMobs().get("witch")[1], dimensionData.getMobs().get("witch")[2]));

        if (dimensionData.getMobs().containsKey("blaze"))
            this.addSpawn(EntityCategory.MONSTER, new SpawnEntry(EntityType.WITCH, dimensionData.getMobs().get("blaze")[0], dimensionData.getMobs().get("blaze")[1], dimensionData.getMobs().get("blaze")[2]));
        if (dimensionData.getMobs().containsKey("piglin"))
            this.addSpawn(EntityCategory.MONSTER, new SpawnEntry(EntityType.WITCH, dimensionData.getMobs().get("piglin")[0], dimensionData.getMobs().get("piglin")[1], dimensionData.getMobs().get("piglin")[2]));
        if (dimensionData.getMobs().containsKey("zombified_piglin"))
            this.addSpawn(EntityCategory.MONSTER, new SpawnEntry(EntityType.WITCH, dimensionData.getMobs().get("zombified_piglin")[0], dimensionData.getMobs().get("zombified_piglin")[1], dimensionData.getMobs().get("zombified_piglin")[2]));
        if (dimensionData.getMobs().containsKey("ghast"))
            this.addSpawn(EntityCategory.MONSTER, new SpawnEntry(EntityType.WITCH, dimensionData.getMobs().get("ghast")[0], dimensionData.getMobs().get("ghast")[1], dimensionData.getMobs().get("ghast")[2]));
    }

    public static BranchedTreeFeatureConfig getTreeConfig(DimensionTreeData data) {
        List<TreeDecorator> decorators = new ArrayList<>();
        if (data.hasLeafVines()) decorators.add(new LeaveVineTreeDecorator());
        if (data.hasTrunkVines()) decorators.add(new TrunkVineTreeDecorator());
        if (data.hasCocoaBeans()) decorators.add(new CocoaBeansTreeDecorator(data.getCocoaChance()));
        if (data.hasBeehives()) decorators.add(new BeehiveTreeDecorator(data.getBeehiveChance()));
        if (data.hasPodzolUnderneath()) decorators.add(new AlterGroundTreeDecorator(new SimpleBlockStateProvider(Blocks.PODZOL.getDefaultState())));

        return new BranchedTreeFeatureConfig.Builder(
                new SimpleBlockStateProvider(data.getWoodType().woodType.getLog().getDefaultState()),
                new SimpleBlockStateProvider(data.getWoodType().woodType.getLeaves().getDefaultState()),
                getFoliagePlacer(data),
                new StraightTrunkPlacer(data.getBaseHeight(), 0, 0)).treeDecorators(decorators).build();
    }

    public static MegaTreeFeatureConfig getMegaTreeConfig(DimensionTreeData data) {
        List<TreeDecorator> decorators = new ArrayList<>();
        if (data.hasLeafVines()) decorators.add(new LeaveVineTreeDecorator());
        if (data.hasTrunkVines()) decorators.add(new TrunkVineTreeDecorator());
        if (data.hasCocoaBeans()) decorators.add(new CocoaBeansTreeDecorator(data.getCocoaChance()));
        if (data.hasBeehives()) decorators.add(new BeehiveTreeDecorator(data.getBeehiveChance()));
        if (data.hasPodzolUnderneath()) decorators.add(new AlterGroundTreeDecorator(new SimpleBlockStateProvider(Blocks.PODZOL.getDefaultState())));

        return new MegaTreeFeatureConfig.Builder(
                new SimpleBlockStateProvider(data.getWoodType().woodType.getLog().getDefaultState()),
                new SimpleBlockStateProvider(data.getWoodType().woodType.getLeaves().getDefaultState()))
                .baseHeight(data.getBaseHeight())
                .heightInterval(data.getFoliageHeightRandom())
                .crownHeight(data.getCrownHeight())
                .treeDecorators(decorators)
                .build();
    }

    private static FoliagePlacer getFoliagePlacer(DimensionTreeData data) {
        switch (data.getFoliagePlacerType()) {
            case HAZEL:
                return new EcotonesHazelFoliagePlacer(Math.max(data.getFoliageRange(), 1), 0, 0, 0, 3 + data.getFoliageHeight());
            case SPRUCE:
                return new SpruceFoliagePlacer(data.getFoliageRange(), 0, 0, data.getFoliageHeight(), 3, 1);
            case SMALL_PINE:
                return new SmallPineFoliagePlacer(data.getFoliageRange(), 0, 0, data.getFoliageHeight(), 3, 1);
            case PINE:
                return new VanillaPineFoliagePlacer(data.getFoliageRange(), 0, 0, data.getFoliageHeight(), 3, 1);
            case STRIATED:
                return new StriatedFoliagePlacer(Math.max(data.getFoliageRange(), 1), 0, 0, 0, 3 + data.getFoliageHeight());
            case UPSIDE_DOWN:
                return new UpsideDownFoliagePlacer(Math.max(data.getFoliageRange(), 1), 0, 0, 0, 3 + data.getFoliageHeight());
            default:
                return new BlobFoliagePlacer(Math.max(data.getFoliageRange(), 1), 0, 0, 0, 3 + data.getFoliageHeight());
        }
    }

    private static Feature<MegaTreeFeatureConfig> getMegaTree(DimensionTreeTypes treeTypes) {
        switch (treeTypes) {
            case MEGA_JUNGLE:
                return Feature.MEGA_JUNGLE_TREE;
            case MEGA_SPRUCE:
                return Feature.MEGA_SPRUCE_TREE;
            case DARK_OAK:
                return Feature.DARK_OAK_TREE;
        }
        return null;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public int getSkyColor() {
        return dimensionData.getDimensionColorPalette().getSkyColor();
    }

    @Override
    @Environment(EnvType.CLIENT)
    public int getGrassColorAt(double x, double z) {
        return dimensionData.getDimensionColorPalette().getGrassColor();
    }

    @Override
    public int getFoliageColor() {
        return dimensionData.getDimensionColorPalette().getFoliageColor();
    }

}