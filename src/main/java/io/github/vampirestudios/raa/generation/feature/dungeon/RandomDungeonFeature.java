package io.github.vampirestudios.raa.generation.feature.dungeon;

import com.mojang.datafixers.Dynamic;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.feature.AbstractTempleFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

import java.util.function.Function;
 
public class RandomDungeonFeature extends AbstractTempleFeature<DefaultFeatureConfig> {
 
    public RandomDungeonFeature(Function<Dynamic<?>, ? extends DefaultFeatureConfig> config) {
        super(config);
    }

    @Override
    protected int getSeedModifier(ChunkGeneratorConfig chunkGeneratorConfig) {
        return 437583903;
    }

    @Override
    public StructureFeature.StructureStartFactory getStructureStartFactory() {
        return RandomDungeonStructureStart::new;
    }
 
    @Override
    public String getName() {
        return "Dungeon";
    }
 
    @Override
    public int getRadius() {
        return 2;
    }

}