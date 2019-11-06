package io.github.vampirestudios.raa.generation.dimensions;

import com.google.common.collect.ImmutableList;
import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.generation.decorator.BiasedNoiseBasedDecoratorConfig;
import io.github.vampirestudios.raa.generation.feature.config.CorruptedFeatureConfig;
import io.github.vampirestudios.raa.registries.Decorators;
import io.github.vampirestudios.raa.registries.Features;
import io.github.vampirestudios.raa.utils.Color;
import io.github.vampirestudios.raa.utils.Rands;
import io.github.vampirestudios.raa.utils.Utils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
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

public class CustomDimensionalBiome extends Biome {

    private DimensionData dimensionData;

    public CustomDimensionalBiome(DimensionData dimensionData) {
        super((new Biome.Settings()
                .configureSurfaceBuilder(Utils.random(Rands.randInt(100)), SurfaceBuilder.GRASS_CONFIG)
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
        this.addStructureFeature(Feature.MINESHAFT.configure(new MineshaftFeatureConfig(0.004D, MineshaftFeature.Type.NORMAL)));
        this.addStructureFeature(Feature.STRONGHOLD.configure(FeatureConfig.DEFAULT));
        DefaultBiomeFeatures.addLandCarvers(this);
        DefaultBiomeFeatures.addDefaultStructures(this);
        DefaultBiomeFeatures.addDefaultLakes(this);
        DefaultBiomeFeatures.addDungeons(this);
        if (Rands.chance(4)) {
            DefaultBiomeFeatures.addPlainsTallGrass(this);
        }
        DefaultBiomeFeatures.addMineables(this);
        DefaultBiomeFeatures.addDefaultOres(this);
        DefaultBiomeFeatures.addDefaultDisks(this);
        if (!Utils.checkBitFlag(dimensionData.getFlags(), Utils.CORRUPTED) && !Utils.checkBitFlag(dimensionData.getFlags(), Utils.DEAD)) {
//            int forestConfig = Rands.randInt(3);
            int forestConfig = 0; //TODO: implement new trees for other types
            NormalTreeFeatureConfig config = getTreeConfig();
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
                            NormalTreeFeatureConfig treeConfig = getTreeConfig();
                            if (treeConfig.foliagePlacer instanceof AcaciaFoliagePlacer) {
                                if (!Rands.chance(4)) {
                                    this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.ACACIA_TREE.configure(config).createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(0, Rands.randFloatRange(0.05F, 0.6F), 1))));
                                    continue;
                                }
                            }
                            this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.NORMAL_TREE.configure(config).createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(0, Rands.randFloatRange(0.05F, 0.6F), 1))));
                        }
                    }
                    break;
                case 1:
                    //Small, inbetween forests
                    float chance = Rands.randInt(24) * 10F + 80F;
                    this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.NORMAL_TREE.configure(config).createDecoratedFeature(Decorators.BIASED_NOISE_DECORATOR.configure(new BiasedNoiseBasedDecoratorConfig(Rands.randInt(20), chance, 1, Heightmap.Type.WORLD_SURFACE_WG))));
                    this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.FANCY_TREE.configure(config).createDecoratedFeature(Decorators.BIASED_NOISE_DECORATOR.configure(new BiasedNoiseBasedDecoratorConfig(Rands.randInt(3), chance, 1, Heightmap.Type.WORLD_SURFACE_WG))));
                    //Large, thinner forests
                    float chance2 = Rands.randInt(12) * 10F + 120F;
                    this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.NORMAL_TREE.configure(config).createDecoratedFeature(Decorators.BIASED_NOISE_DECORATOR.configure(new BiasedNoiseBasedDecoratorConfig(Rands.randInt(10), chance2, 0.0D, Heightmap.Type.WORLD_SURFACE_WG))));
                    this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.FANCY_TREE.configure(config).createDecoratedFeature(Decorators.BIASED_NOISE_DECORATOR.configure(new BiasedNoiseBasedDecoratorConfig(Rands.randInt(2), chance2, 0.0D, Heightmap.Type.WORLD_SURFACE_WG))));
                    break;
                case 2:
                    DefaultBiomeFeatures.addPlainsFeatures(this);
                    break;
            }
        }
        if (!Utils.checkBitFlag(dimensionData.getFlags(), Utils.DEAD)) {
            this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.FLOWER.configure(DefaultBiomeFeatures.field_21089)
                    .createDecoratedFeature(Decorator.COUNT_HEIGHTMAP_DOUBLE.configure(new CountDecoratorConfig(50))));
        }

        if (Utils.checkBitFlag(dimensionData.getFlags(), Utils.CORRUPTED)) {
            this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Features.CRATER_FEATURE.configure(new CorruptedFeatureConfig(true)).createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(0, Rands.randFloatRange(0, 1F), 1))));
            this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Features.CORRUPTED_NETHRRACK.configure(new DefaultFeatureConfig()).createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(0, 0.9F, 1))));
        } else {
            if (Rands.chance(4)) {
                this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Features.CRATER_FEATURE.configure(new CorruptedFeatureConfig(false)).createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(0, Rands.randFloatRange(0, 1F), 1))));
            }
        }

        if (Utils.checkBitFlag(dimensionData.getFlags(), Utils.TECTONIC)) {
            this.addCarver(GenerationStep.Carver.AIR, Biome.configureCarver(Carver.CANYON, new ProbabilityConfig(1F)));
            this.addCarver(GenerationStep.Carver.AIR, Biome.configureCarver(Carver.CAVE, new ProbabilityConfig(1F)));
        }

        float towerChance = Rands.randFloatRange(0.001F, 0.003F);
        float campfireChance = Rands.randFloatRange(0.003F, 0.005F);
        if (Utils.checkBitFlag(dimensionData.getFlags(), Utils.ABANDONED) || Utils.checkBitFlag(dimensionData.getFlags(), Utils.DEAD)) towerChance = Rands.randFloatRange(0.002F, 0.006F);
        if (Utils.checkBitFlag(dimensionData.getFlags(), Utils.ABANDONED) || Utils.checkBitFlag(dimensionData.getFlags(), Utils.DEAD)) campfireChance = 0;
        if (Utils.checkBitFlag(dimensionData.getFlags(), Utils.CIVILIZED)) campfireChance = Rands.randFloatRange(0.005F, 0.007F);

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
        /*if(Rands.chance(8))
            DefaultBiomeFeatures.addMossyRocks(this);
        if(Rands.chance(20))
            DefaultBiomeFeatures.addGiantSpruceTaigaTrees(this);
        if(Rands.chance(10))
            DefaultBiomeFeatures.addIcebergs(this);
        if(Rands.chance(8))
            DefaultBiomeFeatures.addTaigaTrees(this);
        if(Rands.chance(10) && dimensionData.getBiomeData().getScale() > 1.0F)
            DefaultBiomeFeatures.addMountainTrees(this);*/
