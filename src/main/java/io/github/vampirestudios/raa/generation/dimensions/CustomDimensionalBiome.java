package io.github.vampirestudios.raa.generation.dimensions;

import com.google.common.collect.ImmutableList;
import io.github.vampirestudios.raa.api.enums.DimensionChunkGenerators;
import io.github.vampirestudios.raa.generation.decorator.BiasedNoiseBasedDecoratorConfig;
import io.github.vampirestudios.raa.generation.feature.StoneCircleFeature;
import io.github.vampirestudios.raa.generation.feature.TombFeature;
import io.github.vampirestudios.raa.generation.feature.config.CorruptedFeatureConfig;
import io.github.vampirestudios.raa.registries.Decorators;
import io.github.vampirestudios.raa.registries.Features;
import io.github.vampirestudios.raa.utils.Color;
import io.github.vampirestudios.raa.utils.Rands;
import io.github.vampirestudios.raa.utils.Utils;
import io.github.vampirestudios.raa.utils.WoodType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.carver.Carver;
import net.minecraft.world.gen.decorator.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliage.AcaciaFoliagePlacer;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.foliage.PineFoliagePlacer;
import net.minecraft.world.gen.foliage.SpruceFoliagePlacer;
import net.minecraft.world.gen.stateprovider.SimpleStateProvider;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;

import java.util.ArrayList;
import java.util.Arrays;

public class CustomDimensionalBiome extends Biome {

    private DimensionData dimensionData;

