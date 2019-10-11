package io.github.vampirestudios.raa.generation.dimensions;

import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.generation.decorator.BiasedNoiseBasedDecoratorConfig;
import io.github.vampirestudios.raa.generation.dimensions.DimensionData;
import io.github.vampirestudios.raa.client.Color;
import io.github.vampirestudios.raa.utils.Rands;
import io.github.vampirestudios.raa.utils.Utils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.CountExtraChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;

public class CustomDimensionalBiome extends Biome {

    private DimensionData dimensionData;

    public CustomDimensionalBiome(DimensionData dimensionData) {
        super((new Biome.Settings()
                .configureSurfaceBuilder(Utils.random(Rands.randInt(100)), SurfaceBuilder.GRASS_CONFIG)
                .precipitation(Biome.Precipitation.RAIN)
                .category(Biome.Category.PLAINS)
                .depth(dimensionData.getBiomeData().getDepth())
                .scale(dimensionData.getBiomeData().getScale())
                .temperature(dimensionData.getBiomeData().getScale() > 1.3F ? -1.0F : dimensionData.getBiomeData().getTemperature())
                .downfall(dimensionData.getBiomeData().getDownfall())
                .waterColor(dimensionData.getBiomeData().getWaterColor())
                .waterFogColor(new Color(
                        Color.intToRgb(dimensionData.getBiomeData().getWaterColor())[0]/255 + 1,
                        Color.intToRgb(dimensionData.getBiomeData().getWaterColor())[1]/255 + 1,
                        Color.intToRgb(dimensionData.getBiomeData().getWaterColor())[2]/255 + 1).getColor())
                .parent(null)
        ));
        this.dimensionData = dimensionData;

//        this.addStructureFeature(Feature.VILLAGE, new VillageFeatureConfig("village/plains/town_centers", 6));
//        this.addStructureFeature(Feature.PILLAGER_OUTPOST, new PillagerOutpostFeatureConfig(0.004D));
        this.addStructureFeature(Feature.MINESHAFT, new MineshaftFeatureConfig(0.004D * Rands.randInt(4), MineshaftFeature.Type.NORMAL));
        this.addStructureFeature(Feature.STRONGHOLD, FeatureConfig.DEFAULT);
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
        int forestConfig = Rands.randInt(3);
        System.out.println(dimensionData.getName() + " : " + forestConfig);
        switch (forestConfig) {
            case 0: //33% chance of full forest, 33% chance of patchy forest, 33% of no forest
                this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Biome.configureFeature(Feature.NORMAL_TREE, FeatureConfig.DEFAULT, Decorator.COUNT_EXTRA_HEIGHTMAP, new CountExtraChanceDecoratorConfig(Rands.randInt(4), 0.1F, 1)));
                this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Biome.configureFeature(Feature.FANCY_TREE, FeatureConfig.DEFAULT, Decorator.COUNT_EXTRA_HEIGHTMAP, new CountExtraChanceDecoratorConfig(Rands.randInt(3), 0.02F, 1)));
                break;
            case 1:
                //Small, inbetween forests
                float chance = Rands.randInt(24) * 10F + 80F;
                this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Biome.configureFeature(Feature.NORMAL_TREE, FeatureConfig.DEFAULT, RandomlyAddingAnything.DECORATOR, new BiasedNoiseBasedDecoratorConfig(Rands.randInt(20), chance, 0.0D, Heightmap.Type.WORLD_SURFACE_WG)));
                this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Biome.configureFeature(Feature.FANCY_TREE, FeatureConfig.DEFAULT, RandomlyAddingAnything.DECORATOR, new BiasedNoiseBasedDecoratorConfig(Rands.randInt(4), chance, 0.0D, Heightmap.Type.WORLD_SURFACE_WG)));
                //Large, thinner forests
                float chance2 = Rands.randInt(12) * 10F + 120F;
                this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Biome.configureFeature(Feature.NORMAL_TREE, FeatureConfig.DEFAULT, RandomlyAddingAnything.DECORATOR, new BiasedNoiseBasedDecoratorConfig(Rands.randInt(10), chance2, 0.0D, Heightmap.Type.WORLD_SURFACE_WG)));
                this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Biome.configureFeature(Feature.FANCY_TREE, FeatureConfig.DEFAULT, RandomlyAddingAnything.DECORATOR, new BiasedNoiseBasedDecoratorConfig(Rands.randInt(2), chance2, 0.0D, Heightmap.Type.WORLD_SURFACE_WG)));
                break;
            case 2:
                DefaultBiomeFeatures.addPlainsFeatures(this);
                break;
        }
//      this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Biome.configureFeature(RandomlyAddingAnything.TEST_FEATURE, FeatureConfig.DEFAULT, Decorator.TOP_SOLID_HEIGHTMAP, new NopeDecoratorConfig()));
        if(Rands.chance(8))
            DefaultBiomeFeatures.addMossyRocks(this);
        if(Rands.chance(20))
            DefaultBiomeFeatures.addGiantSpruceTaigaTrees(this);
        if(Rands.chance(10))
            DefaultBiomeFeatures.addIcebergs(this);
        if(Rands.chance(8))
            DefaultBiomeFeatures.addTaigaTrees(this);
        if(Rands.chance(10) && dimensionData.getBiomeData().getScale() > 1.0F)
            DefaultBiomeFeatures.addMountainTrees(this);
        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Biome.configureFeature(Feature.NORMAL_TREE, FeatureConfig.DEFAULT, Decorator.COUNT_EXTRA_HEIGHTMAP, new CountExtraChanceDecoratorConfig(Rands.randInt(4), 0.1F, 1)));


        DefaultBiomeFeatures.addDefaultMushrooms(this);
        DefaultBiomeFeatures.addDefaultVegetation(this);
        DefaultBiomeFeatures.addSprings(this);
        DefaultBiomeFeatures.addFrozenTopLayer(this);

        if (Rands.chance(2)) this.addSpawn(EntityCategory.CREATURE, new Biome.SpawnEntry(EntityType.SHEEP, Rands.randInt(300), 4, 4));
        if (Rands.chance(2)) this.addSpawn(EntityCategory.CREATURE, new Biome.SpawnEntry(EntityType.PIG, Rands.randInt(300), 4, 4));
        if (Rands.chance(2)) this.addSpawn(EntityCategory.CREATURE, new Biome.SpawnEntry(EntityType.CHICKEN, Rands.randInt(300), 4, 4));
        if (Rands.chance(2)) this.addSpawn(EntityCategory.CREATURE, new Biome.SpawnEntry(EntityType.COW, Rands.randInt(300), 4, 4));
        if (Rands.chance(2)) this.addSpawn(EntityCategory.CREATURE, new Biome.SpawnEntry(EntityType.HORSE, Rands.randInt(300), 2, 6));
        if (Rands.chance(2)) this.addSpawn(EntityCategory.CREATURE, new Biome.SpawnEntry(EntityType.DONKEY, Rands.randInt(300),  1, 3));
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

    @Override
    @Environment(EnvType.CLIENT)
    public int getSkyColor(float float_1) {
        System.out.println(dimensionData.getSkyColor());
        if(dimensionData.getSkyColor() != 0) {
            return dimensionData.getSkyColor();
        } else {
            return Color.WHITE.getColor();
        }
    }

    @Override
    @Environment(EnvType.CLIENT)
    public int getFoliageColorAt(BlockPos blockPos_1) {
        return dimensionData.getFoliageColor();
    }

    @Override
    @Environment(EnvType.CLIENT)
    public int getGrassColorAt(BlockPos blockPos_1) {
        return dimensionData.getGrassColor();
    }

}