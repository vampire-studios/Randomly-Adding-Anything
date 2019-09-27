package io.github.vampirestudios.raa.init;

import io.github.vampirestudios.raa.generation.dimensions.DimensionData;
import io.github.vampirestudios.raa.utils.Rands;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.CountExtraChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;

public class CustomDimensionBiome extends Biome {

    private DimensionData dimension;

    public CustomDimensionBiome(DimensionData dimension) {
        super((new Biome.Settings()).configureSurfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG).precipitation(Biome.Precipitation.RAIN).category(Biome.Category.PLAINS).depth(Rands.randFloat(2F)).scale(Rands.randFloat(1.5F)).temperature(Rands.randFloat(1F)).downfall(Rands.randFloat(1F)).waterColor(4159204).waterFogColor(329011).parent((String)null));
        this.dimension = dimension;

        //this.addStructureFeature(Feature.VILLAGE, new VillageFeatureConfig("village/plains/town_centers", 6));
        //this.addStructureFeature(Feature.PILLAGER_OUTPOST, new PillagerOutpostFeatureConfig(0.004D));
        this.addStructureFeature(Feature.MINESHAFT, new MineshaftFeatureConfig(0.004D*Rands.randInt(4), MineshaftFeature.Type.NORMAL));
        //this.addStructureFeature(Feature.STRONGHOLD, FeatureConfig.DEFAULT);
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
        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Biome.configureFeature(Feature.NORMAL_TREE, FeatureConfig.DEFAULT, Decorator.COUNT_EXTRA_HEIGHTMAP, new CountExtraChanceDecoratorConfig(Rands.randInt(4), 0.1F, 1)));
        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Biome.configureFeature(Feature.FANCY_TREE, FeatureConfig.DEFAULT, Decorator.COUNT_EXTRA_HEIGHTMAP, new CountExtraChanceDecoratorConfig(Rands.randInt(3), 0.02F, 1)));
        DefaultBiomeFeatures.addPlainsFeatures(this);
        DefaultBiomeFeatures.addDefaultMushrooms(this);
        DefaultBiomeFeatures.addDefaultVegetation(this);
        DefaultBiomeFeatures.addSprings(this);
        DefaultBiomeFeatures.addFrozenTopLayer(this);

        if (Rands.chance(2)) this.addSpawn(EntityCategory.CREATURE, new Biome.SpawnEntry(EntityType.SHEEP, 12, 4, 4));
        if (Rands.chance(2)) this.addSpawn(EntityCategory.CREATURE, new Biome.SpawnEntry(EntityType.PIG, 10, 4, 4));
        if (Rands.chance(2)) this.addSpawn(EntityCategory.CREATURE, new Biome.SpawnEntry(EntityType.CHICKEN, 10, 4, 4));
        if (Rands.chance(2)) this.addSpawn(EntityCategory.CREATURE, new Biome.SpawnEntry(EntityType.COW, 8, 4, 4));
        if (Rands.chance(2)) this.addSpawn(EntityCategory.CREATURE, new Biome.SpawnEntry(EntityType.HORSE, 5, 2, 6));
        if (Rands.chance(2)) this.addSpawn(EntityCategory.CREATURE, new Biome.SpawnEntry(EntityType.DONKEY, 1, 1, 3));
        if (Rands.chance(2)) this.addSpawn(EntityCategory.AMBIENT, new Biome.SpawnEntry(EntityType.BAT, 10, 8, 8));
        if (Rands.chance(2)) this.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.SPIDER, 100, 4, 4));
        if (Rands.chance(2)) this.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.ZOMBIE, 95, 4, 4));
        if (Rands.chance(2)) this.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.ZOMBIE_VILLAGER, 5, 1, 1));
        if (Rands.chance(2)) this.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.SKELETON, 100, 4, 4));
        if (Rands.chance(2)) this.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.CREEPER, 100, 4, 4));
        if (Rands.chance(2)) this.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.SLIME, 100, 4, 4));
        if (Rands.chance(2)) this.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.ENDERMAN, 10, 1, 4));
        if (Rands.chance(2)) this.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.WITCH, 5, 1, 1));
    }

    @Override
    public int getSkyColor(float float_1) {
        return dimension.getSkyColor();
    }

    @Override
    public int getFoliageColorAt(BlockPos blockPos_1) {
        return dimension.getFoliageColor();
    }

    @Override
    public int getGrassColorAt(BlockPos blockPos_1) {
        return dimension.getGrassColor();
    }

}