    public CustomDimensionalBiome(DimensionData dimensionData) {
        super((new Biome.Settings()
                .configureSurfaceBuilder(Utils.randomSurfaceBuilder(Rands.randInt(100), dimensionData), SurfaceBuilder.GRASS_CONFIG)
                .precipitation(Biome.Precipitation.RAIN)
                .category(Biome.Category.PLAINS)
                .depth(dimensionData.getBiomeData().getDepth())
                .scale(dimensionData.getBiomeData().getScale())
                .temperature(dimensionData.getBiomeData().getTemperature())
                .downfall(dimensionData.getBiomeData().getDownfall())
                .waterColor(dimensionData.getBiomeData().getWaterColor())
                .waterFogColor(dimensionData.getBiomeData().getWaterColor())
                .parent(null)
        ));
        this.dimensionData = dimensionData;

//        this.addStructureFeature(Feature.VILLAGE, new VillageFeatureConfig("village/plains/town_centers", 6));
//        this.addStructureFeature(Feature.PILLAGER_OUTPOST, new PillagerOutpostFeatureConfig(0.004D));
        if (!(dimensionData.getDimensionChunkGenerator() == DimensionChunkGenerators.FLOATING))
            if (Utils.checkBitFlag(dimensionData.getFlags(), Utils.ABANDONED))
                this.addStructureFeature(Feature.MINESHAFT.configure(new MineshaftFeatureConfig((Utils.checkBitFlag(dimensionData.getFlags(), Utils.CIVILIZED)) ? 0.016D : 0.004D, MineshaftFeature.Type.NORMAL)));
//        this.addStructureFeature(Feature.STRONGHOLD.configure(FeatureConfig.DEFAULT));
        DefaultBiomeFeatures.addLandCarvers(this);
        DefaultBiomeFeatures.addDefaultStructures(this);
        DefaultBiomeFeatures.addDefaultLakes(this);
        DefaultBiomeFeatures.addDungeons(this);
        if (Rands.chance(4) && !Utils.checkBitFlag(dimensionData.getFlags(), Utils.DEAD) && !Utils.checkBitFlag(dimensionData.getFlags(), Utils.DRY) && !Utils.checkBitFlag(dimensionData.getFlags(), Utils.MOLTEN)) {
            DefaultBiomeFeatures.addPlainsTallGrass(this);
        }
        DefaultBiomeFeatures.addMineables(this);
        DefaultBiomeFeatures.addDefaultOres(this);
        DefaultBiomeFeatures.addDefaultDisks(this);
        if (!Utils.checkBitFlag(dimensionData.getFlags(), Utils.CORRUPTED) && !Utils.checkBitFlag(dimensionData.getFlags(), Utils.DEAD) && !Utils.checkBitFlag(dimensionData.getFlags(), Utils.DRY) && !Utils.checkBitFlag(dimensionData.getFlags(), Utils.MOLTEN)) {
            int forestConfig = Rands.randInt(4);
            if (Utils.checkBitFlag(dimensionData.getFlags(), Utils.LUSH)) forestConfig = 0;
            BranchedTreeFeatureConfig config = getTreeConfig();
            switch (forestConfig) {
                case 0: //33% chance of full forest, 33% chance of patchy forest, 33% of no forest
                    for (int i = 0; i< Rands.randInt((Utils.checkBitFlag(dimensionData.getFlags(), Utils.LUSH)) ? 7 : 3)+1;i++) {
                        if (Rands.chance(3)) {
                            switch (Rands.randInt(3)) {
                                case 0:
                                    this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.MEGA_JUNGLE_TREE.configure(getMegaTreeConfig()).createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(1, Rands.randFloatRange(0.05F, 0.4F), 1))));
                                    break;
                                case 1:
                                    this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.MEGA_SPRUCE_TREE.configure(getMegaTreeConfig()).createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(1, Rands.randFloatRange(0.05F, 0.4F), 1))));
                                    break;
                                case 2:
                                    this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.DARK_OAK_TREE.configure(getMegaTreeConfig()).createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(1, Rands.randFloatRange(0.05F, 0.4F), 1))));
                                    break;
                            }
                        } else {
                            BranchedTreeFeatureConfig treeConfig = getTreeConfig();
                            if (treeConfig.foliagePlacer instanceof AcaciaFoliagePlacer) {
                                if (!Rands.chance(4)) {
                                    this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.ACACIA_TREE.configure(treeConfig).createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(0, Rands.randFloatRange(0.05F, 0.6F), 1))));
                                    continue;
                                }
                            }
                            this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.NORMAL_TREE.configure(treeConfig).createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(0, Rands.randFloatRange(0.05F, 0.6F), 1))));
                        }
                    }
                    break;
                case 1:
                    //Small, inbetween forests and larger, thinner forests
                    BranchedTreeFeatureConfig treeConfig = getTreeConfig();
                    float chance = Rands.randInt(24) * 10F + 80F;
                    float chance2 = Rands.randInt(12) * 10F + 120F;
                    if (treeConfig.foliagePlacer instanceof AcaciaFoliagePlacer) {
                        if (!Rands.chance(4)) {
                            this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.ACACIA_TREE.configure(config).createDecoratedFeature(Decorators.BIASED_NOISE_DECORATOR.configure(new BiasedNoiseBasedDecoratorConfig(Rands.randInt(20), chance, 1, Heightmap.Type.WORLD_SURFACE_WG))));
                            this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.ACACIA_TREE.configure(config).createDecoratedFeature(Decorators.BIASED_NOISE_DECORATOR.configure(new BiasedNoiseBasedDecoratorConfig(Rands.randInt(10), chance2, 0.0D, Heightmap.Type.WORLD_SURFACE_WG))));
                            break;
                        }
                    }
                        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.NORMAL_TREE.configure(config).createDecoratedFeature(Decorators.BIASED_NOISE_DECORATOR.configure(new BiasedNoiseBasedDecoratorConfig(Rands.randInt(20), chance, 1, Heightmap.Type.WORLD_SURFACE_WG))));
                        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.NORMAL_TREE.configure(config).createDecoratedFeature(Decorators.BIASED_NOISE_DECORATOR.configure(new BiasedNoiseBasedDecoratorConfig(Rands.randInt(10), chance2, 0.0D, Heightmap.Type.WORLD_SURFACE_WG))));
                    break;
                case 2:
                    if (Rands.chance(3)) {
                        switch (Rands.randInt(3)) {
                            case 0:
                                this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.MEGA_JUNGLE_TREE.configure(getMegaTreeConfig()).createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(1, 1, 0))));
                                break;
                            case 1:
                                this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.MEGA_SPRUCE_TREE.configure(getMegaTreeConfig()).createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(1, 1, 0))));
                                break;
                            case 2:
                                this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.DARK_OAK_TREE.configure(getMegaTreeConfig()).createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(1, 1, 0))));
                                break;
                        }
                    } else {
                        BranchedTreeFeatureConfig normalTreeConfig = getTreeConfig();
                        if (normalTreeConfig.foliagePlacer instanceof AcaciaFoliagePlacer) {
                            if (!Rands.chance(4)) {
                                this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.ACACIA_TREE.configure(normalTreeConfig).createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(1, 1, 0))));
                                break;
                            }
                        }
                        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.NORMAL_TREE.configure(normalTreeConfig).createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(1, 1, 0))));
                    }
                    break;
                case 3:
                    DefaultBiomeFeatures.addPlainsFeatures(this);
                    break;
            }
        }
        if (!Utils.checkBitFlag(dimensionData.getFlags(), Utils.DEAD) && !Utils.checkBitFlag(dimensionData.getFlags(), Utils.CORRUPTED)) {
            this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.FLOWER.configure(DefaultBiomeFeatures.field_21089)
                    .createDecoratedFeature(Decorator.COUNT_HEIGHTMAP_DOUBLE.configure(new CountDecoratorConfig(Utils.checkBitFlag(dimensionData.getFlags(), Utils.LUSH) ? 20 : 50))));
        }

        if (Utils.checkBitFlag(dimensionData.getFlags(), Utils.CORRUPTED)) {
            this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Features.CRATER_FEATURE.configure(new CorruptedFeatureConfig(true)).createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(0, Rands.randFloatRange(0, 1F), 1))));
            this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Features.CORRUPTED_NETHRRACK.configure(new DefaultFeatureConfig()).createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(0, 0.9F, 1))));