//        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.NORMAL_TREE, FeatureConfig.DEFAULT, Decorator.COUNT_EXTRA_HEIGHTMAP, new CountExtraChanceDecoratorConfig(Rands.randInt(4), 0.1F, 1)));


        DefaultBiomeFeatures.addDefaultMushrooms(this);
        DefaultBiomeFeatures.addDefaultVegetation(this);
        DefaultBiomeFeatures.addSprings(this);
        DefaultBiomeFeatures.addFrozenTopLayer(this);

        if (!Utils.checkBitFlag(dimensionData.getFlags(), Utils.DEAD)) {
            if (Rands.chance(2))
                this.addSpawn(EntityCategory.CREATURE, new Biome.SpawnEntry(EntityType.SHEEP, Rands.randInt(300), 4, 4));
            if (Rands.chance(2))
                this.addSpawn(EntityCategory.CREATURE, new Biome.SpawnEntry(EntityType.PIG, Rands.randInt(300), 4, 4));
            if (Rands.chance(2))
                this.addSpawn(EntityCategory.CREATURE, new Biome.SpawnEntry(EntityType.CHICKEN, Rands.randInt(300), 4, 4));
            if (Rands.chance(2))
                this.addSpawn(EntityCategory.CREATURE, new Biome.SpawnEntry(EntityType.COW, Rands.randInt(300), 4, 4));
            if (Rands.chance(2))
                this.addSpawn(EntityCategory.CREATURE, new Biome.SpawnEntry(EntityType.HORSE, Rands.randInt(300), 2, 6));
            if (Rands.chance(2))
                this.addSpawn(EntityCategory.CREATURE, new Biome.SpawnEntry(EntityType.DONKEY, Rands.randInt(300), 1, 3));
        }

        if (Rands.chance(2)) this.addSpawn(EntityCategory.AMBIENT, new Biome.SpawnEntry(EntityType.BAT, Rands.randInt(300), 8, 8));
        if (Rands.chance(2)) this.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.SPIDER, Rands.randInt(300), 4, 4));
        if (Rands.chance(2)) this.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.ZOMBIE, Rands.randInt(300), 4, 4));
        if (Rands.chance(2)) this.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.ZOMBIE_VILLAGER, Rands.randInt(300), 1, 1));
        if (Rands.chance(2)) this.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.SKELETON, Rands.randInt(300), 4, 4));
        if (Rands.chance(2)) this.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.CREEPER, Rands.randInt(300), 4, 4));
        if (Rands.chance(2)) this.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.SLIME, Rands.randInt(300), 4, 4));
        if (Rands.chance(2)) this.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.ENDERMAN, Rands.randInt(300), 1, 4));
        if (Rands.chance(2)) this.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.WITCH, Rands.randInt(300), 1, 1));
    }

    public static NormalTreeFeatureConfig getTreeConfig() {
        NormalTreeFeatureConfig config;
        int height = Rands.randIntRange(2, 24);
        int foliageHeight = Rands.randIntRange(1, 5);
        BlockState logState;
        BlockState leafState;
        int leafType = Rands.randInt(4);
        switch (leafType) {
            case 0:
                logState = Blocks.OAK_LOG.getDefaultState();
                leafState = Blocks.OAK_LEAVES.getDefaultState();
                break;
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

        switch (Rands.randInt(4)) {
            case 0:
                config = (new NormalTreeFeatureConfig.Builder(new SimpleStateProvider(logState), new SimpleStateProvider(leafState), new BlobFoliagePlacer(Rands.randIntRange(1, 3), 0)))
                        .method_23428(Rands.randIntRange(1, 6)) //
                        .method_23430(height - 1) //trunk height
                        .method_23437(foliageHeight) //foliage amount
                        .method_23438(Rands.randIntRange(1, 6)) //random foliage offset
                        .method_23439(Rands.randIntRange(0, 8)) //water depth
                        .method_23427()
                        .method_23429(decorators)
                        .method_23431();
            break;
            case 1:
                config = (new NormalTreeFeatureConfig.Builder(new SimpleStateProvider(logState), new SimpleStateProvider(leafState), new SpruceFoliagePlacer(Rands.randIntRange(1, 4), 0)))
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
                config = (new NormalTreeFeatureConfig.Builder(new SimpleStateProvider(logState), new SimpleStateProvider(leafState), new PineFoliagePlacer(Rands.randIntRange(1, 3), 0)))
                        .method_23428(Rands.randIntRange(1, 4))
                        .method_23430(height - 1)
                        .method_23435(Rands.randIntRange(1, 2))
                        .method_23437(foliageHeight)
                        .method_23438(Rands.randIntRange(1, 4))
                        .method_23439(Rands.randIntRange(0, 8)) //water depth
                        .method_23427()
                        .method_23429(decorators)
                        .method_23431();
            break;
            case 3:
                config = (new NormalTreeFeatureConfig.Builder(new SimpleStateProvider(logState), new SimpleStateProvider(leafState), new AcaciaFoliagePlacer(Rands.randIntRange(1, 4), 0)))
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
            default:
                config = (new NormalTreeFeatureConfig.Builder(new SimpleStateProvider(logState), new SimpleStateProvider(leafState), new BlobFoliagePlacer(Rands.randIntRange(1, 3), 0)))
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

    private static MegaTreeFeatureConfig getMegaTreeConfig() {
        MegaTreeFeatureConfig config;
        int height = Rands.randIntRange(10, 40);
        BlockState logState;
        BlockState leafState;
        int leafType = Rands.randInt(4);
        switch (leafType) {
            case 0:
                logState = Blocks.OAK_LOG.getDefaultState();
                leafState = Blocks.OAK_LEAVES.getDefaultState();
                break;
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
        config = (new MegaTreeFeatureConfig.class_4637(new SimpleStateProvider(logState), new SimpleStateProvider(leafState)))
                .method_23410(height-2).method_23412(height + 4).method_23411(decorators).method_23409();
        return config;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public int getSkyColor(float float_1) {
//        System.out.println(dimensionData.getSkyColor());
        if(dimensionData.getDimensionColorPallet().getSkyColor() != 0) {
            return dimensionData.getDimensionColorPallet().getSkyColor();
        } else {
            return Color.WHITE.getColor();
        }
//        return Color.WHITE.getColor();
    }

    @Override
    @Environment(EnvType.CLIENT)
    public int getFoliageColorAt(BlockPos blockPos_1) {
        return dimensionData.getDimensionColorPallet().getFoliageColor();
    }

    @Override
    @Environment(EnvType.CLIENT)
    public int getGrassColorAt(BlockPos blockPos_1) {
        return dimensionData.getDimensionColorPallet().getGrassColor();
    }

}
