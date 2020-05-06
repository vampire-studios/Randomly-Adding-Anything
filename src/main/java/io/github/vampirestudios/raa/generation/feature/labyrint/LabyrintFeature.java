package io.github.vampirestudios.raa.generation.feature.labyrint;

import com.mojang.datafixers.Dynamic;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.feature.AbstractTempleFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;

import java.util.function.Function;

public class LabyrintFeature extends AbstractTempleFeature<DefaultFeatureConfig> {
 
    public LabyrintFeature(Function<Dynamic<?>, ? extends DefaultFeatureConfig> config) {
        super(config);
    }

    @Override
    protected int getSeedModifier(ChunkGeneratorConfig chunkGeneratorConfig) {
        return 437583903;
    }

    @Override
    public StructureStartFactory getStructureStartFactory() {
        return LabyrintStructureStart::new;
    }
 
    @Override
    public String getName() {
        return "Labyrint";
    }
 
    @Override
    public int getRadius() {
        return 2;
    }

}