//            this.addFeature(GenerationStep.Feature.LOCAL_MODIFICATIONS, net.minecraft.world.gen.feature.Feature.FOREST_ROCK.configure(new BoulderFeatureConfig(Blocks.OBSIDIAN.getDefaultState(), 1)).createDecoratedFeature(Decorator.FOREST_ROCK.configure(new CountDecoratorConfig(2))));
        } else {
            if (Rands.chance(4)) {
                this.addFeature(GenerationStep.Feature.SURFACE_STRUCTURES, Features.CRATER_FEATURE.configure(new CorruptedFeatureConfig(false)).createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(0, Rands.randFloatRange(0, 1F), 1))));
            }
        }

        if (Utils.checkBitFlag(dimensionData.getFlags(), Utils.DEAD) || Utils.checkBitFlag(dimensionData.getFlags(), Utils.DRY)) {
            BranchedTreeFeatureConfig treeConfig = getTreeConfig();
            this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Features.SMALL_SKELETON_TREE.configure(treeConfig).createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(0, Rands.randFloatRange(0, 0.5F), 1))));
            this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Features.LARGE_SKELETON_TREE.configure(treeConfig).createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(0, Rands.randFloatRange(0, 0.5F), 1))));

            this.addFeature(GenerationStep.Feature.SURFACE_STRUCTURES, net.minecraft.world.gen.feature.Feature.FOSSIL.configure(FeatureConfig.DEFAULT).createDecoratedFeature(Decorator.CHANCE_PASSTHROUGH.configure(new LakeDecoratorConfig(64))));
        }

        //TODO: This doesn't really fit dead dimensions, as they are totally devoid of life.
//        if (Utils.checkBitFlag(dimensionData.getFlags(), Utils.DEAD)) {
//            BranchedTreeFeatureConfig treeConfig = getDeadTreeConfig();
//            this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Features.SMALL_DEADWOOD_TREE.configure(treeConfig).createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(1, 0.05F, 1))));
//            this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Features.LARGE_DEADWOOD_TREE.configure(treeConfig).createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(4, 0.1F, 1))));
//        }

        if (Utils.checkBitFlag(dimensionData.getFlags(), Utils.TECTONIC)) {
            this.addCarver(GenerationStep.Carver.AIR, Biome.configureCarver(Carver.CANYON, new ProbabilityConfig(1F)));
            this.addCarver(GenerationStep.Carver.AIR, Biome.configureCarver(Carver.CAVE, new ProbabilityConfig(1F)));
        }

        float towerChance = 0;
        float campfireChance = Rands.randFloatRange(0.003F, 0.005F);

        if (Utils.checkBitFlag(dimensionData.getFlags(), Utils.ABANDONED)) towerChance = Rands.randFloatRange(0.002F, 0.003F);
        if (Utils.checkBitFlag(dimensionData.getFlags(), Utils.DEAD)) campfireChance = 0;
        if (Utils.checkBitFlag(dimensionData.getFlags(), Utils.CIVILIZED)) {
            campfireChance = Rands.randFloatRange(0.005F, 0.007F);
            towerChance = Rands.randFloatRange(0.002F, 0.008F);
        }

        //debug stuff
//        campfireChance = 1;
//        towerChance = 1;

        // TODO fix this
        this.addFeature(GenerationStep.Feature.SURFACE_STRUCTURES, Features.TOWER.configure(new DefaultFeatureConfig()).createDecoratedFeature(Decorators.RANDOM_EXTRA_HEIGHTMAP_DECORATOR.configure(new CountExtraChanceDecoratorConfig(0, towerChance, 1))));
        this.addFeature(GenerationStep.Feature.SURFACE_STRUCTURES, Features.CAMPFIRE.configure(new DefaultFeatureConfig()).createDecoratedFeature(Decorators.RANDOM_EXTRA_HEIGHTMAP_DECORATOR.configure(new CountExtraChanceDecoratorConfig(0, campfireChance, 1))));

        if (Rands.chance(6)) {
            this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.configure(new RandomFeatureConfig(
                    ImmutableList.of(
                            Feature.HUGE_BROWN_MUSHROOM.configure(DefaultBiomeFeatures.field_21143).method_23387(1)),
                    Feature.NORMAL_TREE.configure(DefaultBiomeFeatures.field_21126)
            )).createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(0, Rands.randFloatRange(0.01F, 1F), 1))));
            this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.configure(new RandomFeatureConfig(
                    ImmutableList.of(
                            Feature.HUGE_RED_MUSHROOM.configure(DefaultBiomeFeatures.field_21142).method_23387(1)),
                    Feature.NORMAL_TREE.configure(DefaultBiomeFeatures.field_21126)
            )).createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(0, Rands.randFloatRange(0.01F, 1F), 1))));
        }
        if(Rands.chance(8))
            DefaultBiomeFeatures.addMossyRocks(this);

        DefaultBiomeFeatures.addDefaultMushrooms(this);
        DefaultBiomeFeatures.addDefaultVegetation(this);
        DefaultBiomeFeatures.addSprings(this);
        DefaultBiomeFeatures.addFrozenTopLayer(this);

        StoneCircleFeature STONE_CIRCLE = Features.register(String.format("%s_stone_circle", dimensionData.getName().toLowerCase()), new StoneCircleFeature(dimensionData));
        this.addFeature(GenerationStep.Feature.SURFACE_STRUCTURES, STONE_CIRCLE.configure(FeatureConfig.DEFAULT).createDecoratedFeature(Decorator.CHANCE_HEIGHTMAP.configure(new LakeDecoratorConfig(120))));

        if (Utils.checkBitFlag(dimensionData.getFlags(), Utils.DEAD) && Utils.checkBitFlag(dimensionData.getFlags(), Utils.CIVILIZED)) {

            this.addFeature(GenerationStep.Feature.SURFACE_STRUCTURES, Features.SPIDER_LAIR.configure(FeatureConfig.DEFAULT).createDecoratedFeature(Decorator.CHANCE_HEIGHTMAP.configure(new LakeDecoratorConfig(230))));

            TombFeature tomb = Features.register(String.format("%s_tomb", dimensionData.getName().toLowerCase()), new TombFeature(dimensionData));
            this.addFeature(GenerationStep.Feature.SURFACE_STRUCTURES, tomb.configure(FeatureConfig.DEFAULT).createDecoratedFeature(Decorators.RANDOM_EXTRA_HEIGHTMAP_DECORATOR.configure(new CountExtraChanceDecoratorConfig(0, 0.009f, 1))));
        } else if (Utils.checkBitFlag(dimensionData.getFlags(), Utils.DEAD) && Utils.checkBitFlag(dimensionData.getFlags(), Utils.ABANDONED)) {
            TombFeature tomb = Features.register(String.format("%s_tomb", dimensionData.getName().toLowerCase()), new TombFeature(dimensionData));
            this.addFeature(GenerationStep.Feature.SURFACE_STRUCTURES, tomb.configure(FeatureConfig.DEFAULT).createDecoratedFeature(Decorators.RANDOM_EXTRA_HEIGHTMAP_DECORATOR.configure(new CountExtraChanceDecoratorConfig(0, 0.009f
                    , 1))));
        }

        if (dimensionData.getMobs().containsKey("sheep")) this.addSpawn(EntityCategory.CREATURE, new Biome.SpawnEntry(EntityType.SHEEP, dimensionData.getMobs().get("sheep")[0], dimensionData.getMobs().get("sheep")[1], dimensionData.getMobs().get("sheep")[2]));
        if (dimensionData.getMobs().containsKey("pig")) this.addSpawn(EntityCategory.CREATURE, new Biome.SpawnEntry(EntityType.PIG, dimensionData.getMobs().get("pig")[0], dimensionData.getMobs().get("pig")[1], dimensionData.getMobs().get("pig")[2]));
        if (dimensionData.getMobs().containsKey("chicken")) this.addSpawn(EntityCategory.CREATURE, new Biome.SpawnEntry(EntityType.CHICKEN, dimensionData.getMobs().get("chicken")[0], dimensionData.getMobs().get("chicken")[1], dimensionData.getMobs().get("chicken")[2]));
        if (dimensionData.getMobs().containsKey("cow")) this.addSpawn(EntityCategory.CREATURE, new Biome.SpawnEntry(EntityType.COW, dimensionData.getMobs().get("cow")[0], dimensionData.getMobs().get("cow")[1], dimensionData.getMobs().get("cow")[2]));
        if (dimensionData.getMobs().containsKey("horse")) this.addSpawn(EntityCategory.CREATURE, new Biome.SpawnEntry(EntityType.HORSE, dimensionData.getMobs().get("horse")[0], dimensionData.getMobs().get("horse")[1], dimensionData.getMobs().get("horse")[2]));
        if (dimensionData.getMobs().containsKey("donkey")) this.addSpawn(EntityCategory.CREATURE, new Biome.SpawnEntry(EntityType.DONKEY, dimensionData.getMobs().get("donkey")[0], dimensionData.getMobs().get("donkey")[1], dimensionData.getMobs().get("donkey")[2]));

        if (dimensionData.getMobs().containsKey("bat")) this.addSpawn(EntityCategory.AMBIENT, new Biome.SpawnEntry(EntityType.BAT, dimensionData.getMobs().get("bat")[0], dimensionData.getMobs().get("bat")[1], dimensionData.getMobs().get("bat")[2]));

        if (dimensionData.getMobs().containsKey("spider")) this.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.SPIDER, dimensionData.getMobs().get("spider")[0], dimensionData.getMobs().get("spider")[1], dimensionData.getMobs().get("spider")[2]));
        if (dimensionData.getMobs().containsKey("zombie")) this.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.ZOMBIE, dimensionData.getMobs().get("zombie")[0], dimensionData.getMobs().get("zombie")[1], dimensionData.getMobs().get("zombie")[2]));
        if (dimensionData.getMobs().containsKey("zombie_villager")) this.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.ZOMBIE_VILLAGER, dimensionData.getMobs().get("zombie_villager")[0], dimensionData.getMobs().get("zombie_villager")[1], dimensionData.getMobs().get("zombie_villager")[2]));
        if (dimensionData.getMobs().containsKey("skeleton")) this.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.SKELETON, dimensionData.getMobs().get("skeleton")[0], dimensionData.getMobs().get("skeleton")[1], dimensionData.getMobs().get("skeleton")[2]));
        if (dimensionData.getMobs().containsKey("creeper")) this.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.CREEPER, dimensionData.getMobs().get("creeper")[0], dimensionData.getMobs().get("creeper")[1], dimensionData.getMobs().get("creeper")[2]));
        if (dimensionData.getMobs().containsKey("slime")) this.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.SLIME, dimensionData.getMobs().get("slime")[0], dimensionData.getMobs().get("slime")[1], dimensionData.getMobs().get("slime")[2]));
        if (dimensionData.getMobs().containsKey("enderman")) this.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.ENDERMAN, dimensionData.getMobs().get("enderman")[0], dimensionData.getMobs().get("enderman")[1], dimensionData.getMobs().get("enderman")[2]));
        if (dimensionData.getMobs().containsKey("witch")) this.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.WITCH, dimensionData.getMobs().get("witch")[0], dimensionData.getMobs().get("witch")[1], dimensionData.getMobs().get("witch")[2]));
    }

    private static BranchedTreeFeatureConfig getTreeConfig() {
        BranchedTreeFeatureConfig config;
        int height = Rands.randIntRange(2, 24);
        int foliageHeight = Rands.randIntRange(1, 5);
        BlockState logState;
        BlockState leafState;
        WoodType woodType = new ArrayList<>(Arrays.asList(WoodType.VANILLA)).get(Rands.randInt(WoodType.VANILLA.length));
        logState = Registry.BLOCK.get(new Identifier(woodType.getIdentifier().getPath() + "_log")).getDefaultState();
        leafState = Registry.BLOCK.get(new Identifier(woodType.getIdentifier().getPath() + "_leaves")).getDefaultState();
        /*switch (leafType) {
            case 1:
                logState = Blocks.BIRCH_LOG.getDefaultState();
                leafState = Blocks.BIRCH_LEAVES.getDefaultState();
                break;
            case 2:
                logState = Blocks.SPRUCE_LOG.getDefaultState();
                leafState = Blocks.SPRUCE_LEAVES.getDefaultState();
                break;
            case 3:
                logState = Blocks.JUNGLE_LOG.getDefaultState();
                leafState = Blocks.JUNGLE_LEAVES.getDefaultState();
                break;
            case 0:
            default:
                logState = Blocks.OAK_LOG.getDefaultState();
                leafState = Blocks.OAK_LEAVES.getDefaultState();
                break;
        }*/

        ArrayList<TreeDecorator> decoratorsRaw = new ArrayList<>();
        if (Rands.chance(3)) decoratorsRaw.add(new LeaveVineTreeDecorator());
        if (Rands.chance(3)) decoratorsRaw.add(new TrunkVineTreeDecorator());
        if (Rands.chance(3)) decoratorsRaw.add(new CocoaBeansTreeDecorator(Rands.randFloatRange(0.1F, 1F)));
        //if (Rands.chance(3)) decoratorsRaw.add(new BeehiveTreeDecorator(Rands.randFloatRange(0.01F, 1F)));
        if (Rands.chance(3)) decoratorsRaw.add(new AlterGroundTreeDecorator(new SimpleStateProvider(Blocks.PODZOL.getDefaultState())));
        ImmutableList<TreeDecorator> decorators = ImmutableList.copyOf(decoratorsRaw);

        switch (Rands.randInt(4)) {
            case 1:
                config = (new BranchedTreeFeatureConfig.Builder(new SimpleStateProvider(logState), new SimpleStateProvider(leafState), new SpruceFoliagePlacer(Rands.randIntRange(1, 4), 0)))
                        .method_23428(Rands.randIntRange(1, 6)) //trunk height rand 1
                        .method_23430(height - 1) //trunk height rand 2
                        .method_23437(foliageHeight) //foliage amount
                        .method_23438(Rands.randIntRange(1, 6)) //random foliage offset
                        .method_23439(Rands.randIntRange(0, 8)) //water depth
                        .method_23433(Rands.randIntRange(1, 8)) //trunk height
                        .method_23434(Rands.randIntRange(1, 4)) //trunk height offset
                        .method_23436(Rands.randIntRange(1, 2)) //foliage height
                        .method_23427()
                        .method_23429(decorators)
                        .method_23431();
            break;
            case 2:
                config = (new BranchedTreeFeatureConfig.Builder(new SimpleStateProvider(logState), new SimpleStateProvider(leafState), new PineFoliagePlacer(Rands.randIntRange(1, 2), 0)))
                        .method_23428(Rands.randIntRange(1, 4))
                        .method_23430(height - 1)
                        .method_23435(Rands.randIntRange(1, 2))
                        .method_23437(foliageHeight/2)
                        .method_23438(Rands.randIntRange(1, 4))
                        .method_23439(Rands.randIntRange(0, 8)) //water depth
                        .method_23427()
                        .method_23429(decorators)
                        .method_23431();
            break;
            case 3:
                config = (new BranchedTreeFeatureConfig.Builder(new SimpleStateProvider(logState), new SimpleStateProvider(leafState), new AcaciaFoliagePlacer(Rands.randIntRange(1, 4), 0)))
                        .method_23428(Rands.randIntRange(1, 6))
                        .method_23430(height - 1)
                        .method_23432(height + Rands.randIntRange(1, 4))
                        .method_23433(Rands.randIntRange(1, 8))
                        .method_23437(foliageHeight) //foliage amount
                        .method_23439(Rands.randIntRange(0, 8)) //water depth
                        .method_23427()
                        .method_23429(decorators)
                        .method_23431();
            break;
            case 0:
            default:
                config = (new BranchedTreeFeatureConfig.Builder(new SimpleStateProvider(logState), new SimpleStateProvider(leafState), new BlobFoliagePlacer(Rands.randIntRange(1, 3), 0)))
                        .method_23428(Rands.randIntRange(1, 6)) //
                        .method_23430(height - 1) //trunk height
                        .method_23437(foliageHeight) //foliage amount
                        .method_23438(Rands.randIntRange(1, 6)) //random foliage offset
                        .method_23439(Rands.randIntRange(0, 8)) //water depth
                        .method_23427()
                        .method_23429(decorators)
                        .method_23431();
        }
        return config;
    }

    private static BranchedTreeFeatureConfig getDeadTreeConfig() {
        BranchedTreeFeatureConfig config;
        int height = Rands.randIntRange(2, 24);
        BlockState logState;
        int leafType = Rands.randInt(4);
        switch (leafType) {
            case 1:
                logState = Blocks.BIRCH_LOG.getDefaultState();
                break;
            case 2:
                logState = Blocks.SPRUCE_LOG.getDefaultState();
                break;
            case 3:
                logState = Blocks.JUNGLE_LOG.getDefaultState();
                break;
            case 0:
            default:
                logState = Blocks.OAK_LOG.getDefaultState();
                break;
        }

        ArrayList<TreeDecorator> decoratorsRaw = new ArrayList<>();
        if (Rands.chance(3)) decoratorsRaw.add(new CocoaBeansTreeDecorator(Rands.randFloatRange(0.1F, 1F)));
        //if (Rands.chance(3)) decoratorsRaw.add(new BeehiveTreeDecorator(Rands.randFloatRange(0.01F, 1F)));
        if (Rands.chance(3)) decoratorsRaw.add(new AlterGroundTreeDecorator(new SimpleStateProvider(Blocks.PODZOL.getDefaultState())));
        ImmutableList<TreeDecorator> decorators = ImmutableList.copyOf(decoratorsRaw);

        switch (Rands.randInt(4)) {
            case 1:
                config = (new BranchedTreeFeatureConfig.Builder(new SimpleStateProvider(logState), new SimpleStateProvider(Blocks.AIR.getDefaultState()), new SpruceFoliagePlacer(Rands.randIntRange(1, 4), 0)))
                        .method_23428(Rands.randIntRange(1, 6)) //trunk height rand 1
                        .method_23430(height - 1) //trunk height rand 2
                        .method_23437(0) //foliage amount
                        .method_23438(Rands.randIntRange(1, 6)) //random foliage offset
                        .method_23439(Rands.randIntRange(0, 8)) //water depth
                        .method_23433(Rands.randIntRange(1, 8)) //trunk height
                        .method_23434(Rands.randIntRange(1, 4)) //trunk height offset
                        .method_23436(Rands.randIntRange(1, 2)) //foliage height
                        .method_23427()
                        .method_23429(decorators)
                        .method_23431();
                break;
            case 2:
                config = (new BranchedTreeFeatureConfig.Builder(new SimpleStateProvider(logState), new SimpleStateProvider(Blocks.AIR.getDefaultState()), new PineFoliagePlacer(Rands.randIntRange(1, 2), 0)))
                        .method_23428(Rands.randIntRange(1, 4))
                        .method_23430(height - 1)
                        .method_23435(Rands.randIntRange(1, 2))
                        .method_23437(0/2)
                        .method_23438(Rands.randIntRange(1, 4))
                        .method_23439(Rands.randIntRange(0, 8)) //water depth
                        .method_23427()
                        .method_23429(decorators)
                        .method_23431();
                break;
            case 3:
                config = (new BranchedTreeFeatureConfig.Builder(new SimpleStateProvider(logState), new SimpleStateProvider(Blocks.AIR.getDefaultState()), new AcaciaFoliagePlacer(Rands.randIntRange(1, 4), 0)))
                        .method_23428(Rands.randIntRange(1, 6))
                        .method_23430(height - 1)
                        .method_23432(height + Rands.randIntRange(1, 4))
                        .method_23433(Rands.randIntRange(1, 8))
                        .method_23437(0) //foliage amount
                        .method_23439(Rands.randIntRange(0, 8)) //water depth
                        .method_23427()
                        .method_23429(decorators)
                        .method_23431();
                break;
            case 0:
            default:
                config = (new BranchedTreeFeatureConfig.Builder(new SimpleStateProvider(logState), new SimpleStateProvider(Blocks.AIR.getDefaultState()), new BlobFoliagePlacer(Rands.randIntRange(1, 3), 0)))
                        .method_23428(Rands.randIntRange(1, 6)) //
                        .method_23430(height - 1) //trunk height
                        .method_23437(0) //foliage amount
                        .method_23438(Rands.randIntRange(1, 6)) //random foliage offset
                        .method_23439(Rands.randIntRange(0, 8)) //water depth
                        .method_23427()
                        .method_23429(decorators)
                        .method_23431();
        }
        return config;
    }

    private static MegaTreeFeatureConfig getMegaTreeConfig() {
        MegaTreeFeatureConfig config;
        int height = Rands.randIntRange(10, 40);
        BlockState logState;
        BlockState leafState;
        int leafType = Rands.randInt(4);
        switch (leafType) {
            case 1:
                logState = Blocks.BIRCH_LOG.getDefaultState();
                leafState = Blocks.BIRCH_LEAVES.getDefaultState();
                break;
            case 2:
                logState = Blocks.SPRUCE_LOG.getDefaultState();
                leafState = Blocks.SPRUCE_LEAVES.getDefaultState();
                break;
            case 3:
                logState = Blocks.JUNGLE_LOG.getDefaultState();
                leafState = Blocks.JUNGLE_LEAVES.getDefaultState();
                break;
            case 0:
            default:
                logState = Blocks.OAK_LOG.getDefaultState();
                leafState = Blocks.OAK_LEAVES.getDefaultState();
                break;
        }

        ArrayList<TreeDecorator> decoratorsRaw = new ArrayList<>();
        if (Rands.chance(3)) decoratorsRaw.add(new LeaveVineTreeDecorator());
        if (Rands.chance(3)) decoratorsRaw.add(new TrunkVineTreeDecorator());
        if (Rands.chance(3)) decoratorsRaw.add(new CocoaBeansTreeDecorator(Rands.randFloatRange(0.1F, 1F)));
        //if (Rands.chance(3)) decoratorsRaw.add(new BeehiveTreeDecorator(Rands.randFloatRange(0.01F, 1F)));
        if (Rands.chance(3)) decoratorsRaw.add(new AlterGroundTreeDecorator(new SimpleStateProvider(Blocks.PODZOL.getDefaultState())));
        ImmutableList<TreeDecorator> decorators = ImmutableList.copyOf(decoratorsRaw);
        config = (new MegaTreeFeatureConfig.Builder(new SimpleStateProvider(logState), new SimpleStateProvider(leafState)))
                .method_23410(height-2).method_23412(height + 4).method_23411(decorators).method_23409();
        return config;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public int getSkyColor() {
        if(dimensionData.getDimensionColorPalette().getSkyColor() != 0) {
            return dimensionData.getDimensionColorPalette().getSkyColor();
        } else {
            return Color.WHITE.getColor();
        }
    }

    @Override
    @Environment(EnvType.CLIENT)
    public int getFoliageColorAt() {
        return dimensionData.getDimensionColorPalette().getFoliageColor();
    }

    @Override
    @Environment(EnvType.CLIENT)
    public int getGrassColorAt(double double_1, double double_2) {
        return dimensionData.getDimensionColorPalette().getGrassColor();
    }

}
